package tasks;

import org.osbot.rs07.script.Script;

public abstract class Task {
	
	protected Script script;
	
	public Task(Script script) {
		this.script = script;
	}
	
	public abstract boolean verify();
	
	public abstract int execute();
	
	public abstract String describe();

}
