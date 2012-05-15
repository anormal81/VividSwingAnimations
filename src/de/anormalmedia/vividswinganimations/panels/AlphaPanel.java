package de.anormalmedia.vividswinganimations.panels;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class AlphaPanel extends JPanel {

    private float alpha = 1f;
    
    public AlphaPanel() {
        super();
        setOpaque( false );
    }

    public void setAlpha( float alpha ) {
        this.alpha = alpha;
        repaint();
    }

    public float getAlpha() {
        return alpha;
    }

    @Override
    public void paint( Graphics g ) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint( RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR );
        g2d.setRenderingHint( RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY );
        g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );

        Composite origComposite = g2d.getComposite();
        g2d.setComposite( AlphaComposite.getInstance( AlphaComposite.SRC_OVER, alpha ) );
        super.paint( g2d );
        g2d.setComposite( origComposite );
        g2d.dispose();
    }

}
