Envelope : Controller {
	// characteristics

	var <>length, <>type, <>min, <>max, <>ampOrFx, <>allBuild;

	//arrays of all

	var <>gateArray, <>busArray, <>minArray, <>maxArray;

	//Ugen dinges

	var <>envSynth;

	classvar <>envDic, <>ampEnvs, <>fxEnvs, <>attacks, <>sustains, <>releases, <>decays, <>types, <>levels, <>lengths ;

	*new
	{ arg lengtharg, typearg;
		^super.new.initEnvelope(lengtharg, typearg);
	}

	*init
	{
		envDic = [\impulse, \sharp, \smooth, \oneup, \slow, \sharpsus, \atmos];
		ampEnvs = [\impulse, \sharp, \slow, \sharpsus, \atmos];
		fxEnvs = [\impulse, \smooth,\sharpsus, \slow ,\oneup];


		attacks = Dictionary(1);
		attacks.add(\impulse -> 0.001);
		attacks.add(\sharp -> 0.005);
		attacks.add(\smooth -> 0.050);
		attacks.add(\oneup -> 0.050);
		attacks.add(\slow -> \var);
		attacks.add(\sharpsus -> 0.005);
		attacks.add(\atmos -> 1.0);

		releases = Dictionary(1);
		releases.add(\impulse -> 0.010);
		releases.add(\sharp -> 0.070);
		releases.add(\smooth -> 0.055);
		releases.add(\oneup -> 0.001);
		releases.add(\slow -> 0.2);
		releases.add(\sharpsus -> 0.015);
		releases.add(\atmos -> 5.0);

		sustains = Dictionary(1);
		sustains.add(\impulse -> 0.001);
		sustains.add(\sharp -> 0.001);
		sustains.add(\smooth -> 0.001);
		sustains.add(\oneup -> \var);
		sustains.add(\slow -> 0.001);
		sustains.add(\sharpsus -> \var);
		sustains.add(\atmos -> 0.001);

		decays = Dictionary(1);
		decays.add(\sharpsus -> 0.5);

		levels = Dictionary(1);
		levels.add(\sharpsus -> 0.1);

		types = Dictionary(1);
		types.add(\impulse -> \lin);
		types.add(\sharp -> \lin);
		types.add(\smooth -> \lin);
		types.add(\oneup -> \lin);
		types.add(\slow -> \lin);
		types.add(\sharpsus -> \adsr);
		types.add(\atmos -> \lin);

		lengths = Dictionary(1);
		lengths.add(\impulse -> 0.012);
		lengths.add(\sharp -> 0.076);
		lengths.add(\smooth -> 0.106);
		lengths.add(\oneup -> 0.052);
		lengths.add(\slow -> 0.201);
		lengths.add(\sharpsus -> 0.521);
		lengths.add(\atmos -> 6.001);


		SynthDef(\pbindadsr, {
			arg outbus, attack = 0.01 , sustain = 0.05, release = 0.05, amp = 1.0, level, decay, min = 0.0, max = 1.0;
			var env, mul;

			mul = max - min;
			env = (mul*amp*EnvGen.kr(Env.new([0,1,level,level,0],[attack, decay, sustain, release]),doneAction: 2))+ min;
			Out.kr(outbus, env)
		}).add;

		SynthDef(\pbindlin, {
			arg outbus, attack = 0.01 , sustain = 0.05, release = 0.05, amp = 1.0, min = 0, max = 1;
			var env, mul;

			mul = max - min;
			env = (mul*EnvGen.kr(Env.linen(attack,sustain,release, amp ,curve: -4),doneAction: 2)) + min;
			Out.kr(outbus, env)
		}).add;





		SynthDef(\adsrgate, {
			arg outbus, attack = 0.01 , sustain = 0.05, release = 0.05, amp = 1.0, level, decay, min = 0.0, max = 1.0, gate = 0.0;
			var env, mul;

			mul = max - min;
			env = (mul*amp*EnvGen.kr(Env.new([0,1,level,level,0],[attack, decay, sustain, release]),gate ))+ min;
			Out.kr(outbus, env)
		}).add;

		SynthDef(\lingate, {
			arg outbus, attack = 0.01 , sustain = 0.05, release = 0.05, amp = 1.0, min = 0, max = 1, gate = 0;
			var env, mul;

			mul = max - min;
			env = (mul*EnvGen.kr(Env.linen(attack,sustain,release, amp ,curve: -4),gate)) + min;
			Out.kr(outbus, env)
		}).add;


	}

	*addAmpEnv
	{arg env;
		ampEnvs.add(env);
	}

	*delAmpEnv
	{arg env;
		ampEnvs.removeAt(ampEnvs.indexOf(env));
	}

	initEnvelope
	{
		arg lengtharg, typearg;

		busArray = List.newClear;
		minArray = List.newClear;
		maxArray = List.newClear;
		gateArray = List.newClear;

		allBuild = false;

		ampOrFx = typearg;
		length = lengtharg;
		case
		{ampOrFx == \amp}
		{
			type = ampEnvs.choose;
			while({lengths.at(type) > length},{type = ampEnvs.choose});
			min = 0.0;
			max = 1.0.rand;
		}
		{ampOrFx == \fx}
		{
			type = fxEnvs.choose;
			while({lengths.at(type) > length},{type = fxEnvs.choose});
			min = 1.0.rand;
			max = 1.0.rand;
		};

		envSynth = List.newClear;

		// bepalen wat de range is waarin het beweegt
		//"initEnvelope".postln;
		//gateGrp.postln;
	}

	updateChar {
		arg lengtharg;

		length = lengtharg;

		case
		{ampOrFx == \amp}
		{
			while({lengths.at(type) > length},{type = ampEnvs.choose});
		}
		{ampOrFx == \fx}
		{
			while({lengths.at(type) > length},{type = fxEnvs.choose});
		};
	}


	build
	{
		arg parameter, gategrp;
		var outputControlBus, minmap, maxmap;


		outputControlBus = parameter.connection;
		minmap = parameter.specInstance.map(min);
		maxmap = parameter.specInstance.map(max);



		busArray.add(outputControlBus);
		minArray.add(minmap);
		maxArray.add(maxmap);
		gateArray.add(gategrp);


/*

		case
		{ types.at(type) == \adsr}
		{
			var attack, decay, sustain, release, level;
			attack = attacks.at(type);
			decay = decays.at(type);
			release = releases.at(type);
			level = levels.at(type);
			sustain = length - (attack + decay + release);
			if(sustain <= 0, {sustain = 0.001});
			//"attack % decay % sustain % release % level % output % maxmap %".format(attack, decay, sustain, release, level, outputControlBus, maxmap) .postln;
			envSynth.add(Synth(\adsrgate,[\attack, attack, \decay, decay, \sustain, sustain, \release, release, \level, level, \outbus, outputControlBus, \min, minmap, \max, maxmap], gategrp));
		}
		{types.at(type) == \lin}
		{
			var attack, sustain, release;
			attack = attacks.at(type);
			release = releases.at(type);
			if(sustains.at(type) == \var, {
				sustain = length - (attack + release);
				if(sustain <= 0, {sustain = 0.001});
				},
			{
				sustain = sustains.at(type);
			});
		    //"attack % sustain % release % output % maxmap %".format(attack, sustain, release, outputControlBus, maxmap).postln;
			envSynth.add(Synth(\lingate,[\attack, attack,\sustain, sustain, \release, release, \outbus, outputControlBus, \min, minmap, \max, maxmap], gategrp));
		};

*/
	}

	freeUGens
	{
		/*
		envSynth.size.do({
			arg i;
			envSynth.at(i).free;
		});

		envSynth = List.newClear;
		*/


	}

	free
	{
		/*
		envSynth.size.do({
			arg i;
			envSynth.at(i).free;
		});

		envSynth = List.newClear;
		*/


	}

	mutate
	{
		case
		{ampOrFx == \amp}
		{
			type = ampEnvs.choose;
			this.checkType;
			min = 0.0;
			max = 1.0.rand;
		}
		{ampOrFx == \fx}
		{
			type = fxEnvs.choose;
			this.checkType;
			min = 1.0.rand;
			max = 1.0.rand;
		};
	}

	crossover
	{
		arg otherenvelope, childlength;
		var childenvelope;

		childenvelope = otherenvelope.deepCopy;

		childenvelope.busArray = List.newClear;
		childenvelope.minArray = List.newClear;
		childenvelope.maxArray = List.newClear;
		childenvelope.gateArray = List.newClear;
		childenvelope.length = childlength;
		childenvelope.allBuild = false;

		case
		{ampOrFx == \amp}
		{
			if(0.5.coin,
			{
				childenvelope.max = this.max;
				childenvelope.type = otherenvelope.type;
			},{
				childenvelope.max = otherenvelope.max;
				childenvelope.type = this.type;

			});
		}
		{ampOrFx == \fx}
		{
			childenvelope.min = [this.min, otherenvelope.min].choose;
			childenvelope.max = [this.max, otherenvelope.max].choose;
			childenvelope.type = [this.type, otherenvelope.type].choose;


		};

		childenvelope.checkType;

		^childenvelope;

	}



	checkType
	{
		case
		{ampOrFx == \amp}
		{
			while({lengths.at(type) > length},{type = ampEnvs.choose});
		}
		{ampOrFx == \fx}
		{
			while({lengths.at(type) > length},{type = fxEnvs.choose});

		};

	}




}




