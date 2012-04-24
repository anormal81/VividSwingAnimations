package de.anormalmedia.vividswinganimations.bounds;

import java.awt.Component;
import java.awt.Dimension;

import de.anormalmedia.vividswinganimations.AbstractAnimation;

public class SizeAnimation extends AbstractAnimation {

    private Component target;
    private final int targetWidth;
    private final int targetHeight;

    private Dimension initialSize;

    public SizeAnimation( Component target, int targetWidth, int targetHeight ) {
        this.target = target;
        this.targetWidth = targetWidth;
        this.targetHeight = targetHeight;
    }

    @Override
    public void prepare() {
        initialSize = target.getSize();
        super.prepare();
    }

    @Override
    public void animate( long timeProgress ) {
        int nextWidth = targetWidth;
        int nextHeight = targetHeight;
        if( targetWidth != -1 ) {
            int deltaWidth = targetWidth - initialSize.width;
            nextWidth = initialSize.width + (int)Math.round( (float)deltaWidth / (float)getDuration() * (float)timeProgress );
        }
        if( targetHeight != -1 ) {
            int deltaHeight = targetHeight - initialSize.height;
            nextHeight = initialSize.height + (int)Math.round( (float)deltaHeight / (float)getDuration() * (float)timeProgress );
        }

        if( targetWidth == -1 ) {
            nextWidth = target.getSize().width;
        }
        if( targetHeight == -1 ) {
            nextHeight = target.getSize().height;
        }
        target.setSize( nextWidth, nextHeight );
    }
}
