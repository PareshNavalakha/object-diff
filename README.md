[![Build Status](https://api.travis-ci.org/PareshNavalakha/ObjectDiffUtil.svg?branch=master)](https://travis-ci.org/PareshNavalakha/ObjectDiffUtil)
[![Coverage Status](https://coveralls.io/repos/github/PareshNavalakha/ObjectDiffUtil/badge.svg?branch=master)](https://coveralls.io/github/PareshNavalakha/ObjectDiffUtil?branch=master)

# Object Diff Utility

Generic utility to find out differences between any two objects of the same type. 
Objective is to make it easy for developers to find out delta between objects and create reports.

Goal: 
Minimal effort by developer to compute delta and get report in any given format.
Highly customizable framework for developers to adapt and extend.


To dos:
1. Document usage examples
2. Build renderers for automated diff reports for Console, HTML, PDF and Excel formats. 

## Getting Started

DiffComputeEngine.getInstance().findDifferences(before, after);

### Prerequisites

Java 8u40 and above

### Installing

TBD Maven dependency 


## Contributors

[Paresh Navalakha](https://github.com/PareshNavalakha) - *Initial work*

[Baba Shinde](https://github.com/baba-shinde) for Travis setup tips.

## License

This project is licensed under the MIT License
