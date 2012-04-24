package de.anormalmedia.vividswinganimations.fade;

import java.awt.Window;

import com.sun.awt.AWTUtilities;

import de.anormalmedia.vividswinganimations.AbstractAnimation;

public class WindowFadeAnimation extends AbstractAnimation {

    private Window      target;
    private final float targetAlpha;

    private float       initialAlpha;

    WindowFadeAnimation( Window target, float targetAlpha ) {
        this.target = target;
        this.targetAlpha = targetAlpha;
    }

    @Override
    public void prepare() {
        AWTUtilities.setWindowOpaque( target, true );
        initialAlpha = AWTUtilities.getWindowOpacity( target );
        super.prepare();
    }

    @Override
    public void animate( long timeProgress ) {
        float deltaAlpha = targetAlpha - initialAlpha;
        float nextAlpha = Math.max( 0f, Math.min( 1f, initialAlpha + ((float)deltaAlpha / (float)getDuration() * (float)timeProgress) ) );

        AWTUtilities.setWindowOpacity( target, nextAlpha );
    }
}
