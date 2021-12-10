compile:
	@cd src && \
	javac -d ../bin/ Public/*.java && \
	javac -d ../bin/ Server/*.java && \
	javac -d ../bin/ Client/*.java
	@echo 'Compile successfully done.'

server:
	@cd bin && \
	java Server.DrawEditorServer

client:
	@cd bin && \
	java Client.DrawEditorClient
