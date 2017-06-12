all:
	@javac V2VM.java -classpath vapor-parser.jar
	@javac Liveness.java -classpath vapor-parser.jar
	@javac PopLive.java -classpath vapor-parser.jar
	@javac FlowGraph.java -classpath vapor-parser.jar
	@javac FlowGraphNode.java -classpath vapor-parser.jar

FlowGraphNode:
	@java -cp "./:vapor-parser.jar" FlowGraphNode

FlowGraph:
	@java -cp "./:vapor-parser.jar" FlowGraph

PopLive:
	@java -cp "./:vapor-parser.jar" PopLive

Liveness:
	@java -cp "./:vapor-parser.jar" Liveness

V2VM:
	@java -cp "./:vapor-parser.jar" V2VM < Phase3Tests/QuickSort.vapor
