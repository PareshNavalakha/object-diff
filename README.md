[![Build Status](https://api.travis-ci.org/PareshNavalakha/object-diff.svg?branch=master)](https://travis-ci.org/PareshNavalakha/object-diff)
[![Coverage Status](https://coveralls.io/repos/github/PareshNavalakha/ObjectDiffUtil/badge.svg?branch=master)](https://coveralls.io/github/PareshNavalakha/ObjectDiffUtil?branch=master)

# Object Diff Utility

Generic utility to find out differences between any two objects of the same type. 
Objective is to make it easy for developers to find out delta between objects and create reports.

Goal: 
Minimal effort by developer to compute delta and get report in any given format.
Highly customizable framework for developers to adapt and extend.

## Getting Started

DiffComputeEngine.getInstance().findDifferences(before, after);

### Prerequisites

Java 8u40 and above.

Code makes use Java 8 features such as Streams. To improve performance, we make use of Parallel Streams. However there were performance issues while using Parallel Streams in versions before 8u40. You can refer [this](https://stackoverflow.com/questions/23489993/nested-java-8-parallel-foreach-loop-perform-poor-is-this-behavior-expected) StackOverflow post for details.

###POJO Contract

The POJOs used for diff compute must override equals methods. It is assumed that the Developer understands the basic contracts of equals/hashcode while making use of Map/HashSet.

###Usage of Annotations

Following Method Annotations are provided by the framework. They must be applied on the corresponding Getter Method.

1. Identifier - This annotation is a must while comparing Collections containing POJOs. This will help co-relate objects for diff compute between 2 Collections.
2. Description - This annotation should be made use to override the Display name of the variable while using Diff Renderers.
3. Ignore - This annotation should be used to skip diff compute for that particular Method.

### Installing

TBD Maven dependency 

## Renderers
The intent of the framework is to provide Renderers out of the Box. We intend to make the following Renderers available.

1. Excel Renderer - Renders Diff in Excel (XLSX) format. To be used while rendering Collections containing Simple POJOs. It is not recommended for Collections containing POJOs having nested POJOs as Excel has a flat 2 dimensional layout.
2. Text Renderer - Yet to start. This will help render in CSV & HTML format. CSV should be used to render simple POJOs. HTML is recommended for email (MIME embedding) and Complex POJO rendering.
3. PDF Renderer - Yet to start. It will render in PDF format.

## Contributors

[Paresh Navalakha](https://github.com/PareshNavalakha) - *Initial work*

[Baba Shinde](https://github.com/baba-shinde) for Travis setup tips.

## License

This project is licensed under the MIT License
