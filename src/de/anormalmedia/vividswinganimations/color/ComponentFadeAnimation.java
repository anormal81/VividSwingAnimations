package de.anormalmedia.vividswinganimations.color;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import de.anormalmedia.vividswinganimations.AbstractAnimation;

public class ComponentFadeAnimation extends AbstractAnimation {

    private Container                            target;
    private final int                            targetAlpha;
    private HashMap<Component, ArrayList<Color>> innerComps;

    public ComponentFadeAnimation( Container target, float targetAlpha ) {
        this.target = target;
        this.targetAlpha = Math.min( 255, Math.max( 0, Math.round( 255 * targetAlpha ) ) );
    }

    @Override
    public void prepare() {
        innerComps = getInnerComps( target );
        ArrayList<Color> colors = new ArrayList<Color>();
        colors.add( target.getForeground() );
        colors.add( target.getBackground() );
        innerComps.put( target, colors );
        super.prepare();
    }

    @Override
    public void animate( long timeProgress ) {
        for( Entry<Component, ArrayList<Color>> entry : innerComps.entrySet() ) {
            Component cmp = entry.getKey();
            ArrayList<Color> cmpcolors = entry.getValue();
            Color initialColor = cmpcolors.get( 0 );
            if( initialColor != null ) {
                cmp.setForeground( calculateAlphaColor( timeProgress, initialColor ) );
            }
            initialColor = cmpcolors.get( 1 );
            if( initialColor != null ) {
                cmp.setBackground( calculateAlphaColor( timeProgress, initialColor ) );
            }
        }
        if( target.getParent() != null ) {
            target.getParent().repaint();
        }
    }

    private Color calculateAlphaColor( long timeProgress, Color initialColor ) {
        int nextA = targetAlpha;
        if( timeProgress <= getDuration() ) {
            int deltaA = targetAlpha - initialColor.getAlpha();
            float alphaDiff = (float)deltaA / (float)getDuration() * (float)timeProgress;
            nextA = Math.min( 255, initialColor.getAlpha() + (int)Math.round( alphaDiff ) );
        }
        return new Color( initialColor.getRed(), initialColor.getGreen(), initialColor.getBlue(), nextA );
    }

    private static HashMap<Component, ArrayList<Color>> getInnerComps( Container container ) {
        HashMap<Component, ArrayList<Color>> comps = new HashMap<Component, ArrayList<Color>>();
        Component[] components = container.getComponents();
        if( components != null && components.length > 0 ) {
            for( Component cmp : components ) {
                ArrayList<Color> colors = new ArrayList<Color>();
                colors.add( cmp.getForeground() );
                colors.add( cmp.getBackground() );
                comps.put( cmp, colors );
                if( cmp instanceof Container ) {
                    comps.putAll( getInnerComps( (Container)cmp ) );
                }
            }
        }
        return comps;
    }

    public static void setAlphaRecursivly( Container target, float alpha ) {
        HashMap<Component, ArrayList<Color>> innerComps = getInnerComps( target );
        ArrayList<Color> colors = new ArrayList<Color>();
        colors.add( target.getForeground() );
        colors.add( target.getBackground() );
        innerComps.put( target, colors );

        int nextA = Math.min( 255, Math.max( 0, Math.round( 255 * alpha ) ) );
        for( Entry<Component, ArrayList<Color>> entry : innerComps.entrySet() ) {
            Component cmp = entry.getKey();
            ArrayList<Color> cmpcolors = entry.getValue();
            Color initialColor = cmpcolors.get( 0 );
            if( initialColor != null ) {
                cmp.setForeground( new Color( initialColor.getRed(), initialColor.getGreen(), initialColor.getBlue(), nextA ) );
            }
            initialColor = cmpcolors.get( 1 );
            if( initialColor != null ) {
                cmp.setBackground( new Color( initialColor.getRed(), initialColor.getGreen(), initialColor.getBlue(), nextA ) );
            }
        }
    }
}
