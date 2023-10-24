# Feature Model analysis using limboole

This is a basic template to get started implementing feature model analysis via a translation from a simple FM language to propositional logic formulas in the syntax of Limboole.

As part of the assignment you will need to implement the TODOs in [FeatureModelTranslator](https://github.com/se-buw/fm4se-featuremodels/blob/main/src/main/java/de/buw/fm4se/featuremodels/FeatureModelTranslator.java) and [FeatureModelAnalyzer](https://github.com/se-buw/fm4se-featuremodels/blob/main/src/main/java/de/buw/fm4se/featuremodels/FeatureModelAnalyzer.java).

## Project Setup

[📼 see the project setup video](https://www.youtube.com/watch?v=40MA55S3F8c&list=PLGyeoukah9NYNMJhcHXLjAGN294O2uXCB&index=6)

1. Install OpenJDK/JDK 
2. Clone the [repository](https://github.com/se-buw/fm4se-featuremodels.git)
3. Open in any IDE of your choice (e.g. Eclipse, VS Code, etc.)
4. Go to `src/main/java/de\buw\fm4se\featuremodels\exec\LimbooleExecutor.java` and change the  `LIMBOOLE_EXE ` according to your system architecture. 
5. Run `src\main\java\de\buw\fm4se\featuremodels\PrinterExample.java`. You should see the output as - 
```
feature car is optional and has 2 children
    feature motor is mandatory and has 2 children in a XOR-group
        feature gasoline is optional and has 0 children
        feature electric is optional and has 0 children
    feature comfort is optional and has 2 children in a OR-group
        feature heating is optional and has 0 children
        feature entertainment is optional and has 0 children
electric REQUIRES heating
```

## Task 1

[📼 see the code walk-through and explanation of this task](https://www.youtube.com/watch?v=qa08IzWqSQs&list=PLGyeoukah9NYNMJhcHXLjAGN294O2uXCB&index=7)

For this task, you need to implement the `translateToFormula(FeatureModel fm)` method in [FeatureModelTranslator](https://github.com/se-buw/fm4se-featuremodels/blob/main/src/main/java/de/buw/fm4se/featuremodels/FeatureModelTranslator.java)  which will return the combined formula in _limboole format_ for a given _Feature Model_.
The translation rules are (as in Lecture Slide 3):


| Feature Model Relation      | Corresponding Formula |  
| ----------------------      | --------------------- |
| _r_ is the root feature     | r                     |
| _p_ is parent of feature _c_   | c -> p             |
| _m_ is a mandatory subfeature of _p_   | p -> m     |
| _p_ is the parent of [1..n] grouped features feature _g1,...,gn_   | p -> (g1 \| ... \|gn) |
| _p_ is the parent of [1..1] grouped features feature _g1,...,gn_   | p -> 1-of-n (g1,...,gn) |

After a correct translation all JUnit tests relating to consistency checks should pass.


## Task 2:

[📼 see the code walk-through and explanation of this task](https://www.youtube.com/watch?v=xrLvDfDfRnQ&list=PLGyeoukah9NYNMJhcHXLjAGN294O2uXCB&index=8)


- Implement the ``deadFeatureNames(FeatureModel fm)`` method in [FeatureModelAnalyzer](https://github.com/se-buw/fm4se-featuremodels/blob/main/src/main/java/de/buw/fm4se/featuremodels/FeatureModelAnalyzer.java) Class which will compute a (potentially empty) list of all dead features. 

- Implement the ``mandatoryFeatureNames(FeatureModel fm)`` method in [FeatureModelAnalyzer](https://github.com/se-buw/fm4se-featuremodels/blob/main/src/main/java/de/buw/fm4se/featuremodels/FeatureModelAnalyzer.java) Class which will compute a (potentially empty) list of all mandatory features. 

For this, reuse the formula you get from Task 1.

Some very basic test cases exist. Run the test cases. 

## Task 3:

In this task you implement an analysis relating two feature models. Your code should check whether all products of one feature model are also products of a second feature model.

Implement the ``checkAllProductsPreserved(FeatureModel fm1, FeatureModel fm2)`` method in [FeatureModelAnalyzer](https://github.com/se-buw/fm4se-featuremodels/blob/main/src/main/java/de/buw/fm4se/featuremodels/FeatureModelAnalyzer.java) Class which will return ``true`` if and only if all products of ``fm1`` are also products of ``fm2`` (we don't care if ``fm2`` has additional products). 

For this, task reuse the formula you get from Task 1.
