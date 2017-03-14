package com.abstractdog.dependency.analyzer.eclipse.project.classpath;

public class ClassPathEntry {
	private String kind;
	private String output;
	private String path;
	private String excluding;
	
	private ClassPathEntry() {
	}
	
	public static class Builder {
		private String kind;
		private String output;
		private String excluding;
		private String path;
		
		public Builder(String kind) {
			this.kind = kind;
		}

		public Builder output (String output){
			this.output = output;
			return this;
		}
		
		public Builder excluding (String excluding){
			this.excluding = excluding;
			return this;
		}
		
		public Builder path (String path){
			this.path = path;
			return this;
		}
		
		public ClassPathEntry build(){
			ClassPathEntry entry = new ClassPathEntry();
			
			entry.kind = kind;
			entry.output = output;
			entry.path = path;
			entry.excluding = excluding;
			
			return entry;
		}
	}
	
	public String getKind() {
		return kind;
	}

	public String getOutput() {
		return output;
	}

	public String getPath() {
		return path;
	}
	
	public String getExcluding() {
		return excluding;
	}
	
	@Override
	public String toString() {
		return String.format("CLASSPATHENTRY {kind:%s, output:%s, path:%s}", kind, output, path);
	}
}
