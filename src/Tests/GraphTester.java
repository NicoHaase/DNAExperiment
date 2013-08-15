package Tests;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import Utils.Keywords;
import DataStructures.DArrayList;
import DataStructures.DHashSet;
import DataStructures.GraphDataStructure;
import DataStructures.IEdgeListDatastructure;
import DataStructures.INodeListDatastructure;
import Graph.Graph;
import Graph.Edges.Edge;
import Graph.Nodes.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(Parameterized.class)
public class GraphTester {
	private Graph graph;
	private GraphDataStructure gds;

	public GraphTester(Class<? extends INodeListDatastructure> nodeListType,
			Class<? extends IEdgeListDatastructure> graphEdgeListType,
			Class<? extends IEdgeListDatastructure> nodeEdgeListType, Class<? extends Node> nodeType)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {
		this.gds = new GraphDataStructure(nodeListType, graphEdgeListType, nodeEdgeListType, nodeType);
		this.graph = new Graph("Test", 0L, gds);
	}

	@SuppressWarnings("rawtypes")
	@Parameterized.Parameters(name = "{0} {1} {2} {3}")
	public static Collection<Object> testPairs() {
		Class[] dataStructures = { DArrayList.class, DHashSet.class };
		Class[] nodeTypes = { UndirectedNode.class, UndirectedDoubleWeightedNode.class,
				DirectedNode.class, DirectedDoubleWeightedNode.class };

		ArrayList<Object> result = new ArrayList<>();
		for (Class nodeListType : dataStructures) {
			for (Class edgeListType : dataStructures) {
				for (Class nodeEdgeListType : dataStructures) {
					for (Class nodeType : nodeTypes) {
						if ( !(INodeListDatastructure.class.isAssignableFrom(nodeListType))) continue;
						if ( !(IEdgeListDatastructure.class.isAssignableFrom(edgeListType))) continue;
						if ( !(IEdgeListDatastructure.class.isAssignableFrom(nodeEdgeListType))) continue;						
						result.add(new Object[] { nodeListType, edgeListType, nodeEdgeListType, nodeType });
					}
				}
			}
		}

		return result;
	}
	
	@Test
	public void addNodeByID() {
		Node n = gds.newNodeInstance(42);
		
		assertEquals(-1, graph.getMaxNodeIndex());
		graph.addNode(n);
		assertTrue(graph.containsNode(n));
		assertEquals(1, graph.getNodeCount());
		assertEquals(42, graph.getMaxNodeIndex());
	}
	
	@Test
	public void addNodeByString() {
		Node n = gds.newNodeInstance("42");

		assertEquals(-1, graph.getMaxNodeIndex());
		graph.addNode(n);
		assertTrue(graph.containsNode(n));
		assertEquals(1, graph.getNodeCount());
		assertEquals(42, graph.getMaxNodeIndex());
	}
	
	@Test
	public void addEdgeByID() {
		Node n1 = gds.newNodeInstance(1);
		Node n2 = gds.newNodeInstance(2);
		graph.addNode(n1);
		graph.addNode(n2);
		
		Edge e = gds.newEdgeInstance(n1, n2);
		graph.addEdge(e);
		n1.addEdge(e);
		n2.addEdge(e);
		
		assertTrue(n1.hasEdge(e));
		assertTrue(n2.hasEdge(e));
	}
	
	@Test
	public void addEdgeByString() {
		Node n1 = gds.newNodeInstance(1);
		Node n2 = gds.newNodeInstance(2);
		graph.addNode(n1);
		graph.addNode(n2);
		
		String edgeString;
		if ( graph.isDirected() ) {
			edgeString = "1" + Keywords.directedEdgeDelimiter + "2";
		} else {
			edgeString = "1" + Keywords.undirectedEdgeDelimiter + "2";
		}
		
		Edge e = gds.newEdgeInstance(edgeString, graph);
		graph.addEdge(e);
		n1.addEdge(e);
		n2.addEdge(e);
		
		assertTrue(n1.hasEdge(e));
		assertTrue(n2.hasEdge(e));
	}
	
	@Test
	public void removeNode() {
		Node dummy = mock(gds.getNodeType());
		when(dummy.getIndex()).thenReturn(42);

		Node dummy2 = mock(gds.getNodeType());
		when(dummy2.getIndex()).thenReturn(23);

		Node dummy3 = mock(gds.getNodeType());
		when(dummy3.getIndex()).thenReturn(17);
		
		assertEquals(-1, graph.getMaxNodeIndex());
		graph.addNode(dummy);
		graph.addNode(dummy2);
		
		assertEquals(42, graph.getMaxNodeIndex());
		graph.removeNode(dummy);
		
		assertEquals(23, graph.getMaxNodeIndex());
		
		assertFalse(graph.containsNode(dummy3));
		assertFalse(graph.removeNode(dummy3));
		
		graph.removeNode(dummy2);
		assertEquals(-1, graph.getMaxNodeIndex());
	}
	
	@Test
	public void nameAndTimestamp() {
		java.util.Date date= new java.util.Date();
		long ts = date.getTime();
		String name = Long.toString(ts);
		Graph g = new Graph(name, ts, this.gds);
		assertEquals(name,g.getName());
		assertEquals(ts, g.getTimestamp());
	}
}
