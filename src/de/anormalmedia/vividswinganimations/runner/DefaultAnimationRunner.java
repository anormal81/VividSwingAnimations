package de.anormalmedia.vividswinganimations.runner;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import de.anormalmedia.vividswinganimations.Animation;

/**
 * The default implementation of {@link AnimationRunner}. This runner allows the parallel execution of one or more
 * animations. <br>
 * <br>
 * Usage:
 * 
 * <pre>
 * DefaultAnimationRunner runner = new DefaultAnimationRunner();
 * runner.addAnimation( new LocationAnimation( targetWindow, targetX, targetY ) );
 * runner.addAnimation( WindowFadeFactory.createWindowFadeAnimation( targetWindow, alpha ) );
 * runner.start();
 * </pre>
 */
public class DefaultAnimationRunner implements AnimationRunner {

    private ArrayList<Animation> animations        = new ArrayList<Animation>();
    protected boolean            animationsRunning = true;
    protected long               starttime         = 0;

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        if( starttime == 0 ) {
            starttime = System.currentTimeMillis();
        }
        SwingUtilities.invokeLater( new Runnable() {
            @Override
            public void run() {
                for( final Animation anim : animations ) {
                    if( anim.isFinished() ) {
                        // Only execute not finished animations
                        continue;
                    }
                    // calculate the current time progress for all animations
                    long frameTime = System.currentTimeMillis() - starttime;
                    // only start animation if start offset is reached
                    if( frameTime >= anim.getStartOffset() ) {
                        // Prepare animation if not done before.
                        if( !anim.isPrepared() ) {
                            anim.prepare();
                        }
                        // execute the animation with the animation specific time progress.
                        boolean moreToAnimate = anim.executeStep( frameTime - anim.getStartOffset() );
                        if( !moreToAnimate ) {
                            anim.finish();
                        }
                    }
                    if( onStepExecuted( anim ) ) {
                        break;
                    }
                }
            }
        } );
    }

    /**
     * Called, if another step of an animation was executed.
     * @param the executed animation
     * @return true to cancel the current step processing, false to continue with the next animation
     */
    protected boolean onStepExecuted( Animation animation ) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRunning() {
        if( !animationsRunning ) {
            return false;
        } else {
            for( int i = 0; i < animations.size(); i++ ) {
                if( !animations.get( i ).isFinished() ) {
                    return true;
                }
            }
        }
        animationsRunning = false;
        return animationsRunning;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        if( animationsRunning ) {
            SystemAnimationThread.getDefault().addAnimation( this );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void cancel() {
        animationsRunning = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAnimation( Animation animation ) {
        animations.add( animation );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeAnimation( Animation animation ) {
        animations.remove( animation );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Animation> getAnimations() {
        return animations;
    }

    @Override
    public void stop() {
        animationsRunning = false;
        for( Animation anim : animations ) {
            anim.finish();
        }
    }
}