package tasks;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

import static org.osbot.rs07.script.MethodProvider.random;

public class Loot extends Task {
	private Area farm;

	public Loot(Script script, Area farm) {
		super(script);
		this.farm = farm;
	}

	@Override
	public boolean verify() {
		return !script.myPlayer().isAnimating() &&
				script.objects.groundItems.closest("Feather") != null &&
				!script.myPlayer().isUnderAttack();
	}

	@Override
	public int execute() {
		GroundItem feather = script.objects.groundItems.closest("Feather");
		if (feather != null && farm.contains(feather)) {
			script.log("Looting feathers");
			if (feather.interact("Take")) {
				new ConditionalSleep(400) {
					@Override
					public boolean condition() {
						return feather.exists();
					};
				}.sleep();
			};
		}
		return random(300, 1100);
	}

	@Override
	public String describe() {
		return "Looting...";
	}

}
