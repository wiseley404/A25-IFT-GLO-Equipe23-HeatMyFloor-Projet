package com.heatmyfloor.gui;

import javax.swing.*;
import java.awt.*;
import com.heatmyfloor.domain.Point;
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
        for (int i = 0; i < 6; i++) {
            trans.add(new JButton(""));
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
    
    public void angleListener(){
        degRotation.addActionListener(e -> {
            if(degRotation == null){
                return;
            }
            String s = degRotation.getText();
            double angle = Double.parseDouble(s.replaceAll("[^0-9.]", ""));

            try{
                mainWindow.controllerActif.changerAngleItemSelectionne(angle);
            }catch(IllegalArgumentException event){
                mainWindow.tabsErreur.addErrorMessage(event.getMessage());
            }   
            
            SwingUtilities.invokeLater(() -> {
                mainWindow.currentCanvas.repaint();
                mainWindow.currentCanvas.requestFocusInWindow();
            });
        });
        
        rotationButton.addActionListener(e -> {
            if(mainWindow.currentCanvas == null){
                return;
            }
            try{
                mainWindow.controllerActif.pivoterItemSelectionne();
            }catch(IllegalArgumentException event){
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
        if(mainWindow.controllerActif.trouverItemSelectionne() == null){
            mainWindow.controllerActif.repositionnerPiece(p);
            
        }else if (mainWindow.controllerActif.estPositionValide(p)) {
            mainWindow.tabsErreur.clearMessages();

            mainWindow.controllerActif.deplacerItemSelectionne(p);
        } else {

            mainWindow.tabsErreur.addErrorMessage("Déplacement refusé : le meuble dépasse les limites de la pièce ou la position est déja occupée.");
        }
    }
   
}
