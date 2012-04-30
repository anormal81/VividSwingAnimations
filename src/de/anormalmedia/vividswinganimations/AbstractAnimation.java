package de.anormalmedia.vividswinganimations;

import java.util.ArrayList;

import de.anormalmedia.vividswinganimations.interpolation.Interpolator;
import de.anormalmedia.vividswinganimations.interpolation.LinearInterpolator;
import de.anormalmedia.vividswinganimations.listener.AnimationListener;

/**
 * The base implementation for animations. All default values apply (duration=200ms, startOffset=0ms,
 * interpolator=LinearInterpolator). <br>
 * <br>
 * Use this class to write your own animation. The animate() method has to be implemented to change the target
 * component. If an initial value is necessary, feel free to override the prepare() method.
 */
public abstract class AbstractAnimation implements Animation {

    private ArrayList<AnimationListener> listeners    = new ArrayList<AnimationListener>();
    private long                         duration     = 200;
    private int                          startOffset  = 0;
    private boolean                      prepared;
    private boolean                      finished;
    private Interpolator                 interpolator = new LinearInterpolator();

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean executeStep( long timeProgress ) {
        // Ensure that the timeProgress is less or equal the duration.
        if( timeProgress > getDuration() ) {
            timeProgress = getDuration();
        }
        long animationProgress = timeProgress;
        // Ask the interpolator to adapt the time progress
        if( getInterpolator() != null ) {
            animationProgress = getInterpolator().interpolate( animationProgress, getDuration() );
        }
        // FIXME Allow values > duration for overshot effects in interpolation
        // Call the animate methode for visible changes
        animate( animationProgress );
        // More steps to execute if the duration is not reached.
        return timeProgress < getDuration();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void prepare() {
        prepared = true;
        fireAnimationStarted();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPrepared() {
        return prepared;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void finish() {
        finished = true;
        fireAnimationFinished();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFinished() {
        return finished;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDuration( long duration ) {
        this.duration = duration;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getDuration() {
        return duration;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStartOffset( int startOffset ) {
        this.startOffset = startOffset;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getStartOffset() {
        return startOffset;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAnimationListener( AnimationListener listener ) {
        listeners.add( listener );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeAnimationListener( AnimationListener listener ) {
        listeners.remove( listener );
    }

    /**
     * Fires the animation start event to all registered listeners
     */
    protected void fireAnimationStarted() {
        for( AnimationListener listener : listeners ) {
            listener.animationStarted();
        }
    }

    /**
     * Fires the animation finish event to all registered listeners
     */
    protected void fireAnimationFinished() {
        for( AnimationListener listener : listeners ) {
            listener.animationFinished();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInterpolator( Interpolator interpolator ) {
        this.interpolator = interpolator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Interpolator getInterpolator() {
        return interpolator;
    }

}
