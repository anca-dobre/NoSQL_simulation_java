build:
	mkdir -p target
	javac ./src/*.java -d target;
	javac -cp target ./src/*.java -d target;
clean:
	rm -rf target
	rm -rf output
run:
	java -cp target Tema2 $(INPUT_FILE)