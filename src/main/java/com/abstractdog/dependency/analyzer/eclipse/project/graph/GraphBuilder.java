package com.abstractdog.dependency.analyzer.eclipse.project.graph;

import java.util.List;

import edu.uci.ics.jung.graph.Graph;

public interface GraphBuilder<T> {
	Graph<T, String> build(List<T> objects);
}
