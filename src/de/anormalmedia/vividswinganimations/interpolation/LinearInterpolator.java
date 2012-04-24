package de.anormalmedia.vividswinganimations.interpolation;

public class LinearInterpolator implements Interpolator {

    @Override
    public long interpolate( long timeProgress, long duration ) {
        return timeProgress;
    }

}
