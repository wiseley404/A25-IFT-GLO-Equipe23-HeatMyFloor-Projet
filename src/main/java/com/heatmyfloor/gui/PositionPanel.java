package com.heatmyfloor.gui;

import javax.swing.*;
import java.awt.*;
import com.heatmyfloor.domain.Point;
import com.heatmyfloor.domain.PointMapper;
import com.heatmyfloor.domain.Util;
import com.heatmyfloor.domain.items.Drain;
import com.heatmyfloor.domain.items.DrainReadOnly;
import com.heatmyfloor.domain.piece.Piece;
import com.heatmyfloor.domain.piece.PieceItem;
import com.heatmyfloor.domain.piece.PieceItemReadOnly;
import com.heatmyfloor.domain.piece.PieceReadOnly;
import java.awt.event.ActionListener;
import com.heatmyfloor.domain.Util;
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

    public PositionPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(230, 230, 230)));
        setPreferredSize(new Dimension(800, 120));
        setBackground(Color.white);
        JPanel inner = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        inner.setOpaque(false);

        JPanel coords = new JPanel();
        coords.setLayout(new BoxLayout(coords, BoxLayout.Y_AXIS));
        coords.setBorder(BorderFactory.createTitledBorder("Position"));
        JPanel xy = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));

        xPosition = new JTextField("", 6);
        xPosition.setMargin(new Insets(2, 2, 2, 4));
        xy.add(new JLabel("X :"));
        xy.add(xPosition);
        yPosition = new JTextField("", 6);
        yPosition.setMargin(new Insets(2, 2, 2, 4));
        xy.add(new JLabel("Y :"));
        xy.add(yPosition);
        coords.add(xy);

        inner.add(coords);

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
 
        inner.add(trans);
 

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
        inner.add(rot);
        
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
        if (mainWindow.controllerActif.trouverItemSelectionne() != null) {
            double xItem = mainWindow.controllerActif.trouverItemSelectionne().getPosition().getX();
            double yItem = mainWindow.controllerActif.trouverItemSelectionne().getPosition().getY();
            xPosition.setText(Util.formatImperial(Util.enPouces(xItem)));
            yPosition.setText(Util.formatImperial(Util.enPouces(yItem)));
        } else {
            if(mainWindow.controllerActif.getPiece() != null){
                double xPiece = mainWindow.controllerActif.getPiece().getPosition().getX();
                double yPiece = mainWindow.controllerActif.getPiece().getPosition().getY();
                xPosition.setText(Util.formatImperial(Util.enPouces(xPiece)));
                yPosition.setText(Util.formatImperial(Util.enPouces(yPiece))); 
            }
        }
    }

    
    public void afficherAngleItemSelectionne() {
        if (mainWindow.controllerActif.trouverItemSelectionne() != null) {
            degRotation.setText(String.valueOf(mainWindow.controllerActif.trouverItemSelectionne().getAngle() + "°"));
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
        ActionListener apply = (e -> {
            if (xPosition == null || yPosition == null)return;
            try{
                double x = Util.enPixels(xPosition.getText());
                double y = Util.enPixels(yPosition.getText());
                moveSelectedTo(x, y);
            }catch(IllegalArgumentException error){
                //mainWindow.tabsErreur.clearMessages();
                mainWindow.tabsErreur.addErrorMessage(error.getMessage());
            }
            
            SwingUtilities.invokeLater(() -> {
                mainWindow.currentCanvas.repaint();//maj l'affichage
                mainWindow.currentCanvas.requestFocusInWindow();
            });
        });

        xPosition.addActionListener(apply);
        yPosition.addActionListener(apply);
    }

    public void moveSelectedTo(double x, double y) {

        Point p = new Point(x, y);
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
