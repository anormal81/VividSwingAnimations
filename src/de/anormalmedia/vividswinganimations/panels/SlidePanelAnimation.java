package de.anormalmedia.vividswinganimations.panels;

import de.anormalmedia.vividswinganimations.AbstractAnimation;

public class SlidePanelAnimation extends AbstractAnimation {

    private SlidePanel  target;
    private final float targetSlideValue;

    private float       initialSlideValue;

    public SlidePanelAnimation( SlidePanel target, float targetSlideValue ) {
        this.target = target;
        this.targetSlideValue = targetSlideValue;
    }

    @Override
    public void prepare() {
        initialSlideValue = target.getSlideValue();
        super.prepare();
    }

    @Override
    public void animate( long timeProgress ) {
        float deltaSV = targetSlideValue - initialSlideValue;
        float nextSV = Math.max( 0f, Math.min( 1f, initialSlideValue + (deltaSV / (float)getDuration() * (float)timeProgress) ) );
        target.setSlideValue( nextSV );
    }
}
