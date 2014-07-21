package de.anormalmedia.vividswinganimations.fade;

import java.awt.Container;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.Window;
import java.lang.reflect.Method;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JWindow;

import de.anormalmedia.vividswinganimations.Animation;
import de.anormalmedia.vividswinganimations.color.ComponentFadeAnimation;
import de.anormalmedia.vividswinganimations.panels.AlphaPanel;
import de.anormalmedia.vividswinganimations.panels.AlphaPanelAnimation;

public class WindowFadeFactory {

    public static Animation createWindowFadeAnimation( Window target, float targetAlpha ) {
        try {
            // Java 7 API
            try {
                Class<?> windowTranslucency = Class.forName( "java.awt.GraphicsDevice$WindowTranslucency" );
                GraphicsDevice gd = target.getGraphicsConfiguration().getDevice();
                Method isWindowTranslucencySupported = gd.getClass().getMethod( "isWindowTranslucencySupported", windowTranslucency );
                Object isSupported = isWindowTranslucencySupported.invoke( gd, windowTranslucency.getField( "TRANSLUCENT" ).get( null ) );
                if( (isSupported instanceof Boolean) && ((Boolean)isSupported).booleanValue() ) {
                    Method getOpacity = Window.class.getMethod( "getOpacity" );
                    Method setOpacity = Window.class.getMethod( "setOpacity", float.class );
                    Float opacity = (Float)getOpacity.invoke( target );
                    setOpacity.invoke( target, opacity );
                    return new WindowFadeAnimation( target, targetAlpha );
                }
            } catch( ClassNotFoundException t ) {
                // Java 7 API not supported
            }

            // Java 6 API
            try {
                Class<?> aWTUtilities = Class.forName( "com.sun.awt.AWTUtilities" );
                Class<?> translucency = Class.forName( "com.sun.awt.AWTUtilities$Translucency" );
                Method isTranslucencySupported = aWTUtilities.getMethod( "isTranslucencySupported", translucency );
                Method isTranslucencyCapable = aWTUtilities.getMethod( "isTranslucencyCapable", GraphicsConfiguration.class );

                Object translucencySupported = isTranslucencySupported.invoke( null, translucency.getField( "TRANSLUCENT" ).get( null ) );
                if( (translucencySupported instanceof Boolean) && ((Boolean)translucencySupported).booleanValue() ) {
                    Object translucencyCapable = isTranslucencyCapable.invoke( null, target.getGraphicsConfiguration() );
                    if( (translucencyCapable instanceof Boolean) && ((Boolean)translucencyCapable).booleanValue() ) {
                        Method setWindowOpaque = aWTUtilities.getMethod( "setWindowOpaque", Window.class, boolean.class );
                        setWindowOpaque.invoke( null, target, Boolean.TRUE );
                        Method getWindowOpacity = aWTUtilities.getMethod( "getWindowOpacity", Window.class );
                        Method setWindowOpacity = aWTUtilities.getMethod( "setWindowOpacity", Window.class, float.class );
                        Float opacity = (Float)getWindowOpacity.invoke( null, target );
                        setWindowOpacity.invoke( null, target, opacity );
                        return new WindowFadeAnimation( target, targetAlpha );
                    }
                }
            } catch( ClassNotFoundException t ) {
                // Java 6 API not supported
            }
        } catch( Throwable t ) {
            t.printStackTrace();
        }
        Container contentPane = null;
        if( target instanceof JFrame ) {
            JFrame frame = (JFrame)target;
            contentPane = frame.getContentPane();
        }
        if( target instanceof JDialog ) {
            JDialog dialog = (JDialog)target;
            contentPane = dialog.getContentPane();
        }
        if( target instanceof JWindow ) {
            JWindow window = (JWindow)target;
            contentPane = window.getContentPane();
        }
        if( contentPane instanceof AlphaPanel ) {
            AlphaPanel aPanel = (AlphaPanel)contentPane;
            return new AlphaPanelAnimation( aPanel, targetAlpha );
        }

        return new ComponentFadeAnimation( target, targetAlpha );
    }
}
