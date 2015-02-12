GAWorkbench2 {
	//probabilities
	var <>mutationProb, <>coProb;

	//feats
	var <>randomChromosomeFunc, <>fitnessFunc, <>mutationFunc, <>crossoverFunc, <poolSize, chromosomeSize;

	//internal state
	var <>genePool, <fitnessScores, tourPool, tourPoolTemp;

	*new
	{arg argPoolSize, argRandomChromosomeFunc, argFitnessFunc, argMutationFunc, argCrossoverFunc;

		^super.new.init(argPoolSize, argRandomChromosomeFunc, argFitnessFunc, argMutationFunc, argCrossoverFunc);
	}

	init
	{arg argPoolSize, argRandomChromosomeFunc, argFitnessFunc, argMutationFunc, argCrossoverFunc;
		poolSize = argPoolSize ? 100;

		randomChromosomeFunc = argRandomChromosomeFunc;
		fitnessFunc = argFitnessFunc;
		mutationFunc = argMutationFunc;
		crossoverFunc = argCrossoverFunc;

		mutationProb = 0.08;
		coProb = 0.5;

		genePool = List.new;
		fitnessScores = nil ! poolSize;

		this.initGenePool;

	}

	initGenePool
	{
		poolSize.do
		({
			genePool.add(randomChromosomeFunc.value);
		});
	}

	rateFitness
	{
		var tempOrder;

		poolSize.do
		({|cnt|

			fitnessScores[cnt] = fitnessFunc.value(genePool[cnt]);
		});

		tempOrder = fitnessScores.order;
		fitnessScores = fitnessScores[tempOrder].reverse;
		genePool = genePool[tempOrder].reverse;

	}

	nextGeneration
	{
		var tempGenePool = List.new;
		var tempChromosome, splitPoint;
		var tp1, tp2, tour, tempParent1, tempParent2;
		var offspring;

		tourPool = List.newClear;

		poolSize.do({
			arg i;
			if(fitnessScores.at(i) != 0, {
				tourPool.add(i);
			});
		});

		"tourPool: %".format(tourPool).postln;


		tourPoolTemp = tourPool.copy;

		while({ tempGenePool.size < poolSize; },
		{
				//"a".postln;
				tp1 = this.poolChooseFill(tourPoolTemp, tourPool);
				//tourPoolTemp.postln;
				tp2 = this.poolChooseFill(tourPoolTemp, tourPool);
				//tourPoolTemp.postln;

				tour = fitnessScores[[tp1, tp2]];

				if(tour[0] > tour[1], { tempParent1 = genePool[tp1]; }, { tempParent1 = genePool[tp2]; });
				//"b".postln;
				//tourPool;
				tp1 = this.poolChooseFill(tourPoolTemp, tourPool);
				//tourPoolTemp.postln;
				tp2 = this.poolChooseFill(tourPoolTemp, tourPool);
				//tourPoolTemp.postln;

				tour = fitnessScores[[tp1, tp2]];

				if(tour[0] > tour[1], { tempParent2 = genePool[tp1]; }, { tempParent2 = genePool[tp2];});
				//"c".postln;
				offspring = this.mateParents(tempParent1, tempParent2);

				tempGenePool.add(offspring);
		});

		//freeing all organism of previous builds

		/*
		//"d".postln;
		poolSize.do({arg i;
			genePool.at(i).free;
		});
		*/

		genePool = tempGenePool;
		fitnessScores = nil ! poolSize;
	}

	mateParents
	{arg p1, p2;


		var offspring;


		if(coProb.coin,
		{
				var offwithoutmut;
				"beforecross".postln;
				offwithoutmut = crossoverFunc.value(p1,p2);

				if(mutationProb.coin, {
					//"beforemut".postln;
					offspring = mutationFunc.value(offwithoutmut);
		            //moet direct gefreed worden omdat dit nooit meer gebruikt zal worden
					offwithoutmut.free;
				},{
					offspring = offwithoutmut;
				});


		},{
				if(mutationProb.coin, {
					if(0.5.coin,
						{
							//"beforemut".postln;
							offspring = mutationFunc.value(p1);},
						{
							//"beforemut".postln;
							offspring = mutationFunc.value(p2);
					});
				},{
					if(0.5.coin,
						{
								//"beforecopy".postln;
								offspring = p1.copyOtherBusGroup
						},{
								//"beforecopy".postln;
								offspring = p2.copyOtherBusGroup;
						});

					});
		});
		^offspring;
	}

	injectFitness
	{|argFitness|

		var tempOrder;

		if(argFitness.size != poolSize,
		{
			"poolSize is % but supplied fitness array has a size of %. Can't use these values.".format(poolSize, argFitness.size).error;
		},
		{
			fitnessScores = argFitness;
			tempOrder = fitnessScores.order;
			fitnessScores = fitnessScores[tempOrder].reverse;
			genePool = genePool[tempOrder].reverse;

		});
	}

	poolChooseFill{
		var choosen;

		choosen = tourPoolTemp.removeAt(tourPoolTemp.size.rand);
		if(tourPoolTemp.size == 0, {tourPoolTemp = tourPool.copy;});
		^choosen;
	}


}











	