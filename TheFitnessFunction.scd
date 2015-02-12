(
var window, slider, score = 0, scoreList = List.newClear, begingen = true, quoting = false ;

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
		~onegenplayerrecord.reset;
		~onegenplayer.reset;
		~onegennexter.next;
		scoreList = List.newClear;
		begingen = false;
		quoting = true;
	},{
		if(scoreList.size < 32,{
				~onegennexter.next;
				scoreList.add(score);
				if(scoreList.size == 32, {
					quoting = false;
					~onegennexter.next; // zerogating;
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
	q = ();
	q[\saved_GA]= a;
	Archive.global.put(\myData,q);
})
    .resize_(9);
window.onClose = {};
window.front;
)

