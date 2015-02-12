(

r = Routine({
	loop {
//scope view

s.scope(1,16);

//Convolve.init;
Timing.init;
Envelope.init;
AmpEnv.init;
TriPulseOsc.init;


//Convolve.build;



~fxGrp = Group.head;
~fxGateGrp = Group.head;
~synthGrp = Group.head;
~gateGrp = Group.head;

~audioConnect = Bus.audio;

~timing = Timing(~gateGrp);

~globalEnv = Envelope(~timing.dur.map(), \amp);

~ampEnv1 = Envelope(~timing.dur.map(), \amp);

~ampEnv2 = Envelope(~timing.dur.map(), \amp);

~ampEnv3 = Envelope(~timing.dur.map(), \amp);

~fxEnv1 =  Envelope(~timing.dur.map(), \fx);

~fxEnv2 =  Envelope(~timing.dur.map(), \fx);

~fxEnv3 =  Envelope(~timing.dur.map(), \fx);

~synth1 = TriPulseOsc(~audioConnect,synthgrp: ~synthGrp);

~synth2 = TriPulseOsc(~audioConnect,synthgrp: ~synthGrp);

~synth3 = TriPulseOsc(~audioConnect,synthgrp: ~synthGrp);

~globalAmpEnv = AmpEnv(~audioConnect,fxgrp: ~fxGrp);

"1".yield;

~globalAmpEnv.build();

~synth1.build();

~synth2.build();

~synth3.build().yield;

~globalEnv.build(~globalAmpEnv.amplitude, ~fxGateGrp);

~ampEnv1.build(~synth1.midAmp, ~gateGrp);

~ampEnv2.build(~synth2.lowAmp, ~gateGrp);

~ampEnv3.build(~synth3.highAmp, ~gateGrp);

//~ampEnv1.build(~synth2.lowAmp, ~gateGrp);

//~ampEnv1.build(~synth1.highAmp, ~gateGrp);

~fxEnv1.build(~synth1.midFreq, ~gateGrp);

~fxEnv2.build(~synth2.lowFineTune, ~gateGrp);

//~fxEnv1.build(~synth1.highFineTune, ~gateGrp);

~fxEnv3.build(~synth3.widthLow, ~gateGrp);

//~fxEnv1.build(~synth1.widthMid, ~gateGrp);

//~fxEnv1.build(~synth1.widthHigh, ~gateGrp);

//~fxEnv2.build(~synth1.widthHigh, ~gateGrp);

//~fxEnv2.build(~synth2.highFineTune, ~gateGrp);

//~fxEnv2.build(~synth3.widthMid, ~gateGrp);

"2".yield;

~timing.build(~globalEnv);

~timing.build(~ampEnv1);

~timing.build(~ampEnv2);

~timing.build(~ampEnv3);

~timing.build(~fxEnv1);

~timing.build(~fxEnv2);

~timing.build(~fxEnv3);

"3".yield;

~globalAmpEnv.changeAudioBus(out: 0);

//~audioConnect.yield;

~timing.deltaList.yield;

"Gate it".postln;



~fxGateGrp.set(\gate, 1);
~gateGrp.set(\gate, 1).yield;

"freeing fixed envelopes".postln;

~globalEnv.freeUGens;

~ampEnv1.freeUGens;

~ampEnv2.freeUGens;

~ampEnv3.freeUGens;

~fxEnv1.freeUGens;

~fxEnv2.freeUGens;

~fxEnv3.freeUGens.yield;

"timinig play".postln;

~timing.play.yield;

s.reboot.yield;

	}
});

)

r.next;

(

~fxGateGrp.set(\gate, 1);
~gateGrp.set(\gate, 1);

)

p = {Poll.kr(Impulse.kr(10), ~synth1.midFreq.connection.kr, \low)}.play(s);

p.free;

(

~fxGateGrp.set(\gate, 0);
~gateGrp.set(\gate, 0);

)

(

~timing.play

)


~audioConnect;

s.reboot


~timing.pPar.reset;


x = ~timing.pPar.asStream;

x.next(())

x.reset;

s.scope(16,0,rate: \control);









