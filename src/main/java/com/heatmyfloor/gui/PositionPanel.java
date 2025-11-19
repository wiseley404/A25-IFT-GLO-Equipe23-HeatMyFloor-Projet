package com.heatmyfloor.gui;

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
        
        //Translation
        JPanel trans = new JPanel(new GridLayout(2, 3, 6, 6));
        trans.setBorder(BorderFactory.createTitledBorder("Translation"));
        for (int i = 0; i < 6; i++) {
            trans.add(new JButton(""));
        }
        inner.add(trans);
        
        //Rotation
        JPanel rot = new JPanel();
        rot.setLayout(new BoxLayout(rot, BoxLayout.Y_AXIS));
        rot.add(new JLabel("Rotation"));
        
        JPanel rotChamp = new JPanel();
        rotChamp.setLayout(new BoxLayout(rotChamp, BoxLayout.X_AXIS));
        degRotation = new JTextField("", 6);
        degRotation.setPreferredSize(new Dimension(150, 30));
        degRotation.setMaximumSize(new Dimension(150, 30));
        degRotation.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 0, Color.GRAY));
        rotChamp.add(degRotation);
        
        rotationButton = new JButton("⟳");
        rotationButton.setPreferredSize(new Dimension(30, 30));
        rotationButton.setMaximumSize(new Dimension(30, 30));
        rotationButton.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 1, Color.GRAY));
        rotChamp.add(rotationButton);
        rot.add(rotChamp);
        inner.add(rot);

        add(inner, BorderLayout.CENTER);

    }

    public void afficherCoordItemSelectionne() {
        if (mainWindow.controllerActif.trouverItemSelectionne() != null) {
            double x = mainWindow.controllerActif.trouverItemSelectionne().getPosition().getX();
            double y = mainWindow.controllerActif.trouverItemSelectionne().getPosition().getY();
            xPosition.setText(String.format("%.2f", x));
            yPosition.setText(String.format("%.2f", y));
        } else {
            xPosition.setText(String.valueOf(mainWindow.controllerActif.getPiece().getPosition().getX()));
            yPosition.setText(String.valueOf(mainWindow.controllerActif.getPiece().getPosition().getY()));
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
            if (xPosition == null || yPosition == null) {
                return;
            }
            Double x = parseNumber(xPosition.getText());
            Double y = parseNumber(yPosition.getText());

            //déplace l'item selectionné
            moveSelectedTo(x, y);
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

            mainWindow.tabsErreur.addErrorMessage("Déplacement refusé : le meuble dépasse les limites de la pièce.");
        }
    }

    private Double parseNumber(String s) {
    if (s == null) return null;

    s = s.replaceAll("[^0-9./ ]", "").trim();

    if (s.isEmpty()) return null;
    try {
        // CAS 1 : Fraction simple "a/b"
        if (s.matches("[0-9]+\\s*/\\s*[0-9]+")) {
            String[] parts = s.split("/");
            double a = Double.parseDouble(parts[0].trim());
            double b = Double.parseDouble(parts[1].trim());
            if (b == 0) return null;
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
            if (c == 0) return null;
            return entier + (b / c);
        }

        // CAS 3 : Nombre normal (décimal)
        return Double.parseDouble(s);

    } catch (Exception e) {
        return null;
    }
    }
}
