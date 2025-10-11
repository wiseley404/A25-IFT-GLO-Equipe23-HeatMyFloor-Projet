package com.heatmyfloor.gui;

import javax.swing.*;
import java.awt.*;

public class Canvas extends JPanel {
    public Canvas() {
        setBackground(new Color(220,220,220));
        setLayout(null); // absolute positioning for placeholder icons
        setBorder(BorderFactory.createLineBorder(new Color(140,140,140), 2));
        JLabel tub = createIconLabel("/icons/tub.svg", 30, 20, 60, 160);
        add(tub);
        JLabel sink = createIconLabel("/icons/sink.svg", 200, 20, 80, 40);
        add(sink);
        JLabel toilet = createIconLabel("/icons/toilet.svg", 300, 20, 48, 48);
        add(toilet);
        JLabel cabinet = createIconLabel("/icons/cabinet.svg", 1020, 120, 60, 220);
        add(cabinet);
    }

    private JLabel createIconLabel(String resPath, int x, int y, int w, int h) {
        JLabel l = new JLabel();
        l.setBounds(x, y, w, h);
        try {
            l.setIcon(new ImageIcon(getClass().getResource(resPath)));
        } catch (Exception e) { }
        l.setBorder(BorderFactory.createDashedBorder(Color.GRAY));
        return l;
    }
}
