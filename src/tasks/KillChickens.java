package tasks;

import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

import static org.osbot.rs07.script.MethodProvider.random;

public class KillChickens extends Task {
	private Area farm;
	public Filter<NPC> chickenFilter = new Filter<NPC>() {
		@Override
		public boolean match(final NPC entity) {
			return entity.getName().equalsIgnoreCase("chicken") &&
				   !entity.isUnderAttack() &&
				   entity.isAttackable() &&
				   farm.contains(entity);
		}
	};
	private int chickenCounter = 0;
	
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
			NPC chicken = script.getNpcs().singleFilter(script.getNpcs().getAll(), chickenFilter);
			
			if (!script.myPlayer().isAnimating() && chicken != null) {
				script.getCamera().toEntity(chicken);
				if (chicken.interact("Attack")) {
					new ConditionalSleep(5000) {
						@Override
						public boolean condition() throws InterruptedException {
							return chicken.exists() &&
								   chicken.getHealthPercent() > 0 &&
								   script.myPlayer().isInteracting(chicken);
						}
					}.sleep();
					if (chicken.getHealthPercent() == 0 || chicken == null) {
						chickenCounter++;
					}
				};
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
	
	public int getChickensKilled() {
		return chickenCounter;
	}

}
