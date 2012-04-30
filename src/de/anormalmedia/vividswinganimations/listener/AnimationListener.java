package de.anormalmedia.vividswinganimations.listener;

/**
 * A listener interface to be registered at animations for retrieving start and finish events.
 */
public interface AnimationListener {

    /**
     * Called if an animation is prepared and will start with the first step.
     */
    void animationStarted();

    /**
     * Called if an animation reached the duration time and finished.
     */
    void animationFinished();

}
