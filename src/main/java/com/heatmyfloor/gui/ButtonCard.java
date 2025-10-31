/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heatmyfloor.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 *
 * @author tatow
 */
public class ButtonCard extends JPanel {

    private final JLabel iconLabel = new JLabel();
    private final JLabel textLabel = new JLabel();
    private boolean hover = false, press = false;
    private ActionListener onClick;

    public ButtonCard(String text, Icon icon) {
        setOpaque(false);
        setLayout(new BorderLayout());
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));

        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setIcon(icon);
        redimensionnerImage(icon, iconLabel);
        textLabel.setText(text);
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(iconLabel, BorderLayout.CENTER);
        add(textLabel, BorderLayout.SOUTH);

        addMouseListener(new java.awt.event.MouseAdapter() {
            /* ... inchangé ... */ });
    }

    public void setOnClick(ActionListener l) {
        this.onClick = l;
    }

    @Override
    public Dimension getPreferredSize() {
        Insets in = getInsets();
        Dimension di = iconLabel.getPreferredSize();
        Dimension dt = textLabel.getPreferredSize();
        int w = Math.max(di.width, dt.width) + in.left + in.right + 12;  // padding
        int h = di.height + dt.height + in.top + in.bottom + 12;
        // garde une taille mini raisonnable sans la forcer “fixe”
        return new Dimension(Math.max(64, w), Math.max(48, h));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int arc = 14;
        Color base = new Color(255, 255, 255, 60);
        Color hoverCol = new Color(255, 232, 200, 120);
        Color pressCol = new Color(255, 220, 180, 160);
        Color fill = press ? pressCol : (hover ? hoverCol : base);

        g2.setColor(fill);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

        g2.setColor(new Color(225, 205, 175));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);
        g2.dispose();
    }

    private void redimensionnerImage(Icon icon, JLabel iconLabel) {
        if (icon instanceof ImageIcon) {
            Image img = ((ImageIcon) icon).getImage();
            int maxW = 60;
            int maxH = 60;

            int w = img.getWidth(null);
            int h = img.getHeight(null);

            // Calcule un facteur d’échelle qui garde les proportions
            double scale = Math.min((double) maxW / w, (double) maxH / h);

            int newW = (int) (w * scale);
            int newH = (int) (h * scale);

            Image scaled = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
            iconLabel.setIcon(new ImageIcon(scaled));
        } else {
            iconLabel.setIcon(icon);
        }
    }
}
