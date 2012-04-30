package de.anormalmedia.vividswinganimations.listener;

/**
 * An abstract implementation of the {@link AnimationListener} to only implement one of the required methods. <br>
 * <br>
 * See {@link AnimationListener} for detailed description.
 */
public abstract class AnimationAdapter implements AnimationListener {

    /**
     * {@inheritDoc}
     */
    @Override
    public void animationStarted() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void animationFinished() {
    }

}
