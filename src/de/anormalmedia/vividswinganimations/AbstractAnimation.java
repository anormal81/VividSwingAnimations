package de.anormalmedia.vividswinganimations;

import java.util.ArrayList;

import de.anormalmedia.vividswinganimations.interpolation.Interpolator;
import de.anormalmedia.vividswinganimations.interpolation.LinearInterpolator;
import de.anormalmedia.vividswinganimations.listener.AnimationListener;

public abstract class AbstractAnimation implements Animation {

    private ArrayList<AnimationListener> listeners    = new ArrayList<AnimationListener>();
    private long                         duration     = 200;
    private int                          startOffset  = 0;
    private boolean                      prepared;
    private boolean                      finished;
    private Interpolator                 interpolator = new LinearInterpolator();

    @Override
    public boolean executeStep( long timeProgress ) {
        if( timeProgress > getDuration() ) {
            timeProgress = getDuration();
        }
        long animationProgress = timeProgress;
        if( getInterpolator() != null ) {
            animationProgress = getInterpolator().interpolate( animationProgress, getDuration() );
        }
        animate( animationProgress );
        return timeProgress < getDuration();
    }

    @Override
    public void prepare() {
        prepared = true;
        fireAnimationStarted();
    }

    @Override
    public boolean isPrepared() {
        return prepared;
    }

    @Override
    public void finish() {
        finished = true;
        fireAnimationFinished();
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public void setDuration( long duration ) {
        this.duration = duration;
    }

    @Override
    public long getDuration() {
        return duration;
    }

    @Override
    public void setStartOffset( int startOffset ) {
        this.startOffset = startOffset;
    }

    @Override
    public int getStartOffset() {
        return startOffset;
    }

    @Override
    public void addAnimationListener( AnimationListener listener ) {
        listeners.add( listener );
    }

    @Override
    public void removeAnimationListener( AnimationListener listener ) {
        listeners.remove( listener );
    }

    protected void fireAnimationStarted() {
        for( AnimationListener listener : listeners ) {
            listener.animationStarted();
        }
    }

    protected void fireAnimationFinished() {
        for( AnimationListener listener : listeners ) {
            listener.animationFinished();
        }
    }

    @Override
    public void setInterpolator( Interpolator interpolator ) {
        this.interpolator = interpolator;
    }

    @Override
    public Interpolator getInterpolator() {
        return interpolator;
    }

}
