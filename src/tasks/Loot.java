package tasks;

import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.script.Script;
import static org.osbot.rs07.script.MethodProvider.random;

public class Loot extends Task {

	public Loot(Script script) {
		super(script);
	}

	@Override
	public boolean verify() {
		return !script.myPlayer().isAnimating() && script.objects.groundItems.closest("Feather") != null;
	}

	@Override
	public int execute() {
		GroundItem feather = script.objects.groundItems.closest("Feather");
		feather.interact("Take");
		return random(300, 1100);
	}

	@Override
	public String describe() {
		return "Looting...";
	}

}
