package de.anormalmedia.vividswinganimations.demo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import de.anormalmedia.vividswinganimations.Animation;
import de.anormalmedia.vividswinganimations.bounds.LocationAnimation;
import de.anormalmedia.vividswinganimations.bounds.SizeAnimation;
import de.anormalmedia.vividswinganimations.color.BackgroundColorAnimation;
import de.anormalmedia.vividswinganimations.color.ForegroundColorAnimation;
import de.anormalmedia.vividswinganimations.effects.BackgroundColorRangeAnimation;
import de.anormalmedia.vividswinganimations.effects.CircleAnimation;
import de.anormalmedia.vividswinganimations.fade.WindowFadeFactory;
import de.anormalmedia.vividswinganimations.listener.AnimationAdapter;
import de.anormalmedia.vividswinganimations.runner.DefaultAnimationRunner;

public class DemoFrame extends JFrame {

    public DemoFrame() {
        super( "Swing Animations" );
        // Window translucency in Java 7 is only supported for undecortated windows.
        setUndecorated( true );

        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        Container contentPane = getContentPane();
        contentPane.setBackground( Color.BLACK );
        contentPane.setLayout( new BorderLayout() );

        JLabel lblGeil = new JLabel( "Geil, ne!?" );
        lblGeil.setForeground( new Color( 0, 0, 0, 0 ) );
        lblGeil.setFont( lblGeil.getFont().deriveFont( Font.BOLD, 32 ) );
        lblGeil.setHorizontalAlignment( SwingUtilities.CENTER );
        lblGeil.setVerticalAlignment( SwingUtilities.CENTER );
        contentPane.add( lblGeil, BorderLayout.CENTER );

        setSize( 400, 300 );
        setVisible( true );
        Toolkit.getDefaultToolkit().setDynamicLayout( true );

        final Rectangle frameBounds = DemoFrame.this.getGraphicsConfiguration().getBounds();

        DefaultAnimationRunner defaultAnimationExecutor = new DefaultAnimationRunner();

        SizeAnimation heightAnimation = new SizeAnimation( this, -1, 600 );
        heightAnimation.setStartOffset( 1000 );
        heightAnimation.setDuration( 1000 );
        defaultAnimationExecutor.addAnimation( heightAnimation );

        SizeAnimation widthAnimation = new SizeAnimation( this, 800, -1 );
        widthAnimation.setStartOffset( 2000 );
        widthAnimation.setDuration( 1000 );
        defaultAnimationExecutor.addAnimation( widthAnimation );

        BackgroundColorAnimation greenBackgroundColorAnimation = new BackgroundColorAnimation( contentPane, Color.GREEN );
        greenBackgroundColorAnimation.setDuration( 1000 );
        defaultAnimationExecutor.addAnimation( greenBackgroundColorAnimation );

        BackgroundColorAnimation redBackgroundColorAnimation = new BackgroundColorAnimation( contentPane, Color.RED );
        redBackgroundColorAnimation.setStartOffset( 1000 );
        redBackgroundColorAnimation.setDuration( 1000 );
        defaultAnimationExecutor.addAnimation( redBackgroundColorAnimation );

        BackgroundColorAnimation blueBackgroundColorAnimation = new BackgroundColorAnimation( contentPane, Color.BLUE );
        blueBackgroundColorAnimation.setStartOffset( 2000 );
        blueBackgroundColorAnimation.setDuration( 1000 );
        defaultAnimationExecutor.addAnimation( blueBackgroundColorAnimation );

        Animation windowFadeAnimation = WindowFadeFactory.createWindowFadeAnimation( this, 0f );
        windowFadeAnimation.setStartOffset( 3000 );
        windowFadeAnimation.setDuration( 1000 );
        defaultAnimationExecutor.addAnimation( windowFadeAnimation );

        windowFadeAnimation.addAnimationListener( new AnimationAdapter() {

            @Override
            public void animationFinished() {
                Point p = DemoFrame.this.getLocation();
                p.x = frameBounds.x + frameBounds.width - DemoFrame.this.getWidth();
                DemoFrame.this.setLocation( p );
            }
        } );

        windowFadeAnimation = WindowFadeFactory.createWindowFadeAnimation( this, 1f );
        windowFadeAnimation.setStartOffset( 4000 );
        windowFadeAnimation.setDuration( 1000 );
        defaultAnimationExecutor.addAnimation( windowFadeAnimation );

        SizeAnimation collapseWidthAnimation = new SizeAnimation( this, 200, -1 );
        collapseWidthAnimation.setStartOffset( 6000 );
        collapseWidthAnimation.setDuration( 1000 );
        defaultAnimationExecutor.addAnimation( collapseWidthAnimation );

        LocationAnimation locationXAnimation = new LocationAnimation( this, frameBounds.x + frameBounds.width / 2 + 100, -1 );
        locationXAnimation.setStartOffset( 6000 );
        locationXAnimation.setDuration( 1000 );
        defaultAnimationExecutor.addAnimation( locationXAnimation );

        SizeAnimation collapseHeighthAnimation = new SizeAnimation( this, -1, 100 );
        collapseHeighthAnimation.setStartOffset( 7000 );
        collapseHeighthAnimation.setDuration( 1000 );
        defaultAnimationExecutor.addAnimation( collapseHeighthAnimation );

        LocationAnimation locationYAnimation = new LocationAnimation( this, -1, 250 );
        locationYAnimation.setStartOffset( 7000 );
        locationYAnimation.setDuration( 1000 );
        defaultAnimationExecutor.addAnimation( locationYAnimation );

        CircleAnimation circleAnimation = new CircleAnimation( this, 200, true );
        circleAnimation.setStartOffset( 8000 );
        circleAnimation.setDuration( 1000 );
        defaultAnimationExecutor.addAnimation( circleAnimation );

        CircleAnimation circleReverseAnimation = new CircleAnimation( this, 200, false );
        circleReverseAnimation.setStartOffset( 9000 );
        circleReverseAnimation.setDuration( 1000 );
        defaultAnimationExecutor.addAnimation( circleReverseAnimation );

        BackgroundColorAnimation blackBackgroundColorAnimation = new BackgroundColorAnimation( contentPane, Color.BLACK );
        blackBackgroundColorAnimation.setStartOffset( 10000 );
        blackBackgroundColorAnimation.setDuration( 1000 );
        defaultAnimationExecutor.addAnimation( blackBackgroundColorAnimation );

        ForegroundColorAnimation foregroundColorAnimation = new ForegroundColorAnimation( lblGeil, Color.WHITE );
        foregroundColorAnimation.setStartOffset( 11000 );
        foregroundColorAnimation.setDuration( 2000 );
        defaultAnimationExecutor.addAnimation( foregroundColorAnimation );

        BackgroundColorRangeAnimation prepareColorRangeAnimation = new BackgroundColorRangeAnimation( contentPane, new Color( Color.HSBtoRGB( 0f, 1f, 1f ) ) );
        prepareColorRangeAnimation.setStartOffset( 13000 );
        prepareColorRangeAnimation.setDuration( 1000 );
        defaultAnimationExecutor.addAnimation( prepareColorRangeAnimation );

        BackgroundColorRangeAnimation backgroundColorRangeAnimation = new BackgroundColorRangeAnimation( contentPane, new Color( Color.HSBtoRGB( 0.99f, 1f, 1f ) ) );
        backgroundColorRangeAnimation.setStartOffset( 14000 );
        backgroundColorRangeAnimation.setDuration( 10000 );
        defaultAnimationExecutor.addAnimation( backgroundColorRangeAnimation );

        windowFadeAnimation = WindowFadeFactory.createWindowFadeAnimation( this, 0f );
        windowFadeAnimation.setStartOffset( 16000 );
        windowFadeAnimation.setDuration( 8000 );
        defaultAnimationExecutor.addAnimation( windowFadeAnimation );

        windowFadeAnimation.addAnimationListener( new AnimationAdapter() {

            @Override
            public void animationFinished() {
                DemoFrame.this.dispose();
            }
        } );

        defaultAnimationExecutor.start();
    }

    /**
     * @param args
     */
    public static void main( String[] args ) {
        new DemoFrame();
    }

}
