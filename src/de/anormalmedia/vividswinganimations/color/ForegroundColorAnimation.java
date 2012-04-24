package de.anormalmedia.vividswinganimations.color;

import java.awt.Color;
import java.awt.Component;

import de.anormalmedia.vividswinganimations.AbstractAnimation;

public class ForegroundColorAnimation extends AbstractAnimation {

    private Component   target;
    private final Color targetColor;

    private Color       initialColor;

    public ForegroundColorAnimation( Component target, Color targetColor ) {
        this.target = target;
        this.targetColor = targetColor;
    }

    @Override
    public void prepare() {
        initialColor = target.getForeground();
        super.prepare();
    }

    @Override
    public void animate( long timeProgress ) {
        int deltaR = targetColor.getRed() - initialColor.getRed();
        int nextR = Math.max( 0, Math.min( 255, initialColor.getRed() + (int)Math.round( (float)deltaR / (float)getDuration() * (float)timeProgress ) ) );
        int deltaG = targetColor.getGreen() - initialColor.getGreen();
        int nextG = Math.max( 0, Math.min( 255, initialColor.getGreen() + (int)Math.round( (float)deltaG / (float)getDuration() * (float)timeProgress ) ) );
        int deltaB = targetColor.getBlue() - initialColor.getBlue();
        int nextB = Math.max( 0, Math.min( 255, initialColor.getBlue() + (int)Math.round( (float)deltaB / (float)getDuration() * (float)timeProgress ) ) );
        int deltaA = targetColor.getAlpha() - initialColor.getAlpha();
        int nextA = Math.max( 0, Math.min( 255, initialColor.getAlpha() + (int)Math.round( (float)deltaA / (float)getDuration() * (float)timeProgress ) ) );

        target.setForeground( new Color( nextR, nextG, nextB, nextA ) );
    }
}
