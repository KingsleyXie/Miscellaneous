# Test System: Windows 10
# Test Java Version:
# 	java version "1.8.0_121"
# 	Java(TM) SE Runtime Environment (build 1.8.0_121-b13)
# 	Java HotSpot(TM) 64-Bit Server VM (build 25.121-b13, mixed mode)

compile:
	@javac socket.java -encoding utf8



test:
	@javac socket.java -encoding utf8
	@java socket -server
	@java socket -client

start-server:
	@javac socket.java -encoding utf8
	@java socket -server

start-client:
	@javac socket.java -encoding utf8
	@java socket -client



clean:
	rm -rf *.class
