package tasks;

import org.osbot.rs07.script.Script;

public class Loot extends Task {

	public Loot(Script script) {
		super(script);
	}

	@Override
	public boolean verify() {
		return !script.myPlayer().isAnimating();
	}

	@Override
	public int execute() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String describe() {
		// TODO Auto-generated method stub
		return null;
	}

}
