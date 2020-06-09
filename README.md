# Justification Diagram Generator

- Author : Corinne Pulgar and Nicolas Corbière
- Supervision : Sébastien Mosser and mireille Blay-Fornarino

This prototype generates a justification diagram from a text file. 

## Installation

JDGenerator is a Maven program. You can either clone the repo and run the following commands :
```
mvn install
mvn compile
```
or [download the jar file](https://github.com/MireilleBF/JustificationDiagram/releases/tag/V1.2).

## Execution
From the cloned repo, run 
```
mvn exec:java -Dexec.mainClass="JDCompiler" -Dexec.args="[INPUT_FILE] -o [OUTPUT_FILE] (-rea [INPUT_REALIZATION_FILE]) [OPTIONS]"

```

or execute the jar file with 
```
java -jar JDGenerator-jar-with-dependencies.jar [INPUT_FILE] -o [OUTPUT_FILE] (-rea [INPUT_REALIZATION_FILE]) [OPTIONS] 
```

### Options
| Flag  | Argument | Description                              |
|-------|----------|------------------------------------------|
| -o    | path     | Output file (no extension)               |
| -svg  | -        | Generate visual graph                    |
| -svgR | -        | Generate visual realisation graph        |
| -gv   | -        | Generate text files before dot formating |
| -td   | -        | Generate todo list                       |

If no output file is entered, the generated files will be named from the input file name. 


There are a few examples: 
```
mvn exec:java -Dexec.mainClass="JDCompiler" -Dexec.args="example/basic.jd -o output/images/basic -svg"
```
```
mvn exec:java -Dexec.mainClass="JDCompiler" -Dexec.args="example/basic.jd -o output/images/basic -rea output/realization/realization.txt -svg  "
```
```
java -jar DGenerator-jar-with-dependencies.jar -Dexec.args="example/basic.jd -o output/images/basic -rea output/realization/realization.txt -svg -td  "
```

## Syntax
The developped syntax strongly ressembles [PlantUML](https://plantuml.com/)'s object and class diagram syntaxes. 

### Tags
The text file should start and end by the corresponding tags.
```
@startuml

' Your code here

@enduml
```
The order of the elements, either declarations or relations, doesn't matter.

### Declarations
Write a declaration to create a node. The expected partern is as follow :
```
<TYPE> <ALIAS> = "<LABEL>"
```
The conclusion and subconclusion may have a restriction field. There can only be one conclusion per diagram.
```
<TYPE> <ALIAS> = "<LABEL>" - "<RESTRICTION>"
```

### Relations
The prototype permits two types of oriented link.

* Expanded link :
```
<ALIAS_1> --> <ALIAS_2>
```
* Collapsed link :
```
<ALIAS_1> ..> <ALIAS_2>
```

## Realization

During the Continuous integration, please add to a file named 'realization.txt' the supports, domains and rationale that have been validated.

For this purpose, in '.github\workflows\maven.yml' add this after the Build and Test of your project and before the generation of diagramm :

```
- name: Realization
      run: echo -e "[Label of the accompliseh task]" >> output/realization/realization.txt
```

You can also specify whether a node needs to check the existence of a certain files or directorys, 

```
- name: Realization
      run: echo -e "[Label of the accompliseh task]!-![file necessery 1];[file necessery 2];|file necessery 3]" >> output/realization/realization.txt
      
<!-- this will chek if 'file necessery 1', 'file necessery 2' and 'file necessery 3' exist -->
```

and you can ask if the number of files in a directory is correct.

```
- name: Realization
      run: echo -e "[Label of the accompliseh task]!-![directory]/!number!10" >> output/realization/realization.txt
      
<!-- it checks if the "directory" contains 10 files -->

```

You can also add references for a certain node with this

```
- name: Realization
      run: echo -e "[Label of the accompliseh task]!ref!Artifacts1" >> output/realization/realization.txt
      
```

## Artifacts

If you want to save the elements created during the continuous integration, always in your 'maven.yl' file, you can write this at the very end of your file :

```
- name: Archive generated codes
  uses: actions/upload-artifact@v2
  with: 
    name: [Name of your Artifact]
    path: [OUTPUT_FILE]
    
    
<!-- This will save all "OUTPUT_FILE" files in the specified artifact -->
```

if you want to save a specific file, you can write this :

```
- name: Archive generated codes
  uses: actions/upload-artifact@v2
  with: 
    name: Artifact1
    path: realization.txt
    
```

If you want more information about worflows, please [go here](https://help.github.com/en/actions/reference/workflow-syntax-for-github-actions)


## Example without realization
Here's an example of a text file, the graph and the todo list it generates.

run this : 

```
mvn exec:java -Dexec.mainClass="JDCompiler" -Dexec.args="example/fig3.jd -o output/images/fig3 -svg -td example/realization/realizationFig3.txt -rea"
```

or 

```
java -jar JDGenerator-jar-with-dependencies.jar example/fig3.jd -o output/images/fig3 -rea example/realization/realizationFig3.txt  -svg -td "
```
#### example.jd
```
@startuml

conclusion C = "Software safety validated" - "Internal"
strategy S = "Assess software safety"
domain D = "Internal accreditation"
rationale R = "Credentials for IEC 62304"

subconclusion C1 = "Specifications validated"

subconclusion C2 = "Architecture validated"
strategy S2 = "Review architecture"
support F = "Architecture"

subconclusion C3 = "Safety specifications validated"
strategy S3 = "Assess risk management"
rationale R3 = "Credentials for ISO 14971"
support G = "Risk mitigation plan"

subconclusion C4 = "Risks consistency"
strategy S4 = "Verify consistency"
support H = "Technical specifications"
support I = "Functional specifications"
support J = "Identified risks"

subconclusion C5 = "Feasible hard points"

S --> C
D --> S
R --> S

C1 --> S
H ..> C1
I ..> C1

C2 --> S
S2 --> C2
F --> S2
H --> S2

C3 --> S
C3 --> S2
S3 --> C3
R3 --> S3
J --> S3
G --> S3

C4 --> S3
S4 --> C4
H --> S4
I --> S4
J --> S4

C5 --> S2
H ..> C5

@enduml
```

#### example.svg

![](examples/fig3.svg)
> The justification diagram was adapted from _Support of Justification Elicitation: Two Industrial Reports_ by Clément Duffau, Thomas Polacsek and Mireille Blay-Fornarino, 2018.

#### example.todo

_Generated List_
```
Requirements list

[ ]	Identified risks
[ ]	Functional specifications
[ ]	Technical specifications
[ ]	Feasible hard points
[ ]	Specifications validated
[ ]	Risk mitigation plan
[ ]	Architecture
[ ]	Risks consistency
[ ]	Safety specifications validated
[ ]	Architecture validated
-----------------------------------------------
[ ]		Software safety validated
-----------------------------------------------
```

## Example with realization 

Now we will see 2 examples of valid and invalid diagrams.
To do so, we will use the following example for both diagrams.

#### example.jd
```
@startuml

conclusion C = "Valid Continuous Integration" - "interne"
PV --> C

subconclusion PV = "Project Valid" 
strategy SE = "Evaluate Project Quality"
SE --> PV
M --> SE
ASC --> SE
AJS --> SE

subconclusion ASC = "Archivees Data" 
strategy SD = "Data Archivate"
support CA = "code Archivate"
support IA = "images Archivate"

SD --> ASC
AJS --> SD
CA --> SD
IA --> SD

subconclusion M = "Maven ready"
strategy SM = "Evaluate Maven Project"
support TM = "Test Maven passed"
support BM = "Build Maven passed" 
BM --> SM
TM --> SM
SM --> M



subconclusion AJS = "Test Coverage validated and Archived"
strategy SJA = "testCoverage Archivate"
support JA = "Jacoco report Archivate"
JV --> SJA
RJ --> SJA
JA --> SJA
SJA --> AJS

subconclusion JV = "Test Coverage validated"
strategy SJ = "Validate testCoverage"
support RJ = "Jacoco Report"
RJ --> SJ
SJ --> JV

@enduml
```

#### example.svg

![](examples/exampleCI/Pattern4CI_Valid.svg)



## Valid Example with realization 
Here's an example of a text file, the graph and the todo list it generates if you have validate all the evidences.

#### maven.yl - Valid 
You should write this in 'maven.yml' :

```
# This workflow will build a Java project with Maven
# For more information see: https://help.github.com/actions/language-and-framework-guides/building-and-testing-java-with-maven

name: Java CI with Maven

on:
  push:
    branches: [ master ]
  pull_request:
    branches: [ master ]

jobs:
  build:

    runs-on: ubuntu-latest

    steps:
    - uses: actions/checkout@v2
    - name: Set up JDK 1.8
      uses: actions/setup-java@v1
      with:
        java-version: 1.8
    #Build of the project
    - name: Build with Maven
      run: mvn -B package --file pom.xml
    #Test of the project
    - name: Test with Maven
      run: mvn test
      
    #---------JustificationDiagram-----------

    #Here, I'm going to create the realization file
    
    #"code Archivate" have the reference 'generatedCode'
    - name: Realization part1
          run: echo -e "Jacoco Report\ncode Archivate!ref!generatedCode\n" >> realization.txt

    #"images Archivate" must verify that 'examples/exampleCI/Pattern4CI.jd' exists and it has 'images' as reference.
    - name: Realization part2
          run: echo -e "images Archivate!-!examples/exampleCI/Pattern4CI.jd!ref!images" >> realization.txt

    #"Test Maven passed" is done
    #"Build Maven passed" must check that the 'examples' directory contains 10 files.
    - name: Realization part3
          run: echo -e "Test Maven passed\nBuild Maven passed!-!examples!number!10" >> realization.txt
    #"Jacoco report" is done
    #"Valid Continuous" have the reference  'GeneratedJD'
    #"Jacoco report Archivate" have the reference 'jacoco'

    - name: Realization part4
         run: echo -e "\nJacoco report Archivate!ref!jacoco\nValid Continuous Integration!ref!GeneratedJD" >> realization.txt
         
    #I generate the two diagrams and the TODO list
    - name: JD&TODO Generation     
       run : mvn exec:java -Dexec.mainClass="JDCompiler" -Dexec.args="examples/exampleCI/Pattern4CI.jd -o output/GeneratedJD/Pattern4CI -rea realization.txt -png -td"
    
    #I archive my diagramms create during the CI
    - name: Archive JD&TODO
      uses: actions/upload-artifact@v2
      with: 
        name: GeneratedJD
        path: output/GeneratedJD
        
    #I archive my realization file in the same artifacts as my diagramms
    - name: Archive realization
      uses: actions/upload-artifact@v2
      with: 
        name: GeneratedJD
        path: realization.txt
        
    #I archive the diagrams generated during the test
    - name: Archive images
      uses: actions/upload-artifact@v2
      with: 
        name: images
        path: output/images
        
    #I archive the Jacoco report
    - name: Archive Jacoco report
      uses: actions/upload-artifact@v2
      with: 
        name: jacoco
        path: target/site/jacoco
        
    #I archive the generated codes
    - name: Archive generated codes
      uses: actions/upload-artifact@v2
      with: 
        name: generatedCode
        path: src/main/java/models
        
     #---------JustificationDiagram-----------


```

#### realization.txt - Valid 

Here we say this:


```
Jacoco Report
code Archivate!ref!generatedCode
images Archivate!-!examples/exampleCI/Pattern4CI.jd!ref!images
Test Maven passed
Build Maven passed!-!examples!number!10
Jacoco report Archivate!ref!jacoco
Valid Continuous Integration!ref!GeneratedJD

```



#### example_REA.svg - Valid

![](examples/exampleCI/Pattern4CI_Valid_REA.svg)


#### example.todo - Valid

_Generated List_
```
Requirements list

[X]	code Archivate - references : generatedCode
[X]	Test Maven passed
[X]	Jacoco Report
[X]	images Archivate - references : images
	[X] examples/exampleCI/Pattern4CI.jd
[X]	Jacoco report Archivate - references : jacoco
[X]	Test Coverage validated
[X]	Build Maven passed
	[x] examples
[X]	Maven ready
[X]	Test Coverage validated and Archived
[X]	Archivees Data
[X]	Project Valid
-----------------------------------------------
[X]		Valid Continuous Integration - references : GeneratedJD
-----------------------------------------------
```



## Invalid Example with realization 
Here's an example of a text file, the graph and the todo list it generates if you don't have validate 'Build Maven passed' and 'Test Maven passed'.

#### maven.yl - Invalid
You should write this in 'maven.yml' :

```   
# This workflow will build a Java project with Maven
# For more information see: https://help.github.com/actions/language-and-framework-guides/building-and-testing-java-with-maven

name: Java CI with Maven

on:
  push:
    branches: [ master ]
  pull_request:
    branches: [ master ]

jobs:
  build:

    runs-on: ubuntu-latest

    steps:
    - uses: actions/checkout@v2
    - name: Set up JDK 1.8
      uses: actions/setup-java@v1
      with:
        java-version: 1.8
    #Build of the project
    - name: Build with Maven
      run: mvn -B package --file pom.xml
    #Test of the project
    - name: Test with Maven
      run: mvn test
      
    #---------JustificationDiagram-----------

    #Here, I'm going to create the realization file
    
    # "Jacoco Report" have the reference 'archi1'.
    # "code Archivate" and "Build Maven passed" is done.
    - name: Realization part1
          run: echo -e "Jacoco Report!ref!Archi1\ncode Archivate\nBuild Maven passed" >> realization.txt

    # "images Archivate" must verify that 'examples/exampleCI/Pattern4CI.jd' exists and it has 'images' as reference.
    - name: Realization part2
          run: echo -e "images Archivate!-!examples/exampleCI/Pattern4CI.jd!ref!images" >> realization.txt

    #"Data Archivate" must check that the 'examples' directory and 'dontExist.txt' directory contains 3 files 
    #and need to verify than 'dontExist.todo' exist. This will lead to an error because 'examples' contains 10 files 
    #and 'dontExist.txt' and 'dontExist.todo' does not exist.
    - name: Realization part3
          run: echo -e "Test Maven passed\nData Archivate!-!examples!number!3;dontExist.txt!number!3;dontExist.todo" >> realization.txt
          
    #"Jacoco report Archivate"  is done
    - name: Realization part4
         run: echo -e "Jacoco report Archivate\n" >> realization.txt

    - name: JD&TODO Generation     
       run : mvn exec:java -Dexec.mainClass="JDCompiler" -Dexec.args="examples/exampleCI/Pattern4CI.jd -o output/GeneratedJD/Pattern4CI -rea realization.txt -png -td "
       
    - name: Archive generated codes
          uses: actions/upload-artifact@v2
          with: 
            name: GeneratedJD
            path: output/GeneratedJD
         
    #I generate the two diagrams and the TODO list
    - name: JD&TODO Generation     
       run : mvn exec:java -Dexec.mainClass="JDCompiler" -Dexec.args="examples/exampleCI/Pattern4CI.jd -o output/GeneratedJD/Pattern4CI -svg -td realization.txt -rea"
    
    #I archive my diagramms create during the CI
    - name: Archive JD&TODO
      uses: actions/upload-artifact@v2
      with: 
        name: GeneratedJD
        path: output/GeneratedJD
        
    #I archive my realization file in the same artifacts as my diagramms
    - name: Archive realization
      uses: actions/upload-artifact@v2
      with: 
        name: GeneratedJD
        path: realization.txt
        
    #I archive the diagrams generated during the test
    - name: Archive images
      uses: actions/upload-artifact@v2
      with: 
        name: images
        path: output/images
        
    #I archive the Jacoco report
    - name: Archive Jacoco report
      uses: actions/upload-artifact@v2
      with: 
        name: jacoco
        path: target/site/jacoco
        
    #I archive the generated codes
    - name: Archive generated codes
      uses: actions/upload-artifact@v2
      with: 
        name: generatedCode
        path: src/main/java/models
    #---------JustificationDiagram-----------


```

#### realization.txt - Invalid


```
Build Maven passed
Jacoco Report!ref!Archi1
code Archivate
images Archivate!-!examples/exampleCI/Pattern4CI.jd!ref!images
Data Archivate!-!examples!number!3;dontExist.txt!number!3;dontExist.todo
Jacoco report Archivate
```


#### example_REA.svg - Invalid

![](examples/exampleCI/Pattern4CI_NotValid_REA.svg)

#### example.todo - Invalid

_Generated List_
```
Requirements list

[X]	code Archivate
[ ]	Test Maven passed
[X]	Jacoco Report - references : Archi1
[X]	images Archivate - references : images
	[X] examples/exampleCI/Pattern4CI.jd
[X]	Jacoco report Archivate
[X]	Test Coverage validated
[X]	Build Maven passed
[ ]	Maven ready
[X]	Test Coverage validated and Archived
[ ]	Data Archivate
	[ ] dontExist.todo - (not found)
	[ ] examples - (3 file expected, but 10 found)
	[ ] dontExist.txt - (not found)
[ ]	Archivees Data
[ ]	Project Valid
-----------------------------------------------
[ ]		Valid Continuous Integration
-----------------------------------------------

```

## Tips and tricks

### Saving Variables in the Workflow

If you want to save variables, such as the input or output of your project, there is a tip for you.

```
name: Java CI with Maven

on:
  push:
    branches: [ master ]
  pull_request:
    branches: [ master ]

jobs:
  build:

    runs-on: ubuntu-latest

    steps:
    - uses: actions/checkout@v2
    - name: Set up JDK 1.8
      uses: actions/setup-java@v1
      with:
        java-version: 1.8
    #Build of the project
    - name: Build with Maven
      run: mvn -B package --file pom.xml
    #Test of the project
    - name: Test with Maven
      run: mvn test
    
    #set Variables
    - name set Variables
        run : |
          echo -e "examples/exampleCI/" >> Ouput.txt
          echo -e "output/GeneratedJD/" >> Input.txt
        
    #I generate the two diagrams and the TODO list and used the Variables
    - name: JD&TODO Generation     
       run : java -jar JDGenerator-jar-with-dependencies.jar (cat varInput.txt)/Pattern4CI.jd -o $(cat varOutput.txt)Pattern4CI -rea realization.txt  -svg -td 
```

### Saving the last diagrammes generated in a readme

If you want to save yout last diagrammes in a readme there is a tip for you.

```
name: Java CI with Maven

on:
  push:
    branches: [ master ]
  pull_request:
    branches: [ master ]

jobs:
  build:

    runs-on: ubuntu-latest

    steps:
    - uses: actions/checkout@v2
    - name: Set up JDK 1.8
      uses: actions/setup-java@v1
      with:
        java-version: 1.8
    #Build of the project
    - name: Build with Maven
      run: mvn -B package --file pom.xml
    #Test of the project
    - name: Test with Maven
      run: mvn test
    
    #set Variables
    - name set Variables
        run : |
          echo -e "examples/exampleCI/" >> Ouput.txt
          echo -e "output/GeneratedJD/" >> Input.txt
        
    #I generate the two diagrams and the TODO list and used the Variables
    - name: JD&TODO Generation     
       run : java -jar JDGenerator-jar-with-dependencies.jar (cat varInput.txt)/Pattern4CI.jd -o $(cat varOutput.txt)Pattern4CI -rea realization.txt  -svg -td 
    
    #I memorize all my files contained in the output directory 
    - name: memorise the final result
      if: false
      run: 
       git config user.name "GitHub Actions";
       git add $(cat Output.txt)*;
       # $(cat Output.txt)* == output/GeneratedJD/*
       git commit -m "add output";
       git push -v;
```

Now, in you readme, put this :



```
Here is the realization diagram: 

![link to Google]([link to the diagrammes])

And here's the pattern that fits him:
<!-- this id an example -->
![link to Google](https://github.com/MireilleBF/JustificationDiagram/blob/master/examples/exampleCI/Pattern4CI_Valid.svg)
```



