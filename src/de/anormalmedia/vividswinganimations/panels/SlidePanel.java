package de.anormalmedia.vividswinganimations.panels;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class SlidePanel extends JPanel {

    public enum DIRECTION {
        fromLeft, fromRight, fromBottom, fromTop;
    }

    private float     slideValue = 0f;
    private DIRECTION direction  = DIRECTION.fromLeft;

    public SlidePanel( DIRECTION direction ) {
        super();
        setOpaque( false );
        this.direction = direction;
    }

    public void setDirection( DIRECTION direction ) {
        this.direction = direction;
    }

    public void setSlideValue( float slideValue ) {
        this.slideValue = slideValue;
        this.repaint();
    }

    public float getSlideValue() {
        return slideValue;
    }

    @Override
    public void setBounds( int x, int y, int width, int height ) {
        synchronized( getTreeLock() ) {
            super.setBounds( x, y, width, height );
        }
        invalidate();
        validateTree();
    }

    @Override
    public void paint( Graphics g ) {
        Graphics2D g2d = (Graphics2D)g.create();
        g2d.setRenderingHint( RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR );
        g2d.setRenderingHint( RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY );
        g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );

        int w = getWidth();
        int h = getHeight();
        int delta = 0;

        Rectangle clip = g2d.getClipBounds();
        int transX = 0;
        int transY = 0;
        switch( direction ) {
            case fromLeft:
                delta = (int)Math.floor( w * slideValue );
                clip = new Rectangle( 0, 0, delta, h );
                transX = -(w - delta);
                break;
            case fromRight:
                delta = (int)Math.floor( w * slideValue );
                clip = new Rectangle( w - delta, 0, delta, h );
                transX = (w - delta);
                break;
            case fromTop:
                delta = (int)Math.floor( h * slideValue );
                clip = new Rectangle( 0, 0, w, delta );
                transY = -(h - delta);
                break;
            case fromBottom:
                delta = (int)Math.floor( h * slideValue );
                clip = new Rectangle( 0, h - delta, w, delta );
                transY = (h - delta);
                break;
        }
        g2d.setClip( clip.intersection( g2d.getClipBounds() ) );
        g2d.translate( transX, transY );
        super.paint( g2d );
        g2d.dispose();
    }
}
