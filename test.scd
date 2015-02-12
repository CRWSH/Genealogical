
s.scope(2,16);

[1].choose

a = 0;

[3.do({a=a+1});,2.do({a=a+1});,1.do({a=a+1});].choose;


a= TriPulseOsc(0);

a.build;

a.freeIt();


a.genePool.at(0).timing.deltaList.postln;



a.midFreq.connection.set(10000)
a.lowOctave.connection.set(1)
a.highOctave.connection.set(0)
a.lowAmp.connection.set(0);
a.highAmp.connection.set(0.0000000);
a.midAmp.connection.set(0);
a.widthHigh.connection;
a.highFineTune.connection.set(1);


a.build
a.gateIt();

c = Group.tail;
d = Bus.control;

b = Envelope(outputcontrolbus: d,gategrp: c);

b.build;

c.set(\gate, 1);

b.outputControlBus

s.scope(12,1,rate: \control);

f =  {Out.ar(1, Pulse.ar(23000), 0.001)}.play

f.free


(

~stat = Routine({
	loop {

		// init
		a = TriPulseOsc(15);
		"1".postln.yield;
		b = Bus.control;
		"2".postln.yield;
		c = Group.tail;
		"3".postln.yield;
		d = Envelope(outputcontrolbus: b,gategrp: c);
		"4".postln.yield;
		e = Bus.audio;
		"5".postln.yield;
		f = AmpEnv(15,0,amp: 1);
		"6".postln.yield;
		f.amplitude.changeControlBus(b);
		"7".postln.yield;


		// build

		f.build;
		"8".postln.yield;
		d.build;
		"9".postln.yield;
		a.build;
		"10".postln.yield;
		f.changeAudioBus(out: 0);
		"11".postln.yield;
		d.envSynth.set(\gate, 1);
		"12".postln.yield;

	};
});

)

~stat.next;

a = PulseOrg();
a.triSynth
a.build;
a.gateIt;
a.zeroGate;
a.freeIt;

a= Parameter();

a.map
a.mutate;

a = TriPulseOsc()

a.lowOctave.map;
a.highOctave.map;
a.midFreq.map;
a.lowFineTune.map;
a.highFineTune.map;
a.lowAmp.map;
a.midAmp.map;
a.highAmp.map;
a.widthLow.map;
a.widthMid.map;
a.widthHigh.map;

a = PulseOrg();

a.aantalSynths
a.mutate;
a.triSynth;


a.triSynth.at(2).lowOctave.map;
a.triSynth.at(2).highOctave.map;
a.triSynth.at(2).midFreq.map;
a.triSynth.at(2).lowFineTune.map;
a.triSynth.at(2).highFineTune.map;
a.triSynth.at(2).lowAmp.map;
a.triSynth.at(2).midAmp.map;
a.triSynth.at(2).highAmp.map;
a.triSynth.at(2).widthLow.map;
a.triSynth.at(2).widthMid.map;
a.triSynth.at(2).widthHigh.map;

a.triSynth.at(1).lowOctave.map;
a.triSynth.at(1).highOctave.map;
a.triSynth.at(1).midFreq.map;
a.triSynth.at(1).lowFineTune.map;
a.triSynth.at(1).highFineTune.map;
a.triSynth.at(1).lowAmp.map;
a.triSynth.at(1).midAmp.map;
a.triSynth.at(1).highAmp.map;
a.triSynth.at(1).widthLow.map;
a.triSynth.at(1).widthMid.map;
a.triSynth.at(1).widthHigh.map;

a.triSynth.at(0).lowOctave.map;
a.triSynth.at(0).highOctave.map;
a.triSynth.at(0).midFreq.map;
a.triSynth.at(0).lowFineTune.map;
a.triSynth.at(0).highFineTune.map;
a.triSynth.at(0).lowAmp.map;
a.triSynth.at(0).midAmp.map;
a.triSynth.at(0).highAmp.map;
a.triSynth.at(0).widthLow.map;
a.triSynth.at(0).widthMid.map;
a.triSynth.at(0).widthHigh.map;


1!2

y = List[ 1, 2, 3 ];
y.removeAt(y.size.rand);
y.postln;
y.size

y.insert(y.size.rand, 4);

(2.rand < 1)

1.0.rand

a = Parameter();
a.map

b = a.copy;

b.newRandomValue;

a.map
b.map

d = Bus.audio;
e =


a = PulseOrg(List[TriPulseOsc(), TriPulseOsc(), TriPulseOsc()]);
b = PulseOrg(List[TriPulseOsc(), nil, nil]);

a.triSynth;
b.triSynth;

[a.triSynth.at(2).lowOctave.map, a.triSynth.at(2).highOctave.map, a.triSynth.at(2).midFreq.map,a.triSynth.at(2).lowFineTune.map,a.triSynth.at(2).highFineTune.map,a.triSynth.at(2).lowAmp.map,a.triSynth.at(2).midAmp.map,a.triSynth.at(2).highAmp.map,a.triSynth.at(2).widthLow.map,a.triSynth.at(2).widthMid.map,a.triSynth.at(2).widthHigh.map];
[b.triSynth.at(2).lowOctave.map, b.triSynth.at(2).highOctave.map, b.triSynth.at(2).midFreq.map,b.triSynth.at(2).lowFineTune.map,b.triSynth.at(2).highFineTune.map,b.triSynth.at(2).lowAmp.map,b.triSynth.at(2).midAmp.map,b.triSynth.at(2).highAmp.map,b.triSynth.at(2).widthLow.map,b.triSynth.at(2).widthMid.map,b.triSynth.at(2).widthHigh.map];

c = a.crossover(b);

c.triSynth

[c.triSynth.at(2).lowOctave.map, c.triSynth.at(2).highOctave.map, c.triSynth.at(2).midFreq.map,c.triSynth.at(2).lowFineTune.map,c.triSynth.at(2).highFineTune.map,c.triSynth.at(2).lowAmp.map,c.triSynth.at(2).midAmp.map,c.triSynth.at(2).highAmp.map,c.triSynth.at(2).widthLow.map,c.triSynth.at(2).widthMid.map,c.triSynth.at(2).widthHigh.map];

[b.triSynth.at(2).lowOctave.map, b.triSynth.at(2).highOctave.map, b.triSynth.at(2).midFreq.map,b.triSynth.at(2).lowFineTune.map,b.triSynth.at(2).highFineTune.map,b.triSynth.at(2).lowAmp.map,b.triSynth.at(2).midAmp.map,b.triSynth.at(2).highAmp.map,b.triSynth.at(2).widthLow.map,b.triSynth.at(2).widthMid.map,b.triSynth.at(2).widthHigh.map];




[a.lowOctave.map,a.highOctave.map,a.midFreq.map,a.lowFineTune.map,a.highFineTune.map,a.lowAmp.map,a.midAmp.map,a.highAmp.map,a.widthLow.map,a.widthMid.map,a.widthHigh.map];
[b.lowOctave.map,b.highOctave.map,b.midFreq.map,b.lowFineTune.map,b.highFineTune.map,b.lowAmp.map,b.midAmp.map,b.highAmp.map,b.widthLow.map,b.widthMid.map,b.widthHigh.map];
[c.lowOctave.map,c.highOctave.map,c.midFreq.map,c.lowFineTune.map,c.highFineTune.map,c.lowAmp.map,c.midAmp.map,c.highAmp.map,c.widthLow.map,c.widthMid.map,c.widthHigh.map];



c = a.crossover(b,e,d);

a= Envelope();
a.attack;
a.sustain;
a.release;


d = Group.tail;
e = Bus.audio;

s.scope(2,16);

Convolve.init;
Convolve.build;
Timing.init;

a = Timing();

b = Timing();

Timing.durMax = 2.0;
Timing.durMin = 0.5;



c = List.newClear;
b = Plotter(\theplot);
b.plotMode = \points;
b.superpose = true;

a.genePool.size.do({
	arg i;
	c.add(a.genePool.at(i).timing.calAccumDelta);
});
b.value = c.asArray;
b.refresh;
b.makeWindow;

GUI.skin.plot.plotColor = (10..0).normalize(0.1, 1).collect { |i| Color.new(i,0,0)}++(10..0).normalize(0.1, 1).collect { |i| Color.new(0,i,0)}++(10..0).normalize(0.1, 1).collect { |i| Color.new(0,0,i)};
GUI.skin.plot.background = Color.grey;












c = Plotter(\theplot);
c.plotMode = \points;
c.refresh;

c.value = a.calAccumDelta;

a.mutate;
a.dur.map();
a.deltaList.asArray.plot;
a.deltaListRel;
a.deltaListRelLong.asArray.plot;

b.dur.map();
b.deltaList;
b.deltaListRel;
b.deltaListRelLong;

c= a.crossover(b);

c.dur.map();
c.deltaList;
c.deltaListRel;
c.deltaListRelLong;

Timing.durMin = 0.05;
Timing.durMax = 0.15;
Timing.deltaMax = 10.0;
Timing.totalDur = 30.0;

a.updateChar;
b.updateChar;





a = PulseOrg();
a.build;
a.gateIt;
a.zeroGate;
a.free;

a.timing.dur.map();
a.timing.deltaList;
a.timing.deltaListRel;

a.timing.play;


c = PulseOrg();
c.build;
c.gateIt;
c.zeroGate;
c.free;

c.timing.dur.map();
c.timing.deltaList;
c.timing.deltaListRel;

c.timing.play;



d = a.crossover(c);
d.build;
d.gateIt;
d.zeroGate;

d.timing.dur.map();
d.timing.deltaList;
d.timing.deltaListRel;

d.timing.play;

a = Pseq([0,Pseq([1],inf)],1).asStream;

a.next;

Env.linen(0.005,0.001,0.100, curve: -5).test.plot;







a.timing.deltaList;
a.timing.play;
a.timing.dur.map;

b = a.mutate;
b.timing.build;

b.build;
b.gateIt;
b.zeroGate;
b.timing.play;
b.timing.dur.map;
b.free;

(
b.timing.play;
a.timing.play;
)

b.mutate;
b.dur.map;
b.deltaList;
b.deltaListRel;


a = 10;
b = a;
a = 5;

a
b

0.rand







b.timing.play;

b = a.copyOtherBusGroup;
b.build;
b.gateIt;



b.deltaListRel;
c.deltaListRel;

c.deltaListRel = 90;

c = b.deepCopy;




b.deltaList;

b.dur.map();

b.attack
b.release;
b.sustain;





a.genePool.at(0).setOutPut;


(
p = Pbind(
    \type, \set,    // This tells it we'll be setting parameters of an existing node...
    \id, 1105,    // ...this tells it whose parameters we'll be setting
    \args, #[\amp],  // and this tells it which parameters to set
	\dur, 0.25,
	\amp, Env.perc(releaseTime: 0.15);
).play;
)

p.stop;



b = a.copyOtherBusGroup;


b = a.mutate;

a.triSynth;
b.triSynth;

Bus.control


b = PulseOrg();
b.build;
b.gateIt;
b.zeroGate;
b.freeUGens;
b.free;

c = b.mutate;

c = a.crossover(b);
c.build;
c.gateIt;
c.zeroGate;
c.free;

0.5.coin;



a = 7;
b = a.copy;

b = 6;

a
b


a = GAWorkbench2();

a.genePool.at(2);
a.
a.rateFitness;

a.fitnessScores;

a.nextGeneration;


(
100.do({
	a.rateFitness;
	a.nextGeneration;
});
)



a = List[1,2,3];

a.insert(3,0);

(
	a.triSynth.size.do({arg j;
		"GenePool position: % and triSynth position: %".format(i, j).postln;
		if(a.triSynth.at(j) != nil, {
			a.triSynth.at(j).lowOctave.currentValue.postln;
			a.triSynth.at(j).highOctave.currentValue.postln;
			a.triSynth.at(j).midFreq.currentValue.postln;
			a.triSynth.at(j).lowFineTune.currentValue.postln;
			a.triSynth.at(j).highFineTune.currentValue.postln;
			a.triSynth.at(j).lowAmp.currentValue.postln;
			a.triSynth.at(j).midAmp.currentValue.postln;
			a.triSynth.at(j).highAmp.currentValue.postln;
			a.triSynth.at(j).widthLow.currentValue.postln;
			a.triSynth.at(j).widthMid.currentValue.postln;
			a.triSynth.at(j).widthHigh.currentValue.postln;
		});
	});

)

(
	b.triSynth.size.do({arg j;
		"GenePool position: % and triSynth position: %".format(i, j).postln;
		if(b.triSynth.at(j) != nil, {
			b.triSynth.at(j).lowOctave.connection.postln;
			b.triSynth.at(j).highOctave.connection.postln;
			b.triSynth.at(j).midFreq.connection.postln;
			b.triSynth.at(j).lowFineTune.connection.postln;
			b.triSynth.at(j).highFineTune.connection.postln;
			b.triSynth.at(j).lowAmp.connection.postln;
			b.triSynth.at(j).midAmp.connection.postln;
			b.triSynth.at(j).highAmp.connection.postln;
			b.triSynth.at(j).widthLow.connection.postln;
			b.triSynth.at(j).widthMid.connection.postln;
			b.triSynth.at(j).widthHigh.connection.postln;
		});
	});

)


(
a = Routine({
	b.reset;
	0.1.yield;
	"a".postln;
	b.play;
});

b = Routine({
	0.1.yield;
	a.reset;
});
)

a.play;


a = List[1,2,3,4,5];

b = {
	arg list;
	var choosen;

	choosen = list.removeAt(list.size.rand);
	choosen
};

c = b.value(a);
a


a.put(0, 1000);

b

2.rand;

1.rand;

a = [0,1,2,3]


s.scope(1,16,rate: \control);
s.scope(8,16);
a.genePool.at(11).gateIt;



Envelope.init;

a = Envelope(1.0, \sharp, Group.tail);

a.build(1);
a.build(2);
a.build(3);


{Out.kr([0,2,4,6,8,10],SinOsc.kr(1))}.play;

Timing.init;

(

p = Pbind(\instrument, \pbindcontroller,
				\group, Group.tail,
				\outbus, [0,2,4,6,8],
				\dur, Pseq([0,2,4,6,8], 1),
				\attack, 0.5,
				\sustain, 0.001,
				\release, 0.5,
	\amp, [0.1,0.2,0.3,0.4,0.5]) ;

)

p.play;
p.reset;

a = 1.0.rand;
b = a.rrand(aNumber: 1.0);

a = Dictionary(0);

a.add(\bla -> 26);
a.add(\drol ->29);

(

b = Bus.control;

a = {
	Out.ar(0, SinOsc.ar(mul: In.kr(b)))
}.play

)

b.set(0.0);


c = {
	Out.kr(b, SinOsc.kr(0.1,mul: 0.5, add: 0.5))
}.play;

s.scope(16,0,rate: \control);

Timing.init;
Envelope.init;


a = Parameter(min: 0, max: 10);
b = Parameter();

c = Group.new;

d = Timing(c);

d.deltaList;
d.dur.map();

e = Envelope(d.dur.map(), \amp, c);

e.type;

e.build(a);
e.build(b);

a.connection;
b.connection;

d.build(e);

Envelope.types.at(e.type) == \lin;

d.play;


c.set(\gate, 1);

(

p = Pbind(\instrument, \pbindlin,
				\group, c,
				\outbus, [0,2,4,6,8],
				\dur, Pseq([0,2,4,6,8], 1),
				\attack, 0.5,
				\sustain, 0.001,
				\release, 0.5,
	\min, 0,
	\max, [1,10,100,1000,10000],
	\amp, 1.0) ;

)

p.reset;
p.play;


f = TriPulseOsc(synthgrp: Group.new);

f.controls;

// testing pulseorg

Timing.init;
Envelope.init;
Convolve.init;
Convolve.build;

a = PulseOrg();
b = PulseOrg();

c = a.crossover(b);

b = a.mutate;



a.build;
b.build;

(
a.timing.dur.map.postln;
b.timing.dur.map.postln;
)


(
a.timing.deltaList.postln;
b.timing.deltaList.postln;
//c.timing.deltaList.postln;
)

a.timing.play;
b.timing.play;
c.timing.play;

a.globalAmpEnv.drive.currentValue;
a.globalAmpEnv.freq.currentValue;
a.globalAmpEnv.rq.currentValue;
a.globalAmpEnv.wet.currentValue;

b.globalAmpEnv.drive.currentValue;
b.globalAmpEnv.freq.currentValue;
b.globalAmpEnv.rq.currentValue;
b.globalAmpEnv.wet.currentValue;





a.build;
a.gateIt;
a.zeroGate;


b.build;
b.gateIt;
b.zeroGate;

c.build;
c.gateIt;
c.zeroGate;

a.timing.dur.map;
a.timing.deltaList;

a.genePool.at(0).globalAmpEnv.amplitude.connection.free;



i = 3;
a = 2;
q = ();
q[(\saved_GA++i).asSymbol]= a;
Archive.global.put(\myData,q);



i = 3;
q = Archive.global.at(\myData);
a = q[\saved_GA3]




q = ();
q[\save] = a.deepCopy;
Archive.global.put(\gen36r3,q);
Archive.write("archiveGA3.txt");


q = ();
q[\save] = nil;
Archive.global.put(\gen11r5,q);
Archive.write("archiveGA.txt");


Archive.global

Archive.write("archiveGA.txt");

Archive.read("archiveGA3.txt");

Archive.global

q = Archive.global.at(\gen26r6);
a = q[\save].deepCopy;

Archive.clear;

a.genePool.at(28).globalEnv.type;


s.record;
s.stopRecording;

a.genePool.at(20).free;
a.genePool.at(26).free;


a = PulseOrg();

a.buildSynths;
a.buildEnvelopes;
a.gateIt;
a.zeroGate;
a.freeEnvelopes;

a.genePool.at(0).envelopesBuild;

a = Envelope(1.0, \fx);
b = Envelope(1.0, \fx);

c = a.crossover(b, 1.0);

a.min;
a.max;
a.type;

b.min;
b.max;
b.type;



c.min;
c.max;
c.type;


Envelope.init;

g = Group.tail;

c = Bus.audio;

a = TriPulseOsc(synthgrp: g);

a.controls;

b = TriPulseOsc(synthgrp: g);

a.controls;

a.mutate;

b.controls;

a.controls.atFail(\highFineTune, {});

a.controls.keys


d = ();
d.put(\abc, 11);
d.at(\abc);
d.at(\ab);
d.removeAt("abc");
a.controls.at(\lowAmp);

a

b

d= a.crossover(b, c, g);

d.controls;


(
// class inits

Convolve.init;
Timing.init;
Envelope.init;
AmpEnv.init;
TriPulseOsc.init;

)

Convolve.build;

s.scope(35, 0, rate: \control);

s.scope(1,16);

a = PulseOrg();

a.timing.deltaList
a.free;

a.buildSynths;
a.buildEnvelopes;
a.buildTiming;
a.setOutPut;
a.gateIt;
a.zeroGate;
a.freeEnvelopes;
a.timing.play;
a.timing.onePlay;

a.timing.deltaList


a.timing.pBinds

b = PulseOrg();
b.timing.deltaList;
b.free;

b.buildSynths;
b.buildEnvelopes;
b.buildTiming;
b.gateIt;
b.zeroGate;
b.setOutPut;
b.freeEnvelopes;
b.timing.play;
b.timing.onePlay;

b.free;

b.timing.deltaList;

c = a.crossover(b);
c.timing.deltaList;

c.buildSynths;
c.buildEnvelopes;
c.buildTiming;
c.gateIt;
c.zeroGate;
c.freeEnvelopes;
c.timing.play;

(

c.triSynth.size.do({arg j;
		"triSynth position: %".format(j).postln;
		if(c.triSynth.at(j) != nil, {
			c.triSynth.at(j).lowOctave.connection.postln;
			c.triSynth.at(j).highOctave.connection.postln;
			c.triSynth.at(j).midFreq.connection.postln;
			c.triSynth.at(j).lowFineTune.connection.postln;
			c.triSynth.at(j).highFineTune.connection.postln;
			c.triSynth.at(j).lowAmp.connection.postln;
			c.triSynth.at(j).midAmp.connection.postln;
			c.triSynth.at(j).highAmp.connection.postln;
			c.triSynth.at(j).widthLow.connection.postln;
			c.triSynth.at(j).widthMid.connection.postln;
			c.triSynth.at(j).widthHigh.connection.postln;
		});
	});

"amplitude".postln;
c.globalAmpEnv.amplitude.connection.postln;

)




c.triSynth.at(2).controls;

c.globalEnv.gateArray;


c.fxEnvs.at(6).max;

a = { Poll.kr(Impulse.kr(10), Line.kr(0, 1, 1), \test) }.play(s);

a.free;

{~synth1.lowAmp.connection.kr(1,7).poll(Impulse.kr(10), \bus7)}.play(s);

b = Bus.audio;


p = {Poll.kr(Impulse.kr(10), ~synth1.lowAmp.connection.kr, \low)}.play(s);

p.free;

d = {~audioConnect.ar(1,0)}.play

d.free;

~audioConnect.get


(
~synth1.lowAmp.connection.get;
~synth1.midAmp.connection.get;
~synth1.highAmp.connection.get;
)


(

a.genePool.size.do({arg i;
	a.genePool.at(i).globalEnv.type.postln;
});

)

s.scope;

a.genePool.at(0).globalEnv.gateArray;

a.genePool.at(2).gateIt;

Synth(\sineperc);

a = List[\del, \add, \pos];

a.removeAt(a.indexOf(\add));

a

s.stopRecording;

PulseOrg.possibleMut;

a= Synth(\sineperc);


a = nil;

a

a.free;

s.nextNodeID;

o = Server.local.options;

o.maxNodes;


100000.do({s.nextNodeID});

s.next

Timing.durMax;

Platform.userAppSupportDir;

Envelope.ampEnvs;

a = Synth(\pulse, [\outbus, 1]);

a.set(\midfreq, 50);

a.set(\amp, 0.1);

a.set(\highfinetune, 1.005);

a.set(\highamp, 0.75);

a.set(\midamp, 0.75);

s.scope;

a.free;


Saw.ar

((1-(0.5.exprand(1.0)))*(10000-50))+50

((1-(0.95.exprand(1.0)))*(10000-50))+50


g = 6.asWarp;

(g.map(0.5)*10000)+50

PulseOrg.possibleMut;
Envelope.ampEnvs;

a.genePool.at(24).gateIt;


temporal

other direction

conv timing

impulse

g4_4

g3_4

g2_3

3 2 4 5 7 9 12 13 19 22 24 25 28

2 0 1 8 11 14 15 16 18 23

g1_1

3 28 24

2 3 5 6 7 8 11 12 14 15 18 21 23

g1





one direction is 0.75 1.37 1.5


g6_2 11 2 4 5

g5_3 2 6 8 13 15 18

g4_2 2 9 18 31

g3_3 4 5

g18 0 5 19 30

g1_4 1 27

g2_4 0 3 /6 8 9 10 11 13 15 17 19 21 /24 28 29 30





divers

impulse sharp
alles op muataion

g6_2

g5_2

g4_2

g3_1 slow on


niet diverse
impulse, sharp
trisynth, time, env

g4_4 11 15 26

g4_3 10 12 13

g4_2 6 10

g4_1 /2 17 29

g3_1 10 22 28

g3_2 6 /7 /17

g3_3  3 11 /19 /24

g3_4 13 /19 /20 22

g2_1 4 16 23

g2_2 1 7 (25)

g2_3 /2 19 25 27

g2_4 22 24

9 10 11 19

g1_1 0 12 28 30

g1_2 3 18 23

g1_3 10 15 18 26

g1_4 5 7 18 29































