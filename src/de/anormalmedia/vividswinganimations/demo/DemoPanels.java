package de.anormalmedia.vividswinganimations.demo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import de.anormalmedia.vividswinganimations.panels.AlphaPanel;
import de.anormalmedia.vividswinganimations.panels.AlphaPanelAnimation;
import de.anormalmedia.vividswinganimations.runner.DefaultAnimationRunner;

public class DemoPanels extends JFrame {

    public DemoPanels() {
        super( "Swing Animations" );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        Container contentPane = getContentPane();
        contentPane.setBackground( Color.red );
        contentPane.setLayout( new BorderLayout() );

        AlphaPanel alphaPanel = new AlphaPanel();
        alphaPanel.setLayout( new BorderLayout() );
        alphaPanel.setOpaque( true );
        alphaPanel.setBackground( Color.YELLOW );
        contentPane.add( alphaPanel, BorderLayout.CENTER );

        JLabel exampleLabel = new JLabel( "Ich bin ein Beispiel" );
        exampleLabel.setHorizontalAlignment( SwingUtilities.CENTER );
        exampleLabel.setVerticalAlignment( SwingUtilities.CENTER );
        alphaPanel.add( exampleLabel, BorderLayout.CENTER );

        setSize( 400, 300 );
        setVisible( true );
        Toolkit.getDefaultToolkit().setDynamicLayout( true );

        DefaultAnimationRunner runner = new DefaultAnimationRunner();
        AlphaPanelAnimation animation = new AlphaPanelAnimation( alphaPanel, 0f );
        animation.setStartOffset( 1000 );
        animation.setDuration( 2000 );
        runner.addAnimation( animation );

        animation = new AlphaPanelAnimation( alphaPanel, 1f );
        animation.setStartOffset( 4000 );
        animation.setDuration( 2000 );
        runner.addAnimation( animation );

        runner.start();
    }

    /**
     * @param args
     */
    public static void main( String[] args ) {
        new DemoPanels();
    }

}
