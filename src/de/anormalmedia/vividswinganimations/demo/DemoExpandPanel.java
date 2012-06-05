package de.anormalmedia.vividswinganimations.demo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import de.anormalmedia.vividswinganimations.panels.AlphaPanel;
import de.anormalmedia.vividswinganimations.panels.AlphaPanelAnimation;
import de.anormalmedia.vividswinganimations.runner.DefaultAnimationRunner;

public class DemoExpandPanel extends JFrame {

    public DemoExpandPanel() {
        super( "Vivid Swing Animations" );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setBackground( new Color( 0, 0, 0, 0 ) );

        final JPanel contentPane = new JPanel();
        contentPane.setLayout( new GridBagLayout() );
        setContentPane( contentPane );

        for( int i = 1; i <= 3; i++ ) {
            String[] items = new String[5];
            for( int g = 0; g < items.length; g++ ) {
                items[g] = "Option " + i + " - " + (g + 1);
            }
            ExpandablePanel panel = new ExpandablePanel( "Title " + i, items );
            contentPane.add( panel, new GridBagConstraints( 0, i - 1, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
        }
        contentPane.add( new JLabel(), new GridBagConstraints( 0, contentPane.getComponentCount(), 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets( 0, 0, 0, 0 ), 0, 0 ) );

        pack();
        for( Component cmp : contentPane.getComponents() ) {
            if( cmp instanceof ExpandablePanel ) {
                ExpandablePanel expPanel = (ExpandablePanel)cmp;
                expPanel.toggle( false, false );
            }
        }
        setVisible( true );
    }

    private static class ExpandablePanel extends JPanel {

        private AlphaPanel             itemPanel;
        private DefaultAnimationRunner runner;

        public ExpandablePanel( String title, String... items ) {
            super( new BorderLayout() );
            final JCheckBox cbOpenClose = new JCheckBox( title );
            cbOpenClose.addActionListener( new ActionListener() {

                @Override
                public void actionPerformed( ActionEvent e ) {
                    toggle( cbOpenClose.isSelected(), true );

                }
            } );
            add( cbOpenClose, BorderLayout.NORTH );
            itemPanel = createItemPanel( items );
            add( itemPanel, BorderLayout.CENTER );
        }

        public void toggle( boolean expand, boolean animate ) {
            if( !animate ) {
                itemPanel.setAlpha( expand ? 1f : 0f );
                return;
            }

            AlphaPanelAnimation slidePanelAnimation = new AlphaPanelAnimation( itemPanel, expand ? 1f : 0f );
            slidePanelAnimation.setDuration( 300 );

            if( runner != null ) {
                runner.cancel();
            }
            runner = new DefaultAnimationRunner();
            runner.addAnimation( slidePanelAnimation );
            runner.start();
        }

        private AlphaPanel createItemPanel( String[] items ) {
            AlphaPanel panel = new AlphaPanel() {
                @Override
                public Dimension getPreferredSize() {
                    Dimension preferredSize = super.getPreferredSize();
                    preferredSize.height = (int)Math.floor( preferredSize.height * itemPanel.getAlpha() );
                    return preferredSize;
                }

                @Override
                public void setAlpha( float alpha ) {
                    super.setAlpha( alpha );
                    ExpandablePanel.this.revalidate();
                }
            };
            panel.setLayout( new GridLayout( items.length, 1 ) );
            panel.setBorder( new EmptyBorder( 0, 20, 0, 0 ) );

            for( String item : items ) {
                panel.add( new JCheckBox( item ) );
            }

            return panel;
        }

    }

    /**
     * @param args
     */
    public static void main( String[] args ) {
        new DemoExpandPanel();
    }

}
