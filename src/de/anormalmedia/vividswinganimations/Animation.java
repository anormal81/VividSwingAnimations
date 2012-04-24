package de.anormalmedia.vividswinganimations;

import de.anormalmedia.vividswinganimations.interpolation.Interpolator;
import de.anormalmedia.vividswinganimations.listener.AnimationListener;

public interface Animation {

    void animate( long timeProgress );

    boolean executeStep( long timeProgress );

    void prepare();

    boolean isPrepared();

    void finish();

    boolean isFinished();

    void setDuration( long duration );

    long getDuration();

    void setStartOffset( int startOffset );

    int getStartOffset();

    void addAnimationListener( AnimationListener listener );

    void removeAnimationListener( AnimationListener listener );
    
    void setInterpolator( Interpolator interpolator );
    
    Interpolator getInterpolator();

}
