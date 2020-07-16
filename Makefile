default: build run

build:
	if ! [ -d ./out/production/cap4601-project1 ]; then mkdir -p ./out/production/cap4601-project1; fi
	javac src/*.java -d ./out/production/cap4601-project1

run:
	cd out/production/cap4601-project1; java Main "../../../documents/Data_Set/" "../../../documents/stopwords.txt"

debug:
	cd out/production/cap4601-project1; java Main "../../../documents/Data_Set/" "../../../documents/stopwords.txt" -debug