package com.abstractdog.dependency.analyzer.eclipse.project.list;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.abstractdog.dependency.analyzer.eclipse.project.EclipseProject;
import com.abstractdog.dependency.analyzer.eclipse.project.Project;

public class ProjectList implements Serializable {
	private static final long serialVersionUID = 1L;
	
	List<Project> projects = new ArrayList<Project>();

	public void add(Project project) {
		projects.add(project);
	}

	public List<Project> get() {
		return new ArrayList<Project>(projects);
	}
	
	public Project searchByName(String name) {
		for (Project project : projects) {
			if (name.equals(project.getName())){
				return project;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return projects.toString();
	}

	public Object contains(EclipseProject project) {
		return projects.contains(project);
	}

}
