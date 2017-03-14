package com.abstractdog.dependency.analyzer.eclipse.project.search.test;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import com.abstractdog.dependency.analyzer.eclipse.project.EclipseProject;
import com.abstractdog.dependency.analyzer.eclipse.project.Project;
import com.abstractdog.dependency.analyzer.eclipse.project.list.ProjectList;
import com.abstractdog.dependency.analyzer.eclipse.project.search.ProjectSearcher;

public class ProjectSearcherTest {

	@Test
	public void testSearchProjects() throws Exception{
		ProjectList projects = new ProjectSearcher("..").searchProjects();
		
		for (Project project : projects.get()){
			System.out.println(project);
		}
		
		Assert.assertEquals(true, projects.contains(EclipseProject.create(new File("."))));
	}
}
