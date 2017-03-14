package com.abstractdog.dependency.analyzer.eclipse.project;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import com.abstractdog.dependency.analyzer.eclipse.project.classpath.ClassPathEntry;
import com.abstractdog.dependency.analyzer.eclipse.project.classpath.ClassPathParser;
import com.abstractdog.dependency.analyzer.eclipse.project.list.ProjectList;
import com.abstractdog.dependency.analyzer.eclipse.project.projectfile.ProjectFileParser;

public class EclipseProject extends AbstractProject implements Serializable {
	private static final long serialVersionUID = 1L;
	private ProjectList dependencies;
	
	private EclipseProject(File directory) throws Exception {
		this.baseDir = directory;
		this.name = parseName(directory);
	}

	public void parseClassPathDependencies() throws Exception {
		dependencies = new ProjectList();
		
		ClassPathParser classPathParser = new ClassPathParser(new File(this.baseDir, ".classpath"));
		List<ClassPathEntry> classPathEntries = classPathParser.getClassPathEntries();

		for (ClassPathEntry entry : classPathEntries){
			if (entry.getKind().equals("src") && entry.getPath() != null
					&& entry.getPath().startsWith("/")) { // project names are absoulute to workspace
				dependencies.add(EclipseProject.create(new File(".." + entry.getPath()))); // FIXME
			}
		}
	}

	private String parseName(File directory) throws Exception {
		File projectFile = new File(directory, ".project");
		
		if(!projectFile.exists()){
			throw new RuntimeException(String.format("project not found:  %s",
					directory.getName()));
		}
		
		return new ProjectFileParser(projectFile).getName();
	}

	public static EclipseProject create(File directory) throws Exception {
		EclipseProject project = new EclipseProject(directory);
		return project;
	}

	public ProjectList getDependencies(){
		return dependencies;
	}
	
	@Override
	public String toString() {
		return String.format("PROJECT {%s, dependencies: %s}", getName(), dependencies);
	}
}
