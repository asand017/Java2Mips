all:
	@javac V2VM.java -classpath vapor-parser.jar
	@javac Liveness.java -classpath vapor-parser.jar
	@javac PopLive.java -classpath vapor-parser.jar
	@javac ControlFlowG.java -classpath vapor-parser.jar
	@javac ControlFlowNode.java -classpath vapor-parser.jar

ControlFlowNode:
	@java -cp "./:vapor-parser.jar" ControlFlowNode

ControlFlowG:
	@java -cp "./:vapor-parser.jar" ControlFlowG

PopLive:
	@java -cp "./:vapor-parser.jar" PopLive

Liveness:
	@java -cp "./:vapor-parser.jar" Liveness

V2VM:
	@java -cp "./:vapor-parser.jar" V2VM < Phase3Tests/QuickSort.vapor
