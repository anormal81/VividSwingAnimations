package de.anormalmedia.vividswinganimations.bounds;

import java.awt.Component;
import java.awt.Point;

import de.anormalmedia.vividswinganimations.AbstractAnimation;

public class LocationAnimation extends AbstractAnimation {

    private Component target;
    private final int targetX;
    private final int targetY;

    private Point     initialLocation;

    public LocationAnimation( Component target, int targetX, int targetY ) {
        this.target = target;
        this.targetX = targetX;
        this.targetY = targetY;
    }

    @Override
    public void prepare() {
        initialLocation = target.getLocation();
        super.prepare();
    }

    @Override
    public void animate( long timeProgress ) {
        int nextX = targetX;
        int nextY = targetY;
        if( targetX != -1 ) {
            int deltaX = targetX - initialLocation.x;
            nextX = initialLocation.x + (int)Math.round( (float)deltaX / (float)getDuration() * (float)timeProgress );
        }
        if( targetY != -1 ) {
            int deltaY = targetY - initialLocation.y;
            nextY = initialLocation.y + (int)Math.round( (float)deltaY / (float)getDuration() * (float)timeProgress );
        }

        if( targetX == -1 ) {
            nextX = target.getLocation().x;
        }
        if( targetY == -1 ) {
            nextY = target.getLocation().y;
        }
        target.setLocation( nextX, nextY );
    }
    
    public int getTargetX() {
        return targetX;
    }
    
    public int getTargetY() {
        return targetY;
    }
}
