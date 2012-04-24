package de.anormalmedia.vividswinganimations.fade;

import java.awt.Window;

import com.sun.awt.AWTUtilities;

import de.anormalmedia.vividswinganimations.Animation;
import de.anormalmedia.vividswinganimations.color.ComponentFadeAnimation;

public class WindowFadeFactory {

    public static Animation createWindowFadeAnimation( Window target, float targetAlpha ) {
        try {
            if( AWTUtilities.isTranslucencySupported( AWTUtilities.Translucency.TRANSLUCENT ) && AWTUtilities.isTranslucencyCapable( target.getGraphicsConfiguration() ) ) {
                AWTUtilities.setWindowOpaque( target, true );
                AWTUtilities.setWindowOpacity( target, AWTUtilities.getWindowOpacity( target ) );
                return new WindowFadeAnimation( target, targetAlpha );
            }
        } catch( UnsupportedOperationException uoe ) {
        }

        // Wenn keine Window-Opacity, dann nur bei Java 7 einen Animator liefern
        boolean java7 = false;
        try {
            Class.forName( "javax.swing.plaf.nimbus.NimbusLookAndFeel" );
            java7 = true;
        } catch( ClassNotFoundException cnfe ) {
        }
        if( java7 ) {
            return new ComponentFadeAnimation( target, targetAlpha );
        }
        return null;
    }
}
