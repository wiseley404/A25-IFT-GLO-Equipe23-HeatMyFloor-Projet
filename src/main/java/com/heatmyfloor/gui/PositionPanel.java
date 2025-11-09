package com.heatmyfloor.gui;

import com.heatmyfloor.domain.piece.Controller;
import javax.swing.*;
import java.awt.*;
import com.heatmyfloor.domain.Point;
import java.awt.event.ActionListener;

/**
 *
 * @author tatow
 */
public class PositionPanel extends JPanel {

    private JTextField xPosition;
    private JTextField yPosition;
    private JTextField degRotation;
    private MainWindow mainWindow;
    
    public PositionPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        

 

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createMatteBorder(1,0,0,0, new Color(230,230,230)));
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

        JPanel trans = new JPanel(new GridLayout(2,3,6,6));
        trans.setBorder(BorderFactory.createTitledBorder("Translation"));
        for (int i=0;i<6;i++) trans.add(new JButton(""));
        inner.add(trans);
        
        
     
        JPanel rot = new JPanel();
        rot.setLayout(new BoxLayout(rot, BoxLayout.Y_AXIS));
        rot.add(new JLabel("Rotation"));
        rot.add(new JTextField("90°", 6));
        inner.add(rot);

        add(inner, BorderLayout.CENTER);
        
        positionListener();
    }
        public void afficherCoordItemSelectionne(){
        if(mainWindow.controller.trouverItemSelectionne() != null){
            xPosition.setText(String.valueOf(mainWindow.controller.trouverItemSelectionne().getPosition().getX()));
            yPosition.setText(String.valueOf(mainWindow.controller.trouverItemSelectionne().getPosition().getY()));  
        }else{
            xPosition.setText("");
            yPosition.setText("");
        }
    }
    
    public void afficherAngleItemSelectionne(){
        if(mainWindow.controller.trouverItemSelectionne() != null){
            degRotation.setText(String.valueOf(mainWindow.controller.trouverItemSelectionne().getAngle() + "°" ));
        }else{
            degRotation.setText("");

        }
    }
    
    
    
            public void positionListener() { 
                ActionListener apply = (e  -> {
            
            if ( xPosition == null || yPosition == null) return;
            Double x = parseNumber(xPosition.getText());
            Double y  = parseNumber(yPosition.getText());
            
    

            //déplace et redimensionne l'item selectionné
            moveSelectedTo(x, y);  
            mainWindow.currentCanvas.repaint();                     //maj l'affichage  
        });
      
       
        
        // appuie sur Enter = applique
        xPosition.addActionListener(apply);
        yPosition.addActionListener(apply);
        }
    
        //ajout de ces methodes
    public void moveSelectedTo(double x, double y) {     //place l'item aux coordonées exact
    mainWindow.controller.deplacerItemSelectionne(new Point(x, y));
    }
    
    
    
    private Double parseNumber(String s) {
        try {
            // enlève tout sauf chiffres et point
            return Double.parseDouble(s.replaceAll("[^0-9.]", ""));
        } catch (NumberFormatException e) {
            return null;
    }
}
    
    
    
    
    
    
    
}
