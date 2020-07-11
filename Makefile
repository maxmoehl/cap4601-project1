default: build run

build:
	javac src/*.java -d ./out/production/cap4601-project1

run:
	cd out/production/cap4601-project1; java Main ./documents/Data Set/

debug:
	cd out/production/cap4601-project1; java Main "./documents/Data Set/" -debug