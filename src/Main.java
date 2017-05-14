import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import tasks.*;

import java.awt.*;
import java.util.ArrayList;
 
@ScriptManifest(author = "Tasemu", info = "Chicken killing script", name = "Chicken Killer", version = 1.0, logo = "")
public class Main extends Script {
	
	private long startTime;
	private ArrayList<Task> tasks = new ArrayList<>();
 
    @Override
    public void onStart() {
        log("Welcome to Chicken Killer!");
        log("Current version: " + getVersion());
        
        startTime = System.currentTimeMillis();
        
        getExperienceTracker().start(Skill.ATTACK);
        getExperienceTracker().start(Skill.STRENGTH);
        getExperienceTracker().start(Skill.DEFENCE);
        getExperienceTracker().start(Skill.HITPOINTS);
        getExperienceTracker().start(Skill.RANGED);
        getExperienceTracker().start(Skill.MAGIC);
        
        tasks.add(new KillChickens(this));
        tasks.add(new Loot(this));
    }
    
    public int getChickenCount() {
    	int xpPerChicken = 6;
    	int totalXp = getExperienceTracker().getGainedXP(Skill.ATTACK)
    				+ getExperienceTracker().getGainedXP(Skill.STRENGTH)
    				+ getExperienceTracker().getGainedXP(Skill.DEFENCE)
    				+ getExperienceTracker().getGainedXP(Skill.RANGED)
    				+ getExperienceTracker().getGainedXP(Skill.MAGIC);
    	
    	return totalXp / xpPerChicken;
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
    	int xpDrawMarker = 0;
    	
    	g.setColor(Color.WHITE);
    	g.drawString("Chicken Killer", 10, 40);
    	g.drawString("Time running: " + formatTime(runTime), 10, 55);
    	g.drawString("Chickens Killed: ", 10, 70);
    	
    	if (getExperienceTracker().getGainedXP(Skill.ATTACK) > 0) {
    		g.drawString("ATT XP gained: " + getExperienceTracker().getGainedXP(Skill.ATTACK), 10, 85 + xpDrawMarker);
    		xpDrawMarker += 15;
    	}
    	
    	if (getExperienceTracker().getGainedXP(Skill.STRENGTH) > 0) {
    		g.drawString("STR XP gained: " + getExperienceTracker().getGainedXP(Skill.STRENGTH), 10, 85 + xpDrawMarker);
    		xpDrawMarker += 15;
    	}
    	
    	if (getExperienceTracker().getGainedXP(Skill.DEFENCE) > 0) {
    		g.drawString("DEF XP gained: " + getExperienceTracker().getGainedXP(Skill.DEFENCE), 10, 85 + xpDrawMarker);
    		xpDrawMarker += 15;
    	}
    	
    	if (getExperienceTracker().getGainedXP(Skill.HITPOINTS) > 0) {
    		g.drawString("HP XP gained: " + getExperienceTracker().getGainedXP(Skill.HITPOINTS), 10, 85 + xpDrawMarker);
    		xpDrawMarker += 15;
    	}
    	
    	if (getExperienceTracker().getGainedXP(Skill.RANGED) > 0) {
    		g.drawString("RANGED XP gained: " + getExperienceTracker().getGainedXP(Skill.RANGED), 10, 85 + xpDrawMarker);
    		xpDrawMarker += 15;
    	}
    	
    	if (getExperienceTracker().getGainedXP(Skill.MAGIC) > 0) {
    		g.drawString("MAGIC XP gained: " + getExperienceTracker().getGainedXP(Skill.MAGIC), 10, 85 + xpDrawMarker);
    		xpDrawMarker += 15;
    	}
    }
    
    public final String formatTime(final long ms){
        long s = ms / 1000, m = s / 60, h = m / 60;
        s %= 60; m %= 60; h %= 24;
        return String.format("%02d:%02d:%02d", h, m, s);
    }
 
}