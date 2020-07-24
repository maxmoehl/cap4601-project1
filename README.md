# docusim

## Usage

`java Main <document folder> <stopwords file> [-debug]`

## build and execute

### Prerequisites

Make sure you have up to date JDK and JRE installed and can
access the `java` and `javac` commands from the command line. 

### On systems that support *nix shell commands

The Makefile defines three targets: `build`, `run` and `debug`.
By default, it builds and runs the program. It expects a file
containing the stopwords at `documents/stopwords.txt` and the
document files to be analyzed in `documents/Data_Set` The
`debug` target runs the program with additional outputs.

### On any other system

Navigate into the sources folder and execute the following
commands as needed.

Build:  
`javac *.java`

Run:  
`java Main "/Path to/the documents" "Path to/stopwords file"`

Debug:  
`java Main "/Path to/the documents" "Path to/stopwords file" -debug`