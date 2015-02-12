(
// show all control connections
a.genePool.size.do({arg i;
	"GenePool Position: % Audiobus: % Gatecontrol: %".format(i,a.genePool.at(i).audioConnect,a.genePool.at(i).globalAmpEnv.amplitude.connection).postln;
	"Groups Gate: % Synth: % GateFX: % FX: %".format(a.genePool.at(i).gateGrp, a.genePool.at(i).synthGrp, a.genePool.at(i).fxGateGrp, a.genePool.at(i).fxGrp).postln;

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

Convolve.audioBusses.at(\normal).postln;
Convolve.audioBusses.at(\telephone).postln;
Convolve.audioBusses.at(\djembe).postln;
Convolve.audioBusses.at(\tube).postln;
Convolve.audioBusses.at(\spring).postln;
Convolve.audioBusses.at(\echo).postln;
Convolve.audioBusses.at(\hall).postln;
Convolve.audioBusses.at(\mr).postln;

)

(
// just one

	"GenePool Position: % Audiobus: % Gatecontrol: %".format(1,a.genePool.at(1).audioConnect,a.genePool.at(1).globalAmpEnv.amplitude.connection).postln;
"Groups Gate: % Synth: % GateFX: % FX: %".format(a.genePool.at(1).gateGrp, a.genePool.at(1).synthGrp, a.genePool.at(1).fxGateGrp, a.genePool.at(1).fxGrp).postln;

	a.genePool.at(1).triSynth.size.do({arg j;
		"triSynth position: %".format(j).postln;
		if(a.genePool.at(1).triSynth.at(j) != nil, {
			a.genePool.at(1).triSynth.at(j).lowOctave.connection.get.postln;
			a.genePool.at(1).triSynth.at(j).highOctave.connection.get.postln;
			a.genePool.at(1).triSynth.at(j).midFreq.connection.get.postln;
			a.genePool.at(1).triSynth.at(j).lowFineTune.connection.get.postln;
			a.genePool.at(1).triSynth.at(j).highFineTune.connection.get.postln;
			a.genePool.at(1).triSynth.at(j).lowAmp.connection.get.postln;
			a.genePool.at(1).triSynth.at(j).midAmp.connection.get.postln;
			a.genePool.at(1).triSynth.at(j).highAmp.connection.get.postln;
			a.genePool.at(1).triSynth.at(j).widthLow.connection.get.postln;
			a.genePool.at(1).triSynth.at(j).widthMid.connection.get.postln;
			a.genePool.at(1).triSynth.at(j).widthHigh.connection.get.postln;
		});
	});

)

//controls
(

a.genePool.at(1).triSynth.at(0).controls.postln;
a.genePool.at(1).triSynth.at(1).controls.postln;
a.genePool.at(1).triSynth.at(2).controls.postln;


)

// info over de envelopes
(

a.genePool.size.do({arg i;
	a.genePool.at(i).fxEnvs.size.do({arg j;
		a.genePool.at(i).fxEnvs.at(j).minArray.postln;
	});
});




)

(

a.genePool.size.do({arg i;
	a.genePool.at(i).conv.postln;
});


//types of all envelopes


(

a.genePool.at(1).ampEnvs.size.do({arg i;
	a.genePool.at(1).ampEnvs.at(i).busArray.postln;
});

a.genePool.at(1).fxEnvs.size.do({arg i;
	a.genePool.at(1).fxEnvs.at(i).busArray.postln;
});



)



(

a.genePool.at(1).ampEnvs.size.do({arg i;
	a.genePool.at(1).ampEnvs.at(i).minArray.postln;
});

a.genePool.at(1).fxEnvs.size.do({arg i;
	a.genePool.at(1).fxEnvs.at(i).minArray.postln;
});

)


(

a.genePool.at(1).ampEnvs.size.do({arg i;
	a.genePool.at(1).ampEnvs.at(i).maxArray.postln;
});

a.genePool.at(1).fxEnvs.size.do({arg i;
	a.genePool.at(1).fxEnvs.at(i).maxArray.postln;
});

)

(

a.genePool.at(1).ampEnvs.size.do({arg i;
	a.genePool.at(1).ampEnvs.at(i).type.postln;
});

a.genePool.at(1).fxEnvs.size.do({arg i;
	a.genePool.at(1).fxEnvs.at(i).type.postln;
});



)

)

a.genePool.at(1).globalAmpEnv.amplitude.connection;
a.genePool.at(1).globalAmpEnv.outAudioBus;
a.genePool.at(1).globalAmpEnv.fitAmp;










)

a.genePool.at(2).gateIt;

s.scope;





(
a.genePool.size.do({
	arg i;
	a.genePool.at(i).timing.deltaList.postln;
});
)

s.scope(1,16)

(

a.genePool.size.do({
	arg i;
	a.genePool.at(i).globalAmpEnv.ampEnvSynth.postln;

});

)

Archive.global;


(

a.genePool.size.do({
	arg i;
	a.genePool.at(i).globalEnv.type.postln;

});

)



//convs

(

a.genePool.size.do({
	arg i;

	a.genePool.at(i).conv.postln;

});

)

a.genePool.at(1).gateIt;



a.genePool.at(1).initAfterSave(a.genePool.at(1).triSynth, Group.head,Group.head,Group.head, Group.head);

a.genePool.at(1).buildParameters;
a.genePool.at(1).buildSynths;
a.genePool.at(1).buildEnvelopes;
a.genePool.at(1).buildTiming;
a.genePool.at(1).setOutPut;

a.genePool.at().globalEnv.type.postln;











