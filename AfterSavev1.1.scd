(
// class inits

Convolve.init;
Timing.init;
Envelope.init;
AmpEnv.init;
TriPulseOsc.init;
PulseOrg.init;

)


(

Convolve.build;
Archive.clear;
Archive.read("gen18r2_4v2.txt");
q = Archive.global.at(\gen);
a = q[\save].deepCopy;
Timing.durMin = q[\durMin].deepCopy;
Timing.durMax = q[\durMax].deepCopy;
Timing.deltaMax = q[\deltaMax].deepCopy;
Timing.totalDur = q[\totalDur].deepCopy;

)






(

//after save

var window, slider, slider2, slider3, slider4, slider5, score = 0, start = true, begingen = false, quoting = false, buildplay = false, nextgen = false, choose = false,  convolvebuild = false, injectones = false, updatetiming = false, fitplay = false, pop = 1, deltamax = Timing.deltaMax, durmin = Timing.durMin, durmax = Timing.durMax, totaldur = Timing.totalDur, plotlist = List.newClear, plotter =Plotter(\theplot), firstgen = true, savedcounter = 0, randChromFunc, mutationFunc, crossoverFunc, fxgrp, fxgategrp, synthgrp, gategrp;
o = Server.local.options;

o.maxNodes = 100000;

fxgrp = Group.head;
fxgategrp = Group.head;
synthgrp = Group.head;
gategrp = Group.head;

//Convolve.build;

//"a".postln;


a.genePool.size.do({arg i;
	a.genePool.at(i).initAfterSave(a.genePool.at(i).triSynth, fxgrp,fxgategrp,synthgrp, gategrp);
});

//"b".postln;

~scoreList = List.newClear;
~scoreListPop1 = List.newClear;
~scoreListPop2 = List.newClear;
~scoreListPop3 = List.newClear;
~scoreListPop4 = List.newClear;



a.coProb = 0.99;
a.mutationProb = 0.99;

plotter.plotMode = \levels;
plotter.superpose = true;


~onegennexter = Routine({
	loop {
		a.genePool.size.do({arg i;
			a.genePool.at(i).gateIt;
			"Organism % is gated".format(i).postln;
			"DeltaList is \n %".format(a.genePool.at(i).timing.deltaList).postln.yield;
		});
		/*
		a.genePool.size.do({arg i;
			a.genePool.at(i).zeroGate;
		});
		"ALL ZERO GATED".postln.yield;
		*/
	}
});

~onegentimingnexter = Routine({
	a.genePool.size.do({arg i;
		a.genePool.at(i).timing.play;
		"Organism % is playing with timing".format(i).postln.yield;
	});

});

~firstbuild = Routine({
	if(firstgen == false, {
		"FREEING ALL ORGANISMS".postln;
		a.genePool.size.do({arg i;
			a.genePool.at(i).free;
		});
		2.0.yield;
		a.injectFitness(~scoreList);
		"INJECT FITNESS".postln;
		2.0.yield;
		a.nextGeneration;
		"CALCULATE NEXT GEN".postln;
	});
	3.0.yield;
	"BUILDING SYNTHS".postln;
	a.genePool.size.do({arg i;
		a.genePool.at(i).buildSynths;
	});
	2.0.yield;
	"BUILDING ENVELOPES".postln;
	a.genePool.size.do({arg i;
		a.genePool.at(i).buildEnvelopes;
	});
	2.0.yield;
	"BUILDING TIMING".postln;
	a.genePool.size.do({arg i;
		a.genePool.at(i).buildTiming;
	});
	2.0.yield;
	a.genePool.size.do({arg i;
		a.genePool.at(i).setOutPut;
	});
	"RIGHT OUTPUT IS SET".postln;
	/*
	s.record();
	"START RECORD AND RIGHT OUTPUT IS SET".postln;
	1.0.yield;
	/*
	a.genePool.size.do({arg i;
		a.genePool.at(i).timing.play;
	});
	*/
	"GENERATION IS PLAYING".postln;
	(Timing.totalDur + 2.0).yield;
	s.stopRecording;
	"STOP RECORDING".postln;
	"GENERATION IS DONE PLAYING".postln;
	*/
	begingen = true;
	firstgen = false
});

~onegenplayer = Routine({
	0.1.yield;
	a.genePool.size.do({arg i;
		a.genePool.at(i).gateIt;
		"Organism % is gated".format(i).postln;
		0.05.yield;
	});
	0.7.yield;
	/*
	a.genePool.size.do({arg i;
		a.genePool.at(i).zeroGate;
	});
	"ALL ZERO GATED".postln;
	*/
});

~onegentimingplayandrecord = Routine({
	/*
	if( a.genePool.at(0).envelopesBuild == true, {
		a.genePool.size.do({arg i;
			a.genePool.at(i).freeEnvelopes;
		});
		"FREEING ENVELOPES".postln;
		2.0.yield;
	});
	*/
	s.record();
	"START RECORDING".postln;
	1.0.yield;
	a.genePool.size.do({arg i;
		a.genePool.at(i).timing.play;
	});
	"GENERATION IS PLAYING".postln;
	(Timing.totalDur + 2.0).yield;
	s.stopRecording;
	"STOP RECORDING".postln;
	"GENERATION IS DONE PLAYING".postln;
});

~pauseandnext = Routine({
	/*
	if( a.genePool.at(0).envelopesBuild == false, {
		a.genePool.size.do({arg i;
			a.genePool.at(i).builEnvelopes;
		});
		"BUILD ENVELOPES".postln;
		2.0.yield;
	});
	*/
	0.2.yield;
	~onegennexter.next;
});

~pauseandnexttiming = Routine({
	0.2.yield;
	~onegentimingnexter.next;
	(Timing.totalDur + 2.0).yield;
	"Organism is done playing".postln;

});

~buildplay = Routine({
	if(begingen || (choose || updatetiming), {
		"BUILDING PARAMETERS".postln;
		a.genePool.size.do({arg i;
			a.genePool.at(i).buildParameters;
		});
		2.0.yield;
		"BUILDING SYNTHS".postln;
		a.genePool.size.do({arg i;
			a.genePool.at(i).buildSynths;
		});
		2.0.yield;
		"BUILDING ENVELOPES".postln;
		a.genePool.size.do({arg i;
			a.genePool.at(i).buildEnvelopes;
		});
		2.0.yield;
		"BUILDING TIMING".postln;
		a.genePool.size.do({arg i;
			a.genePool.at(i).buildTiming;
		});
		2.0.yield;
		a.genePool.size.do({arg i;
			a.genePool.at(i).setOutPut;
		});
		"RIGHT OUTPUT IS SET".postln;
		s.record();
		"START RECORDING".postln;
		0.5.yield;
		Synth(\sineperc);
		0.5.yield;
		a.genePool.size.do({arg i;
			a.genePool.at(i).timing.play;
		});
		"GENERATION IS PLAYING".postln;
		(Timing.totalDur + 2.0).yield;
		s.stopRecording;
		"STOP RECORDING".postln;
		"GENERATION IS DONE PLAYING".postln;
		},{
		if(buildplay, {
			a.genePool.size.do({arg i;
				a.genePool.at(i).timing.play;
			});
			"GENERATION IS PLAYING".postln;
			(Timing.totalDur + 2.0).yield;
			"GENERATION IS DONE PLAYING".postln;
		},{
			"NOT READY TO BUILD".postln;
		});
	});
	if(begingen, {
		begingen = false;
		buildplay = true;
	});
	if(choose, {
		choose = false;
		buildplay = true;
	});
	if(updatetiming, {
		updatetiming = false;
		buildplay = true;
	});

});

~quoting = Routine ({
	if(buildplay, {
		~scoreList = List.newClear;
		~onegennexter.reset;
		~pauseandnext.reset;
		~pauseandnext.play;
	});
	if(quoting && (~scoreList.size < 32), {
		~pauseandnext.reset;
		~pauseandnext.play;
		~scoreList.add(score);
		~scoreList.size.postln;
	});
	if(~scoreList.size == 32, {
		"Fitness scores are ready for creating a new generation".postln;
	});

	if(buildplay == false && quoting == false, {
		"NOT READY TO QUOTE YET".postln;
	});


	if(buildplay, {
		buildplay = false;
		quoting = true;
	});
});

~nextgen = Routine({
	if(fitplay ||(choose && ~scoreList.size == 32) , {
		"FREEING ALL ORGANISMS".postln;
		a.genePool.size.do({arg i;
			a.genePool.at(i).free;
		});
		2.0.yield;
		a.injectFitness(~scoreList);
		"INJECT FITNESS".postln;
		b = a.deepCopy;
		c = a.deepCopy;
		d = a.deepCopy;
		e = a.deepCopy;
		2.0.yield;
		b.nextGeneration;
		c.nextGeneration;
		d.nextGeneration;
		e.nextGeneration;
		"CALCULATE NEXT GEN".postln;
		2.0.yield;

	},{
		"NOT READY TO CALCULATE NEXT GEN YET".postln;
	});
	if(fitplay, {
		fitplay = false;
		nextgen = true;
	});
	if(choose && ~scoreList.size == 32, {
		choose = false;
		nextgen = true;
	});



});

~choosepop1 = Routine({

	if(buildplay || fitplay , {
		"FREEING ALL ORGANISMS".postln;
		a.genePool.size.do({arg i;
			a.genePool.at(i).free;
		});
		2.0.yield;
	});
	if(buildplay || (nextgen || (choose || fitplay)), {
		"SELECT POPULATION 1".postln;
		case
		{pop == 1}
		{
			~scoreListPop1 = ~scoreList.deepCopy
		}
		{pop == 2}
		{
			~scoreListPop2 = ~scoreList.deepCopy
		}
		{pop == 3}
		{
			~scoreListPop3 = ~scoreList.deepCopy
		}
		{pop == 4}
		{
			~scoreListPop4 = ~scoreList.deepCopy
		};

		pop = 1;
		~scoreList = ~scoreListPop1.deepCopy;
		a = b.deepCopy;
		}, {
		"NOT READY TO CHOOSE AN POPULATION".postln;
	});
	if(buildplay, {
		buildplay = false;
		choose = true;
	});
	if(nextgen, {
		nextgen = false;
		choose = true;
	});
	if(fitplay, {
		fitplay = false;
		choose = true;
	});
});

~choosepop2 = Routine({
	if(buildplay || fitplay, {
		"FREEING ALL ORGANISMS".postln;
		a.genePool.size.do({arg i;
			a.genePool.at(i).free;
		});
		2.0.yield;
	});
	if(buildplay || (nextgen ||(choose || fitplay)), {
		"SELECT POPULATION 2".postln;
		case
		{pop == 1}
		{
			~scoreListPop1 = ~scoreList.deepCopy
		}
		{pop == 2}
		{
			~scoreListPop2 = ~scoreList.deepCopy
		}
		{pop == 3}
		{
			~scoreListPop3 = ~scoreList.deepCopy
		}
		{pop == 4}
		{
			~scoreListPop4 = ~scoreList.deepCopy
		};

		pop = 2;
		~scoreList = ~scoreListPop2.deepCopy;
		a = c.deepCopy;
		}, {
		"NOT READY TO CHOOSE AN POPULATION".postln;
	});
	if(buildplay, {
		buildplay = false;
		choose = true;
	});
	if(nextgen, {
		nextgen = false;
		choose = true;
	});
	if(fitplay, {
		fitplay = false;
		choose = true;
	});
});

~choosepop3 = Routine({
	if(buildplay || fitplay, {
		"FREEING ALL ORGANISMS".postln;
		a.genePool.size.do({arg i;
			a.genePool.at(i).free;
		});
		2.0.yield;
	});
	if(buildplay || (nextgen || (choose || fitplay)), {
		"SELECT POPULATION 3".postln;
		case
		{pop == 1}
		{
			~scoreListPop1 = ~scoreList.deepCopy
		}
		{pop == 2}
		{
			~scoreListPop2 = ~scoreList.deepCopy
		}
		{pop == 3}
		{
			~scoreListPop3 = ~scoreList.deepCopy
		}
		{pop == 4}
		{
			~scoreListPop4 = ~scoreList.deepCopy
		};

		pop = 3;
		~scoreList = ~scoreListPop3.deepCopy;
		a = d.deepCopy;
		}, {
		"NOT READY TO CHOOSE AN POPULATION".postln;
	});
	if(buildplay, {
		buildplay = false;
		choose = true;
	});
	if(nextgen, {
		nextgen = false;
		choose = true;
	});
	if(fitplay, {
		fitplay = false;
		choose = true;
	});
});

~choosepop4 = Routine({
	if(buildplay || fitplay, {
		"FREEING ALL ORGANISMS".postln;
		a.genePool.size.do({arg i;
			a.genePool.at(i).free;
		});
		2.0.yield;
	});
	if(buildplay || (nextgen || (choose || fitplay)), {
		case
		{pop == 1}
		{
			~scoreListPop1 = ~scoreList.deepCopy
		}
		{pop == 2}
		{
			~scoreListPop2 = ~scoreList.deepCopy
		}
		{pop == 3}
		{
			~scoreListPop3 = ~scoreList.deepCopy
		}
		{pop == 4}
		{
			~scoreListPop4 = ~scoreList.deepCopy
		};

		pop = 4;
		~scoreList = ~scoreListPop4.deepCopy;
		a = e.deepCopy;
		"SELECT POPULATION 4".postln;
		}, {
		"NOT READY TO CHOOSE AN POPULATION".postln;
	});
	if(buildplay, {
		buildplay = false;
		choose = true;
	});
	if(nextgen, {
		nextgen = false;
		choose = true;
	});
	if(fitplay, {
		fitplay = false;
		choose = true;
	});
});

~injectones = Routine({
	if(buildplay, {
		~scoreList = List.newClear;
		a.genePool.size.do({~scoreList.add(1);});
		buildplay = false;
		injectones = true;
		"MAKING ALL ORGANISM EQUAL IMPORTANT".postln;
	}, {
		"NOT READY YET TO QUOTE".postln;
	});
});

~inject3s = Routine({
	if(buildplay, {
		~scoreList = List.newClear;
		a.genePool.size.do({~scoreList.add(3);});
		buildplay = false;
		injectones = true;
		"MAKING ALL ORGANISM EQUAL IMPORTANT".postln;
	}, {
		"NOT READY YET TO QUOTE".postln;
	});
});

~updatetiming = Routine({
	if(buildplay, {
		"FREEING ALL ORGANISMS".postln;
		a.genePool.size.do({arg i;
			a.genePool.at(i).free;
		});
		2.0.yield;
	});
	if((choose || (buildplay || (updatetiming || start))), {
		var dif, plus, smallenvelope = 10 ;
		"Update Timing for all Organisms".postln;
		Timing.durMin = durmin;
		Timing.durMax = durmax;
		Timing.deltaMax = deltamax;
		Timing.totalDur = totaldur;
		Envelope.ampEnvs.size.do({arg i;
			if(smallenvelope > Envelope.lengths.at(Envelope.ampEnvs.at(i)), {
				smallenvelope = Envelope.lengths.at(Envelope.ampEnvs.at(i)) + 0.001;
			})
		});
		if(Timing.durMin < smallenvelope, {
			Timing.durMin = smallenvelope + 0.001;
		});
		if(Timing.durMax < Timing.durMin, {
			Timing.durMax = Timing.durMin + 0.001;
		});

		if(Timing.deltaMax < Timing.durMax, {
			Timing.deltaMax =  Timing.durMax + 0.001;
		});
		dif = Timing.deltaMax - Timing.durMin;
		plus = dif/5;
		if(((Timing.durMin + plus)*3) > Timing.totalDur, {
			Timing.totalDur = (Timing.durMin + plus)*3;
		});
		"durmin: % durmax: % deltamax: % totaldur: %".format(Timing.durMin,Timing.durMax,Timing.deltaMax,Timing.totalDur).postln;
		a.genePool.size.do({
			arg i;
			a.genePool.at(i).updateChar;
		});
		},{
			"NOT READY YET TO UPDATE TIMING".postln;
	});
	if(choose, {
		choose = false;
		updatetiming = true;
	});
	if(buildplay, {
		buildplay = false;
		updatetiming = true;
	});
	if(start, {
		start = false;
		updatetiming = true;
	});

});

~genone = Routine({
	if(updatetiming, {
		"BUILDING FIRST GENERATION".postln;
		a = GAWorkbench2(32,randChromFunc, argMutationFunc: mutationFunc,argCrossoverFunc: crossoverFunc);
		updatetiming = false;
		begingen = true;
		},
		{
			"NOT POSSIBLE TO CREATE FIRST GENERATION YET".postln;
	});
});

~fitplay = Routine({
	if((quoting && ~scoreList.size == 32) || injectones, {
		a.genePool.size.do({arg i;
			a.genePool.at(i).setFitAmp(~scoreList.at(i));
		});
		0.5.yield;
		"FITNESS AMPLITUDES ARE SET".postln;

		s.record();
		"START RECORDING".postln;
		0.5.yield;
		Synth(\sineperc);
		0.5.yield;
		a.genePool.size.do({arg i;
			a.genePool.at(i).timing.play;
		});
		"GENERATION IS PLAYING".postln;
		(Timing.totalDur + 2.0).yield;
		s.stopRecording;
		"STOP RECORDING".postln;
		"GENERATION IS DONE PLAYING".postln;
		},{
			"NOT READY TO PLAY GEN WITH FITNESS AMPLITUDES YET".postln;
	});

	if((quoting && ~scoreList.size == 32), {
		quoting = false;
		fitplay = true;
	});
	if(injectones, {
		injectones = false;
		fitplay = true;
	});


});




//build first generation


//window defenition and actions

window = Window("The Fitness Function V1.1.", Rect(300,200,500,550));
window.view.decorator = FlowLayout(window.view.bounds);
slider = EZSlider(window, 430 @ 20, "score", ControlSpec(0,3,step:1,default:1),
	{ arg slider; score = slider.value;  }
     , labelWidth: 80, numberWidth: 60);
slider2 =  EZSlider(window, 430 @ 20, "max delta", ControlSpec(0.2,20.0,default:Timing.deltaMax),
	{ arg slider; deltamax = slider.value;  }
     , labelWidth: 80, numberWidth: 60);
slider3 =  EZRanger(window, 430 @ 20, "range dur", ControlSpec(0.05,10.0,default:0.2),
	{ arg slider;
		durmin = slider.value.at(0);
		durmax = slider.value.at(1);
	},[Timing.durMin,Timing.durMax]
     , labelWidth: 80, numberWidth: 60);
slider4 =  EZSlider(window, 430 @ 20, "dur gen", ControlSpec(0,30.0,default:Timing.totalDur),
	{ arg slider; totaldur = slider.value;  }
     , labelWidth: 80, numberWidth: 60);
slider4 =  EZSlider(window, 430 @ 20, "mutation prob", ControlSpec(0,1.0,default:0.99),
	{ arg slider; a.mutationProb = slider.value;  }
     , labelWidth: 80, numberWidth: 60);
window.view.decorator.left_(410).top_(150);
Button(window, 80@60) // button uses size 50 by 50
    .states_([["Record"],["Stop \n Recording"]])
    .action_({|view|  if (view.value ==1,{
	s.record;
	"START RECORDING".postln;
	},
	{
		s.stopRecording;
		"STOP RECORDING".postln;

})})
    .resize_(9);
window.view.decorator.left_(310).top_(150);
Button(window, 80@60) // button uses size 50 by 50
    .states_([["Inject 3s"]])
    .mouseDownAction_({

	    ~inject3s.reset;
		~inject3s.play;

	})
    .resize_(9);
window.view.decorator.left_(210).top_(150); // decorator set to specific location
Button(window, 80@60) // button uses size 50 by 50
    .states_([["Next \n Organism \n & Quote"]])
    .mouseDownAction_({
	    ~quoting.reset;
	    ~quoting.play;
})
    .resize_(9);
window.view.decorator.left_(110).top_(150); // decorator set to specific location
Button(window, 90@60) // button uses size 50 by 50
    .states_([["Next \n Generation"]])
    .mouseDownAction_({
	    ~nextgen.reset;
	    ~nextgen.play;
})
    .resize_(9);
window.view.decorator.left_(10).top_(150); // decorator set to specific location
Button(window, 90@60) // button uses size 50 by 50
    .states_([["Play \n Generation"]])
    .mouseDownAction_({
       ~buildplay.reset;
	   ~buildplay.play;
})
    .resize_(9);
window.view.decorator.left_(10).top_(225);
Button(window, 90@60) // button uses size 50 by 50
    .states_([["Update \n Timing"]])
    .mouseDownAction_({
	   ~updatetiming.reset;
	   ~updatetiming.play;
})
    .resize_(9);
window.view.decorator.left_(110).top_(225);
Button(window, 80@60) // button uses size 50 by 50
    .states_([["Select \n Pop 1"]])
    .mouseDownAction_({
	     ~choosepop1.reset;
	     ~choosepop1.play;
	})
    .resize_(9);
window.view.decorator.left_(210).top_(225);
Button(window, 80@60) // button uses size 50 by 50
    .states_([["Select \n Pop 2"]])
    .mouseDownAction_({
	     ~choosepop2.reset;
	     ~choosepop2.play;
	})
    .resize_(9);
window.view.decorator.left_(310).top_(225);
Button(window, 80@60) // button uses size 50 by 50
    .states_([["Select \n Pop 3"]])
    .mouseDownAction_({
	     ~choosepop3.reset;
	     ~choosepop3.play;
	})
    .resize_(9);
window.view.decorator.left_(410).top_(225);
Button(window, 80@60) // button uses size 50 by 50
    .states_([["Select \n Pop 4"]])
    .mouseDownAction_({
	     ~choosepop4.reset;
	     ~choosepop4.play;
	})
    .resize_(9);
window.view.decorator.left_(10).top_(300);
Button(window, 80@60) // button uses size 50 by 50
    .states_([["TriSynth  \n Mut Off "],["TriSynth  \n Mut On"]])
    .action_({|view|  if (view.value ==1,{
	PulseOrg.addMutation(\trisynth);

	},
	{
		PulseOrg.delMutation(\trisynth);


})})
    .resize_(9);
window.view.decorator.left_(110).top_(300);
Button(window, 80@60) // button uses size 50 by 50
    .states_([["Conv Mut \n Off"],["Conv Mut \n On"]])
    .action_({|view|  if (view.value ==1,{
	PulseOrg.addMutation(\conv);
	},
	{
		PulseOrg.delMutation(\conv);

})})
    .resize_(9);
window.view.decorator.left_(210).top_(300);
Button(window, 80@60) // button uses size 50 by 50
    .states_([["Pulse Mut \n Off"],["Pulse Mut \n On"]])
    .action_({|view|  if (view.value ==1,{
	PulseOrg.addMutation(\tripulseosc);

	},
	{
		PulseOrg.delMutation(\tripulseosc);


})})
    .resize_(9);
window.view.decorator.left_(310).top_(300);
Button(window, 80@60) // button uses size 50 by 50
    .states_([["Time Mut \n Off"],["Time Mut \n On"]])
    .action_({|view|  if (view.value ==1,{
	PulseOrg.addMutation(\timing);

	},
	{
		PulseOrg.delMutation(\timing);


})})
    .resize_(9);
window.view.decorator.left_(410).top_(300);
Button(window, 80@60) // button uses size 50 by 50
    .states_([["Env Mut \n Off"],["Env Mut \n On"]])
    .action_({|view|  if (view.value ==1,{
	PulseOrg.addMutation(\envelope);

	},
	{
		PulseOrg.delMutation(\envelope);


})})
    .resize_(9);
window.view.decorator.left_(10).top_(375);
Button(window, 80@60) // button uses size 50 by 50
    .states_([["Impulse  \n On "],["Impulse  \n Off"]])
    .action_({|view|  if (view.value ==1,{
	Envelope.delAmpEnv(\impulse);

	},
	{
		Envelope.addAmpEnv(\impulse);


})})
    .resize_(9);
window.view.decorator.left_(110).top_(375);
Button(window, 80@60) // button uses size 50 by 50
    .states_([["Sharp  \n On "],["Sharp  \n Off"]])
    .action_({|view|  if (view.value ==1,{
	Envelope.delAmpEnv(\sharp);

	},
	{
		Envelope.addAmpEnv(\sharp);


})})
    .resize_(9);
window.view.decorator.left_(210).top_(375);
Button(window, 80@60) // button uses size 50 by 50
    .states_([["Slow  \n On "],["Slow  \n Off"]])
    .action_({|view|  if (view.value ==1,{
	Envelope.delAmpEnv(\slow);

	},
	{
		Envelope.addAmpEnv(\slow);


})})
    .resize_(9);
window.view.decorator.left_(310).top_(375);
Button(window, 80@60) // button uses size 50 by 50
    .states_([["Sharpsus  \n On "],["Sharpsus  \n Off"]])
    .action_({|view|  if (view.value ==1,{
	Envelope.delAmpEnv(\sharpsus);

	},
	{
		Envelope.addAmpEnv(\sharpsus);


})})
    .resize_(9);
window.view.decorator.left_(410).top_(375);
Button(window, 80@60) // button uses size 50 by 50
    .states_([["Atmos  \n On "],["Atmos  \n Off"]])
    .action_({|view|  if (view.value ==1,{
	Envelope.delAmpEnv(\atmos);

	},
	{
		Envelope.addAmpEnv(\atmos);


})})
    .resize_(9);
window.view.decorator.left_(10).top_(450);
Button(window, 80@60) // button uses size 50 by 50
    .states_([["Create \n 1st gen"]])
    .mouseDownAction_({
	     ~genone.reset;
	     ~genone.play;
	})
    .resize_(9);

window.view.decorator.left_(110).top_(450);
Button(window, 80@60) // button uses size 50 by 50
    .states_([["Play with \n fitness"]])
    .mouseDownAction_({
	     ~fitplay.reset;
	     ~fitplay.play;
	})
    .resize_(9);

window.onClose = {};
window.front;
)

