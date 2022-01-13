compile:
	@javac -d bin -sourcepath src src/Public/*.java
	@javac -d bin -sourcepath src src/Server/*.java
	@javac -d bin -sourcepath src src/Client/*.java
	@echo 'Compile successfully done.'

server:
	@java -classpath bin Server.DrawEditorServer

client:
	@java -classpath bin Client.DrawEditorClient
