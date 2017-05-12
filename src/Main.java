import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import tasks.Task;

import java.awt.*;
import java.util.ArrayList;
 
@ScriptManifest(author = "Tasemu", info = "Chicken killing script with banking", name = "Chicken Killer", version = 1.0, logo = "")
public class Main extends Script {
	
	private int chickensKilled;
	private long startTime;
	private ArrayList<Task> tasks = new ArrayList<>();
 
    @Override
    public void onStart() {
        log("Welcome to Chicken Killer!");
        log("Current version: " + getVersion());
        
        startTime = System.currentTimeMillis();
        chickensKilled = 0;
    }
 
    @Override
    public int onLoop() throws InterruptedException {
    	for (Task task : tasks) {
            if (task.verify())
                return task.execute();
        }
    	
        return random(600, 1600);
    }
 
    @Override
    public void onExit() {
        log("Bye!");
    }
 
    @Override
    public void onPaint(Graphics2D g) {
    	final long runTime = System.currentTimeMillis() - startTime;
    	
    	g.setColor(Color.WHITE);
    	g.drawString("Chicken Killer", 10, 40);
    	g.drawString("Time running: " + formatTime(runTime), 10, 105);
    }
    
    public final String formatTime(final long ms){
        long s = ms / 1000, m = s / 60, h = m / 60;
        s %= 60; m %= 60; h %= 24;
        return String.format("%02d:%02d:%02d", h, m, s);
    }
 
}