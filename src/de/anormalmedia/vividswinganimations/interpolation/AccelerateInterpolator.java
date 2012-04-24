package de.anormalmedia.vividswinganimations.interpolation;

public class AccelerateInterpolator implements Interpolator {

    @Override
    public long interpolate( long timeProgress, long duration ) {
        float x = (float)timeProgress / (float)duration;
        double y = Math.pow( x, 3 );
        return (long)Math.round( duration * y );
    }

}
