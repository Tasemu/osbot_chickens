package tasks;

import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

import static org.osbot.rs07.script.MethodProvider.random;

public class KillChickens extends Task {

	public KillChickens(Script script) {
		super(script);
	}
	
	@Override
	public boolean verify() {
		return !script.getCombat().isFighting();
	}

	@Override
	public int execute() {
		script.log("Finding chicken...");
		NPC chicken = script.getNpcs().closest("Chicken");
		
		if (!script.myPlayer().isAnimating() && chicken != null) {
			chicken.interact("Attack");
			new ConditionalSleep(5000) {
				@Override
				public boolean condition() throws InterruptedException {
					return script.myPlayer().isAnimating();
				}
			}.sleep();
		}
		
		return random(300, 1200);
	}

	@Override
	public String describe() {
		return "Attacking Chickens.";
	}
}
