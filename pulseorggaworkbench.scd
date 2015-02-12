(
Convolve.init;
)

(

//init

var window, slider, score = 0, scoreList = List.newClear, begingen = true, quoting = false, convolvebuild = false ;

var randChromFunc = {
	PulseOrg()
};

var mutationFunc = {
	arg pulseorg;

	pulseorg.mutate
};

var crossoverFunc = {
	arg p1, p2;

	p1.crossover(p2)
};

Convolve.build;
a = GAWorkbench2(32,randChromFunc, argMutationFunc: mutationFunc,argCrossoverFunc: crossoverFunc);


a.coProb = 0.99;


~onegennexter = Routine({
	loop {
		a.genePool.size.do({arg i;
			a.genePool.at(i).gateIt;
			"Organism % is gated".format(i).postln.yield;
		});
		a.genePool.size.do({arg i;
			a.genePool.at(i).zeroGate;
		});
		"ALL ZERO GATED".postln.yield;
	}
});

~onegenplayerrecord = Routine({
	a.genePool.size.do({arg i;
		a.genePool.at(i).build;
	});
	"BUILD".postln;
	1.0.yield;
	s.record();
	"START RECORD".postln;
	1.0.yield;
	a.genePool.size.do({arg i;
		a.genePool.at(i).gateIt;
		"Organism % is gated".format(i).postln;
		0.05.yield;
	});
	1.0.yield;
	s.stopRecording;
	"STOP RECORDING".postln;
	0.5.yield;
	a.genePool.size.do({arg i;
		a.genePool.at(i).zeroGate;
	});
	"ALL ZERO GATED".postln;
	begingen = true;
});

~onegenplayer = Routine({
	0.1.yield;
	a.genePool.size.do({arg i;
		a.genePool.at(i).gateIt;
		"Organism % is gated".format(i).postln;
		0.05.yield;
	});
	0.7.yield;
	a.genePool.size.do({arg i;
		a.genePool.at(i).zeroGate;
	});
	"ALL ZERO GATED".postln;
});

~pauseandnext = Routine({
	0.2.yield;
	~onegennexter.next;
});

//play first generation
~onegenplayerrecord.play;

//window defenition and actions

window = Window("The Fitness Function", Rect(300,200,400,110));
window.view.decorator = FlowLayout(window.view.bounds);
slider = EZSlider(window, 350 @ 20, "score", ControlSpec(0,3,step:1,default:0),
	{ arg slider; score = slider.value;  }
     , labelWidth: 80, numberWidth: 60);
window.view.decorator.left_(310).top_(40); // decorator set to specific location
Button(window, 80@60) // button uses size 50 by 50
    .states_([["Next \n Organism \n & Quote"]])
    .mouseDownAction_({
	if(begingen, {
		"begingen".postln;
		~onegenplayerrecord.reset;
		~onegenplayer.reset;
		~pauseandnext.reset;
		~pauseandnext.play;
		scoreList = List.newClear;
		begingen = false;
		quoting = true;
	},{
		if(scoreList.size < 32,{
				"quoting".postln;
				~pauseandnext.reset;
				~pauseandnext.play;
				scoreList.add(score);
				scoreList.size.postln;
				if(scoreList.size == 32, {
					quoting = false;
					"Fitness scores are ready for creating a new generation".postln;
				});

		},{
				"Fitness scores are ready for creating a new generation".postln;
		});
	});
})
    .resize_(9);
window.view.decorator.left_(210).top_(40); // decorator set to specific location
Button(window, 90@60) // button uses size 50 by 50
    .states_([["Next \n Generation"]])
    .mouseDownAction_({
	if(quoting == false && scoreList.size == 32, {
		~onegenplayer.reset;
		~onegennexter.reset;
		a.injectFitness(scoreList);
		a.nextGeneration;
		~onegenplayerrecord.play;
	},{
		"Quoting for the next generation is not done yet".postln;
	});
})
    .resize_(9);
window.view.decorator.left_(110).top_(40); // decorator set to specific location
Button(window, 90@60) // button uses size 50 by 50
    .states_([["Play \n Generation"]])
    .mouseDownAction_({
	if(quoting == false, {
		~onegenplayer.reset;
		~onegennexter.reset;
		~onegenplayerrecord.reset;
		~onegenplayer.play;
	},{
		"Quoting for the next generation is not done yet".postln;
	});
})
    .resize_(9);
window.view.decorator.left_(10).top_(40); // decorator set to specific location
Button(window, 90@60) // button uses size 50 by 50
    .states_([["Save \n Generation"]])
    .mouseDownAction_({
	"save generation".postln;
	q = ();
	q[\saved_GA]= a;
	Archive.global.put(\myData,q);
})
    .resize_(9);
window.onClose = {};
window.front;
)



(

//after save

var window, slider, score = 0, scoreList = List.newClear, begingen = true, quoting = false ;

q = Archive.global.at(\myData);

a = q[\saved_GA].value;


a.genePool.size.do({arg i;
	a.genePool.at(i).initAfterSave(a.genePool.at(i).triSynth);
});



~onegennexter = Routine({
	loop {
		a.genePool.size.do({arg i;
			a.genePool.at(i).gateIt;
			"Organism % is gated".format(i).postln.yield;
		});
		a.genePool.size.do({arg i;
			a.genePool.at(i).zeroGate;
		});
		"ALL ZERO GATED".postln.yield;
	}
});

~onegenplayerrecord = Routine({
	a.genePool.size.do({arg i;
		a.genePool.at(i).build;
	});
	"BUILD".postln;
	0.1.yield;
	s.record();
	"START RECORD".postln;
	1.0.yield;
	a.genePool.size.do({arg i;
		a.genePool.at(i).gateIt;
		"Organism % is gated".format(i).postln;
		0.210.yield;
	});
	1.0.yield;
	s.stopRecording;
	"STOP RECORDING".postln;
	0.5.yield;
	a.genePool.size.do({arg i;
		a.genePool.at(i).zeroGate;
	});
	"ALL ZERO GATED".postln;
	begingen = true;
});

~onegenplayer = Routine({
	0.1.yield;
	a.genePool.size.do({arg i;
		a.genePool.at(i).gateIt;
		"Organism % is gated".format(i).postln;
		0.210.yield;
	});
	0.5.yield;
	a.genePool.size.do({arg i;
		a.genePool.at(i).zeroGate;
	});
	"ALL ZERO GATED".postln;
});

~pauseandnext = Routine({
	0.2.yield;
	~onegennexter.next;
});

//play first generation
~onegenplayerrecord.play;

//window defenition and actions

window = Window("The Fitness Function", Rect(300,200,400,110));
window.view.decorator = FlowLayout(window.view.bounds);
slider = EZSlider(window, 350 @ 20, "score", ControlSpec(0,3,step:1,default:0),
	{ arg slider; score = slider.value;  }
     , labelWidth: 80, numberWidth: 60);
window.view.decorator.left_(310).top_(40); // decorator set to specific location
Button(window, 80@60) // button uses size 50 by 50
    .states_([["Next \n Organism \n & Quote"]])
    .mouseDownAction_({
	if(begingen, {
		"begingen".postln;
		~onegenplayerrecord.reset;
		~onegenplayer.reset;
		~pauseandnext.reset;
		~pauseandnext.play;
		scoreList = List.newClear;
		begingen = false;
		quoting = true;
	},{
		if(scoreList.size < 32,{
				"quoting".postln;
				~pauseandnext.reset;
				~pauseandnext.play;
				scoreList.add(score);
				scoreList.size.postln;
				if(scoreList.size == 32, {
					quoting = false;
					"Fitness scores are ready for creating a new generation".postln;
				});

		},{
				"Fitness scores are ready for creating a new generation".postln;
		});
	});
})
    .resize_(9);
window.view.decorator.left_(210).top_(40); // decorator set to specific location
Button(window, 90@60) // button uses size 50 by 50
    .states_([["Next \n Generation"]])
    .mouseDownAction_({
	if(quoting == false && scoreList.size == 32, {
		~onegenplayer.reset;
		~onegennexter.reset;
		a.injectFitness(scoreList);
		a.nextGeneration;
		~onegenplayerrecord.play;
	},{
		"Quoting for the next generation is not done yet".postln;
	});
})
    .resize_(9);
window.view.decorator.left_(110).top_(40); // decorator set to specific location
Button(window, 90@60) // button uses size 50 by 50
    .states_([["Play \n Generation"]])
    .mouseDownAction_({
	if(quoting == false, {
		~~onegenplayer.reset;
		~onegennexter.reset;
		~onegenplayerrecord.reset;
		~onegenplayer.play;
	},{
		"Quoting for the next generation is not done yet".postln;
	});
})
    .resize_(9);
window.view.decorator.left_(10).top_(40); // decorator set to specific location
Button(window, 90@60) // button uses size 50 by 50
    .states_([["Save \n Generation"]])
    .mouseDownAction_({
	"save generation".postln;
	q = ();
	q[\saved_GA]= a;
	Archive.global.put(\myData,q);
})
    .resize_(9);
window.onClose = {};
window.front;



)


s.record();
s.stopRecording;

s.sendMsg(\n_free, 2151);










s.scope(32,16)

s.scope(16,16,rate: \control);

(

a.genePool.size.do({arg i;
	a.genePool.at(i).zeroGate;
});

)

a.genePool.at(0).triSynth.at(2).lowOctave.connection

a.genePool.at(0).triSynth.at(0).highOctave.connection

a.genePool.at(7).gateIt;

a.injectFitness([1,0,1,1,0,0,0,1]);

a.nextGeneration;

(
// show all control connections
a.genePool.size.do({arg i;
	"GenePool Position: % Audiobus: % Gatecontrol: %".format(i,a.genePool.at(i).audioConnect,a.genePool.at(i).globalEnvBus).postln;
	"Groups Gate: % Synth: % FX: %".format(a.genePool.at(i).gateGrp, a.genePool.at(i).synthGrp, a.genePool.at(i).fxGrp).postln;
	a.genePool.at(i).triSynth.size.do({arg j;
		"triSynth position: %".format(j).postln;
		if(a.genePool.at(i).triSynth.at(j) != nil, {
			a.genePool.at(i).triSynth.at(j).lowOctave.connection.postln;
			a.genePool.at(i).triSynth.at(j).highOctave.connection.postln;
			a.genePool.at(i).triSynth.at(j).midFreq.connection.postln;
			a.genePool.at(i).triSynth.at(j).lowFineTune.connection.postln;
			a.genePool.at(i).triSynth.at(j).highFineTune.connection.postln;
			a.genePool.at(i).triSynth.at(j).lowAmp.connection.postln;
			a.genePool.at(i).triSynth.at(j).midAmp.connection.postln;
			a.genePool.at(i).triSynth.at(j).highAmp.connection.postln;
			a.genePool.at(i).triSynth.at(j).widthLow.connection.postln;
			a.genePool.at(i).triSynth.at(j).widthMid.connection.postln;
			a.genePool.at(i).triSynth.at(j).widthHigh.connection.postln;
		});
	});
});

)

s.record




(

a.genePool.size.do({arg i;
	a.genePool.at(i).build;
});

)


a.genePool.at(0).globalAmpEnv.amplitude.connection;


s.record("/Users/maartenvanoverveldt/Documents/sonology/thesis/supercollider/etude1/testforsave/1.wav");
s.stopRecording;

s.record

Convolve.init;
Convolve.build;













	