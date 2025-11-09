package com.heatmyfloor.gui;

import com.heatmyfloor.domain.piece.Controller;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.EmptyBorder;
import com.heatmyfloor.gui.Canvas;
import java.awt.event.*;
import java.util.function.*;

/**
 *
 * @author tatow
 */
public class Proprietes extends JPanel {
    private JTextField largeurPiece;
    private JTextField hauteurPiece;
    private JTextField distanceIntersections;
    private JTextField largeurItem;
    private JTextField hauteurItem;
    private JTextField distanceFils;
    private JTextField longueurFil;
    private MainWindow mainWindow;
    
    public Proprietes(MainWindow mainWIndow) {
        this.mainWindow = mainWindow;
        setPreferredSize(new Dimension(300, 0));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(230, 230, 230)));
        setBackground(Color.white);

        add(buildTitleBar(), BorderLayout.NORTH);

        //  Contenu scrollable 
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);
        content.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Pièce
        content.add(sectionPiece());
        content.add(Box.createVerticalStrut(10));

        // Membrane
        content.add(sectionMembrane());
        content.add(Box.createVerticalStrut(10));

        // Meuble
        content.add(sectionMeuble());
        content.add(Box.createVerticalStrut(10));

        // Fil
        content.add(sectionFil());
        content.add(Box.createVerticalGlue());

        JScrollPane sp = new JScrollPane(content,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        sp.setBorder(null);
        sp.getViewport().setOpaque(false);
        add(sp, BorderLayout.CENTER);
        
        dimensionListener();
    }

    // ===================== UI =====================
    private JComponent buildTitleBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setOpaque(false);
        bar.setBorder(new EmptyBorder(8, 12, 8, 12));

        JLabel title = new JLabel("Propriétés");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 14f));

        JButton gear = new JButton("⚙");
        gear.setFocusable(false);
        gear.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        gear.setOpaque(false);
        gear.setContentAreaFilled(false);
        gear.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        gear.setToolTipText("Paramètres");

        bar.add(title, BorderLayout.WEST);
        bar.add(gear, BorderLayout.EAST);
        return bar;
    }
    
    private JSpinner Longueur;
    private JSpinner Largeur;

    private JComponent sectionPiece() {
        SectionPanel s = new SectionPanel("Pièce");
        Longueur = number(300.0, 2.0, 1000.0, 1.0);
        Largeur = number(250.0,2.0,450.0,1.0);
        s.addRow("Longueur :", Longueur);
        s.addRow("Largeur  :", Largeur);
        
        Longueur.addChangeListener(e -> modifierRectangle());
        Largeur.addChangeListener(e -> modifierRectangle());
        
        return s;
    }
    
    private void modifierRectangle(){
      java.awt.Window window = SwingUtilities.getWindowAncestor(this); 
       if (window instanceof MainWindow) {
           MainWindow main = (MainWindow) window;
           Component comp = main.getSelectedCanvas();
            if (comp instanceof Canvas) {
                Canvas canvas = (Canvas) comp;
                int longueur = ((Double) Longueur.getValue()).intValue();
                int largeur = ((Double) Largeur.getValue()).intValue();
                
                canvas.dessinerRectangle(longueur,largeur);
            }
            
           
       
            
       }
    }
    private JComponent sectionMembrane() {
        SectionPanel s = new SectionPanel("Membrane");
        distanceIntersections = text("");
        s.addRow("Intersections :", distanceIntersections);
        s.addSpacer(8);
        s.addFull(button("Générer un Graphe"));
        return s;
    }

    private JComponent sectionMeuble() {
        SectionPanel s = new SectionPanel("Meuble");

        largeurItem = text("");
        hauteurItem = text("");
        s.addRow("Largeur :", largeurItem);
        s.addRow("Hauteur  :", hauteurItem);
        return s;
    }
    
    public void afficherProprietesItemSelectionne() {
        if(mainWindow.controller.trouverItemSelectionne() != null){
            largeurItem.setText(String.valueOf(mainWindow.controller.trouverItemSelectionne().getLargeur()));
            hauteurItem.setText(String.valueOf(mainWindow.controller.trouverItemSelectionne().getHauteur()));  
        }else{
            largeurItem.setText("");
            hauteurItem.setText("");
        }
        
    }


     
        
        
        
        // Détecte quand l'utilisateur valide ou qitte le champ /enter
        public void dimensionListener() { 
                ActionListener apply = (e  -> {
            
            if ( largeurItem == null || hauteurItem == null) return;
            Double largeur = parseNumber(largeurItem.getText());
            Double hauteur  = parseNumber(hauteurItem.getText());
            
    

            //déplace et redimensionne l'item selectionné
            resizeSelected(largeur, hauteur);  
            mainWindow.currentCanvas.repaint();                     //maj l'affichage  
        });
      
       
        
        // appuie sur Enter = applique
        largeurItem.addActionListener(apply);
        hauteurItem.addActionListener(apply);
        }
    
       
        
        
        
        
        public void resizeSelected(double L, double H) {   
        if (L <= 0 || H <= 0) return;//Modifier les dimensions (longueur, largeur) de l’item actuellement sélectionné.
        mainWindow.controller.redimensionnerItemSelectionne(L, H);
        }
    
   
    
    
    //ajout
    private Double parseNumber(String s) {
    try {
        // enlève tout sauf chiffres et point
        return Double.parseDouble(s.replaceAll("[^0-9.]", ""));
    } catch (NumberFormatException e) {
        return null;
    }
}

    
    
    
    
    


    private JComponent sectionFil() {
        SectionPanel s = new SectionPanel("Fil");
        distanceFils = text("");
        longueurFil = text("");
        s.addRow("Distance Fils :", distanceFils);
        s.addRow("Longueur      :", longueurFil);
        s.addSpacer(8);
        s.addFull(button("Générer un chemin"));
        return s;
    }

    private JTextField text(String value) {
        JTextField tf = new JTextField(value, 10);
        tf.setMargin(new Insets(4, 6, 4, 6));
        return tf;
    }

    private JSpinner number(Double value, Double min, Double max, Double step) {

        JSpinner sp = new JSpinner(new SpinnerNumberModel(value, min, max, step));

        sp.setEditor(new JSpinner.NumberEditor(sp, "#.########")); // affichage décimal

//        stylizeSpinner(sp);

//        sp.addChangeListener(e -> controller.onLongueurChanged(((Number) sp.getValue()).doubleValue()));

        return sp;

    }

    private JButton button(String label) {
        JButton b = new JButton(label);
        b.setFocusable(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setMargin(new Insets(8, 10, 8, 10));
        return b;
    }

    /**
     * Panneau de section à fond blanc, bords arrondis, titre + fin filet.
     * Layout interne = GridBagLayout pour un alignement propre.
     */
    static class SectionPanel extends JPanel {

        private final String title;
        private final GridBagConstraints gc;
        private int row = 0;

        SectionPanel(String title) {
            this.title = title;
            setOpaque(false);
            setLayout(new GridBagLayout());
            setBorder(new EmptyBorder(10, 12, 10, 12));
            gc = new GridBagConstraints();
            gc.insets = new Insets(6, 0, 6, 0);
            gc.fill = GridBagConstraints.HORIZONTAL;
            gc.gridy = 0;

            // Titre + filet
            JLabel t = new JLabel(title);
            t.setFont(t.getFont().deriveFont(Font.BOLD, 14f));
            t.setForeground(new Color(55, 55, 55));

            gc.gridx = 0;
            gc.weightx = 0;
            add(t, gc);

            JSeparator sep = new JSeparator();
            sep.setForeground(new Color(230, 230, 230));
            gc.gridx = 1;
            gc.weightx = 1.0;
            add(sep, gc);

            row++;
        }

        /**
         * Ajoute une ligne label + champ
         */
        void addRow(String label, JComponent field) {
            GridBagConstraints c1 = (GridBagConstraints) gc.clone();
            c1.gridy = row;
            c1.gridx = 0;
            c1.weightx = 0;
            c1.insets = new Insets(4, 0, 4, 8);
            JLabel l = new JLabel(label);
            add(l, c1);

            GridBagConstraints c2 = (GridBagConstraints) gc.clone();
            c2.gridy = row;
            c2.gridx = 1;
            c2.weightx = 1.0;
            add(field, c2);
            row++;
        }

        /**
         * Ajoute un composant sur toute la largeur (ex: bouton)
         */
        void addFull(JComponent comp) {
            GridBagConstraints c = (GridBagConstraints) gc.clone();
            c.gridy = row;
            c.gridx = 0;
            c.gridwidth = 2;
            c.weightx = 1.0;
            add(comp, c);
            row++;
        }

        /**
         * Espace vertical supplémentaire
         */
        void addSpacer(int h) {
            GridBagConstraints c = (GridBagConstraints) gc.clone();
            c.gridy = row;
            c.gridx = 0;
            c.gridwidth = 2;
            add(Box.createVerticalStrut(h), c);
            row++;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int arc = 12;
            Shape r = new RoundRectangle2D.Float(2, 2, getWidth() - 4, getHeight() - 4, arc, arc);

            g2.setColor(Color.WHITE);
            g2.fill(r);
            g2.setColor(new Color(230, 230, 230));
            g2.draw(r);

            g2.dispose();
        }
    }
}
