package com.abstractdog.dependency.analyzer.eclipse.project.search;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.abstractdog.dependency.analyzer.eclipse.project.EclipseProject;
import com.abstractdog.dependency.analyzer.eclipse.project.Project;
import com.abstractdog.dependency.analyzer.eclipse.project.list.ProjectList;

public class ProjectSearcher {
	private File rootDir = new File(".");
	private boolean enableSerCache = false; // FIXME: not working properly
	
	public ProjectSearcher(String rootDir) {
		this.rootDir = new File(rootDir);
	}
	
	public ProjectList searchProjects() throws Exception{
		ProjectList projects = new ProjectList();
		
		if (enableSerCache && serializedExists()){
			return getSerialized();
		}
		
		Files.walk(Paths.get(rootDir.getPath())).filter(path -> Files.isDirectory(path)).forEach(path -> {
			if (isProjectFolder(path)){
				try {
					Project project = EclipseProject.create(path.toFile());
					((EclipseProject)project).parseClassPathDependencies();
					
					projects.add(project);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
		
		if (enableSerCache){
			serialize(projects);
		}
		
		return projects;
	}

	protected void serialize(ProjectList projects) throws Exception {
		FileOutputStream fileOut = new FileOutputStream(new File(this.rootDir, "projects.ser"));
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		
		out.writeObject(projects);
		
		out.close();
		fileOut.close();	
	}

	protected ProjectList getSerialized() throws Exception {
		FileInputStream fileIn = new FileInputStream(new File(this.rootDir,
				"projects.ser"));
		ObjectInputStream in = new ObjectInputStream(fileIn);
		ProjectList projects = (ProjectList) in.readObject();
		
		in.close();
		fileIn.close();
		
		return projects;
	}

	protected boolean serializedExists() {
		return new File(this.rootDir, "projects.ser").exists();
	}

	public static boolean isProjectFolder(Path path) {
		File dir = path.toFile();
		return isProjectFolder(dir);
	}
	public static boolean isProjectFolder(File dir) {
		return new File(dir, ".project").exists() && new File(dir, ".classpath").exists();
	}
}
