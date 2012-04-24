package de.anormalmedia.vividswinganimations.interpolation;

public class DecelerateInterpolator implements Interpolator {

    @Override
    public long interpolate( long timeProgress, long duration ) {
        float x = (float)timeProgress / (float)duration;
        double y = Math.sqrt( x );
        return (long)Math.round( duration * y );
    }

}
