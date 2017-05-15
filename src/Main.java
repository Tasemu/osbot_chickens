import org.osbot.rs07.api.map.Area;
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
	private Area farm = new Area(
		    new int[][]{
		        { 3184, 3276 },
		        { 3184, 3278 },
		        { 3183, 3281 },
		        { 3179, 3288 },
		        { 3179, 3289 },
		        { 3178, 3289 },
		        { 3177, 3288 },
		        { 3175, 3288 },
		        { 3174, 3289 },
		        { 3171, 3289 },
		        { 3169, 3291 },
		        { 3169, 3295 },
		        { 3170, 3296 },
		        { 3170, 3298 },
		        { 3169, 3299 },
		        { 3169, 3300 },
		        { 3173, 3304 },
		        { 3173, 3308 },
		        { 3180, 3308 },
		        { 3180, 3304 },
		        { 3182, 3304 },
		        { 3183, 3303 },
		        { 3184, 3303 },
		        { 3186, 3301 },
		        { 3186, 3299 },
		        { 3187, 3298 },
		        { 3187, 3296 },
		        { 3186, 3295 },
		        { 3186, 3291 },
		        { 3184, 3289 },
		        { 3190, 3280 },
		        { 3192, 3280 },
		        { 3193, 3279 },
		        { 3193, 3276 },
		    }
		);
	private KillChickens chickenKillingTask = new KillChickens(this, farm);
    
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
        
        tasks.add(chickenKillingTask);
        tasks.add(new Loot(this, farm));
    }
    
    public int getChickenCount() {
    	int xpPerChicken = 12;
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
    	
    	drawMouse(g);
    	
    	g.setColor(Color.WHITE);
    	g.drawString("Chicken Killer", 10, 40);
    	g.drawString("Time running: " + formatTime(runTime), 10, 55);
    	g.drawString("Chickens Killed: " + chickenKillingTask.getChickensKilled(), 10, 70);
    	
    	if (getExperienceTracker().getGainedXP(Skill.ATTACK) > 0) {
    		g.drawString("ATT XP gained: " + getExperienceTracker().getGainedXP(Skill.ATTACK) + " (TTL: " + formatTime(getExperienceTracker().getTimeToLevel(Skill.ATTACK)) + ")", 10, 85 + xpDrawMarker);
    		xpDrawMarker += 15;
    	}
    	
    	if (getExperienceTracker().getGainedXP(Skill.STRENGTH) > 0) {
    		g.drawString("STR XP gained: " + getExperienceTracker().getGainedXP(Skill.STRENGTH) + " (TTL: " + formatTime(getExperienceTracker().getTimeToLevel(Skill.STRENGTH)) + ")", 10, 85 + xpDrawMarker);
    		xpDrawMarker += 15;
    	}
    	
    	if (getExperienceTracker().getGainedXP(Skill.DEFENCE) > 0) {
    		g.drawString("DEF XP gained: " + getExperienceTracker().getGainedXP(Skill.DEFENCE) + " (TTL: " + formatTime(getExperienceTracker().getTimeToLevel(Skill.DEFENCE)) + ")", 10, 85 + xpDrawMarker);
    		xpDrawMarker += 15;
    	}
    	
    	if (getExperienceTracker().getGainedXP(Skill.HITPOINTS) > 0) {
    		g.drawString("HP XP gained: " + getExperienceTracker().getGainedXP(Skill.HITPOINTS) + " (TTL: " + formatTime(getExperienceTracker().getTimeToLevel(Skill.HITPOINTS)) + ")", 10, 85 + xpDrawMarker);
    		xpDrawMarker += 15;
    	}
    	
    	if (getExperienceTracker().getGainedXP(Skill.RANGED) > 0) {
    		g.drawString("RANGED XP gained: " + getExperienceTracker().getGainedXP(Skill.RANGED) + " (TTL: " + formatTime(getExperienceTracker().getTimeToLevel(Skill.RANGED)) + ")", 10, 85 + xpDrawMarker);
    		xpDrawMarker += 15;
    	}
    	
    	if (getExperienceTracker().getGainedXP(Skill.MAGIC) > 0) {
    		g.drawString("MAGIC XP gained: " + getExperienceTracker().getGainedXP(Skill.MAGIC) + " (TTL: " + formatTime(getExperienceTracker().getTimeToLevel(Skill.MAGIC)) + ")", 10, 85 + xpDrawMarker);
    		xpDrawMarker += 15;
    	}
    	
    }
    
    public final String formatTime(final long ms){
        long s = ms / 1000, m = s / 60, h = m / 60;
        s %= 60; m %= 60; h %= 24;
        return String.format("%02d:%02d:%02d", h, m, s);
    }
    
    private void drawMouse(Graphics graphics) {
		((Graphics2D) graphics).setRenderingHints(
			new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
		Point pointer = mouse.getPosition();
		Graphics2D spinG = (Graphics2D) graphics.create();
		Graphics2D spinGRev = (Graphics2D) graphics.create();
		spinG.setColor(new Color(255, 255, 255));
		spinGRev.setColor(Color.cyan);
		spinG.rotate(System.currentTimeMillis() % 2000d / 2000d * (360d) * 2 * Math.PI / 180.0, pointer.x, pointer.y);
		spinGRev.rotate(System.currentTimeMillis() % 2000d / 2000d * (-360d) * 2 * Math.PI / 180.0, pointer.x, pointer.y);
		final int outerSize = 20;
		final int innerSize = 12;
		spinG.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		spinGRev.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		spinG.drawArc(pointer.x - (outerSize / 2), pointer.y - (outerSize / 2), outerSize, outerSize, 100, 75);
		spinG.drawArc(pointer.x - (outerSize / 2), pointer.y - (outerSize / 2), outerSize, outerSize, -100, 75);
		spinGRev.drawArc(pointer.x - (innerSize / 2), pointer.y - (innerSize / 2), innerSize, innerSize, 100, 75);
		spinGRev.drawArc(pointer.x - (innerSize / 2), pointer.y - (innerSize / 2), innerSize, innerSize, -100, 75);
	}
 
}