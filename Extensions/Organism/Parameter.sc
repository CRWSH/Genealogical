Parameter {

	//flags
	var controlable, float, <controlled;

	//characteristics
	var minval, maxval, curve, distribution, <>specInstance;

	// build dingen

	var <>connection;

	//currentValue is always between 0 and 1.0
	var <>currentValue;

	//new creates a random currentvalue according to the disdripution function if currentvalue is nil

	*new
	{
		arg con= true, fl= true, min = 0.0, max = 1.0, curval = nil, curv = \lin, dis = \norm;

		^super.new.initParameter(con, fl, min, max, curval, curv, dis)
	} 	// super stands for the superclass, in this case Object

	initParameter
	{
		arg con= true, fl= true, min = 0.0, max = 1.0, curval = nil, curv = \lin, dis = \norm;

		controlable = con;
		float = fl;
		minval = min;
		maxval = max;
		curve = curv;
		distribution = dis;
		currentValue = curval;
		if (currentValue == nil,{this.newRandomValue;});
		if (float,
			{
				specInstance = ControlSpec(minval,maxval, curve);
			},
			{
				specInstance = ControlSpec(minval,maxval, curve, 1.0);
		});

		controlled = false;
	}

	build
	{
		connection = Bus.control;
	}

	map
	{
		^specInstance.map(currentValue);
	}

	connectControlBus
	{ arg bus;
		connection.free;
		connection = bus;
		controlled = true;
	}

	changeControlBus
	{
		connection = Bus.control;
	}

	free
	{
		connection.free;
	}

	mutate
	{
			this.newRandomValue;
	}

	newRandomValue
	{
		case{distribution == \norm}
				{
					currentValue = 1.0.rand;
				}
				{distribution == \qbet}
				{
					if ( 2.rand == 1,
						{
							currentValue = 0.5.exprand(1.0);
						},
						{
							currentValue = 1 - 0.95.exprand(1.0);
						}

					);
				};
	}

}



	