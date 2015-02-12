AmpEnv : FX
{
	// characteristics

	var <inAudioBus, <outAudioBus, <>fxGrp, <>amplitude, <>fitAmp;

	//ugen dinges

	var <>ampEnvSynth;

	classvar synthDef;

	*init
	{
		synthDef =	SynthDef(\envamp, {
			arg inbus = 14,
			outbus = 15,
			amp = 0,
			fitamp = 1;
			var in,
			out;

			in =In.ar(inbus);
			out = in * amp * fitamp;
			Out.ar(outbus, out);
		}).add;
	}



	*new
	{
		arg  outaudiobus = 15, fxgrp, amp;

		^super.new.initAmpEnv( outaudiobus, fxgrp, amp);
	// super stands for the superclass, in this case Object
	}

	initAmpEnv
	{
		arg outaudiobus = 15, fxgrp, amp;

		outAudioBus = outaudiobus;
		fxGrp = fxgrp;
		fitAmp = 1;

		if(amp == nil,
			{
				amplitude = Parameter(curval: 0.0);
			},{
				amplitude = Parameter(curval: amp);
		});


	}

	buildParameters
	{arg inaudiobus;

		inAudioBus = inaudiobus;

		// making all control busses

		amplitude.build;


	}


	build
	{


		// builden

		ampEnvSynth = Synth(\envamp, [\inbus, inAudioBus, \outbus, outAudioBus, \fitamp, fitAmp], fxGrp);

		// juiste connecties maken voor alle parameters

		if(amplitude.controlled,
			{
				ampEnvSynth.map(\amp, amplitude.connection);
			},
			{
				amplitude.connection.set(amplitude.map);
				ampEnvSynth.map(\amp, amplitude.connection);
		});
	}

	changeAudioBus
	{ arg in, out;
		if(in != nil,
			{
				ampEnvSynth.set(\inbus, in);
		});
		if(out != nil,
			{
				ampEnvSynth.set(\outbus, out);
		});
	}

	freeUGens
	{
		ampEnvSynth.free;
	}


	free
	{
		//"Amplitude".postln;
		// free control busses

		amplitude.free;

		// free Ugens

		ampEnvSynth.free;

	}

	setFitAmp
	{arg fitamp;
		fitAmp = fitamp;
		ampEnvSynth.set(\fitamp, fitAmp);
	}



}


	