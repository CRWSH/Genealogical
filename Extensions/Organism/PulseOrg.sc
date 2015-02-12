PulseOrg{

	var <>triSynth;

	// ugen stuff voor de synth the builden

	var <gateGrp, <>synthGrp, <fxGrp, <>fxGateGrp, <>audioConnect, <>globalEnv, <>ampEnvs, <>fxEnvs, <>globalAmpEnv,  <>conv, <> timing, <> envelopesBuild;

	//eigenschappen van structuur

	classvar aantalMogelijkeSynths = 3, <>numberAmpEnvs = 2, <>numberFxEnvs = 7, <>possibleMut;

	//GA hulpjes

	var <>aantalSynths;

	//so many parameters by creation is impossible to give in arguments hence standard a random creation
	//an array of 3 is created. The elements can be nothing or an TriPulseOsc

	*new
	{ arg trisynth, fxgrp, fxgategrp, synthgrp, gategrp;
		^super.new.initPulseOrg(trisynth,fxgrp, fxgategrp, synthgrp, gategrp);
	}

	*init
	{
		possibleMut = List.newClear;

		SynthDef( \sineperc, {
			var env;
			env = EnvGen.ar(Env.perc(releaseTime: 0.1),levelScale: 0.1 ,doneAction: 2);
			Out.ar([0,1], 0.1*SinOsc.ar(440)*env)
		}).add;

	}

	*addMutation
	{arg mutation;
		possibleMut.add(mutation);
	}

	*delMutation
	{arg mutation;
		possibleMut.removeAt(possibleMut.indexOf(mutation));
	}

	initPulseOrg
	{arg trisynth, fxgrp, fxgategrp, synthgrp, gategrp;

		envelopesBuild = false;

		//which convolution will be present

		conv = Convolve.posConv.choose;



		// aanmaken groepen

		fxGrp = fxgrp;
		fxGateGrp = fxgategrp;
		synthGrp = synthgrp;
		gateGrp = gategrp;
		//"PulseOrg".postln;
		//gateGrp.postln;

		//aanmaken timing

		//"before timing".postln;

		timing = Timing(gateGrp);

		//"duration".postln;
		//timing.dur.map().postln;

		//aanmaken Envelopes

		globalEnv = Envelope(timing.dur.map(), \amp);

		ampEnvs = List.newClear;

		numberAmpEnvs.do({arg i;
			ampEnvs.add(Envelope(timing.dur.map(), \amp))
		});

		fxEnvs = List.newClear;

		numberFxEnvs.do({arg i;
			fxEnvs.add(Envelope(timing.dur.map(), \fx))
		});





		// aanmaken synths

		if(trisynth == nil,
			{
				triSynth = List.newClear(aantalMogelijkeSynths);
				/*
				triSynth.put(aantalMogelijkeSynths.rand,TriPulseOsc(audioConnect,synthgrp: synthGrp));
				aantalSynths = 1;
				*/
				triSynth.put(0,TriPulseOsc(synthgrp: synthGrp));
				triSynth.put(1,TriPulseOsc(synthgrp: synthGrp));
				triSynth.put(2,TriPulseOsc(synthgrp: synthGrp));
				aantalSynths = 3;


			},
			{  //veranderen van alle bussen en groupen gerelateerd met

				triSynth = trisynth;
				aantalSynths = 0;
				triSynth.size.do({arg i;
					if(triSynth.at(i) != nil, {
						aantalSynths = aantalSynths + 1;
						//triSynth.at(i).outAudioBus = audioConnect;
						triSynth.at(i).synthGrp = synthGrp;
						/*
						triSynth.at(i).lowOctave.changeControlBus;
						triSynth.at(i).highOctave.changeControlBus;
						triSynth.at(i).midFreq.changeControlBus;
						triSynth.at(i).lowFineTune.changeControlBus;
						triSynth.at(i).highFineTune.changeControlBus;
						triSynth.at(i).lowAmp.changeControlBus;
						triSynth.at(i).midAmp.changeControlBus;
						triSynth.at(i).highAmp.changeControlBus;
						triSynth.at(i).widthLow.changeControlBus;
						triSynth.at(i).widthMid.changeControlBus;
						triSynth.at(i).widthHigh.changeControlBus;
						*/
					});
				});

		});



		// aanmaken fx

		globalAmpEnv = AmpEnv(fxgrp: fxGrp); //eerst nog niet verbinden met 0 pas bij gateIt




	}

	initAfterSave
	{arg trisynth, fxgrp, fxgategrp, synthgrp, gategrp;
		//the most important thing that has to change is the groups

		fxGrp = fxgrp;
		fxGateGrp = fxgategrp;
		synthGrp = synthgrp;
		gateGrp = gategrp;

		triSynth = trisynth;
		aantalSynths = 0;
		triSynth.size.do({arg i;
				if(triSynth.at(i) != nil, {
					aantalSynths = aantalSynths + 1;
					//triSynth.at(i).outAudioBus = audioConnect;
					triSynth.at(i).synthGrp = synthGrp;
				});
		});

		globalAmpEnv.fxGrp = fxGrp;
		globalAmpEnv.fitAmp = 1;

		timing.gateGrp = gateGrp;
	}

	copyOtherBusGroup
	{
		var copypulseorg, trisynth = List.newClear, copytiming, copyconv, copyglobalenv, copyenv;

		// control busses of triSynth

		triSynth.size.do({arg i;
			if(triSynth.at(i) != nil, {
				var lowoctave, highoctave, midfreq, lowfinetune, highfinetune, lowamp, midamp, highamp, widthlow, widthmid, widthhigh;
				lowoctave = triSynth.at(i).lowOctave.currentValue;
				highoctave = triSynth.at(i).highOctave.currentValue;
				midfreq = triSynth.at(i).midFreq.currentValue;
				lowfinetune = triSynth.at(i).lowFineTune.currentValue;
				highfinetune = triSynth.at(i).highFineTune.currentValue;
				lowamp = triSynth.at(i).lowAmp.currentValue;
				midamp = triSynth.at(i).midAmp.currentValue;
				highamp = triSynth.at(i).highAmp.currentValue;
				widthlow = triSynth.at(i).widthLow.currentValue;
				widthmid = triSynth.at(i).widthMid.currentValue;
				widthhigh = triSynth.at(i).widthHigh.currentValue;

				trisynth.add(TriPulseOsc(lowoctave: lowoctave, highoctave: highoctave,midfreq: midfreq,lowfinetune: lowfinetune,highfinetune: highfinetune,lowamp: lowamp,midamp: midamp,highamp: highamp,widthlow: widthlow,widthmid: widthmid,widthhigh: widthhigh));

				},{
					trisynth.add(nil);
			});
		});

		copypulseorg = PulseOrg(trisynth, fxGrp, fxGateGrp,synthGrp, gateGrp);

		// controls van TriPulseOsc

		copypulseorg.triSynth.size.do({ arg i;
			if(triSynth.at(i) != nil, {
				copypulseorg.triSynth.at(i).controls = triSynth.at(i).controls.deepCopy;
			});
		});

		// store conv

		copyconv = conv.deepCopy;
		copypulseorg.conv = copyconv;

		// store timing

		copytiming = timing.deepCopy;
		copytiming.gateGrp = copypulseorg.gateGrp;
		copypulseorg.timing = copytiming;


		//envelopes

		//globalenv

		copyglobalenv = globalEnv.deepCopy;
		copyglobalenv.busArray = List.newClear;
		copyglobalenv.minArray = List.newClear;
		copyglobalenv.maxArray = List.newClear;
		copyglobalenv.gateArray = List.newClear;
		copypulseorg.globalEnv = copyglobalenv;

		//ampenvs

		ampEnvs.size.do({arg i;
			copyenv = ampEnvs.at(i).deepCopy;
			copyenv.busArray = List.newClear;
			copyenv.minArray = List.newClear;
			copyenv.maxArray = List.newClear;
			copyenv.gateArray = List.newClear;
			copypulseorg.ampEnvs.put(i,copyenv.deepCopy);
		});

		//fxenvs

		fxEnvs.size.do({arg i;
			copyenv = fxEnvs.at(i).deepCopy;
			copyenv.busArray = List.newClear;
			copyenv.minArray = List.newClear;
			copyenv.maxArray = List.newClear;
			copyenv.gateArray = List.newClear;
			copypulseorg.fxEnvs.put(i,copyenv.deepCopy);
		});








		^copypulseorg
	}

	reInitTriSynth
	{
		aantalSynths = 0;
		triSynth.size.do({arg i;
					if(triSynth.at(i) != nil, {
						aantalSynths = aantalSynths + 1;
						//triSynth.at(i).outAudioBus = audioConnect;
						triSynth.at(i).synthGrp = synthGrp;
					});
			});
	}

	updateChar
	{
		timing.updateChar;

		globalEnv.updateChar(timing.dur.map);

		ampEnvs.size.do({arg i;
			ampEnvs.at(i).updateChar(timing.dur.map);
		});

		fxEnvs.size.do({arg i;
			fxEnvs.at(i).updateChar(timing.dur.map);
		});
	}




	buildParameters
	{
		//aanmaken bussen

		audioConnect = Bus.audio;



		triSynth.size.do({arg i;
			if(triSynth.at(i) != nil, {
				triSynth.at(i).buildParameters(audioConnect);
			});
		});

		globalAmpEnv.buildParameters(audioConnect);

	}

	buildSynths
	{


		triSynth.size.do({arg i;
			if(triSynth.at(i) != nil, {
				triSynth.at(i).build();
			});
		});

		globalAmpEnv.build();

	}

	buildEnvelopes
	{
		//envelopes

		//globalenvelope

		//"building envelopes".postln;


		//"globaLEnv".postln;
		globalEnv.build(globalAmpEnv.amplitude, fxGateGrp);
		globalEnv.allBuild = true;

		//tripulseosc envelopes

		triSynth.size.do({arg i;
			if(triSynth.at(i) != nil, {
				if(triSynth.at(i).controls.at(\midFreq) != nil, {
					var whichenvelope;
					//"midFreq".postln;
					whichenvelope = triSynth.at(i).controls.at(\midFreq);
					fxEnvs.at(whichenvelope).build(triSynth.at(i).midFreq, gateGrp);

				});
				if(triSynth.at(i).controls.at(\lowFineTune) != nil, {
					var whichenvelope;
					//"lowFineTune".postln;
					whichenvelope = triSynth.at(i).controls.at(\lowFineTune);
					fxEnvs.at(whichenvelope).build(triSynth.at(i).lowFineTune, gateGrp);
				});
				if(triSynth.at(i).controls.at(\highFineTune) != nil, {
					var whichenvelope;
					//"highFinetune".postln;
					whichenvelope = triSynth.at(i).controls.at(\highFineTune);
					fxEnvs.at(whichenvelope).build(triSynth.at(i).highFineTune, gateGrp);
				});
				if(triSynth.at(i).controls.at(\lowAmp) != nil, {
					var whichenvelope;
					//"lowAmp".postln;
					whichenvelope = triSynth.at(i).controls.at(\lowAmp);
					ampEnvs.at(whichenvelope).build(triSynth.at(i).lowAmp, gateGrp);
				});
				if(triSynth.at(i).controls.at(\midAmp) != nil, {
					var whichenvelope;
					//"midAmp".postln;
					whichenvelope = triSynth.at(i).controls.at(\midAmp);
					ampEnvs.at(whichenvelope).build(triSynth.at(i).midAmp, gateGrp);
				});
				if(triSynth.at(i).controls.at(\highAmp) != nil, {
					var whichenvelope;
					//"highAmp".postln;
					whichenvelope = triSynth.at(i).controls.at(\highAmp);
					ampEnvs.at(whichenvelope).build(triSynth.at(i).highAmp, gateGrp);
				});
				if(triSynth.at(i).controls.at(\widthLow) != nil, {
					var whichenvelope;
					//"widthLow".postln;
					whichenvelope = triSynth.at(i).controls.at(\widthLow);
					fxEnvs.at(whichenvelope).build(triSynth.at(i).widthLow, gateGrp);
				});
				if(triSynth.at(i).controls.at(\widthMid) != nil, {
					var whichenvelope;
					//"widthMid".postln;
					whichenvelope = triSynth.at(i).controls.at(\widthMid);
					fxEnvs.at(whichenvelope).build(triSynth.at(i).widthMid, gateGrp);
				});
				if(triSynth.at(i).controls.at(\widthHigh) != nil, {
					var whichenvelope;
					//"widthHigh".postln;
					whichenvelope = triSynth.at(i).controls.at(\widthHigh);
					fxEnvs.at(whichenvelope).build(triSynth.at(i).widthHigh, gateGrp);
				});

			});
		});

		//everything is build

		ampEnvs.size.do({arg i;
			ampEnvs.at(i).allBuild = true;
		});

		fxEnvs.size.do({arg i;
			fxEnvs.at(i).allBuild = true;
		});

		envelopesBuild = true;

	}

	buildTiming
	{
		//timing

		//"building timing".postln;

		//"globalEnv".postln;

		timing.build(globalEnv);

		//"ampEnvs".postln;

		ampEnvs.size.do({arg i;

				timing.build(ampEnvs.at(i));
		});

		//"fxEnvs".postln;

		fxEnvs.size.do({arg i;
				timing.build(fxEnvs.at(i));
		});
	}


	gateIt
	{
		globalAmpEnv.changeAudioBus(out: Convolve.audioBusses.at(conv));
		timing.onePlay;
		//globalEnv.envSynth.set(\gate, 1);
	}

	setOutPut
	{
		globalAmpEnv.changeAudioBus(out: Convolve.audioBusses.at(conv));
	}


	zeroGate
	{
		gateGrp.set(\gate, 0);
		fxGateGrp.set(\gate, 0);
	}


	freeUGens
	{

		globalAmpEnv.freeUGens;

		globalEnv.freeUGens;

		triSynth.size.do({arg i;
			if(triSynth.at(i) != nil, {
				triSynth.at(i).free;
			});
		});

		//Envelopes flag

		envelopesBuild = false;

	}

	free
	{

		// free 4 groups

		/*
		fxGrp.free;
		fxGateGrp.free;
		synthGrp.free;
		gateGrp.free;

		*/


		//free all audiobusses

		audioConnect.free;


		// free all control busses

		//"a".postln;
		triSynth.size.do({arg i;
			if(triSynth.at(i) != nil, {
				triSynth.at(i).free;
			});
		});

		globalAmpEnv.free;


		//"b".postln;
		globalEnv.free;

		ampEnvs.size.do({arg i;
			ampEnvs.at(i).free;
		});

		fxEnvs.size.do({arg i;
			fxEnvs.at(i).free;
		});


		envelopesBuild = false;



		timing.free;



	}

	freeEnvelopes
	{
		globalEnv.freeUGens;

		ampEnvs.size.do({arg i;
			ampEnvs.at(i).freeUGens;
		});

		fxEnvs.size.do({arg i;
			fxEnvs.at(i).freeUGens;
		});

		envelopesBuild = false;

	}






	mutate
	{
		var mutatedpulseorg, whichmutation;

		mutatedpulseorg = this.copyOtherBusGroup;

		//"a".postln;
		//whichmutation = \timing;
		whichmutation = possibleMut.choose;
		case
		{whichmutation == \trisynth}
		{
			var possiblemut, trisynthmutation;
			possiblemut= List.newClear;
			if(mutatedpulseorg.aantalSynths < 3, {possiblemut.add(\add)});
			if(mutatedpulseorg.aantalSynths > 1, {possiblemut.add(\del)});
			//"b".postln;
			trisynthmutation = possiblemut.choose;
			case
			{trisynthmutation == \add}
			{
				mutatedpulseorg.aantalSynths = mutatedpulseorg.aantalSynths + 1;
				block
				{ arg break;
					mutatedpulseorg.triSynth.size.do({ arg i;
						if(mutatedpulseorg.triSynth.at(i) == nil,
							{
								mutatedpulseorg.triSynth.put(i,TriPulseOsc(mutatedpulseorg.audioConnect,synthgrp: mutatedpulseorg.synthGrp));
								break.value;
						});
					});
				}
			}
			{trisynthmutation == \del}
			{
				mutatedpulseorg.aantalSynths = mutatedpulseorg.aantalSynths - 1;
				block
				{ arg break;
					mutatedpulseorg.triSynth.size.do({ arg i;
						if(mutatedpulseorg.triSynth.at(i) != nil,
							{
								mutatedpulseorg.triSynth.put(i,nil);
								break.value;
						});
					});
				}
			};
		}
		{whichmutation == \tripulseosc}
		{
			var possiblemut;
			possiblemut= List.newClear;
			//"c".postln;
			mutatedpulseorg.triSynth.size.do({ arg i;
					if(triSynth.at(i) != nil,
					{
						possiblemut.add(mutatedpulseorg.triSynth.at(i));
					});
			});
			//possiblemut.postln;
			possiblemut.choose.mutate;

		}
		{whichmutation == \conv}
		{
			mutatedpulseorg.conv = Convolve.posConv.choose;
		}
		{whichmutation == \timing}
		{
			mutatedpulseorg.timing.mutate;

			mutatedpulseorg.globalEnv.length =  mutatedpulseorg.timing.dur.map();
			mutatedpulseorg.globalEnv.checkType;

			mutatedpulseorg.ampEnvs.size.do({arg i;
				mutatedpulseorg.ampEnvs.at(i).length = mutatedpulseorg.timing.dur.map();
				mutatedpulseorg.ampEnvs.at(i).checkType;
			});

			mutatedpulseorg.fxEnvs.size.do({arg i;
				mutatedpulseorg.fxEnvs.at(i).length = mutatedpulseorg.timing.dur.map();
				mutatedpulseorg.fxEnvs.at(i).checkType;
			});


		}
		{whichmutation == \envelope}
		{
			mutatedpulseorg.globalEnv.mutate;

			mutatedpulseorg.ampEnvs.size.do({arg i;
				mutatedpulseorg.ampEnvs.at(i).mutate;
			});

			mutatedpulseorg.fxEnvs.size.do({arg i;
				mutatedpulseorg.fxEnvs.at(i).mutate;
			});
		};


		^mutatedpulseorg;
	}



	crossover
	{
		arg otherPulseOrg;
		var tripulseosc1list= List.newClear, tripulseosc2list = List.newClear, childpulseorg,  childtripulseosclist = List.newClear, size1, size2, childsize, tripulseosc1, tripulseosc2;

		childpulseorg = PulseOrg(childtripulseosclist,fxGrp,fxGateGrp, synthGrp, gateGrp);

		//convolution

		//"a".postln;
		childpulseorg.conv = [conv, otherPulseOrg.conv].choose;

		//timing;

		childpulseorg.timing =timing.crossover(otherPulseOrg.timing, childpulseorg.gateGrp);

		//envelopes

		childpulseorg.globalEnv = globalEnv.crossover(otherPulseOrg.globalEnv, childpulseorg.timing.dur.map());

		childpulseorg.ampEnvs.size.do({arg i;
			childpulseorg.ampEnvs.put(i, ampEnvs.at(i).crossover(otherPulseOrg.ampEnvs.at(i), childpulseorg.timing.dur.map()));
		});

		childpulseorg.fxEnvs.size.do({arg i;
			childpulseorg.fxEnvs.put(i,fxEnvs.at(i).crossover(otherPulseOrg.fxEnvs.at(i), childpulseorg.timing.dur.map()));
		});


		//synths

		triSynth.size.do({ arg i;
			if(triSynth.at(i) != nil,
				{
					tripulseosc1list.add(triSynth.at(i));
			});
			if(otherPulseOrg.triSynth.at(i) != nil,
				{
					tripulseosc2list.add(otherPulseOrg.triSynth.at(i));
			});
		});
		//"a".postln;
		size1 = tripulseosc1list.size;
		size2 = tripulseosc2list.size;
		while({(size1 > 0) && (size2 > 0)},
			{
				tripulseosc1 = tripulseosc1list.removeAt(tripulseosc1list.size.rand);
				tripulseosc2 = tripulseosc2list.removeAt(tripulseosc2list.size.rand);
				size1 = size1 - 1;
				size2 = size2 - 1;
					childtripulseosclist.add(tripulseosc1.crossover(tripulseosc2,childpulseorg.audioConnect,childpulseorg.synthGrp));
		});


		if(size1 == 0,
			{
			if([true,false].choose,
				{
						while({size2 > 0}, {
						tripulseosc2 = tripulseosc2list.removeAt(tripulseosc2list.size.rand);
						size2 = size2 -1;
					childtripulseosclist.add(tripulseosc1.crossover(tripulseosc2,childpulseorg.audioConnect,childpulseorg.synthGrp));
					});
			});
			},{
				if([true,false].choose,
				{
						while({size1 > 0}, {
						tripulseosc1 = tripulseosc1list.removeAt(tripulseosc1list.size.rand);
						size1 = size1 -1;
					childtripulseosclist.add(tripulseosc1.crossover(tripulseosc2,childpulseorg.audioConnect,childpulseorg.synthGrp));
					});
			});
		});

		//"b".postln;

		childsize = childtripulseosclist.size;
		while( {childsize < 3},
			{
				childtripulseosclist.insert((childtripulseosclist.size+1).rand, nil);
				childsize = childsize + 1;
		});
		childpulseorg.reInitTriSynth;
		^childpulseorg;
	}

	setFitAmp
	{ arg fitness;
		case
		{fitness == 3}
		{
			globalAmpEnv.setFitAmp(1.0);
		}
		{fitness == 2}
		{
			globalAmpEnv.setFitAmp(0.5);
		}
		{fitness == 1}
		{
			globalAmpEnv.setFitAmp(0.25);
		}
		{fitness == 0}
		{
			globalAmpEnv.setFitAmp(0);
		};
	}



}
