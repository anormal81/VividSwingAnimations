package de.anormalmedia.vividswinganimations.runner;

import de.anormalmedia.vividswinganimations.Animation;

/**
 * Extended default runner to start one animation after the other. The start offset time will apply before each animation starts:
 * <pre>
 * - start of runner
 * - startOffset first animation
 * - duration first animation
 * - startOffset second animation
 * - duration second animation
 * - ...
 * - end of runner
 * </pre>
 */
public class SequentialAnimationRunner extends DefaultAnimationRunner {

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean onStepExecuted( Animation animation ) {
        if( !animation.isFinished() ) {
            // The current animation is not finished. Prevent further animations to start.
            return true;
        } else {
            // The current animation has just finished, reset the start time for the next animation
            starttime = System.currentTimeMillis();
        }
        return false;
    }
}
