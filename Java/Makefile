# Test System: Windows 10
# Test Java Version:
# 	java version "1.8.0_121"
# 	Java(TM) SE Runtime Environment (build 1.8.0_121-b13)
# 	Java HotSpot(TM) 64-Bit Server VM (build 25.121-b13, mixed mode)

compile-only:
	@javac statistics.java -encoding utf8

run:
	@javac statistics.java -encoding utf8
	@java statistics

test:
	@echo "Note: This test case is concised to save the JFrame's Render time"
	@echo "For complete test instead please run command:    make run"
	@echo
	@javac statistics.java -encoding utf8
	@java statistics -concise

test-mt:
	@javac statistics.java -encoding utf8
	@java statistics -concise -mt4statistics -mt4detailinfo

test-mt4st:
	@javac statistics.java -encoding utf8
	@java statistics -concise -mt4statistics

test-mt4de:
	@javac statistics.java -encoding utf8
	@java statistics -concise -mt4detailinfo



clean:
	rm -rf *.class
