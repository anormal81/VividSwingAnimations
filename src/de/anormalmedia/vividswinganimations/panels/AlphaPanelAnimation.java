package de.anormalmedia.vividswinganimations.panels;

import de.anormalmedia.vividswinganimations.AbstractAnimation;

public class AlphaPanelAnimation extends AbstractAnimation {

    private AlphaPanel  target;
    private final float targetAlpha;

    private float       initialAlpha;

    public AlphaPanelAnimation( AlphaPanel target, float targetAlpha ) {
        this.target = target;
        this.targetAlpha = targetAlpha;
    }

    @Override
    public void prepare() {
        initialAlpha = target.getAlpha();
        super.prepare();
    }

    @Override
    public void animate( long timeProgress ) {
        float deltaA = targetAlpha - initialAlpha;
        float nextA = Math.max( 0f, Math.min( 1f, initialAlpha + (deltaA / (float)getDuration() * (float)timeProgress) ) );
        target.setAlpha( nextA );
    }
}
