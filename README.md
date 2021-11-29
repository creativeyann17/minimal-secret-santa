# Minimal Secret Santa
Scala implementation of a command line secret-santa.

## Configuration
The program requires a CSV file as input, example:

|NAME|EXCLUDE|
|-|-|
a|b
b|a
c
d
e

*Note: EXCLUDE can contain multiple elements, use '|' as separator.*
## Execution

```
sbt clean assembly
java -jar minimal-secret-santa.jar data.csv
```