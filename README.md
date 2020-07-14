# docusim

## build and execute

### On systems that support *nix shell commands

The Makefile defines three targets: `build`, `run` and `debug`.
By default, it builds and runs the program. The `debug` target
runs the program with additional outputs.

### On any other system

Navigate into the sources folder and execute the following
commands as needed.

Build:  
`javac *.java`

Run:  
`java Main "/Path to/the documents"`

Debug:  
`java Main "/Path to/the documents" -debug`