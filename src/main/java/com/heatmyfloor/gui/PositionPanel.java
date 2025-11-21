package com.heatmyfloor.gui;

import javax.swing.*;
import java.awt.*;
import com.heatmyfloor.domain.Point;
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
        coords.add(new JLabel("Position"));
        JPanel xy = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));

        xPosition = new JTextField("", 6);
        xy.add(new JLabel("X :"));
        xy.add(xPosition);
        yPosition = new JTextField("", 6);
        xy.add(new JLabel("Y :"));
        xy.add(yPosition);
        coords.add(xy);

        inner.add(coords);

        JPanel trans = new JPanel(new GridLayout(2, 3, 6, 6));
        trans.setBorder(BorderFactory.createTitledBorder("Translation"));
        JButton HG = new JButton(new ImageIcon(getClass().getResource("/Icons/HautGauche.jpg")));
        JButton BG = new JButton(new ImageIcon(getClass().getResource("/Icons/BasGauche.jpg")));
        JButton HD = new JButton(new ImageIcon(getClass().getResource("/Icons/HautDroit.jpg")));
        JButton BD = new JButton(new ImageIcon(getClass().getResource("/Icons/BasDroit.jpg")));
        JButton HM = new JButton(new ImageIcon(getClass().getResource("/Icons/HautMilieu.jpg")));
        JButton BM = new JButton(new ImageIcon(getClass().getResource("/Icons/BasMilieu.jpg")));

        JButton[] boutons = {HG, BG, HD, BD, HM, BM};
        for (JButton bouton : boutons) {
            redimensionnerTailleImage(bouton, 40, 40);
        }

        for (JButton bouton : boutons) {
            trans.add(bouton);
        }
        inner.add(trans);

        JPanel rot = new JPanel();
        rot.setLayout(new BoxLayout(rot, BoxLayout.Y_AXIS));
        rot.add(new JLabel("Rotation"));

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

        rotationButton = new JButton("⟳");
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

            if (it != null) {
                // Récupérer la pièce associée
                PieceReadOnly piece = mainWindow.controllerActif.getPiece(); // ou autre méthode pour accéder à la pièce

                Point hautGauche = piece.getExtremiteHautGauche();

                ((PieceItem) it).setPosition(hautGauche);
                inner.repaint();
            }

        });

        /*HD.addActionListener(e ->{
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
                 inner.repaint();



                        
        });*/
 /*BM.addActionListener(e ->{
            PieceItemReadOnly it = mainWindow.controllerActif.trouverItemSelectionne();
           
                 // Dimensions du meuble
                double meubleLargeur = it.getLargeur();
                double meubleHauteur = it.getHauteur();
                PieceReadOnly piece = mainWindow.controllerActif.getPiece(); // ou autre méthode pour accéder à la pièce
                Point extremite = piece.getExtremiteBasMilieu();


                // Coin haut-droite de la pièce, ajusté pour que le meuble reste dedans
                double x = extremite.getX() - meubleLargeur;
                double y = extremite.getY() - meubleHauteur;

                
                ((PieceItem) it).setPosition(new Point(x, y));
                 inner.repaint();



                        
        }); */
 /*BD.addActionListener(e ->{
            PieceItemReadOnly it = mainWindow.controllerActif.trouverItemSelectionne();
           
                 // Dimensions du meuble
                double meubleLargeur = it.getLargeur();
                double meubleHauteur = it.getHauteur();
                PieceReadOnly piece = mainWindow.controllerActif.getPiece(); // ou autre méthode pour accéder à la pièce
                Point extremite = piece.getExtremiteBasDroite();



                // Coin haut-droite de la pièce, ajusté pour que le meuble reste dedans
                double x = extremite.getX();
                double y = extremite.getY()- meubleHauteur;

                
                ((PieceItem) it).setPosition(new Point(x, y));
                 inner.repaint();



        }); 
        
        BG.addActionListener(e ->{
            PieceItemReadOnly it = mainWindow.controllerActif.trouverItemSelectionne();
           
                 // Dimensions du meuble
                double meubleLargeur = it.getLargeur();
                double meubleHauteur = it.getHauteur();
                PieceReadOnly piece = mainWindow.controllerActif.getPiece(); // ou autre méthode pour accéder à la pièce
                Point extremite = piece.getExtremiteBasGauche();


                // Coin haut-droite de la pièce, ajusté pour que le meuble reste dedans
                double x =extremite.getX() - (meubleLargeur/2);
                double y = extremite.getY();

                 ((PieceItem) it).setPosition(new Point(x, y));
                 inner.repaint();



        });
    
        HM.addActionListener(e ->{
            PieceItemReadOnly it = mainWindow.controllerActif.trouverItemSelectionne();
           
                 // Dimensions du meuble
                double meubleLargeur = it.getLargeur();
                double meubleHauteur = it.getHauteur();
                PieceReadOnly piece = mainWindow.controllerActif.getPiece(); // ou autre méthode pour accéder à la pièce
                Point  extremite = piece.getExtremiteHautMilieu();


                // Coin haut-droite de la pièce, ajusté pour que le meuble reste dedans
                double x = extremite.getX() - (meubleLargeur / 2);
                double y =  extremite.getY() - meubleHauteur;

                 ((PieceItem) it).setPosition(new Point(x, y));
                 inner.repaint();



        });*/
    }

    public void afficherCoordItemSelectionne() {
        if (mainWindow.controllerActif.trouverItemSelectionne() != null) {
            double xItem = mainWindow.controllerActif.trouverItemSelectionne().getPosition().getX();
            double yItem = mainWindow.controllerActif.trouverItemSelectionne().getPosition().getY();
            xPosition.setText(Util.formatImperial(Util.enPouces(xItem)));
            yPosition.setText(Util.formatImperial(Util.enPouces(yItem)));
        } else {
            double xPiece = mainWindow.controllerActif.getPiece().getPosition().getX();
            double yPiece = mainWindow.controllerActif.getPiece().getPosition().getY();
            xPosition.setText(Util.formatImperial(Util.enPouces(xPiece)));
            yPosition.setText(Util.formatImperial(Util.enPouces(yPiece)));
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
            } catch (IllegalArgumentException event) {
                mainWindow.tabsErreur.addErrorMessage(event.getMessage());
            }

            SwingUtilities.invokeLater(() -> {
                mainWindow.currentCanvas.repaint();
                mainWindow.currentCanvas.requestFocusInWindow();
            });
        });

        rotationButton.addActionListener(e -> {
            if (mainWindow.currentCanvas == null) {
                return;
            }
            try {
                mainWindow.controllerActif.pivoterItemSelectionne();
            } catch (IllegalArgumentException event) {
                mainWindow.tabsErreur.addErrorMessage(event.getMessage());
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

            mainWindow.tabsErreur.addErrorMessage("Déplacement refusé : le meuble dépasse les limites de la pièce ou la position est déja occupée.");
            if (mainWindow.controllerActif.estPositionValide(p)) {
                mainWindow.tabsErreur.clearMessages();

                mainWindow.controllerActif.deplacerItemSelectionne(p);
            } else {

                mainWindow.tabsErreur.addErrorMessage("Déplacement refusé : le meuble dépasse les limites de la pièce.");
            }
        }
    }
<<<<<<< HEAD
   
=======

    private Double parseNumber(String s) {
        if (s == null) {
            return null;
        }

        s = s.replaceAll("[^0-9./ ]", "").trim();

        if (s.isEmpty()) {
            return null;
        }
        try {
            // CAS 1 : Fraction simple "a/b"
            if (s.matches("[0-9]+\\s*/\\s*[0-9]+")) {
                String[] parts = s.split("/");
                double a = Double.parseDouble(parts[0].trim());
                double b = Double.parseDouble(parts[1].trim());
                if (b == 0) {
                    return null;
                }
                return a / b;
            }

            // CAS 2 : Fraction "a b/c"
            if (s.matches("[0-9]+\\s+[0-9]+\\s*/\\s*[0-9]+")) {
                String[] parts = s.split("\\s+");
                double entier = Double.parseDouble(parts[0]);
                String frac = parts[1];
                String[] fracParts = frac.split("/");
                double b = Double.parseDouble(fracParts[0]);
                double c = Double.parseDouble(fracParts[1]);
                if (c == 0) {
                    return null;
                }
                return entier + (b / c);
            }

            // CAS 3 : Nombre normal (décimal)
            return Double.parseDouble(s);

        } catch (Exception e) {
            return null;
        }
    }

    private void redimensionnerTailleImage(JButton bouton, int largeur, int longueur) {
        Icon icon = bouton.getIcon();
        if (icon == null) {
            return;
        }
        Image image = ((ImageIcon) icon).getImage();
        Image img = image.getScaledInstance(largeur, longueur, Image.SCALE_SMOOTH);
        bouton.setIcon(new ImageIcon(img));

    }

    /* public void deplacerHautGauche(JButton HG){
        ActionListener apply = (e ->{
            PieceItemReadOnly it = mainWindow.controllerActif.trouverItemSelectionne();
            if (it != null){
                moveSelectedTo(0,0);
            }
        });
        HG.addActionListener(apply);
    }*/
>>>>>>> 746c711 (ésolution des conflits)
}
