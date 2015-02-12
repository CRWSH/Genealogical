TriPulseOsc
{
	//parameters

	var <>lowOctave, <>highOctave, <>midFreq, <>lowFineTune, <>highFineTune, <>lowAmp, <>midAmp, <>highAmp, <>widthLow, <>widthMid, <>widthHigh;

	//controls

	var <>controls;

	//build dingen

	var <pulse,  plsConnect, <>outAudioBus, <>synthGrp;

	//structuur

	classvar aantalParameters = 11, synthDef;


	//the new  function creates a random value if no value is given for a parameter.
	//the values in the arguments are always between 0 and 1.

	*init
	{

	//reeks 1 & 2

	/*
	SynthDef(\pulse, {
			arg lowoctave = 3, // heeft geen control value
			highoctave = 2, // heeft geen control value
			midfreq = 200,
			lowfinetune = 1,
			highfinetune = 1,
			lowamp = 1,
			midamp = 0.75,
			highamp = 0.25,
			widthlow = 0.5,
			widthmid = 0.25,
			widthhigh = 0.1,
			outbus = 15,
			amp = 0.005;

			/*

			Out.ar(outbus, ((Pulse.ar(midfreq, widthmid)*midamp) + (SinOsc.ar((midfreq/(lowoctave + 1)) * lowfinetune)*lowamp) + (Saw.ar((midfreq * (highoctave + 1)) * highfinetune)*highamp))* amp)
		}*/
			Out.ar(outbus, ((Pulse.ar(midfreq, widthmid)*midamp) + (Pulse.ar((midfreq/(lowoctave + 1)) * lowfinetune, widthlow)*lowamp) + (Pulse.ar((midfreq * (highoctave + 1)) * highfinetune, widthhigh)*highamp))* amp)
		}).add;
		*/

		// reeks 3
		SynthDef(\pulse, {
			arg lowoctave = 3, // heeft geen control value
			highoctave = 2, // heeft geen control value
			midfreq = 200,
			lowfinetune = 1,
			highfinetune = 1,
			lowamp = 1,
			midamp = 0.75,
			highamp = 0.25,
			widthlow = 0.5,
			widthmid = 0.25,
			widthhigh = 0.1,
			outbus = 15,
			amp = 0.005;



			Out.ar(outbus, ((Pulse.ar(midfreq, widthmid)*midamp) + (SinOsc.ar((midfreq/(lowoctave + 1)) * lowfinetune)*lowamp) + (Saw.ar((midfreq * (highoctave + 1)) * highfinetune)*highamp))* amp)}).add;
	}

	*new
	{
		arg  lowoctave, highoctave, midfreq, lowfinetune, highfinetune, lowamp, midamp, highamp, widthlow, widthmid, widthhigh, synthgrp;



		^super.new.initTriPulseOsc(lowoctave, highoctave, midfreq, lowfinetune, highfinetune, lowamp, midamp, highamp, widthlow, widthmid, widthhigh, synthgrp)
	// super stands for the superclass, in this case Object






	}

	initTriPulseOsc
	{
		arg lowoctave, highoctave, midfreq, lowfinetune, highfinetune, lowamp, midamp, highamp, widthlow, widthmid, widthhigh, synthgrp;

		controls = Dictionary(0);


		if(lowoctave == nil,
			{
				lowOctave = Parameter(false,false,0,3);
			},
			{

				lowOctave = Parameter(false,false,0,3,lowoctave);
			}
		);
		if(highoctave == nil,
			{
				highOctave = Parameter(false,false,0,1);
			},
			{

				highOctave = Parameter(false,false,0,1,highoctave);
			}
		);
		if(midfreq == nil,
			{

				midFreq = Parameter(true,true,40, 10000,curv: 6, dis: \qbet);

				//reeks 1 & 2

				if(0.5.coin, {
					controls.add(\midFreq -> PulseOrg.numberFxEnvs.rand);
				});




			},
			{

				midFreq = Parameter(true,true,40, 10000, midfreq,6,  dis: \qbet);
			}
		);
		if(lowfinetune == nil,
			{
				lowFineTune = Parameter(true,true,1.005, 1.5);
				if(0.5.coin, {
					controls.add(\lowFineTune -> PulseOrg.numberFxEnvs.rand);
				});
			},
			{

				lowFineTune = Parameter(true,true,1.005, 1.5, lowfinetune);
			}
		);
		if(highfinetune == nil,
			{
				highFineTune = Parameter(true,true,0.9999, 1);
				if(0.5.coin, {
					controls.add(\highFineTune -> PulseOrg.numberFxEnvs.rand);
				});
			},
			{

				highFineTune = Parameter(true,true,0.999, 1, highfinetune);
			}
		);
		if(lowamp == nil,
			{
				lowAmp = Parameter(true,true,0, 0.57);
				if(0.5.coin, {
					controls.add(\lowAmp -> PulseOrg.numberAmpEnvs.rand);
				});
			},
			{

				lowAmp = Parameter(true,true,0, 0.57, lowamp);
			}
		);
		if(midamp == nil,
			{
				midAmp = Parameter(true,true,0, 0.57);
				if(0.5.coin, {
					controls.add(\midAmp -> PulseOrg.numberAmpEnvs.rand);
				});
			},
			{

				midAmp = Parameter(true,true,0, 0.57, midamp);
			}
		);
		if(highamp == nil,
			{
				highAmp = Parameter(true,true,0, 0.57);
				if(0.5.coin, {
					controls.add(\highAmp -> PulseOrg.numberAmpEnvs.rand);
				});
			},
			{

				highAmp = Parameter(true,true,0, 0.57, highamp);

			}
		);
		if(widthlow == nil,
			{
				widthLow = Parameter(true,true, 0, 1.0);
				if(0.5.coin, {
					controls.add(\widthLow -> PulseOrg.numberFxEnvs.rand);
				});

			},
			{

				widthLow = Parameter(true,true, 0, 1.0, widthlow);
			}
		);
		if(widthmid == nil,
			{
				widthMid = Parameter(true,true, 0, 1.0);
				if(0.5.coin, {
					controls.add(\widthMid -> PulseOrg.numberFxEnvs.rand);
				});
			},
			{

				widthMid = Parameter(true,true, 0, 1.0, widthmid);
			}
		);
		if(widthhigh == nil,
			{
				widthHigh = Parameter(true,true, 0, 1.0);
				if(0.5.coin, {
					controls.add(\widthHigh-> PulseOrg.numberFxEnvs.rand);
				});
			},
			{

				widthHigh = Parameter(true,true, 0, 1.0, widthhigh);
			}
		);

		synthGrp = synthgrp;

	}

	buildParameters
	{arg outaudiobus;

		outAudioBus = outaudiobus;

		lowOctave.build;
		highOctave.build;
		midFreq.build;
		lowFineTune.build;
		highFineTune.build;
		lowAmp.build;
		midAmp.build;
		highAmp.build;
		widthLow.build;
		widthMid.build;
		widthHigh.build;
	}

	build
	{
		//"d".postln;
		//making all control busses


		//"TriPulseOsc build".postln;
		pulse = Synth(\pulse, [\outbus, outAudioBus], target: synthGrp);

		//setting Bus controls

		//"e".postln;
		lowOctave.connection.set(lowOctave.map());
		//"e".postln;
		highOctave.connection.set(highOctave.map());
		//"e".postln;
		midFreq.connection.set(midFreq.map());
		//"e".postln;
		lowFineTune.connection.set(lowFineTune.map());
		//"e".postln;
		highFineTune.connection.set(highFineTune.map());
		//"e".postln;
		lowAmp.connection.set(lowAmp.map());
		//"e".postln;
		midAmp.connection.set(midAmp.map());
		//"e".postln;
		highAmp.connection.set(highAmp.map());
		//"e".postln;
		widthLow.connection.set(widthLow.map());
		//"e".postln;
		widthMid.connection.set(widthMid.map());
		//"e".postln;
		widthHigh.connection.set(widthHigh.map());
		//"e".postln;

		//connecting the right Bus controls with the right synth parameter
		//"f".postln;
		pulse.map(\lowoctave, lowOctave.connection);
		pulse.map(\highoctave, highOctave.connection);
		pulse.map(\midfreq, midFreq.connection);
		pulse.map(\lowfinetune, lowFineTune.connection);
		pulse.map(\highfinetune, highFineTune.connection);
		pulse.map(\lowamp, lowAmp.connection);
		pulse.map(\midamp, midAmp.connection);
		pulse.map(\highamp, highAmp.connection);
		pulse.map(\widthlow, widthLow.connection);
		pulse.map(\widthmid, widthMid.connection);
		pulse.map(\widthhigh, widthHigh.connection);

	}

	freeUGens
	{
		pulse.free;

	}

	free
	{
		//"TriPulseOsc".postln;
		pulse.free;

		//"l".postln;
		lowOctave.free;
		//"2".postln;
		highOctave.free;
		//"3".postln;
		midFreq.free;
		//"4".postln;
		lowFineTune.free;
		//"5".postln;
		highFineTune.free;
		//"6".postln;
		lowAmp.free;
		//"7".postln;
		midAmp.free;
		//"8".postln;
		highAmp.free;
		//"9".postln;
		widthLow.free;
		//"l0".postln;
		widthMid.free;
		//"l1".postln;
		widthHigh.free;

	}



	mutate
	{
		var whichmutation;

		whichmutation=[\lowOctave, \highOctave, \midFreq, \lowFineTune, \highFineTune, \lowAmp, \midAmp, \highAmp, \widthLow, \widthMid, \widthHigh].choose;

		case
		{whichmutation == \lowOctave}
		{
			lowOctave.mutate;
		}
		{whichmutation == \highOctave}
		{
			highOctave.mutate;
		}
		{whichmutation == \midFreq}
		{

			midFreq.mutate;

			// reeks 1 & 2

			/*
			if(0.5.coin, {
				midFreq.mutate;
				if(controls.atFail(\midFreq, {}) != nil, {controls.removeAt(\midFreq)});
			},{
				if(controls.atFail(\midFreq, {}) == nil, {
					controls.add(\midFreq -> PulseOrg.numberFxEnvs.rand);
				},{
					controls.removeAt(\midFreq);
					controls.add(\midFreq -> PulseOrg.numberFxEnvs.rand);
				});
			});

			*/
		}
		{whichmutation == \lowFineTune}
		{
			if(0.5.coin, {
				lowFineTune.mutate;
				if(controls.atFail(\lowFineTune, {}) != nil, {controls.removeAt(\lowFineTune)});
			},{
				if(controls.atFail(\lowFineTune, {}) == nil, {
					controls.add(\lowFineTune -> PulseOrg.numberFxEnvs.rand);
				},{
					controls.removeAt(\lowFineTune);
					controls.add(\lowFineTune -> PulseOrg.numberFxEnvs.rand);
				});
			});
		}
		{whichmutation == \highFineTune}
		{
			if(0.5.coin, {
				highFineTune.mutate;
				if(controls.atFail(\highFineTune, {}) != nil, {controls.removeAt(\highFineTune)});
			},{
				if(controls.atFail(\highFineTune, {}) == nil, {
					controls.add(\highFineTune -> PulseOrg.numberFxEnvs.rand);
				},{
					controls.removeAt(\highFineTune);
					controls.add(\highFineTune -> PulseOrg.numberFxEnvs.rand);
				});
			});
		}
		{whichmutation == \lowAmp}
		{
			if(0.5.coin, {
				lowAmp.mutate;
				if(controls.atFail(\lowAmp, {}) != nil, {controls.removeAt(\lowAmp)});
			},{
				if(controls.atFail(\lowAmp, {}) == nil, {
					controls.add(\lowAmp -> PulseOrg.numberAmpEnvs.rand);
				},{
					controls.removeAt(\lowAmp);
					controls.add(\lowAmp -> PulseOrg.numberAmpEnvs.rand);
				});
			});
		}
		{whichmutation == \midAmp}
		{
			if(0.5.coin, {
				midAmp.mutate;
				if(controls.atFail(\midAmp, {}) != nil, {controls.removeAt(\midAmp)});
			},{
				if(controls.atFail(\midAmp, {}) == nil, {
					controls.add(\midAmp -> PulseOrg.numberAmpEnvs.rand);
				},{
					controls.removeAt(\midAmp);
					controls.add(\midAmp -> PulseOrg.numberAmpEnvs.rand);
				});
			});
		}
		{whichmutation == \highAmp}
		{
			if(0.5.coin, {
				highAmp.mutate;
				if(controls.atFail(\highAmp, {}) != nil, {controls.removeAt(\highAmp)});
			},{
				if(controls.atFail(\highAmp, {}) == nil, {
					controls.add(\highAmp -> PulseOrg.numberAmpEnvs.rand);
				},{
					controls.removeAt(\highAmp);
					controls.add(\highAmp -> PulseOrg.numberAmpEnvs.rand);
				});
			});
		}
		{whichmutation == \widthLow}
		{
			if(0.5.coin, {
				widthLow.mutate;
				if(controls.atFail(\widthLow, {}) != nil, {controls.removeAt(\widthLow)});
			},{
				if(controls.atFail(\widthLow, {}) == nil, {
					controls.add(\widthLow -> PulseOrg.numberFxEnvs.rand);
				},{
					controls.removeAt(\widthLow);
					controls.add(\widthLow -> PulseOrg.numberFxEnvs.rand);
				});
			});
		}
		{whichmutation == \widthMid}
		{
			if(0.5.coin, {
				widthMid.mutate;
				if(controls.atFail(\widthMid, {}) != nil, {controls.removeAt(\widthMid)});
			},{
				if(controls.atFail(\widthMid, {}) == nil, {
					controls.add(\widthMid -> PulseOrg.numberFxEnvs.rand);
				},{
					controls.removeAt(\widthMid);
					controls.add(\widthMid -> PulseOrg.numberFxEnvs.rand);
				});
			});
		}
		{whichmutation == \widthHigh}
		{
			if(0.5.coin, {
				widthHigh.mutate;
				if(controls.atFail(\widthHigh, {}) != nil, {controls.removeAt(\widthHigh)});
			},{
				if(controls.atFail(\widthHigh, {}) == nil, {
					controls.add(\widthHigh -> PulseOrg.numberFxEnvs.rand);
				},{
					controls.removeAt(\widthHigh);
					controls.add(\widthHigh -> PulseOrg.numberFxEnvs.rand);
				});
			});
		};

	}

	crossover
	{
		arg othertripulseosc, audioconnect, synthgrp;
		var tripulseoscchild, crossoverchange = 1.0.rand;
		var lowoctave, highoctave, midfreq, lowfinetune, highfinetune, lowamp, midamp, highamp, widthlow, widthmid, widthhigh, newcontrols;

		newcontrols = Dictionary(0);



		if(crossoverchange.coin,
			{
				lowoctave = lowOctave.currentValue;
			},
			{
				lowoctave = othertripulseosc.lowOctave.currentValue;
		});
		if(crossoverchange.coin,
			{
				highoctave = highOctave.currentValue;
			},
			{
				highoctave = othertripulseosc.highOctave.currentValue;
		});
		if(crossoverchange.coin,
			{
				midfreq = midFreq.currentValue;

				//reeks 1 & 2



				if(controls.at(\midFreq) != nil, {
		newcontrols.add(\midFreq -> controls.at(\midFreq).copy)});


			},
			{
				midfreq = othertripulseosc.midFreq.currentValue;

				// reeks 1 & 2


		        if(othertripulseosc.controls.at(\midFreq) != nil, {
		newcontrols.add(\midFreq -> othertripulseosc.controls.at(\midFreq).copy)});


		});
		if(crossoverchange.coin,
			{
				lowfinetune = lowFineTune.currentValue;
		        if(controls.at(\lowFineTune) != nil, {
		newcontrols.add(\LowFineTune -> controls.at(\lowfinetune).copy)});
			},
			{
				lowfinetune = othertripulseosc.lowFineTune.currentValue;
			     if(othertripulseosc.controls.at(\lowFineTune) != nil, {
		newcontrols.add(\lowFineTune -> othertripulseosc.controls.at(\lowFineTune).copy)});
		});
		if(crossoverchange.coin,
			{
				highfinetune = highFineTune.currentValue;
		        if(controls.at(\highFineTune) != nil, {
		newcontrols.add(\highFineTune -> controls.at(\highFineTune).copy)});
			},
			{
				highfinetune = othertripulseosc.highFineTune.currentValue;
				if(othertripulseosc.controls.at(\highFineTune) != nil, {
		newcontrols.add(\highFineTune -> othertripulseosc.controls.at(\highFineTune).copy)});
		});
		if(crossoverchange.coin,
			{
				lowamp = lowAmp.currentValue;
		        if(controls.at(\lowAmp) != nil, {
		newcontrols.add(\lowAmp -> controls.at(\lowAmp).copy)});
			},
			{
				lowamp = othertripulseosc.lowAmp.currentValue;
				if(othertripulseosc.controls.at(\lowAmp) != nil, {
		newcontrols.add(\lowAmp -> othertripulseosc.controls.at(\lowAmp).copy)});
		});
		if(crossoverchange.coin,
			{
				midamp = midAmp.currentValue;
		         if(controls.at(\midAmp) != nil, {
		newcontrols.add(\midAmp -> controls.at(\midAmp).copy)});
			},
			{
				midamp = othertripulseosc.midAmp.currentValue;
			    if(othertripulseosc.controls.at(\midAmp) != nil, {
		newcontrols.add(\midAmp -> othertripulseosc.controls.at(\midAmp).copy)});
		});
		if(crossoverchange.coin,
			{
				highamp = highAmp.currentValue;
		        if(controls.at(\highAmp) != nil, {
		newcontrols.add(\highAmp -> controls.at(\highAmp).copy)});
			},
			{
				highamp = othertripulseosc.highAmp.currentValue;
				if(othertripulseosc.controls.at(\highAmp) != nil, {
		newcontrols.add(\highAmp -> othertripulseosc.controls.at(\highAmp).copy)});
		});
		if(crossoverchange.coin,
			{
				widthlow = widthLow.currentValue;
		        if(controls.at(\widthLow) != nil, {
		newcontrols.add(\widthLow -> controls.at(\widthLow).copy)});
			},
			{
				widthlow = othertripulseosc.widthLow.currentValue;
				if(othertripulseosc.controls.at(\widthLow) != nil, {
		newcontrols.add(\widthLow -> othertripulseosc.controls.at(\widthLow).copy)});
		});
		if(crossoverchange.coin,
			{
				widthmid = widthMid.currentValue;
		        if(controls.at(\widthMid) != nil, {
		newcontrols.add(\widthMid -> controls.at(\widthMid).copy)});
			},
			{
				widthmid = othertripulseosc.widthMid.currentValue;
	            if(othertripulseosc.controls.at(\widthMid) != nil, {
		newcontrols.add(\widthMid -> othertripulseosc.controls.at(\widthMid).copy)});
		});
		if(crossoverchange.coin,
			{
				widthhigh = widthHigh.currentValue;
		        if(controls.at(\widthHigh) != nil, {
		newcontrols.add(\widthHigh-> controls.at(\widthHigh).copy)});
			},
			{
				widthhigh = othertripulseosc.widthHigh.currentValue;
		        if(othertripulseosc.controls.at(\widthHigh) != nil, {
		newcontrols.add(\widthHigh -> othertripulseosc.controls.at(\widthHigh).copy)});
		});

		tripulseoscchild = TriPulseOsc(audioconnect, lowoctave, highoctave, midfreq, lowfinetune, highfinetune, lowamp, midamp, highamp, widthlow, widthmid, widthhigh, synthgrp);

		tripulseoscchild.controls = newcontrols;






		//changing all the control busses to make sure they are not the same then the source

		/*
		tripulseoscchild.lowOctave.changeControlBus;
		tripulseoscchild.highOctave.changeControlBus;
		tripulseoscchild.midFreq.changeControlBus;
		tripulseoscchild.lowFineTune.changeControlBus;
		tripulseoscchild.highFineTune.changeControlBus;
		tripulseoscchild.lowAmp.changeControlBus;
		tripulseoscchild.midAmp.changeControlBus;
		tripulseoscchild.highAmp.changeControlBus;
		tripulseoscchild.widthLow.changeControlBus;
		tripulseoscchild.widthMid.changeControlBus;
		tripulseoscchild.widthHigh.changeControlBus;
		*/


		^tripulseoscchild;
	}












}
