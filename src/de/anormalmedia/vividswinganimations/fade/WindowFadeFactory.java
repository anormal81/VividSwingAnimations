package de.anormalmedia.vividswinganimations.fade;

import java.awt.Container;
import java.awt.GraphicsDevice;
import java.awt.GraphicsDevice.WindowTranslucency;
import java.awt.Window;

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
            GraphicsDevice gd = target.getGraphicsConfiguration().getDevice();
            if( gd.isWindowTranslucencySupported( WindowTranslucency.TRANSLUCENT ) ) {
                target.setOpacity( target.getOpacity() );
                return new WindowFadeAnimation( target, targetAlpha );
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
