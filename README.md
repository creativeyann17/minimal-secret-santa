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

*Note: EXCLUDE are people you don't want to give gifts to each other. It can contain multiple elements, use '|' as separator.*

## Template
The program requires a template file as input (can be a markdown file), example:

```md
**[Event] 🎁 Secret-Santa 🎁**

Hi {FROM} !

Your secret santa gift will be for: **{TO}**

```
The template supports the following params:

|Param|Description|
|-|-|
{FROM}|Who will give the gift
{TO}|Who will receive the gift

## Execution

```
sbt clean assembly
java -jar minimal-secret-santa.jar data.csv template.md
```