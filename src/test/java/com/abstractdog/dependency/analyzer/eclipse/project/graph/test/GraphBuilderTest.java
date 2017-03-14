package com.abstractdog.dependency.analyzer.eclipse.project.graph.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.junit.Test;

import com.abstractdog.dependency.analyzer.eclipse.project.Project;
import com.abstractdog.dependency.analyzer.eclipse.project.graph.NamedGraphBuilder;
import com.abstractdog.dependency.analyzer.eclipse.project.graph.NamedGraphElement;
import com.abstractdog.dependency.analyzer.eclipse.project.graph.ProjectGraphBuilder;
import com.abstractdog.dependency.analyzer.eclipse.project.graph.jung.GraphDrawer;
import com.abstractdog.dependency.analyzer.eclipse.project.list.ProjectList;
import com.abstractdog.dependency.analyzer.eclipse.project.search.ProjectSearcher;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

public class GraphBuilderTest {

	@Test
	public void testGraphBuilderSimple() throws Exception {
		NamedGraphElement e1 = new NamedGraphElement("1").addEdge("2").addEdge("3");
		NamedGraphElement e2 = new NamedGraphElement("2").addEdge("1");
		NamedGraphElement e3 = new NamedGraphElement("3").addEdge("1");

		Graph<NamedGraphElement, String> g = (Graph<NamedGraphElement, String>) new NamedGraphBuilder()
				.build(Arrays.asList(new NamedGraphElement[] { e1, e2, e3 }));

		BufferedImage image = new GraphDrawer(g).withLabelTransformer(new ToStringLabeller() {
			@Override
			public String apply(Object v) {
				return v.toString();
			}
		}).invoke();
		
		File outputFile = new File("graph.png");
		ImageIO.write(image, "png", outputFile);
	}

	@Test
	public void testGraphBuilderProjects() throws Exception {
		ProjectList projects = new ProjectSearcher("..").searchProjects();

		Graph<Project, String> g = (Graph<Project, String>) new ProjectGraphBuilder()
				.build(projects.get());

		BufferedImage image = new GraphDrawer(g).withLabelTransformer(new ToStringLabeller() {
			@Override
			public String apply(Object v) {
				return ((Project) v).getName();
			}
		}).invoke();


		File outputFile = new File("graph.png");
		ImageIO.write(image, "png", outputFile);
	}
}
