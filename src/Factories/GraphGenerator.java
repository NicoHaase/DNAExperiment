package Factories;

import DataStructures.GraphDataStructure;
import Graph.Graph;
import Graph.Nodes.Node;
import Utils.parameters.Parameter;
import Utils.parameters.ParameterList;

public abstract class GraphGenerator extends ParameterList implements IGraphGenerator {
	protected long timestampInit;
	protected int nodesInit;
	protected int edgesInit;

	protected GraphDataStructure gds;
	
	public GraphGenerator(String name, Parameter[] params, GraphDataStructure gds, long timestampInit, int nodesInit,
			int edgesInit) {
		super(name, params);

		this.timestampInit = timestampInit;
		this.nodesInit = nodesInit;
		this.edgesInit = edgesInit;
		
		this.gds = gds;
		
		if ( !gds.isReadable()) {
			throw new RuntimeException("Cannot generate a graph if any datastructure is not readable");
		}
	}
	
	public Graph newGraphInstance() {
		return this.gds.newGraphInstance(this.getName(), this.timestampInit, this.nodesInit, this.edgesInit);
	}
	
	/*
	public BatchReader<G, N, E> getBatchReader() {
		return new BatchReader<G, N, E>(this);
	}

	public BatchWriter<G, N, E> getBatchWriter() {
		return new BatchWriter<G, N, E>();
	} */
}
