package com.abstractdog.dependency.analyzer.eclipse.project.graph;

import java.util.List;

import com.abstractdog.dependency.analyzer.eclipse.project.EclipseProject;
import com.abstractdog.dependency.analyzer.eclipse.project.Project;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Pair;

public class ProjectGraphBuilder implements GraphBuilder<Project> {
	@Override
	public Graph<Project, String> build(List<Project> list) {
		DirectedSparseGraph<Project, String> graph = new DirectedSparseGraph<Project, String>();
		
		for (Project item: list){
			graph.addVertex(item);
		}
		
		for (Project project : list){
			// edges
			for (Project dep : ((EclipseProject)project).getDependencies().get()){
				graph.addEdge(project.getName() + "_" + dep.getName(),
						new Pair<Project>(project, dep));
			}
		}
		
		return graph;
	}
}
