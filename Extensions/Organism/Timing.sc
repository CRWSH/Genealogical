Timing : Controller {
	// characteristics

	var <>deltaList, <>deltaListRel, <>deltaListRelLong, <>delta, <>dur, <>attack, <>release, <>sustain;

	//Ugen dinges

	var <>pBinds, <>pPar, <>gateGrp, <>onePbind, <>onePpar;

	//class ranges

	classvar <>durMin, <>durMax, <>deltaMax, <>totalDur, maxListLength = 70;

	*new
	{ arg gategrp;
		^super.new.initTiming(gategrp);
	}

	*init
	{
		totalDur = 10.0;
		durMin = 0.1;
		durMax = 9.5;
		deltaMax = 10.0;
	}

	initTiming
	{
		arg gategrp;
		var counteddur;

		pBinds = List.newClear;
		onePbind = List.newClear;

		gateGrp = gategrp;


		deltaList = List.newClear();

		//"before building list".postln;



		while({deltaList.size < 2}, {
		counteddur = 0.0;

		dur = Parameter(false, min: durMin, max: durMax);
		delta= Parameter(false, min: dur.map(), max: deltaMax);

		deltaList = List.newClear();
		deltaListRel = List.newClear();
		deltaListRelLong = List.newClear();

		counteddur = counteddur + delta.map();

		maxListLength.do({
			arg i;
			delta.newRandomValue;
			counteddur = counteddur + delta.map();
			if(counteddur < totalDur, {
				deltaListRel.add(delta.currentValue);
				deltaListRelLong.add(delta.currentValue);
				deltaList.add(delta.map());
				},
				{
				deltaListRelLong.add(delta.currentValue);

				});
			});

		});


	}

	//build van timing altijd na builds van envelopes

	build
	{
		arg envelope;
		var type;

		type = Envelope.types.at(envelope.type);

		if(deltaList.size != 0, {
			//"type: %".format(type).postln;
			case
			{type == \adsr}
			{
				var attack, decay, sustain, release, level;
				attack = Envelope.attacks.at(envelope.type);
				decay = Envelope.decays.at(envelope.type);
				release = Envelope.releases.at(envelope.type);
				level = Envelope.levels.at(envelope.type);
				sustain = envelope.length - (attack + decay + release);
				if(sustain <= 0, {sustain = 0.001});

				//"attack % decay % sustain % release % level %".format(attack, decay, sustain, release, level) .postln;
				//"bus, min, max, gategrp: % \n % \n % \n %".format(envelope.busArray.asArray,  envelope.minArray.asArray, envelope.maxArray.asArray, envelope.gateArray.asArray).postln;

				pBinds.add(Pbind(\instrument, \pbindadsr,
				\group, gateGrp,
				\outbus, envelope.busArray.asArray,
				\dur, Pseq(deltaList.asArray, 1),
				\attack, attack,
				\decay, decay,
				\sustain, sustain,
				\level, level,
				\release, release,
				\min, envelope.minArray.asArray,
				\max, envelope.maxArray.asArray,
					\amp, Pseq([0,Pseq([1],inf)],1)));

				onePbind.add(Pbind(\instrument, \pbindadsr,
				\group, gateGrp,
				\outbus, envelope.busArray.asArray,
				\dur, Pseq([2],1),
				\attack, attack,
				\decay, decay,
				\sustain, sustain,
				\level, level,
				\release, release,
				\min, envelope.minArray.asArray,
				\max, envelope.maxArray.asArray,
					\amp, 1));
			}
			{type == \lin}
			{
				var attack, sustain, release;
				if(Envelope.attacks.at(envelope.type) == \var, {
					attack = envelope.length - 0.201;
					if(attack <= 0, {attack = 0.001});
					},{
					attack = Envelope.attacks.at(envelope.type);
				});
				release = Envelope.releases.at(envelope.type);
				if(Envelope.sustains.at(envelope.type) == \var, {
					sustain = envelope.length - (attack + release);
					if(sustain <= 0, {sustain = 0.001});
				},
				{
					sustain = Envelope.sustains.at(envelope.type);
				});
				//"attack % sustain % release % ".format(attack, sustain, release).postln;
				//"bus, min, max, gategrp:: % \n % \n % \n %".format(envelope.busArray.asArray,  envelope.minArray.asArray, envelope.maxArray.asArray, envelope.gateArray.asArray).postln;

				pBinds.add(Pbind(\instrument, \pbindlin,
				\group, gateGrp,
				\outbus, envelope.busArray.asArray,
				\dur, Pseq(deltaList.asArray, 1),
				\attack, attack,
				\sustain, sustain,
				\release, release,
				\min, envelope.minArray.asArray,
				\max, envelope.maxArray.asArray,
					\amp, Pseq([0,Pseq([1],inf)],1)));

				onePbind.add(Pbind(\instrument, \pbindlin,
				\group, gateGrp,
				\outbus, envelope.busArray.asArray,
				\dur, Pseq([2],1),
				\attack, attack,
				\sustain, sustain,
				\release, release,
				\min, envelope.minArray.asArray,
				\max, envelope.maxArray.asArray,
						\amp, 1));
			}
		});
		if(pBinds.size >0, {
			pPar = Ppar(pBinds.asArray);
			onePpar = Ppar(onePbind.asArray);
		});

	}

	play
	{
		if(deltaList.size > 1, {
				pPar.reset;
				pPar.play;
		});
	}

	onePlay
	{
		if(deltaList.size > 1, {
				onePpar.reset;
				onePpar.play;
		});

	}

	free
	{
		dur.free;
		delta.free;
	}

	fillLists
	{
		var counteddur = 0.0;

		deltaList = List.newClear;
		deltaListRel = List.newClear;

		deltaListRelLong.size.do({
				arg i;
			    delta.currentValue = deltaListRelLong.at(i);
			    counteddur = counteddur + delta.map();
				if (counteddur < totalDur, {
					deltaListRel.add(delta.currentValue);
					deltaList.add(delta.map());
				});
			});
	}


	mutate
	{
		var whichmutation;
		whichmutation = [\envelope, \timing].wchoose([1.0/4.0, 3.0/4.0]);
		case
		{whichmutation == \envelope}
		{
			dur.newRandomValue();

			attack= 0.015;
			sustain = 0.030;
			release = dur.map()-(attack + sustain);
			delta= Parameter(false, min: dur.map(), max: deltaMax);

			// fill deltalist & deltalisrel until long enough;

			this.fillLists;


		}
		{whichmutation == \timing}
		{
			var changeindex, counteddur = 0.0;
			delta.newRandomValue;

			changeindex = deltaList.size.rand;

			deltaListRelLong.put(changeindex, delta.currentValue);

			// fill deltalist & deltalisrel until long enough;

			this.fillLists;


		};
	}

	crossover
	{
		arg othertiming, gategrp;
		var childtiming, firstposition, crossoverpoint, counteddur = 0;

		childtiming = othertiming.deepCopy;
		childtiming.pBinds = List.newClear;
		childtiming.onePbind = List.newClear;

		//"After deepCopy \n timing.deltaList: \n % \n othertiming.deltaList: \n %".format(deltaList, othertiming.deltaList).postln;

		firstposition = [\timing, \othertiming].choose;

		case
		{ firstposition == \timing}
		{
			childtiming.dur = dur.deepCopy;
			childtiming.delta = delta.deepCopy;
			childtiming.attack = attack;
			childtiming.sustain = sustain;
			childtiming.release = release;

			childtiming.deltaListRelLong = List.newClear;

			if(deltaListRel.size < othertiming.deltaListRel.size,
				{
					crossoverpoint = (deltaListRel.size -1).rand;
				},
				{
					crossoverpoint = (othertiming.deltaListRel.size -1).rand;
			});

			//making new deltaListLong

			deltaListRelLong.size.do({
				arg i;
				if(i <= crossoverpoint,
					{
						childtiming.deltaListRelLong.add(deltaListRelLong.at(i));
					},
					{
						childtiming.deltaListRelLong.add(othertiming.deltaListRelLong.at(i));
				});
			});


		}
		{firstposition == \othertiming}
		{

			childtiming.dur = othertiming.dur.deepCopy;
			childtiming.delta = othertiming.delta.deepCopy;
			childtiming.attack = othertiming.attack;
			childtiming.sustain = othertiming.sustain;
			childtiming.release = othertiming.release;

			childtiming.deltaListRelLong = List.newClear;

			if(deltaListRel.size < othertiming.deltaListRel.size,
				{
					crossoverpoint = (deltaListRel.size -1).rand;
				},
				{
					crossoverpoint = (othertiming.deltaListRel.size -1).rand;
			});


			deltaListRelLong.size.do({
				arg i;
				if(i <= crossoverpoint,
					{
						childtiming.deltaListRelLong.add(othertiming.deltaListRelLong.at(i));
					},
					{
						childtiming.deltaListRelLong.add(deltaListRelLong.at(i));
				});
			});

		};

		//"After crossover \n timing.deltaList: \n % \n othertiming.deltaList: \n %".format(deltaList, othertiming.deltaList).postln;

		//make short enough

		childtiming.fillLists;

		//change group

		childtiming.gateGrp = gategrp;


		^childtiming
	}

	updateChar
	{
		var counteddur = 0.0;
		//dur.free;
		//delta.free;
		dur = Parameter(min: durMin, max: durMax, curval: dur.currentValue);

		//attack= 0.015;
		//sustain = 0.030;
		//release = dur.map()-(attack + sustain);

		delta = Parameter(min: dur.map(), max: deltaMax);


		this.fillLists;

		//this.build;
	}

	calAccumDelta

	{
		var accumDelta = List.newClear, counteddur =0.0;

		(deltaList - 1).size.do({
			arg i;
			counteddur = counteddur + deltaList.at(i);
			accumDelta.add(counteddur);
		});

		^accumDelta.asArray;
	}








}




