package de.anormalmedia.vividswinganimations.runner;

import java.awt.Toolkit;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import de.anormalmedia.vividswinganimations.Animation;

public class DefaultAnimationRunner implements AnimationRunner {

    private static final int     FRAMES_PER_SECOND = 120;

    private ArrayList<Animation> animations        = new ArrayList<Animation>();
    private Thread               thread            = null;
    private boolean              animationsRunning = false;
    private int                  timeToWait        = 1000 / FRAMES_PER_SECOND;
    private boolean              canceled;

    @Override
    public void run() {
        animationsRunning = true;

        final long starttime = System.currentTimeMillis();
        while( animationsRunning && !canceled ) {

            long animateTime = System.currentTimeMillis();
            animationsRunning = false;
            ArrayList<Animation> localanimations = new ArrayList<Animation>( animations );
            for( final Animation anim : localanimations ) {
                if( anim.isFinished() ) {
                    continue;
                }
                try {
                    SwingUtilities.invokeAndWait( new Runnable() {

                        @Override
                        public void run() {
                            long frameTime = System.currentTimeMillis() - starttime;
                            if( frameTime >= anim.getStartOffset() ) {
                                if( !anim.isPrepared() ) {
                                    anim.prepare();
                                }
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
                    return;
                } catch( InvocationTargetException e ) {
                    e.printStackTrace();
                }
            }
            Toolkit.getDefaultToolkit().sync();
            try {
                long delta = timeToWait - (System.currentTimeMillis() - animateTime);
                Thread.sleep( delta < 1 ? 1 : delta );
            } catch( InterruptedException e ) {
                return;
            }
        }
    }

    @Override
    public boolean isRunning() {
        return thread != null && thread.isAlive();
    }

    @Override
    public void start() {
        stop();
        thread = new Thread( this, "DefaultAnimationExecutor" );
        thread.setDaemon( true );
        thread.start();
    }

    @Override
    public void cancel() {
        canceled = true;
        thread = null;
    }

    @Override
    public void stop() {
        if( thread != null && thread.isAlive() ) {
            thread.interrupt();
        }
        thread = null;
    }

    @Override
    public void addAnimation( Animation animation ) {
        animations.add( animation );
    }

    @Override
    public void removeAnimation( Animation animation ) {
        synchronized( animations ) {
            animations.remove( animation );
        }
    }

    @Override
    public List<Animation> getAnimations() {
        synchronized( animations ) {
            return animations;
        }
    }
}
