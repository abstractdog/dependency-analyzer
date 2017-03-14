package com.abstractdog.dependency.analyzer.eclipse.project.graph;

import java.util.ArrayList;
import java.util.List;

public class NamedGraphElement {
	private String name;
	private List<String> edges = new ArrayList<String>();
	
	public NamedGraphElement(String name) {
		this.name = name;
	}
	
	public NamedGraphElement addEdge(String edge){
		edges.add(edge);
		return this;
	}

	public List<String> getEdges(){
		return edges;
	}
	
	public String getName(){
		return name;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj.toString().equals(toString());
	}
	
	@Override
	public String toString() {
		return name;
	}
}
