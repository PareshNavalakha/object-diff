[![Build Status](https://api.travis-ci.org/PareshNavalakha/ObjectDiffUtil.svg?branch=master)](https://travis-ci.org/PareshNavalakha/ObjectDiffUtil)

# Object Diff Utility

Generic utility to find out differences between any two objects of the same type. 
Objective is to make it easy for developers to find out delta between objects and create reports.

Goal: 
Minimal effort by developer to compute delta and get report in any given format.
Highly customizable framework for developers to adapt and extend.


To dos:
1. Document usage examples
2. Improve logging
3. Improve test cases
4. Improve quality of comments
5. Most importantly: Build renders for automated diff reports in CSV, PDF and Excel formats. 

## Getting Started

DiffComputeEngine.getInstance().findDifferences(before, after);

### Prerequisites

Java 8

### Installing

TBD Maven dependency 


## Contributors

[Paresh Navalakha](https://github.com/PareshNavalakha) - *Initial work*

[Baba Shinde](https://github.com/baba-shinde) for Travis setup tips.

## License

This project is licensed under the MIT License
