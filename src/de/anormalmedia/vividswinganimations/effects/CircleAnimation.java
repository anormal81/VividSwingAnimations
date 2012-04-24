package de.anormalmedia.vividswinganimations.effects;

import java.awt.Component;
import java.awt.Point;

import de.anormalmedia.vividswinganimations.AbstractAnimation;

public class CircleAnimation extends AbstractAnimation {

    private Component     target;
    private final int     radius;

    private Point         initialLocation;
    private int           centerX;
    private int           centerY;
    private final boolean forward;

    public CircleAnimation( Component target, int radius, boolean forward ) {
        this.target = target;
        this.radius = radius;
        this.forward = forward;
    }

    @Override
    public void prepare() {
        initialLocation = target.getLocation();
        centerX = initialLocation.x - radius;
        centerY = initialLocation.y;
        super.prepare();
    }

    @Override
    public void animate( long timeProgress ) {
        int angleInDegrees = (int)Math.round( (float)360 / (float)getDuration() * (float)timeProgress );
        if( !forward ) {
            angleInDegrees = 360 - angleInDegrees;
        }
        float arc = (float)(angleInDegrees * Math.PI / 180f);
        int nextX = (int)(centerX + radius * Math.cos( arc ));
        int nextY = (int)(centerY + radius * Math.sin( arc ));

        target.setLocation( nextX, nextY );
    }
}
