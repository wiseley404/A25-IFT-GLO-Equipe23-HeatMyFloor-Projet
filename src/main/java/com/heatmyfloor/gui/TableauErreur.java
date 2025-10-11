package com.heatmyfloor.gui;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author tatow
 */
public class TableauErreur extends JPanel {
    public TableauErreur() {
        setPreferredSize(new Dimension(360, 120));
        setBorder(BorderFactory.createMatteBorder(1,1,0,0, new Color(230,230,230)));
        setLayout(new BorderLayout());
        JPanel title = new JPanel(new FlowLayout(FlowLayout.LEFT));
        title.setOpaque(false);
        title.add(new JLabel("Messages d'erreur"));
        add(title, BorderLayout.NORTH);

        JPanel msg = new JPanel(new FlowLayout(FlowLayout.LEFT));
        msg.setOpaque(false);
        JLabel warn = new JLabel("⚠");
        warn.setFont(warn.getFont().deriveFont(20f));
        msg.add(warn);
        msg.add(new JLabel("Vous ne pouvez pas déplacer ce meuble à cette position."));
        add(msg, BorderLayout.CENTER);
    }
}
