/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heatmyfloor.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tatow
 */
public class FormeIrregulierPanel extends JPanel {

    private final List<Point> points = new ArrayList<>();
    private Point pointActuel = null;
    private boolean modeDessin = false;

    public FormeIrregulierPanel() {

        setBackground(Color.WHITE);
        MouseAdapter ma = new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    modeDessin = false;
                    repaint();
                } else if (modeDessin) {
                    points.add(e.getPoint());
                    repaint();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if (modeDessin) {
                    pointActuel = e.getPoint();
                    repaint();
                }
            }
        };

        addMouseListener(ma);
        addMouseMotionListener(ma);
    }

    public void activerModeDessin(boolean actif) {
        modeDessin = actif;
        if (!actif) {
            pointActuel = null;
        }
        repaint();
    }

    public List<Point> getPoints() {
        return new ArrayList<>(points);
    }

    public void effacer() {
        points.clear();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setStroke(new BasicStroke(2));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (points.size() > 2) {
            Polygon polygon = new Polygon();
            for (Point p : points) {
                polygon.addPoint(p.x, p.y);
            }

            if (modeDessin) {
                g2.setColor(Color.BLUE);
                g2.drawPolyline(polygon.xpoints, polygon.ypoints, points.size());

                if (pointActuel != null) {
                    Point dernier = points.get(points.size() - 1);
                    g2.setColor(Color.GRAY);
                    g2.drawLine(dernier.x, dernier.y, pointActuel.x, pointActuel.y);
                }
            } else {
                g2.setColor(new Color(255, 232, 200, 120));  // bleu semi-transparent
                g2.fillPolygon(polygon);

                g2.setColor(Color.BLACK);
                g2.drawPolygon(polygon);
            }
        }

        // Dessine les points rouges
        g2.setColor(Color.RED);
        for (Point p : points) {
            g2.fillOval(p.x - 3, p.y - 3, 6, 6);
        }

        g2.dispose();
    }
}
