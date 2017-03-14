package com.abstractdog.dependency.analyzer.eclipse.project.graph;

import java.util.List;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Pair;

public class NamedGraphBuilder implements GraphBuilder<NamedGraphElement> {

	@Override
	public Graph<NamedGraphElement, String> build(
			List<NamedGraphElement> elements) {
		
		DirectedSparseGraph<NamedGraphElement, String> graph = new DirectedSparseGraph<NamedGraphElement, String>();

		for (NamedGraphElement e : elements) {
			graph.addVertex(e);
			
			for (String edge : e.getEdges()){
				graph.addEdge(e.getName() + "_" + edge.toString(),
						new Pair<NamedGraphElement>(e, new NamedGraphElement(edge)));
			}
		}
		
		return graph;
	}
}
