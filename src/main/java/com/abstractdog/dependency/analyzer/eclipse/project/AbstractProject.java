package com.abstractdog.dependency.analyzer.eclipse.project;

import java.io.File;
import java.io.IOException;

public abstract class AbstractProject implements Project {
	protected String name;
	protected File baseDir;
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public File getBaseDir() {
		return baseDir;
	}
	
	@Override
	public String toString() {
		return String.format("PROJECT: %s (dir: %s)", getName(), getBaseDir().getPath());
	}
	

	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		try {
			return ((Project) obj).getName().equals(name)
					&& ((Project) obj).getBaseDir().getCanonicalPath()
							.equalsIgnoreCase(getBaseDir().getCanonicalPath());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
