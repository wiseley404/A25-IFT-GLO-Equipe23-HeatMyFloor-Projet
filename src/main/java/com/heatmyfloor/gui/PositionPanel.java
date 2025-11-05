package com.heatmyfloor.gui;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author tatow
 */
public class PositionPanel extends JPanel {
    public PositionPanel() {
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
        xy.add(new JLabel("X :"));
        xy.add(new JTextField("120", 6));
        xy.add(new JLabel("Y :"));
        xy.add(new JTextField("80", 6));
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
    }
}
