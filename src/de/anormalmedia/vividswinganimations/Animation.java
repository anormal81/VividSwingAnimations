package de.anormalmedia.vividswinganimations;

import de.anormalmedia.vividswinganimations.interpolation.Interpolator;
import de.anormalmedia.vividswinganimations.listener.AnimationListener;

/**
 * The animation interface. This interface is implemented by each animation. It handles the offsets, durations and
 * listeners.<br>
 * <br>
 * The following explanation should help to implement your own animation. Before you start, have a look at
 * {@link AbstractAnimation}, it provides most of the default behavior. <br>
 * <ul>
 * <li>An animation runner, holding the animation, waits until getStartOffset() is reached.</li>
 * <li>If the start offset is reached and the animation is not prepared, prepare() will be called.</li>
 * <li>For each next animation step, executeStep() is called. This method should normalize the time to match the
 * duration. The {@link Interpolator} can be applied here to get e.g. accelerate effects. The animate() method must be
 * triggered afterwards with the new time progress value. The executeStep() method must return true if the animation
 * will have further steps, false if the last step was executed.</li>
 * <li>In the animate call, the target components value must be changed according to the current time progress in
 * relation to the getDuration() value.</li>
 * <li>If the duration is reached, finish() is called. isFinished() must return the correct finish state to allow the
 * runner to ignore the animation if the time progress is past the duration value.</li>
 * <li>The animation listeners should retrieve their events in prepare call and finish call.</li>
 * </ul>
 */
public interface Animation {

    /**
     * Sets the animatable value of the component to the value, matching the current progress ( timProgress /
     * getDuration() ).
     * @param timeProgress the current progress in animating. Range is from 0 to getDuration().
     */
    void animate( long timeProgress );

    /**
     * Executes the next animation step. An {@link Interpolator} can be applied for simulating speed changes.
     * @param timeProgress the current progress in animating. Range starts from 0 and can be larger than getDuration().
     * This call has to check and normalize the timeProgress value.
     * @return true if more steps need to be executed, false if the last step was animated.
     */
    boolean executeStep( long timeProgress );

    /**
     * Prepares the current animations. Is called directly before the first step is executed. In general, this method
     * fetched the current value from the target to store this initial value for step processing.
     */
    void prepare();

    /**
     * Returns whether the current animation was already prepared.
     * @return true if the aniimation is prepared, false otherwise.
     */
    boolean isPrepared();

    /**
     * Called if the animation has finished.
     */
    void finish();

    /**
     * Returns whether this animation is finished.
     * @return true, if the animation is finished, false if not.
     */
    boolean isFinished();

    /**
     * Sets the duration in ms, the animation should last.
     * @param duration in ms the time for the animation to last.
     */
    void setDuration( long duration );

    /**
     * Returns the duration, the animation will last in ms.
     * @return the duration, the animation will last in ms.
     */
    long getDuration();

    /**
     * Sets the offset in ms, the animation waits, before it is prepared and executed.
     * @param startOffset the offset in ms to wait before start.
     */
    void setStartOffset( int startOffset );

    /**
     * Returns the offset in ms, the animation waits, before it is started.
     * @return the offset in ms, the animation waits, before it is started.
     */
    int getStartOffset();

    /**
     * Adds a listener to receive events if an animation started or finished.
     * @param listener a new listener to be added.
     */
    void addAnimationListener( AnimationListener listener );

    /**
     * Removes a listener to not receive further animation events.
     * @param listener the listener to be removed.
     */
    void removeAnimationListener( AnimationListener listener );

    /**
     * Sets an {@link Interpolator} to adapt the time progress values for e.g. acceleration effects.
     * @param interpolator the {@link Interpolator} to be used for the animation.
     */
    void setInterpolator( Interpolator interpolator );

    /**
     * Returns the {@link Interpolator} that will adapt the time progress.
     * @return the {@link Interpolator} that will adapt the time progress.
     */
    Interpolator getInterpolator();

}
