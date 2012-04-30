package de.anormalmedia.vividswinganimations.runner;

import java.util.List;

import de.anormalmedia.vividswinganimations.Animation;

/**
 * The {@link AnimationRunner} is the controller of a whole set of concurrent animations. <br>
 * <br>
 * In order to start an animation, simple create a runner instance, add the animation and call start(); <br>
 * <br>
 * See {@link DefaultAnimationRunner} for an example.
 */
public interface AnimationRunner extends Runnable {

    /**
     * Returns whether the runner has active animations running and is not canceled.
     * @return true if animations are running, false otherwise.
     */
    boolean isRunning();

    /**
     * Starts the animation thread in order to execute the registered animations.
     */
    void start();

    /**
     * Stops the animation thread and interrupts all running animations. Try to use the cancel method instead of stop.
     * It is more safe for the animation to create a defined state.
     */
    void stop();

    /**
     * Cancels the currently running animations. This call waits for the current steps of the animations to finish and
     * stops afterwards.
     */
    void cancel();

    /**
     * Adds a new animation to be executed.
     * @param animation the animation to be executed.
     */
    void addAnimation( Animation animation );

    /**
     * Removes an animation. If the animations are executed, the current step will finish, before the removal will take
     * effect.
     * @param animation the animation to be removed.
     */
    void removeAnimation( Animation animation );

    /**
     * Returns the list of animations.
     * @return the list of animations.
     */
    List<Animation> getAnimations();
}
