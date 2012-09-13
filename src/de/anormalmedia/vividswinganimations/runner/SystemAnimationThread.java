package de.anormalmedia.vividswinganimations.runner;

import java.awt.Toolkit;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.SwingUtilities;

/**
 * This is the (by default singleton) instance which queues and executes all animation runners. It's
 * recommended to always use the instance provided by {@link #getDefault()}
 */
public class SystemAnimationThread implements Runnable{

    private static final int     FRAMES_PER_SECOND = 120;
    
    private static final SystemAnimationThread DEFAULT_THREAD = new SystemAnimationThread();

    private Set<AnimationRunner> animations        = new HashSet<AnimationRunner>();
    protected Thread             thread            = null;
    protected int                timeToWait        = 1000 / FRAMES_PER_SECOND;

    /**
     * Returns the singleton instance of the animation thread
     * @return the singleton instance of the animation thread, never null
     */
    public static SystemAnimationThread getDefault(){
        return DEFAULT_THREAD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {

        while( true ) {

            long animateTime = System.currentTimeMillis();

            // Store animation locally to allow concurrent changes
            ArrayList<AnimationRunner> localanimations = new ArrayList<AnimationRunner>();
            synchronized( animations ) {
                localanimations.addAll( animations );
            }
            for( final AnimationRunner runner : localanimations ) {
                if( !runner.isRunning() ) {
                    synchronized( animations ) {
                        animations.remove( runner );
                    }
                    // Only execute not finished animations
                    continue;
                }
                runner.run();
            }
            // Sync to the monitor screen.
            try {
                SwingUtilities.invokeAndWait( new Runnable() {
                    @Override
                    public void run() {
                        // no implementation, just used to wait for all recent animations to finish
                    }
                } );
            } catch( InterruptedException e1 ) {
                thread = null;
                return;
            } catch( InvocationTargetException e1 ) {
                e1.printStackTrace();
            }
            // Note: Some toolkit implementations only have an empty implementation of this method:
            Toolkit.getDefaultToolkit().sync();
            boolean doSleep = true;
            synchronized( animations ) {
                if( animations.size() == 0 ) {
                    // no more animations pending, wait until a new one is added
                    try {
                        animations.wait();
                    } catch( InterruptedException e ) {
                        // runner was stopped while waiting for new animations
                        return;
                    }
                    // don't sleep after the wake-up
                    doSleep = false;
                }
            }
            if( doSleep ) {
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
    }

    /**
     * Returns whether the animation thread is running
     * @return <code>true</code>, if the animation thread is running
     */
    public boolean isRunning() {
        return thread != null && thread.isAlive();
    }

    /**
     * Starts the animation thread and executes all pending animations
     */
    public void start() {
        stop();
        thread = new Thread( this, "System Animation Thread" );
        thread.setDaemon( true );
        thread.start();
    }

    /**
     * Stops the animation thread.<BR>
     * It's not recommended to use this function since it may not finish the current animations.
     */
    public void stop() {
        if( thread != null && thread.isAlive() ) {
            thread.interrupt();
        }
        thread = null;
    }

    /**
     * Adds an animation set and starts/wakes up the animation thread, if required
     */
    public void addAnimation( AnimationRunner animation ) {
        synchronized( animations ) {
            animations.add( animation );
            if( !isRunning() ){
                start();
            } else {
                animations.notify();
            }
        }
    }
}