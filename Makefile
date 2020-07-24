default: build run

build:
	if ! [ -d ./bin ]; then mkdir bin; fi
	javac src/*.java -d ./bin

run:
	cd bin; java Main "../documents/Data_Set/" "../documents/stopwords.txt"

debug:
	cd bin; java Main "../documents/Data_Set/" "../documents/stopwords.txt" -debug
