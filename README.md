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

## Build

Will build a JAR with a prepended universal execution script in the `target/scala-x/` folder.
This script will by itself detects the installed JVM and call `java -jar` so user doesn't have to bother with that.

```
sbt clean assembly
```

## Execution

Universal JAR
```
java -jar minimal-secret-santa.jar
```
Mac / Linux prepended execution script
```
./minimal-secret-santa
```
Windows prepended execution script
```
minimal-secret-santa.bat
```
Without any params, you should see the display of command usage:
```shell
Usage: minimal-secret-santa [-hV] -i=<input> [-t=<template>]
Official Ferload Client command line interface for files download.
  -h, --help            Show this help message and exit.
  -i, --input=<input>   input CSV file with list of people (required)
  -t, --template=<template>
                        Optional template location to generate invitation
                          messages
  -V, --version         Print version information and exit.
```
Example of execution with params:
```
java -jar minimal-secret-santa.jar -i input.csv -t template.md
```