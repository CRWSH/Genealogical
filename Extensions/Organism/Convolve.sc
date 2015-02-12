Convolve : FX {
	classvar <audioBusses , <irSpecs, <pan, <synths, fftSize = 2048, group, <>posConv;

	*init{
		posConv = [\normal, \telephone, \djembe, \tube, \spring, \hall, \mr];
		group = Group.tail;

		audioBusses = Dictionary(8);
		audioBusses.add(\normal -> Bus.audio);
		audioBusses.add(\telephone -> Bus.audio);
		audioBusses.add(\djembe -> Bus.audio);
		audioBusses.add(\tube -> Bus.audio);
		audioBusses.add(\spring -> Bus.audio);
		audioBusses.add(\echo -> Bus.audio);
		audioBusses.add(\hall -> Bus.audio);
		audioBusses.add(\mr -> Bus.audio);

		pan = Dictionary(8);
		pan.add(\telephone -> 0 );
		pan.add(\djembe -> 0.25);
		pan.add(\normal -> -0.25);
		pan.add(\tube -> -0.50);
		pan.add(\spring -> 0.50);
		pan.add(\echo -> -0.75);
		pan.add(\hall -> -0.75);
		pan.add(\mr -> 0.75);

		SynthDef(\conv, {
			arg inbus = 15, outbus = 16,fftSize = 2048, irbufnum, pan = 0, amp = 1, wetdry = 1, lfoRate = 0.1,
			phase = 0,
			depth = 2.5,
			predelay = 10,
			maxdelaytime = 0.2,
			fdbAmt = -0.5;
			var in, out, chorus, delays;





			delays = 10;


			in = In.ar(inbus);
/*
			chorus = Mix.fill(delays, { arg i;
				var fdbIn, fdbOut, sig, out;
				fdbIn = LocalIn.ar(1);
				sig = DelayC.ar(in + fdbIn, maxdelaytime, SinOsc.ar(lfoRate, (2pi*i)/delays, depth, predelay) * 0.001) * 1/delays;
				fdbOut =	LocalOut.ar(sig * fdbAmt);
				sig
			});
			*/

			out = (wetdry * PartConv.ar(in, fftSize, irbufnum)) + ((1-wetdry)*in);
			Out.ar(outbus, Pan2.ar(out*amp, pan))
		}).add;

		SynthDef(\pan, {
			arg inbus = 15, outbus = 16, pan = 1;
			Out.ar(outbus, Pan2.ar(In.ar(inbus), pan))
		}).add;

		irSpecs = Dictionary(8);
		{
			var irtelephone, irdjembe, irtube, irspring, irecho, irhall, irmr,  bufsize, s;

			s = Server.local;



			irtelephone = Buffer.read(s, "/Users/maartenvanoverveldt/Documents/sonology/thesis/supercollider/impulseresonses/selection/telephone.wav");
			irdjembe = Buffer.read(s, "/Users/maartenvanoverveldt/Documents/sonology/thesis/supercollider/impulseresonses/selection/Djembe.wav");
			irtube = Buffer.read(s, "/Users/maartenvanoverveldt/Documents/sonology/thesis/supercollider/impulseresonses/selection/tuberadio.wav");
			irspring = Buffer.read(s, "/Users/maartenvanoverveldt/Documents/sonology/thesis/supercollider/impulseresonses/selection/SpringL05.wav");
			irecho = Buffer.read(s, "/Users/maartenvanoverveldt/Documents/sonology/thesis/supercollider/impulseresonses/selection/mode04.wav");
			irhall = Buffer.read(s, "/Users/maartenvanoverveldt/Documents/sonology/thesis/supercollider/impulseresonses/selection/hall.wav");
			irmr = Buffer.read(s, "/Users/maartenvanoverveldt/Documents/sonology/thesis/supercollider/impulseresonses/selection/MR-II.wav");


			s.sync;
			bufsize= PartConv.calcBufSize(fftSize, irtelephone);
			irSpecs.add(\telephone ->  Buffer.alloc(s, bufsize, 1));
			irSpecs.at(\telephone).preparePartConv(irtelephone, fftSize);

			s.sync;
			bufsize= PartConv.calcBufSize(fftSize, irdjembe);
			irSpecs.add(\djembe ->  Buffer.alloc(s, bufsize, 1));
			irSpecs.at(\djembe).preparePartConv(irdjembe, fftSize);

			s.sync;
			bufsize= PartConv.calcBufSize(fftSize, irtube);
			irSpecs.add(\tube ->  Buffer.alloc(s, bufsize, 1));
			irSpecs.at(\tube).preparePartConv(irtube, fftSize);

			s.sync;
			bufsize= PartConv.calcBufSize(fftSize, irspring);
			irSpecs.add(\spring ->  Buffer.alloc(s, bufsize, 1));
			irSpecs.at(\spring).preparePartConv(irspring, fftSize);

			s.sync;
			bufsize= PartConv.calcBufSize(fftSize, irecho);
			irSpecs.add(\echo ->  Buffer.alloc(s, bufsize, 1));
			irSpecs.at(\echo).preparePartConv(irecho, fftSize);

			s.sync;
			bufsize= PartConv.calcBufSize(fftSize, irhall);
			irSpecs.add(\hall ->  Buffer.alloc(s, bufsize, 1));
			irSpecs.at(\hall).preparePartConv(irhall, fftSize);

			s.sync;
			bufsize= PartConv.calcBufSize(fftSize, irmr);
			irSpecs.add(\mr ->  Buffer.alloc(s, bufsize, 1));
			irSpecs.at(\mr).preparePartConv(irmr, fftSize);


			s.sync;
			irtelephone.free;
			irdjembe.free;
			irtube.free;
			irspring.free;
			irecho.free;
			irhall.free;
			irmr.free
		}.fork;
	}

	*build{
		synths = Dictionary(8);

		synths.add(\normal -> Synth(\pan, [\inbus, audioBusses.at(\normal), \outbus, 0, \pan, pan.at(\normal)], group));

		synths.add(\telephone -> Synth(\conv, [\inbus, audioBusses.at(\telephone), \outbus, 0, \irbufnum, irSpecs.at(\telephone).bufnum, \pan, pan.at(\telephone)], group));

		synths.add(\djembe -> Synth(\conv, [\inbus, audioBusses.at(\djembe), \outbus, 0, \irbufnum, irSpecs.at(\djembe).bufnum, \pan, pan.at(\djembe), \amp, 0.5], group));

		synths.add(\tube -> Synth(\conv, [\inbus, audioBusses.at(\tube), \outbus, 0, \irbufnum, irSpecs.at(\tube).bufnum, \pan, pan.at(\tube)], group));

		synths.add(\spring -> Synth(\conv, [\inbus, audioBusses.at(\spring), \outbus, 0, \irbufnum, irSpecs.at(\spring).bufnum, \pan, pan.at(\spring), \wetdry, 0.2], group));

		synths.add(\echo -> Synth(\conv, [\inbus, audioBusses.at(\echo), \outbus, 0, \irbufnum, irSpecs.at(\echo).bufnum, \pan, pan.at(\echo)], group));

		synths.add(\hall -> Synth(\conv, [\inbus, audioBusses.at(\hall), \outbus, 0, \irbufnum, irSpecs.at(\hall).bufnum, \pan, pan.at(\hall), \wetdry, 0.2], group));

		synths.add(\mr -> Synth(\conv, [\inbus, audioBusses.at(\mr), \outbus, 0, \irbufnum, irSpecs.at(\mr).bufnum, \pan, pan.at(\mr), \wetdry, 0.2], group));
	}










}

		