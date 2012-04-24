package de.anormalmedia.vividswinganimations.runner;

import java.util.List;

import de.anormalmedia.vividswinganimations.Animation;

public interface AnimationRunner extends Runnable {

    boolean isRunning();
    
    void start();
    
    void stop();
    
    void cancel();
    
    void addAnimation(Animation animation);
    
    void removeAnimation(Animation animation);
    
    List<Animation> getAnimations();
}
