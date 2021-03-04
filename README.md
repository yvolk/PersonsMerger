The real life project that solves the Systems Integration task to identify records
of the same persons in data sets that come from different systems.
Number of identifiable attributes and even their values differ (e.g. due to errors and 
historical changes),
and there are no strict rules, when we should consider two persons
from different sources as the same person, when to treat them as different, 
and when to decide that this is "unknown" to the system and maybe needs manual decision step.

The automatic decisions are taken using *trained models* that are obtained with an 
[Evolutionary algorithm](https://en.wikipedia.org/wiki/Evolutionary_algorithm) 
that uses default values of *weights* for initial models and then iteratively, 
generation by generation, breeds better models, applying mutation and selection 
at each iteration.

The **PersonsData** set of sample pairs of persons' records with expected outputs 
(more than two hundred pairs) is used to train the models.

**ModelTest** test cases for the initial and for trained models allow quickly evaluate
these models for fitness for the PersonsData.

**Learning** test cases implement the Evolutionary algorithm. 

Run these tests to see learning and trained models in action! 

Written in Kotlin programming language.