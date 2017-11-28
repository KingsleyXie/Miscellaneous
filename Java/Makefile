# Test System: Windows 10
# Test Java Version:
# 	java version "1.8.0_121"
# 	Java(TM) SE Runtime Environment (build 1.8.0_121-b13)
# 	Java HotSpot(TM) 64-Bit Server VM (build 25.121-b13, mixed mode)

compile:
	@javac statistics.java -encoding utf8



complete-test:
	@echo "Note: This test case need time for JFrame's Render"
	@echo "For concise test instead please run command:    make test"
	@echo
	@javac statistics.java -encoding utf8
	@java statistics

test:
	@echo "=========== Concised test: Multi Threads For None ===========" && echo
	@javac statistics.java -encoding utf8
	@java statistics -concise

test-mt4both:
	@echo "=========== Concised test: Multi Threads For Both ===========" && echo
	@javac statistics.java -encoding utf8
	@java statistics -concise -mt4statistics -mt4detailinfo

test-lg:
	@echo "=========== Concised test: LargeTest Without Multi Threads ===========" && echo
	@javac statistics.java -encoding utf8
	@java statistics -concise -largetest

test-mt4lg:
	@echo "=========== Concised test: LargeTest With Multi Threads ===========" && echo
	@javac statistics.java -encoding utf8
	@java statistics -concise -mt4statistics -largetest

test-mt4st:
	@echo "=========== Concised test: Multi Threads For Statistics ===========" && echo
	@javac statistics.java -encoding utf8
	@java statistics -concise -mt4statistics

test-mt4de:
	@echo "=========== Concised test: Multi Threads For DetailInfo ===========" && echo
	@javac statistics.java -encoding utf8
	@java statistics -concise -mt4detailinfo



clean:
	rm -rf *.class
