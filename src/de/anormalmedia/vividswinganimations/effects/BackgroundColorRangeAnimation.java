package de.anormalmedia.vividswinganimations.effects;

import java.awt.Color;
import java.awt.Component;

import de.anormalmedia.vividswinganimations.AbstractAnimation;

public class BackgroundColorRangeAnimation extends AbstractAnimation {

    private Component   target;
    private final Color targetColor;

    private float[]     initialHsbvals;
    private float[]     targetHsbVals;

    public BackgroundColorRangeAnimation( Component target, Color targetColor ) {
        this.target = target;
        this.targetColor = targetColor;
    }

    @Override
    public void prepare() {
        initialHsbvals = new float[3];
        Color bgColor = target.getBackground();
        Color.RGBtoHSB( bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue(), initialHsbvals );
        targetHsbVals = new float[3];
        Color.RGBtoHSB( targetColor.getRed(), targetColor.getGreen(), targetColor.getBlue(), targetHsbVals );
        super.prepare();
    }

    @Override
    public void animate( long timeProgress ) {
        float deltaH = targetHsbVals[0] - initialHsbvals[0];
        float nextH = Math.max( 0f, Math.min( 1f, initialHsbvals[0] + ((float)deltaH / (float)getDuration() * (float)timeProgress) ) );
        float deltaS = targetHsbVals[1] - initialHsbvals[1];
        float nextS = Math.max( 0f, Math.min( 1f, initialHsbvals[1] + ((float)deltaS / (float)getDuration() * (float)timeProgress) ) );
        float deltaB = targetHsbVals[2] - initialHsbvals[2];
        float nextB = Math.max( 0f, Math.min( 1f, initialHsbvals[2] + ((float)deltaB / (float)getDuration() * (float)timeProgress) ) );

        target.setBackground( new Color( Color.HSBtoRGB( nextH, nextS, nextB ) ) );
    }
}
