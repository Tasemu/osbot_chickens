package utils;

import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.event.Event;

import java.awt.*;

public class AttackStyle extends Event {

    private final int attackStyleParent = 593;
    private final int[] attackStyleChildren = {3, 7, 11, 15};
    private final String xpType;
    private int attackStyleToCheck = 0;

    public AttackStyle(final String xpType) {
        this.xpType = xpType;
    }


    @Override
    public int execute() throws InterruptedException {
        if (getTabs().getOpen() != Tab.ATTACK) {
            getTabs().open(Tab.ATTACK);
            return 0;
        }

        RS2Widget attackStyleWidget = getWidgets().get(attackStyleParent, attackStyleChildren[attackStyleToCheck]);

        if (attackStyleWidget == null) {
            setFailed();
            return 0;
        }

        if (!attackStyleWidget.hover()) {
            return 0;
        }

        sleep(random(500, 600));

        if (getWidgets().singleFilter(attackStyleParent, widget -> widget.getMessage().matches(".*\\(" + xpType + " XP\\)$")) == null) {
            attackStyleToCheck++;
            if (attackStyleToCheck >= attackStyleChildren.length) {
                setFailed();
            }
            return 0;
        }

        Rectangle widgetBounds = attackStyleWidget.getBounds();

        double colorX = widgetBounds.getMinX() + 5;
        double colorY = widgetBounds.getMinY() + 5;

        if (getColorPicker().colorAt((int) colorX, (int) colorY).getRed() > 100) {
            log("Already selected");
            setFinished();
            return 0;
        }

        if (attackStyleWidget.interact()) {
            setFinished();
        }
        return 0;
    }
}