# Genealogical
SuperCollider code to create sound groups (populations) using a Genetic Algorithm (Nature Toolkit Quark)

In the AfterSavev1.1 script you can find a GUI where you can create generation after generation by doing the fitness selection manually. It uses classes definied in the extension. To load these classes, theses class-files have to be in the extension directory of SuperCollider. More Information about the exact working principle you can find in next chapter (a part of my original thesis about this subject) .

2.3. Implementation of the Genetic Algorithm Application

2.3.1. Structure of the Synthesis Method

Figure 2.3: The Synthesis Structure of an Individual Organism
The total implementation of the GA application was built using Supercollider
(Appendix: GA for Sound Organism v2.0). Before the implementation of a GA can
be started, it is crucial to know the structure of the synthesis method. The genetic
presentation and the way the recombination and mutation operators have to be
implemented depend on the definition of the structure. If you look at figure 2.3,
everything within the dotted line is organism-specific and has to be defined in the
genetic presentation. The eight convolution Supercollider Synths outside the
dashed line contain a fixed pan position and an Impulse response whit which the
sound is convolved. Some impulse responses originate from old audio-related
electronic equipment: spring reverb, a tape delay, a telephone horn and a tube
amplifier. Other responses are taken from some room acoustics. The parameters
within these convolution Synths are the same for every organism. An individual
organism is composed out of one to three Synths blocks (figure 2.3). These synths
contain three Ugens. Various types are possible: Pulse, Saw, SinOsc, Dust and
Tgrain. The Synth blocks have a maximum of eleven parameters. Three of them
control the amplitude of the Ugens. There are a maximum of five parameters that
are related to speed: frequency, play rate or average impulses per second. The rest
of the parameters are Ugen specific: pulse width, center position of buffer, duration
of the grain, etc. All the synth blocks or connected to a gate that contains an
amplitude parameter. This gate is connected to one of the eight convolution Synths,
which one is stored in the genetic code.

The dotted line contains three types of parameters. The type and ranges of the
parameters are fixed settings. As these are the same for every organism, they are
not stored in the genetic representation. For the first type, the parameter is a fixed
value and is not controlled by an external mechanism. As to the second type, the
parameter is either a fixed value or controlled by an envelope. Logically, the last
type will always be controlled by an envelope. For every parameter, its values and
settings that determine whether it is controlled by an envelope and, if so, by which
one, are stored in the genetic code of the organism. Every organism has nine
different envelopes, each of which can vary between seven different envelope
types. The genetic representation contains the type of each envelope and some
additional time and level values that are specific to the type. ControlSpec is used to
adjust the ranges so as to make it possible for one envelope to control more than
one parameter. Each generation has a specific duration, which can changed by the
user of the application (see dur gen setting in figure 2.4). A sound is played at least
once during a generation, but can also occur more often. How many times a sound
is played, and when, is saved in the genetic representation of the organism.
In the first version (Appendix: GA for Sound Organisms v1.0) of my implementation
the genetic representation was stored in a List (an Array with a variable length). This
became problematic when changes had to be made to the structure of the synthesis
method because every parameter or setting had a fixed place in the List. To make it
easier to make dynamic changes in the configuration of the synthesis structure or
other parts of the application, it was decide to use the object-oriented capabilities
in Supercollider. Another option would have been to use dictionaries, an unordered
collection type of Supercollider, but I only learned this later on in the process.
A possible improvement of the current synthesis technique is allowing more change
in the configuration of the structure. The only configurational settings the genetic
presentation contains, is the number of Synth blocks used, which envelopes control
which parameters and to which convolution synth the gate is connected to. The
difference between the structural configuration of different organisms is only
determined by these settings.

The objective of the Genetic Programming algorithm is to use induction to devise a
computer program. This is achieved by using evolutionary operators on candidate
programs with a tree structure to improve the adaptive fit between the population
of candidate programs and an objective function. An assessment of a candidate
solution involves its execution (Browlee, 2011, p. 100).
The objective of the Gene Expression Programming algorithm is to improve the
adaptive fit of an expressed program in the context of a problem specific cost
function. This is achieved through the use of an evolutionary process that operates
on a sub-symbolic representation of candidate solutions using surrogates for the
processes (descent with modification) and mechanisms (genetic recombination,
mutation, inversion, transposition, and gene expression) of evolution (Brownlee,
2011, p. 134).

A Genetic Programming (GP) and a Gene Expression Programming (GEP) algorithm
are both more advanced methods than a GA. The genetic representation of an
organism within these two algorithms are programs on them selfs. These algorithms
are designed to evolve this programs according biologic evolution principles. The
create a program, the GP and GEP algorithm can use a list of operators, multiple
inputs, multiple ouputs and parameters. What these ingredients are and the location
of them in the genetic presentation is determined by the algorithms. These
algorithms are better suited to achieve a more flexible synthesis structure than the
implemented application. Therefore the possible sounds this flexible synthesis
structure can produce, is also larger. An example where a GEP algorithm (personal
communication with A. Allik, January 30, 2014) is use to produce sound organisms,
is tehis' Live Coded Evolution (Allik, 2013).

2.3.2. The Genetic algorithm

All the information of an organism is stored in an object of the class PulseOrg which
contains sub-objects of the class type Timing, Envelope, Conv, TriPulseOsc (Synth in
figure 2.3 ) and AmpEnv (gate in figure 2.2). The sub-objects contain relevant parts
of the genetic representation. The amount of TriPulseOsc objects varies between
one and three and is dependent on the number of Synths blocks present in the
configuration of the synthesis structure (a characteristic of the organism). The nine
Envelope objects inside the PulseOrg object corresponds with to the number of
envelopes present in the predefined structure. For the other sub-objects ,only one is
stored in PulseOrg. The form of the genetic representation is different for every subclass.
Therefore all the sub-classes contain their own specific crossover and mutation
functions. When two organisms reproduce, all the sub-class crossover functions are
executed. Only one sub-class is chosen and its matching mutation function is
completed. Which one is determined at random. The reason why only one is
chosen, corresponds to a typical implementation of a GA where the chance for a
mutation to occur is 1/L, with L referring to the amount of components contained in
the genetic representation. Apart from genetic operator functions and
representation, the classes also contain functions for initialization, building the
synthesis structure on the Supercollider server, freeing the synthesis structure,
playing individual organisms and total populations, etc.

In the implemented algorithm, whenever a generation is calculated, the old
population is replaced entirely. Hence, an organism is only present during one
generation. For the calculation of new generations I used an external Supercollider,
the GAWorkBench of the NatureToolkit (Bozurt,2009), which works as follows. All
the organisms with a fitness score higher than zero are collected in an temporary
pool array. Two parents are used to reproduce one organism. For the selection of
one parent, two organism are removed from the temporary pool array. The
organism with the highest fitness score is selected as parent. Hence, to calculate
one new organism, four organisms are removed from the pool collection. When the
temporary array is empty, it is refilled with the initial content. This process is
repeated until an entirely new generation has been created, which depends on the
pool size setting. This selection technique ensures that the number of times an
organism is selected as a possible parent is approximately the same for all the
organisms.


Figure 2.4: the GUI of the application

The user performs the fitness selection by giving every organism in the population a
score between zero and three. There is no automatic audio analysis process that
determines the fitness of a given organism. The fitness evaluation is done entirely
by the user. If he attributes a score of zero, the organism will not be used in the
creation of a next generation and will not be heard when the total generation is
played. If a sound receives a score of three, it is more likely to reproduce than a
sound with a score of two or one. The GA is in thesis used as a method for
transforming sound groups (a population). The problem solver capabilities of a GA
are used as a guide to create a situation where the composer perceives, during the
transformation, a splitting of one sound group into more groups. To achieve this
situation (thoroughly discussed in chapter 5) many successive and parallel
generations are required. To reduce the time-consuming nature of the manual
selection procedure, a population size of 32 was chosen. This is small in comparison
with a typical GA implementation, if used as a problem solver. Most examples of
Evolutionary Algorithms used in the book Clever Algorithms (Brownlee, 2011) have
a population size of 100. If the synthesis structure of 32 organisms is created on the
Supercollider Server, the required CPU power is around 50%. Therefore the
population limit for this synthesis structure is approximately 64.
While testing the application and composing of the first piece 'A Nursery Etude', it
was concluded that a population size of 32 is too small, in most situations, to place
the emphasis on the group as an entity and not on the individual sounds. The group
as an entity is not only dependent on the group size, as the way the sounds are
packed together in time is also a determining factor, but it is a crucial parameter
that can intensify the effect. Therefore an alteration was made that would allow
more sounds in one generation. After a manual selection is performed by the
user/composer, four sub-populations with the identical size of 32 are calculated. The
manual selection procedure contains two steps. First, the sub-population that best
fits the needs of the composer to create a new generation is selected(select pop
1,2,3 and 4 in figure 2.4). Then, every organism in this sub-population is given a
fitness score as well. The three other sub-populations are not used for manual
selection, but can be used as extra sound material, in order to increase the
maximum number of different sounds in any given population from 32 to 128.
In the application a connection is made between the fitness score awarded to a
particular sound and the volume at which it is played. Sounds that are more likely to
reproduce are played louder as well. This feature has been introduced mainly in
order to ensure that the fittest sounds do not dissolve into the rest of the population
when a generation is played. By awarding a zero to an organism, it is ensured that it
has no chance to reproduce and will not be heard during play either. When given a
three, the highest possible score, the sound will be played at his own specific
volume. The volume of sounds that received a score of one or two will be
attenuated commensurately. Only one sub-populations is used to perform a fitness
selection on. The dynamic relation created by varying fitness scores will only be
present in the selected sub-population and will be absent from the others. To
ensure that the fittest organisms do not dissolve into the rest of the population, the
three other sub-populations are played at a lower volume.


Figure 2.5: State Transition Diagram of the Application
Figure 2.5 represents a state transition diagram of the application. It is a useful tool
to explain the process of how new generations and sound material can be created.
A initial random generation is created upon starting the application. The first thing
the user will do, is adjust the timing according to his needs. The settings in the GUI
(figure 2.4) that have an impact on the timing are max delta, range dur and dur gen.
The time duration of a sound is determined by the duration range (range dur) in
combination with the envelope type that controls the amplitude parameter of the
gate (figure 2.3). Some types have a variable length, others a fixed one. If a type
has a variable length, the duration is determined by both the type and the duration
range. Dur gen represents the time it takes to play the current generation. If the
sound of an organism is played more than once during a generation, the max delta
value will regulate the maximum time between consecutive plays. The range of that
particular parameter, which is the same for every organism, is determined by range
dur and max delta. The values of these parameters are saved in the genetic
representation. By pressing the Update Timing button, all the adjusted settings are
stored and the necessary modifications are made to the part of the genetic
representation concerning time. The possible amplitude envelope types can be
locked by using the row of buttons from Impulse Off to Atmos Off. If, for example,
only Impulse and Sharp are switched on, an envelope mutation can only affect these
two values. When the timing update is finished and the user activates Play
Generation, all the necessary Supercollider Synths are created on the server and
connected with the appropriate audio and control busses. When the entire structure

has been built, the generation is played and automatically recorded in a sound file.
The fitness scores can be awarded in two different ways. By pushing the Next
Organism & Quote button, the organisms are played and the score setting (fitness
score) is stored one by one. The other way is to award every organism the same
fitness score at once by using the inject 1s button. This button is especially useful
during the In-Between phase (see chapter 5). When the fitness quoting of a subpopulation
has been accomplished, the Play with Fitness button is pressed to play
the generation and re-record it with volumes of the individual organisms that are
adjusted to the fitness scores. During the creation of a following generation,
initiated by the Next Generation button, all the Supercollider Synths and relevant
busses of the current generation are deleted before the calculation of four new subpopulations
begins. The mutation prob setting determines the chances for a
mutation to occur in an organism. The sub-populations can be selected and played
with the select pop buttons. The row of buttons from TriSynth Mut Off until Env Mut
Off in the GUI can be used to lock certain areas in the genetic presentation of the
organisms. Their only effect is on the way a mutation is applied. For example, if the
Synth Mut button is switched off, a mutation can no longer change the values or the
means to control the parameters within the Synth blocks in the synthesis structure
(figure 2.3).

Before a sub-population is played for the first time, the synthesis structure of all the
organisms are created from scratch on the Supercollider Server. When the Next
Generation button is hit, this structure is completely removed from the server. The
process constitutes a loop of building the entire synthesis structure and removing it
afterwards. Other more efficient frameworks are possible, where parts of the
structure stay on the server all the time and changes are made when new
generations are created. This is how the design was originally intended, but it had a
great deal of bugs. A significant source of the problems encountered was that the
chronological order in which the Synths on the Supercollider Server are built can be
a determining factor for obtaining a functional synthesis structure. In the end, there
was opted for a design that constantly builds and removes the structure, as this was
deemed more predictable in order of execution. Performing structural changes to
the application, what always is inevitable in an experimental phase, became also
more transparent than in the previous framework.

