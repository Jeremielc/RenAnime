all : javac
	

javac : src/*
	rm -rf dist/
	mkdir dist/
	javac src/com/jeremielc/renanime/*.java
	cp -r src/* dist/
	rm -rf dist/com/jeremielc/renanime/*.java

run : 
	cd dist/
	java com.jeremielc.renanime.Main
