package com.heatmyfloor.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
import com.heatmyfloor.domain.Util;
import com.heatmyfloor.domain.items.Drain;
import com.heatmyfloor.domain.Point;



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
    private JTextField diametreDrain;
    private JTextField PositionX;
    private JTextField PositionY;
    
    private JButton undo;
    private JButton redo;


    public Proprietes(MainWindow mainWindow) {
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
        
        content.add(sectionDrain());
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

    }


    private JComponent buildTitleBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setOpaque(false);
        bar.setBorder(new EmptyBorder(8, 12, 8, 12));

        JLabel title = new JLabel("Propriétés");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 14f));
        
        //Boutons UNDO-REDO + PARAMETRES UNITE VALEURS NUMERIQUES
        JPanel espaceBoutton = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
        
        //UNDO
        ImageIcon undoImage  = new ImageIcon(getClass().getResource("/Icons/undo.png"));
        Image undoImg = undoImage.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        undo = new JButton (new ImageIcon(undoImg));
        undo.setToolTipText("Undo");
        undo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        undo.setPreferredSize(new Dimension(30, 25));
        undo.setBorderPainted(false);     
        undo.setContentAreaFilled(false);  
        undo.setFocusPainted(false);
        
        //REDO
        ImageIcon redoImage  = new ImageIcon(getClass().getResource("/Icons/redo.png"));
        Image redoImg = redoImage.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        redo = new JButton (new ImageIcon(redoImg));
        redo.setToolTipText("Redo");
        redo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        redo.setPreferredSize(new Dimension(30, 25));
        redo.setBorderPainted(false);     
        redo.setContentAreaFilled(false);  
        redo.setFocusPainted(false);
        
        espaceBoutton.add(undo);
        espaceBoutton.add(redo);
        espaceBoutton.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        espaceBoutton.setOpaque(false);
        
        undo.addActionListener(e -> undoListener());
        redo.addActionListener(e -> redoListener());

         bar.add(title, BorderLayout.WEST);
         bar.add(espaceBoutton, BorderLayout.EAST);
         updateUndoRedoButtons();
         return bar;
    }

    
    public void undoListener(){
        mainWindow.controllerActif.annulerModif();
        mainWindow.currentCanvas.repaint();
        afficherProprietesItemSelectionne();
        mainWindow.panelPosition.afficherAngleItemSelectionne();
        mainWindow.panelPosition.afficherCoordItemSelectionne();
        updateUndoRedoButtons();
    }
    
    public void redoListener(){
        mainWindow.controllerActif.retablirModif();
        mainWindow.currentCanvas.repaint();
        afficherProprietesItemSelectionne();
        mainWindow.panelPosition.afficherAngleItemSelectionne();
        mainWindow.panelPosition.afficherCoordItemSelectionne();
        updateUndoRedoButtons();
    }

    
    private JComponent sectionPiece() {
        SectionPanel s = new SectionPanel("Pièce");
        largeurPiece = text("");
        hauteurPiece = text("");
        
        s.addRow("Largeur", largeurPiece);
        s.addRow("Hauteur", hauteurPiece);  
        return s;
    }
    
    public void afficherProprietesPiece() {
            double largPx = mainWindow.controllerActif.getPiece().getLargeur();
            double hautPx = mainWindow.controllerActif.getPiece().getHauteur();

            double largIn = Util.enPouces(largPx);
            double hautIn = Util.enPouces(hautPx);
            largeurPiece.setText(Util.formatImperial(largIn));
            hauteurPiece.setText(Util.formatImperial(hautIn));
            updateUndoRedoButtons();
     
    } 

    
    public void dimensionPieceListener() { 
        ActionListener apply = (e  -> {
          if (largeurPiece == null || hauteurPiece == null) return;

           try{      
                double largeur = Util.enPixels(largeurPiece.getText());
                double hauteur = Util.enPixels(hauteurPiece.getText()); 
                resizePiece(largeur, hauteur);
                updateUndoRedoButtons();
           }catch(IllegalArgumentException error){
               mainWindow.tabsErreur.clearMessages();
               mainWindow.tabsErreur.addErrorMessage(error.getMessage());
           }

            SwingUtilities.invokeLater(() -> {
                   mainWindow.currentCanvas.revalidate();;
                   mainWindow.currentCanvas.repaint();
                   mainWindow.currentCanvas.requestFocusInWindow();
            });  
        }); 

            largeurPiece.addActionListener(apply);
            hauteurPiece.addActionListener(apply);
    }
    
    
    public void resizePiece(double L, double H) {   
        if (L <= 0 || H <= 0) return;
        mainWindow.controllerActif.redimensionnerPiece(L, H);
        updateUndoRedoButtons();
    }

     public void resizeDrain(double diametre) {   

        mainWindow.controllerActif.redimensionnerDrainSelectionne(diametre);

        
       if(mainWindow.controllerActif.trouverDrainSelectionne() != null){
           mainWindow.tabsErreur.addErrorMessage("Drain trouve");
           mainWindow.controllerActif.redimensionnerDrainSelectionne(diametre);
       }else{
           mainWindow.tabsErreur.addErrorMessage("Drain non trouve");
       }
        
    }
    
    public void moveSelectedTo(double x, double y){
           mainWindow.controllerActif.deplacerDrainSelectionne(x,y);
        
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
        if(mainWindow.controllerActif.trouverItemSelectionne() != null){
            
            double largPx = mainWindow.controllerActif.trouverItemSelectionne().getLargeur();
            double hautPx = mainWindow.controllerActif.trouverItemSelectionne().getHauteur();

            double largIn = Util.enPouces(largPx);
            double hautIn = Util.enPouces(hautPx);
            largeurItem.setText(Util.formatImperial(largIn));
            hauteurItem.setText(Util.formatImperial(hautIn));
        
        }else{
            largeurItem.setText("");
            hauteurItem.setText("");
        }
        updateUndoRedoButtons();
    }
    
    public void afficherProprietesDrainSelectionne() {
        if(mainWindow.controllerActif.trouverItemSelectionne() != null){
           
            double diametre =  mainWindow.controllerActif.trouverDrainSelectionne().getDiametre();
            double positionX = mainWindow.controllerActif.trouverDrainSelectionne().getPosition().getX();
            double positionY = mainWindow.controllerActif.trouverDrainSelectionne().getPosition().getY();
            
            

            
//            diametreDrain.setText(String.format("%.2f", Util.enUnite(diametre, u)));
//           PositionX.setText(String.format("%.2f", Util.enUnite(positionX, u))); 
//            PositionY.setText(String.format("%.2f", Util.enUnite(positionY, u)));
//        }else{
//            diametreDrain.setText("");
//            PositionX.setText("");
//            PositionY.setText("");

        }
        
    }
     
    public void dimensionItemListener() { 
        ActionListener apply = (e  -> {

            if (largeurItem == null || hauteurItem == null) return;
            try{
                double largeur = Util.enPixels(largeurItem.getText());
                double hauteur = Util.enPixels(hauteurItem.getText());
                resizeItemSelected(largeur, hauteur); 
                updateUndoRedoButtons();
            }catch(IllegalArgumentException error){
                mainWindow.tabsErreur.clearMessages();
                mainWindow.tabsErreur.addErrorMessage(error.getMessage());
            }

            SwingUtilities.invokeLater(() -> {
                mainWindow.currentCanvas.repaint();
                mainWindow.currentCanvas.requestFocusInWindow();
            });                        
        });

        largeurItem.addActionListener(apply);
        hauteurItem.addActionListener(apply);
    }
    

//    public void proprieteDrainListener() { 
//        ActionListener apply = (e  -> {
//
//            if ( diametreDrain == null || PositionX == null || PositionY == null) return;
//            Double diametre = parseNumber(diametreDrain.getText());
//            Double posX = parseNumber(PositionX.getText());
//            Double posY = parseNumber(PositionY.getText());
//            
//            
//            if ( diametre == null||posX == null || posY==null ) {
//                return;
//            }
//
//            resizeDrain(diametre);
//            moveSelectedTo(posX, posY);
//            SwingUtilities.invokeLater(() -> {
//                mainWindow.currentCanvas.repaint();
//                mainWindow.currentCanvas.requestFocusInWindow();
//            });                        
//        });
//
//        largeurItem.addActionListener(apply);
//        hauteurItem.addActionListener(apply);
//    }
    

    public void proprieteDrainListener() { 
        ActionListener apply = (e  -> {

            if ( diametreDrain == null || PositionX == null || PositionY == null) return;

            mainWindow.tabsErreur.addErrorMessage("Hello, je suis dans Listener");
            //resizeDrain(diametre);
            try{
//                moveSelectedTo(posX, posY);
            }catch (IllegalArgumentException error){
                mainWindow.tabsErreur.addErrorMessage(error.getMessage());
            }
            
            SwingUtilities.invokeLater(() -> {
                mainWindow.currentCanvas.repaint();
                mainWindow.currentCanvas.requestFocusInWindow();
            });                        
        });

        PositionX.addActionListener(apply);
        PositionY.addActionListener(apply);
        diametreDrain.addActionListener(apply);
    }

    private JComponent sectionDrain() {
       SectionPanel s = new SectionPanel("Drain");

        diametreDrain= text("");
        PositionX= text("");
        PositionY= text("");

        
        s.addRow("diametre :", diametreDrain);
        s.addRow("x :", PositionX);
        s.addRow("y :", PositionY);

//        addActionListener(e ->afficherProprietesDrainSelectionne());
        return s; 
    }

    
    public void resizeItemSelected(double L, double H) {   
        if (L <= 0 || H <= 0) return;
        mainWindow.controllerActif.redimensionnerItemSelectionne(L, H);
        updateUndoRedoButtons();
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

    private JButton button(String label) {
        JButton b = new JButton(label);
        b.setFocusable(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setMargin(new Insets(8, 10, 8, 10));
        return b;
    }


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

    public void updateUndoRedoButtons(){
        if (mainWindow == null || mainWindow.controllerActif == null){
            undo.setEnabled(false);
            redo.setEnabled(false);
            return;
        }
        
        boolean canUndo = mainWindow.controllerActif.peutAnnuler();
        boolean canRedo = mainWindow.controllerActif.peutRetablir();
        
        undo.setEnabled(canUndo);
        redo.setEnabled(canRedo);
    }
}
