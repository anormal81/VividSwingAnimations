package de.anormalmedia.vividswinganimations.demo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import de.anormalmedia.vividswinganimations.panels.AlphaPanel;
import de.anormalmedia.vividswinganimations.panels.AlphaPanelAnimation;
import de.anormalmedia.vividswinganimations.panels.SlidePanel;
import de.anormalmedia.vividswinganimations.panels.SlidePanelAnimation;
import de.anormalmedia.vividswinganimations.panels.SlidePanel.DIRECTION;
import de.anormalmedia.vividswinganimations.runner.DefaultAnimationRunner;

public class DemoExpandPanel extends JFrame {

    public DemoExpandPanel() {
        super( "Vivid Swing Animations" );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setBackground( new Color( 0, 0, 0, 0 ) );

        final JPanel contentPane = new JPanel();
        contentPane.setLayout( new GridBagLayout() );
        setContentPane( contentPane );

        for( int i = 1; i <= 2; i++ ) {
            String[] items = new String[3];
            for( int g = 0; g < items.length; g++ ) {
                items[g] = "Option " + i + " - " + (g + 1);
            }
            ExpandableAlphaPanel panel = new ExpandableAlphaPanel( "Alpha " + i, items );
            contentPane.add( panel, new GridBagConstraints( 0, i - 1, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
        }
        for( int i = 3; i <= 4; i++ ) {
            String[] items = new String[3];
            for( int g = 0; g < items.length; g++ ) {
                items[g] = "Option " + i + " - " + (g + 1);
            }
            ExpandableSlidePanel panel = new ExpandableSlidePanel( DIRECTION.fromLeft, "Slide left " + i, items );
            contentPane.add( panel, new GridBagConstraints( 0, i - 1, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
        }
        for( int i = 5; i <= 6; i++ ) {
            String[] items = new String[3];
            for( int g = 0; g < items.length; g++ ) {
                items[g] = "Option " + i + " - " + (g + 1);
            }
            ExpandableSlidePanel panel = new ExpandableSlidePanel( DIRECTION.fromRight, "Slide right " + i, items );
            contentPane.add( panel, new GridBagConstraints( 0, i - 1, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
        }
        for( int i = 7; i <= 8; i++ ) {
            String[] items = new String[3];
            for( int g = 0; g < items.length; g++ ) {
                items[g] = "Option " + i + " - " + (g + 1);
            }
            ExpandableSlidePanel panel = new ExpandableSlidePanel( DIRECTION.fromTop, "Slide top " + i, items );
            contentPane.add( panel, new GridBagConstraints( 0, i - 1, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
        }
        contentPane.add( new JLabel(), new GridBagConstraints( 0, contentPane.getComponentCount(), 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets( 0, 0, 0, 0 ), 0, 0 ) );

        pack();
        for( Component cmp : contentPane.getComponents() ) {
            if( cmp instanceof ExpandableSlidePanel ) {
                ExpandableSlidePanel expPanel = (ExpandableSlidePanel)cmp;
                expPanel.toggle( false, false );
            }
            if( cmp instanceof ExpandableAlphaPanel ) {
                ExpandableAlphaPanel expPanel = (ExpandableAlphaPanel)cmp;
                expPanel.toggle( false, false );
            }
        }
        setVisible( true );
    }

    private static class ExpandableAlphaPanel extends JPanel {

        private AlphaPanel             itemPanel;
        private DefaultAnimationRunner runner;

        public ExpandableAlphaPanel( String title, String... items ) {
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
            itemPanel.validate();
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
                public void validate() {
                    Dimension preferredSize = super.getPreferredSize();
                    Rectangle bounds = super.getBounds();
                    bounds.height = preferredSize.height;
                    setBounds( bounds );
                    if( runner == null || !runner.isRunning() ) {
                        super.validate();
                    }
                }

                @Override
                protected void validateTree() {
                    if( runner == null || !runner.isRunning() ) {
                        super.validateTree();
                    }
                }

                @Override
                public void setAlpha( float alpha ) {
                    super.setAlpha( alpha );
                    ExpandableAlphaPanel.this.revalidate();
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

    private static class ExpandableSlidePanel extends JPanel {

        private SlidePanel             itemPanel;
        private DefaultAnimationRunner runner;
        private final DIRECTION direction;

        public ExpandableSlidePanel( DIRECTION direction, String title, String... items ) {
            super( new BorderLayout() );
            this.direction = direction;
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
                itemPanel.setSlideValue( expand ? 1f : 0f );
                return;
            }

            SlidePanelAnimation slidePanelAnimation = new SlidePanelAnimation( itemPanel, expand ? 1f : 0f );
            slidePanelAnimation.setDuration( 300 );

            if( runner != null ) {
                runner.cancel();
            }
            runner = new DefaultAnimationRunner();
            runner.addAnimation( slidePanelAnimation );
            itemPanel.validate();
            runner.start();
        }

        private SlidePanel createItemPanel( String[] items ) {
            SlidePanel panel = new SlidePanel( direction ) {
                @Override
                public Dimension getPreferredSize() {
                    Dimension preferredSize = super.getPreferredSize();
                    preferredSize.height = (int)Math.floor( preferredSize.height * itemPanel.getSlideValue() );
                    return preferredSize;
                }

                @Override
                public void validate() {
                    Dimension preferredSize = super.getPreferredSize();
                    Rectangle bounds = super.getBounds();
                    bounds.height = preferredSize.height;
                    setBounds( bounds );
                    if( runner == null || !runner.isRunning() ) {
                        super.validate();
                    }
                }

                @Override
                protected void validateTree() {
                    if( runner == null || !runner.isRunning() ) {
                        super.validateTree();
                    }
                }

                @Override
                public void setSlideValue( float alpha ) {
                    super.setSlideValue( alpha );
                    ExpandableSlidePanel.this.revalidate();
                }
            };
            panel.setSlideValue( 1f );
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
