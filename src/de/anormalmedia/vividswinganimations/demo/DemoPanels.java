package de.anormalmedia.vividswinganimations.demo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import de.anormalmedia.vividswinganimations.interpolation.AccelerateInterpolator;
import de.anormalmedia.vividswinganimations.listener.AnimationAdapter;
import de.anormalmedia.vividswinganimations.panels.AlphaPanel;
import de.anormalmedia.vividswinganimations.panels.AlphaPanelAnimation;
import de.anormalmedia.vividswinganimations.panels.SlidePanel;
import de.anormalmedia.vividswinganimations.panels.SlidePanel.DIRECTION;
import de.anormalmedia.vividswinganimations.panels.SlidePanelAnimation;
import de.anormalmedia.vividswinganimations.runner.DefaultAnimationRunner;

public class DemoPanels extends JFrame {

    public DemoPanels() {
        super( "Vivid Swing Animations" );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setBackground( new Color( 0, 0, 0, 0 ) );

        final JLayeredPane contentPane = new JLayeredPane();
        contentPane.setBackground( Color.DARK_GRAY );
        setContentPane( contentPane );

        JPanel ground = new JPanel();
        ground.setOpaque( true );
        ground.setBackground( Color.DARK_GRAY );
        contentPane.add( ground, JLayeredPane.DEFAULT_LAYER );

        AlphaPanel alphaPanel = new AlphaPanel();
        alphaPanel.setLayout( new BorderLayout() );
        alphaPanel.setAlpha( 0f );
        contentPane.add( alphaPanel, JLayeredPane.PALETTE_LAYER );

        JPanel exampleFadePanel = new JPanel( new BorderLayout() );
        exampleFadePanel.setOpaque( false );
        exampleFadePanel.setBorder( new CompoundBorder( new EmptyBorder( 20, 20, 20, 20 ), BorderFactory.createBevelBorder( BevelBorder.LOWERED ) ) );
        alphaPanel.add( exampleFadePanel, BorderLayout.CENTER );

        JLabel exampleLabel = new JLabel( "I'm the example" );
        exampleLabel.setHorizontalAlignment( SwingUtilities.CENTER );
        exampleLabel.setVerticalAlignment( SwingUtilities.CENTER );
        exampleLabel.setBackground( Color.BLACK );
        exampleLabel.setOpaque( true );
        exampleLabel.setForeground( Color.RED );
        exampleFadePanel.add( exampleLabel, BorderLayout.CENTER );

        final SlidePanel slidePanel = new SlidePanel( DIRECTION.fromLeft );
        slidePanel.setLayout( new BorderLayout() );
        contentPane.add( slidePanel, JLayeredPane.POPUP_LAYER );

        JPanel slideInExample = new JPanel( new BorderLayout() );
        slidePanel.add( slideInExample, BorderLayout.CENTER );
        slideInExample.setBackground( Color.BLUE );
        slideInExample.setOpaque( true );
        slideInExample.setBorder( BorderFactory.createLineBorder( Color.red, 2 ) );

        exampleLabel = new JLabel( "Sliding in and out ..." );
        exampleLabel.setHorizontalAlignment( SwingUtilities.CENTER );
        exampleLabel.setVerticalAlignment( SwingUtilities.CENTER );
        exampleLabel.setForeground( Color.RED );
        slideInExample.add( exampleLabel, BorderLayout.CENTER );

        setSize( 400, 300 );
        setVisible( true );
        Toolkit.getDefaultToolkit().setDynamicLayout( true );

        Rectangle r = new Rectangle( 0, 0, contentPane.getWidth(), contentPane.getHeight() );
        ground.setBounds( r );
        alphaPanel.setBounds( r );
        slidePanel.setBounds( 0, 0, 200, contentPane.getHeight() );

        DefaultAnimationRunner runner = new DefaultAnimationRunner();
        AlphaPanelAnimation fadeIn = new AlphaPanelAnimation( alphaPanel, 1f );
        fadeIn.setStartOffset( 1000 );
        fadeIn.setDuration( 2000 );
        runner.addAnimation( fadeIn );

        AlphaPanelAnimation fadeOut = new AlphaPanelAnimation( alphaPanel, 0f );
        fadeOut.setStartOffset( 4000 );
        fadeOut.setDuration( 2000 );
        runner.addAnimation( fadeOut );

        SlidePanelAnimation slideIn = new SlidePanelAnimation( slidePanel, 1f );
        slideIn.setInterpolator( new AccelerateInterpolator() );
        slideIn.setStartOffset( 7000 );
        slideIn.setDuration( 1000 );
        runner.addAnimation( slideIn );

        SlidePanelAnimation slideOut = new SlidePanelAnimation( slidePanel, 0f );
        slideOut.setInterpolator( new AccelerateInterpolator() );
        slideOut.setStartOffset( 10000 );
        slideOut.setDuration( 1000 );
        slideOut.addAnimationListener( new AnimationAdapter() {
            @Override
            public void animationFinished() {
                slidePanel.setDirection( DIRECTION.fromRight );
                slidePanel.setBounds( contentPane.getWidth() - 200, 0, 200, contentPane.getHeight() );
                super.animationFinished();
            }
        } );
        runner.addAnimation( slideOut );

        slideIn = new SlidePanelAnimation( slidePanel, 1f );
        slideIn.setInterpolator( new AccelerateInterpolator() );
        slideIn.setStartOffset( 13000 );
        slideIn.setDuration( 1000 );
        runner.addAnimation( slideIn );

        slideOut = new SlidePanelAnimation( slidePanel, 0f );
        slideOut.setInterpolator( new AccelerateInterpolator() );
        slideOut.setStartOffset( 16000 );
        slideOut.setDuration( 1000 );
        slideOut.addAnimationListener( new AnimationAdapter() {
            @Override
            public void animationFinished() {
                slidePanel.setDirection( DIRECTION.fromTop );
                slidePanel.setBounds( 0, 0, contentPane.getWidth(), 200 );
                super.animationFinished();
            }
        } );
        runner.addAnimation( slideOut );

        slideIn = new SlidePanelAnimation( slidePanel, 1f );
        slideIn.setInterpolator( new AccelerateInterpolator() );
        slideIn.setStartOffset( 19000 );
        slideIn.setDuration( 1000 );
        runner.addAnimation( slideIn );

        slideOut = new SlidePanelAnimation( slidePanel, 0f );
        slideOut.setInterpolator( new AccelerateInterpolator() );
        slideOut.setStartOffset( 22000 );
        slideOut.setDuration( 1000 );
        slideOut.addAnimationListener( new AnimationAdapter() {
            @Override
            public void animationFinished() {
                slidePanel.setDirection( DIRECTION.fromBottom );
                slidePanel.setBounds( 0, contentPane.getHeight() - 200, contentPane.getWidth(), 200 );
                super.animationFinished();
            }
        } );
        runner.addAnimation( slideOut );

        slideIn = new SlidePanelAnimation( slidePanel, 1f );
        slideIn.setInterpolator( new AccelerateInterpolator() );
        slideIn.setStartOffset( 25000 );
        slideIn.setDuration( 1000 );
        runner.addAnimation( slideIn );

        slideOut = new SlidePanelAnimation( slidePanel, 0f );
        slideOut.setInterpolator( new AccelerateInterpolator() );
        slideOut.setStartOffset( 28000 );
        slideOut.setDuration( 1000 );
        slideOut.addAnimationListener( new AnimationAdapter() {
            @Override
            public void animationFinished() {
                DemoPanels.this.dispose();
            }
        } );
        runner.addAnimation( slideOut );

        runner.start();
    }

    /**
     * @param args
     */
    public static void main( String[] args ) {
        new DemoPanels();
    }

}
