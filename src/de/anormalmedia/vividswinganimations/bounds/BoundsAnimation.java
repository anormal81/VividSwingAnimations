package de.anormalmedia.vividswinganimations.bounds;

import java.awt.Component;
import java.awt.Rectangle;

import de.anormalmedia.vividswinganimations.AbstractAnimation;

public class BoundsAnimation extends AbstractAnimation {

    private Component       target;
    private final Rectangle targetBounds;
    private Rectangle       initialBounds;

    public BoundsAnimation( Component target, Rectangle targetBounds ) {
        this.target = target;
        this.targetBounds = targetBounds;
    }

    @Override
    public void prepare() {
        initialBounds = target.getBounds();
        super.prepare();
    }

    @Override
    public void animate( long timeProgress ) {
        int nextX = targetBounds.x;
        int nextY = targetBounds.y;
        int nextWidth = targetBounds.width;
        int nextHeight = targetBounds.height;
        if( targetBounds.x != -1 ) {
            int deltaX = targetBounds.x - initialBounds.x;
            nextX = initialBounds.x + (int)Math.round( (float)deltaX / (float)getDuration() * (float)timeProgress );
        }
        if( targetBounds.y != -1 ) {
            int deltaY = targetBounds.y - initialBounds.y;
            nextY = initialBounds.y + (int)Math.round( (float)deltaY / (float)getDuration() * (float)timeProgress );
        }
        if( targetBounds.width != -1 ) {
            int deltaWidth = targetBounds.width - initialBounds.width;
            nextWidth = initialBounds.width + (int)Math.round( (float)deltaWidth / (float)getDuration() * (float)timeProgress );
        }
        if( targetBounds.height != -1 ) {
            int deltaHeight = targetBounds.height - initialBounds.height;
            nextHeight = initialBounds.height + (int)Math.round( (float)deltaHeight / (float)getDuration() * (float)timeProgress );
        }

        Rectangle currentBounds = target.getBounds();
        if( targetBounds.x == -1 ) {
            nextX = currentBounds.x;
        }
        if( targetBounds.y == -1 ) {
            nextY = currentBounds.y;
        }
        if( targetBounds.width == -1 ) {
            nextWidth = currentBounds.width;
        }
        if( targetBounds.height == -1 ) {
            nextHeight = currentBounds.height;
        }
        target.setBounds( nextX, nextY, nextWidth, nextHeight );
    }

    public Rectangle getTargetBounds() {
        return targetBounds;
    }
}
