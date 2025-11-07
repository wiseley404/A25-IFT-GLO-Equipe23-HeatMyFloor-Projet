package com.heatmyfloor.gui;

import javax.swing.*;
import java.awt.*;
import javax.swing.JLabel;

public class Canvas extends JPanel {

    private Rectangle rectangle;
    private FormeIrregulierPanel dessinPanel;

    public Canvas() {
        setBackground(Color.white);
        setLayout(null); // absolute positioning for placeholder icons
        setBorder(BorderFactory.createLineBorder(new Color(140, 140, 140), 2));
//        JLabel tub = createIconLabel("/icons/tub.svg", 30, 20, 60, 160);
//        add(tub);
//        JLabel sink = createIconLabel("/icons/sink.svg", 200, 20, 80, 40);
//        add(sink);
//        JLabel toilet = createIconLabel("/icons/toilet.svg", 300, 20, 48, 48);
//        add(toilet);
//        JLabel cabinet = createIconLabel("/icons/cabinet.svg", 1020, 120, 60, 220);
//        add(cabinet);
    }

    public void dessinerRectangle(int longueur, int largeur) {

        if (dessinPanel != null) {
            remove(dessinPanel);
            dessinPanel.effacer();
        }

        int x = (getWidth() - longueur) / 2;
        int y = (getHeight() - largeur) / 2;
        rectangle = new Rectangle(x, y, longueur, largeur);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(new Color(255, 232, 200, 120));
        super.paintComponent(g);
        if (rectangle != null) {
            g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        }
    }

    private JLabel createIconLabel(String resPath, int x, int y, int w, int h) {
        JLabel l = new JLabel();
        l.setBounds(x, y, w, h);
        try {
            l.setIcon(new ImageIcon(getClass().getResource(resPath)));
        } catch (Exception e) {
        }
        l.setBorder(BorderFactory.createDashedBorder(Color.GRAY));
        return l;
    }

    void dessinerFormeIrreguliere() {
        rectangle = null;
        if (dessinPanel != null) {
            remove(dessinPanel);
        }

        dessinPanel = new FormeIrregulierPanel();
        dessinPanel.setOpaque(false);
        dessinPanel.setBounds(0, 0, getWidth(), getHeight());
        dessinPanel.activerModeDessin(true);

        add(dessinPanel);
        revalidate();
        repaint();

        JOptionPane.showMessageDialog(this,
                "Cliquez pour placer des points.\nDouble-cliquez pour fermer la forme.",
                "Mode dessin", JOptionPane.INFORMATION_MESSAGE);
    }
}
