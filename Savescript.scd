
(

Archive.clear;
q = ();
q[\save] = a.deepCopy;
q[\durMin] = Timing.durMin.deepCopy;
q[\durMax] = Timing.durMax.deepCopy;
q[\deltaMax] = Timing.deltaMax.deepCopy;
q[\totalDur] = Timing.totalDur.deepCopy;
Archive.global.put(\gen,q);
Archive.write("tempd2g8_4.txt");

)

(

r = ();
r[\scorelist] = ~scoreList.deepCopy;
Archive.global.put(\hen,r);
Archive.write("testgen1r1_1");

)








)

(

Archive.read("r1_1gen1.txt");
q = Archive.global.at(\gen);
a = q[\save].deepCopy;
Timing.durMin = q[\durMin].deepCopy;
Timing.durMax = q[\durMax].deepCopy;
Timing.deltaMax = q[\deltaMax].deepCopy;
Timing.totalDur = q[\totalDur].deepCopy;

)

Archive.global;



