package tasks;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

import static org.osbot.rs07.script.MethodProvider.random;

public class KillChickens extends Task {
	private Area farm;

	public KillChickens(Script script, Area farm) {
		super(script);
		this.farm = farm;
	}
	
	@Override
	public boolean verify() {
		return !script.myPlayer().isAnimating() && !script.myPlayer().isHitBarVisible();
	}

	@Override
	public int execute() {
		if (farm.contains(script.myPlayer())) {
			script.log("Finding chicken...");
			NPC chicken = script.getNpcs().closest("Chicken");
			
			if (
				!script.myPlayer().isAnimating() &&
				chicken != null && farm.contains(chicken) &&
				!chicken.isUnderAttack()
			) {
				script.getCamera().toEntity(chicken);
				chicken.interact("Attack");
				new ConditionalSleep(5000) {
					@Override
					public boolean condition() throws InterruptedException {
						return script.myPlayer().isAnimating() && script.myPlayer().isHitBarVisible();
					}
				}.sleep();
			}
		} else {
			script.getWalking().webWalk(farm);
		}
		
		return random(300, 1200);
	}

	@Override
	public String describe() {
		return "Attacking Chickens.";
	}
}
