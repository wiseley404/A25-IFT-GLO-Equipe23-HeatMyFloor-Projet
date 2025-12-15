package com.heatmyfloor.gui;

import javax.swing.*;
import java.awt.*;
import com.heatmyfloor.domain.Point;
import com.heatmyfloor.domain.piece.PieceItem;
import com.heatmyfloor.domain.piece.PieceItemReadOnly;
import com.heatmyfloor.domain.piece.PieceReadOnly;
import java.awt.event.ActionListener;
import com.heatmyfloor.domain.Util;
import com.heatmyfloor.domain.items.ElementChauffant;
import com.heatmyfloor.domain.items.Thermostat;
import com.heatmyfloor.domain.piece.Piece;
/**
 *
 * @author tatow
 */
public class PositionPanel extends JPanel {

    private JTextField xPosition;
    private JTextField yPosition;
    private JTextField degRotation;
    private JButton rotationButton;
    private MainWindow mainWindow;
    
    public JLabel sourisLabelXPiece;
    public JLabel sourisLabelYPiece;
    public JLabel sourisLabelXCanvas;
    public JLabel sourisLabelYCanvas;

    public PositionPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(230, 230, 230)));
        setPreferredSize(new Dimension(800, 120));
        setBackground(Color.white);
        JPanel inner = new JPanel();
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        inner.setOpaque(false);
        
        JPanel rangeeHaut = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        rangeeHaut.setOpaque(false);
        JPanel coords = new JPanel();
        coords.setLayout(new BoxLayout(coords, BoxLayout.Y_AXIS));
        coords.setBorder(BorderFactory.createTitledBorder("Position"));
        JPanel xy = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));

        xPosition = new JTextField("", 7);
        xPosition.setMargin(new Insets(2, 2, 2, 4));
        xy.add(new JLabel("X :"));
        xy.add(xPosition);
        yPosition = new JTextField("", 7);
        yPosition.setMargin(new Insets(2, 2, 2, 4));
        xy.add(new JLabel("Y :"));
        xy.add(yPosition);
        coords.add(xy);

        rangeeHaut.add(coords);

        JPanel trans = new JPanel(new GridBagLayout());
        trans.setBorder(BorderFactory.createTitledBorder("Translation"));

        // Boutons Alignement
        JButton HG = new JButton("↖"); 
        JButton BG = new JButton("↙"); 
        JButton HD = new JButton("↗"); 
        JButton BD = new JButton("↘");  
        JButton HM = new JButton("↑");  
        JButton BM = new JButton("↓");  
 
        JButton[] boutons = {HG, HM, HD, BG, BM, BD};
        for (JButton bouton : boutons) {
            bouton.setPreferredSize(new Dimension(25, 25));
            bouton.setMaximumSize(new Dimension(25, 25));
            bouton.setFont(new Font("Arial Unicode MS", Font.PLAIN, 22));
            bouton.setFocusPainted(false); 
            bouton.setFocusable(false);
            bouton.setMargin(new Insets(0, 0, 0, 0));  
        }
 
        // Arrangement Boutons
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);
 
        // Ligne supérieure
        gbc.gridx = 0; gbc.gridy = 0; trans.add(HG, gbc);
        gbc.gridx = 1; gbc.gridy = 0; trans.add(HM, gbc);
        gbc.gridx = 2; gbc.gridy = 0; trans.add(HD, gbc);
 
        // Ligne inférieure
        gbc.gridx = 0; gbc.gridy = 1; trans.add(BG, gbc);
        gbc.gridx = 1; gbc.gridy = 1;  trans.add(BM, gbc);
        gbc.gridx = 2; gbc.gridy = 1; trans.add(BD, gbc);
 
        rangeeHaut.add(trans);
 

        JPanel rot = new JPanel();
        rot.setLayout(new BoxLayout(rot, BoxLayout.Y_AXIS));
        rot.setBorder(BorderFactory.createTitledBorder("Rotation"));

        JPanel rotChamp = new JPanel();
        rotChamp.setLayout(new BoxLayout(rotChamp, BoxLayout.X_AXIS));
        rotChamp.setOpaque(false);
        degRotation = new JTextField("", 6);
        degRotation.setPreferredSize(new Dimension(150, 27));
        degRotation.setMaximumSize(new Dimension(150, 27));
        degRotation.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 1, 1, 0, Color.GRAY),
                BorderFactory.createEmptyBorder(2, 6, 2, 6)
        ));

        rotChamp.add(degRotation);

        rotationButton = new JButton("↷");
        rotationButton.setFont(new Font("Arial Unicode MS", Font.PLAIN, 18));
        rotationButton.setPreferredSize(new Dimension(30, 27));
        rotationButton.setMaximumSize(new Dimension(30, 27));
        rotationButton.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
        rotationButton.setFocusPainted(false);
        rotationButton.setFocusable(false);

        rotChamp.add(rotationButton);
        rot.add(rotChamp);
        rangeeHaut.add(rot);
        
        inner.add(rangeeHaut);
        
        //Position souris
        JPanel sourisPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        sourisPanel.setOpaque(false);
        sourisPanel.add(new JLabel("Souris 🖰"));
        sourisPanel.add(Box.createHorizontalStrut(10));
        sourisPanel.add(new JLabel("Pièce"));
        sourisPanel.add(new JLabel("x : "));
        sourisLabelXPiece = new JLabel("");
        sourisLabelXPiece.setPreferredSize(new Dimension(80, 20));
        sourisPanel.add(sourisLabelXPiece);
        
        sourisPanel.add(Box.createHorizontalStrut(20));
        sourisPanel.add(new JLabel("y : "));
        sourisLabelYPiece = new JLabel("");
        sourisLabelYPiece.setPreferredSize(new Dimension(80, 20));
        sourisPanel.add(sourisLabelYPiece);
        sourisPanel.add(Box.createHorizontalStrut(50));
        
        sourisPanel.add(new JLabel("Canvas"));
        sourisPanel.add(new JLabel("x : "));
        sourisLabelXCanvas = new JLabel("");
        sourisPanel.add(sourisLabelXCanvas);
        sourisLabelXCanvas.setPreferredSize(new Dimension(80, 20));
               
        sourisPanel.add(Box.createHorizontalStrut(20));
        sourisPanel.add(new JLabel("y : "));
        sourisLabelYCanvas = new JLabel("");
        sourisLabelYCanvas.setPreferredSize(new Dimension(80, 20));
        sourisPanel.add(sourisLabelYCanvas);
       
        inner.add(sourisPanel);   
        add(inner, BorderLayout.CENTER);

        HG.addActionListener(e -> {
            PieceItemReadOnly it = mainWindow.controllerActif.trouverItemSelectionne();
            
            PieceItem its = (PieceItem)it;
            Point Pos = its.getPosition();
            if (it != null) {
                // Récupérer la pièce associée
                PieceReadOnly piece = mainWindow.controllerActif.getPiece(); // ou autre méthode pour accéder à la pièce

                Point hautGauche = piece.getExtremiteHautGauche();       
                   Point delta = new Point(
                   hautGauche.getX() - Pos.getX(),
                   hautGauche.getY() - Pos.getY()
               );
                            
               its.setPosition(hautGauche);       
               mainWindow.currentCanvas.repaint();              
            }
        });

        HD.addActionListener(e ->{
            PieceItemReadOnly it = mainWindow.controllerActif.trouverItemSelectionne();
           
                 // Dimensions du meuble
                double meubleLargeur = it.getLargeur();
                double meubleHauteur = it.getHauteur();
                PieceReadOnly piece = mainWindow.controllerActif.getPiece(); // ou autre méthode pour accéder à la pièce
                Point extremite = piece.getExtremiteHautDroite();
 
                // Coin haut-droite de la pièce, ajusté pour que le meuble reste dedans
                double x = extremite.getX()- meubleLargeur;
                double y = extremite.getY();
                
                ((PieceItem) it).setPosition(new Point(x, y));
                mainWindow.currentCanvas.repaint();             
        });
        
        BM.addActionListener(e ->{
            PieceItemReadOnly it = mainWindow.controllerActif.trouverItemSelectionne();
           
                 // Dimensions du meuble
                double meubleLargeur = it.getLargeur();
                double meubleHauteur = it.getHauteur();
                PieceReadOnly piece = mainWindow.controllerActif.getPiece(); 
                Point extremite = piece.getExtremiteBasMilieu();

                // Coin haut-droite de la pièce, ajusté pour que le meuble reste dedans
                double x = extremite.getX() - (meubleLargeur/2.0);
                double y = extremite.getY() - meubleHauteur;

                ((PieceItem) it).setPosition(new Point(x, y));
                 mainWindow.currentCanvas.repaint();                   
        }); 
        
        BD.addActionListener(e ->{
            PieceItemReadOnly it = mainWindow.controllerActif.trouverItemSelectionne();
           
                 // Dimensions du meuble
                double meubleLargeur = it.getLargeur();
                double meubleHauteur = it.getHauteur();
                PieceReadOnly piece = mainWindow.controllerActif.getPiece(); 
                Point extremite = piece.getExtremiteBasDroite();

                // Coin haut-droite de la pièce, ajusté pour que le meuble reste dedans
                double x = extremite.getX()-meubleLargeur;
                double y = extremite.getY()- meubleHauteur;
        
                ((PieceItem) it).setPosition(new Point(x, y));
                mainWindow.currentCanvas.repaint();
        }); 
        
        BG.addActionListener(e ->{
            PieceItemReadOnly it = mainWindow.controllerActif.trouverItemSelectionne();
                  
                double meubleHauteur = it.getHauteur();
                PieceReadOnly piece = mainWindow.controllerActif.getPiece(); 
                Point extremite = piece.getExtremiteBasGauche();

                // Coin haut-droite de la pièce, ajusté pour que le meuble reste dedans
                double x =extremite.getX();
                double y = extremite.getY()- meubleHauteur;

                ((PieceItem) it).setPosition(new Point(x, y));
                mainWindow.currentCanvas.repaint();
        });
    
        HM.addActionListener(e ->{
            PieceItemReadOnly it = mainWindow.controllerActif.trouverItemSelectionne();
             
                double meubleLargeur = it.getLargeur();           
                PieceReadOnly piece = mainWindow.controllerActif.getPiece(); 
                Point  extremite = piece.getExtremiteHautMilieu();
   
                double x = extremite.getX() - (meubleLargeur / 2.0);
                double y =  extremite.getY() ;

                 ((PieceItem) it).setPosition(new Point(x, y));
                mainWindow.currentCanvas.repaint();
        });
    }

    public void afficherCoordItemSelectionne() {
        double xPiece = mainWindow.controllerActif.getPiece().getPosition().getX();
        double yPiece = mainWindow.controllerActif.getPiece().getPosition().getY();
        double hauteurPiece = mainWindow.controllerActif.getPiece().getHauteur();
        
        if (mainWindow.controllerActif.trouverItemSelectionne() != null) {
            double xItem = mainWindow.controllerActif.trouverItemSelectionne().getPosition().getX();
            double yItem = mainWindow.controllerActif.trouverItemSelectionne().getPosition().getY();
            double hauteurItem = mainWindow.controllerActif.trouverItemSelectionne().getHauteur();
            
            double xRelatif = xItem - xPiece;
            double yRelatif = (yPiece + hauteurPiece) - (yItem + hauteurItem);
            xPosition.setText(Util.formatImperial(Util.enPouces(xRelatif)));
            yPosition.setText(Util.formatImperial(Util.enPouces(yRelatif)));
        } else {
            if(mainWindow.controllerActif.getPiece() != null){
                xPosition.setText(Util.formatImperial(Util.enPouces(xPiece)));
                yPosition.setText(Util.formatImperial(Util.enPouces(yPiece))); 
            }
        }
    }

    
    public void afficherAngleItemSelectionne() {
        if (mainWindow.controllerActif.trouverItemSelectionne() != null) {
            degRotation.setText(String.format("%.2f°", mainWindow.controllerActif.trouverItemSelectionne().getAngle()));
        } else {
            degRotation.setText("");

        }
    }

    public void angleListener() {
        degRotation.addActionListener(e -> {
            if (degRotation == null) {
                return;
            }
            String s = degRotation.getText();
            double angle = Double.parseDouble(s.replaceAll("[^0-9.]", ""));

            try {
                mainWindow.controllerActif.changerAngleItemSelectionne(angle);
            } catch (IllegalArgumentException error) {
                mainWindow.tabsErreur.clearMessages();
                mainWindow.tabsErreur.addErrorMessage(error.getMessage());
            }

            SwingUtilities.invokeLater(() -> {
                mainWindow.currentCanvas.repaint();
                mainWindow.currentCanvas.requestFocusInWindow();
            });
        });

        rotationButton.addActionListener(e -> {
            try {
                mainWindow.controllerActif.pivoterItemSelectionne();
            } catch (IllegalArgumentException error) {
                mainWindow.tabsErreur.clearMessages();
                mainWindow.tabsErreur.addErrorMessage(error.getMessage());
            }
            mainWindow.currentCanvas.repaint();
        });
    }

    public void positionListener() {
        if(mainWindow.controllerActif == null) return;
    
        ActionListener apply = (e -> {
            if (xPosition == null || yPosition == null)return;
            PieceItemReadOnly item = mainWindow.controllerActif.trouverItemSelectionne();         
            try{
                double x = Util.enPixels(xPosition.getText());
                double y = Util.enPixels(yPosition.getText());
                if(item != null){
                    double hauteurItem = item.getHauteur();
                    double xPiece = mainWindow.controllerActif.getPiece().getPosition().getX();
                    double yPiece = mainWindow.controllerActif.getPiece().getPosition().getY();
                    double hauteurPiece = mainWindow.controllerActif.getPiece().getHauteur();
                    
                    double xAbsolu = x + xPiece;
                    double yAbsolu = yPiece + hauteurPiece - y - hauteurItem;
                    moveSelectedTo(xAbsolu, yAbsolu);
                }else{
                    moveSelectedTo(x, y);
                }
                
            }catch(IllegalArgumentException error){
                mainWindow.tabsErreur.addErrorMessage(error.getMessage());
            }
            
            SwingUtilities.invokeLater(() -> {
                mainWindow.currentCanvas.repaint();
                mainWindow.currentCanvas.requestFocusInWindow();
            });
        });

        xPosition.addActionListener(apply);
        yPosition.addActionListener(apply);
    }

    public void moveSelectedTo(double x, double y) {

        Point p = new Point(x, y);
        PieceItemReadOnly item = mainWindow.controllerActif.trouverItemSelectionne();
        PieceReadOnly piece = mainWindow.controllerActif.getPiece();
        if(item instanceof Thermostat thermo && thermo.getMur() != null){
            p = thermo.getMur().projetterPositionItemSurMur(p, thermo, (Piece)piece);
        }else if(item instanceof ElementChauffant elem && elem.getMur() != null){
            p = elem.getMur().projetterPositionItemSurMur(p, elem, (Piece)piece);
        }
        
        if (mainWindow.controllerActif.trouverItemSelectionne() == null) {
            mainWindow.controllerActif.repositionnerPiece(p);

        } else if (mainWindow.controllerActif.estPositionValide(p)) {
            mainWindow.tabsErreur.clearMessages();
            mainWindow.controllerActif.deplacerItemSelectionne(p);
        } else {
            mainWindow.tabsErreur.clearMessages();
            mainWindow.tabsErreur.addErrorMessage("Déplacement refusé : le meuble dépasse les limites de la pièce.");
        }
    }

}
