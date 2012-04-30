package de.anormalmedia.vividswinganimations.runner;

import java.awt.Toolkit;
import java.lang.reflect.InvocationTargetException;
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

    private static final int     FRAMES_PER_SECOND = 120;

    private ArrayList<Animation> animations        = new ArrayList<Animation>();
    protected Thread             thread            = null;
    protected boolean            animationsRunning = false;
    protected int                timeToWait        = 1000 / FRAMES_PER_SECOND;
    protected boolean            canceled;
    protected long               starttime         = 0;

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        animationsRunning = true;

        starttime = System.currentTimeMillis();
        while( animationsRunning && !canceled ) {

            long animateTime = System.currentTimeMillis();
            animationsRunning = false;
            // Store animation locally to allow parallel changes
            ArrayList<Animation> localanimations = new ArrayList<Animation>();
            synchronized( animations ) {
                localanimations.addAll( animations );
            }
            for( final Animation anim : localanimations ) {
                if( anim.isFinished() ) {
                    // Only execute not finished animations
                    continue;
                }
                try {
                    // Wait for each animation to finish, so we can calculate the required sleep time to match the frame rate.
                    SwingUtilities.invokeAndWait( new Runnable() {

                        @Override
                        public void run() {
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
                                animationsRunning |= moreToAnimate;
                            } else {
                                animationsRunning |= true;
                            }
                        }
                    } );
                } catch( InterruptedException e ) {
                    // runner was stopped
                    return;
                } catch( InvocationTargetException e ) {
                    //FIXME: Should not occur, but must be handled
                    e.printStackTrace();
                }
                if( onStepExecuted( anim ) ) {
                    break;
                }
            }
            // Sync to the monitor screen.
            Toolkit.getDefaultToolkit().sync();
            try {
                // Calculate the remaining sleep time. Minimum is 1ms.
                long delta = timeToWait - (System.currentTimeMillis() - animateTime);
                Thread.sleep( delta < 1 ? 1 : delta );
            } catch( InterruptedException e ) {
                // runner was stopped
                return;
            }
        }
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
        return thread != null && thread.isAlive();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        stop();
        thread = new Thread( this, "DefaultAnimationExecutor" );
        thread.setDaemon( true );
        thread.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void cancel() {
        canceled = true;
        thread = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        if( thread != null && thread.isAlive() ) {
            thread.interrupt();
        }
        thread = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAnimation( Animation animation ) {
        synchronized( animations ) {
            animations.add( animation );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeAnimation( Animation animation ) {
        synchronized( animations ) {
            animations.remove( animation );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Animation> getAnimations() {
        synchronized( animations ) {
            return animations;
        }
    }
}
