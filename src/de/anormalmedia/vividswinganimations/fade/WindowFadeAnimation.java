package de.anormalmedia.vividswinganimations.fade;

import java.awt.Window;
import java.lang.reflect.Method;

import de.anormalmedia.vividswinganimations.AbstractAnimation;

public class WindowFadeAnimation extends AbstractAnimation {

    private Window      target;
    private final float targetAlpha;

    private float       initialAlpha;
    private Method      getOpacity;
    private Method      setOpacity;
    private Method      setWindowOpaque;
    private Method      getWindowOpacity;
    private Method      setWindowOpacity;

    WindowFadeAnimation( Window target, float targetAlpha ) {
        this.target = target;
        this.targetAlpha = targetAlpha;

        try {
            getOpacity = target.getClass().getMethod( "getOpacity" );
            setOpacity = target.getClass().getMethod( "setOpacity", float.class );
        } catch( Throwable t ) {
            try {
                Class<?> aWTUtilities = Class.forName( "com.sun.awt.AWTUtilities" );
                setWindowOpaque = aWTUtilities.getMethod( "setWindowOpaque", Window.class, boolean.class );
                getWindowOpacity = aWTUtilities.getMethod( "getWindowOpacity", Window.class );
                setWindowOpacity = aWTUtilities.getMethod( "setWindowOpacity", Window.class, float.class );
            } catch( Throwable t2 ) {
                t2.printStackTrace();
            }
        }
    }

    @Override
    public void prepare() {
        try {
            if( setWindowOpaque != null ) {
                setWindowOpaque.invoke( null, target, Boolean.TRUE );
            }
            if( getOpacity != null ) {
                initialAlpha = ((Float)getOpacity.invoke( target )).floatValue();
            }
            if( getWindowOpacity != null ) {
                initialAlpha = ((Float)getWindowOpacity.invoke( null, target )).floatValue();
            }
        } catch( Throwable t ) {
            t.printStackTrace();
        }
        super.prepare();
    }

    @Override
    public void animate( long timeProgress ) {
        float deltaAlpha = targetAlpha - initialAlpha;
        float nextAlpha = Math.max( 0f, Math.min( 1f, initialAlpha + ((float)deltaAlpha / (float)getDuration() * (float)timeProgress) ) );
        try {
            if( setOpacity != null ) {
                setOpacity.invoke( target, Float.valueOf( nextAlpha ) );
            }
            if( setWindowOpacity != null ) {
                setWindowOpacity.invoke( null, target, Float.valueOf( nextAlpha ) );
            }
        } catch( Throwable t ) {
            t.printStackTrace();
        }
    }
}
