/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heatmyfloor.gui;

import com.heatmyfloor.domain.PointMapper;
import com.heatmyfloor.domain.Util;
import com.heatmyfloor.domain.graphe.Graphe;
import com.heatmyfloor.domain.graphe.Intersection;
import com.heatmyfloor.domain.piece.Piece;
import com.heatmyfloor.domain.piece.PieceIrreguliere;
import com.heatmyfloor.domain.piece.PieceReadOnly;
import com.heatmyfloor.gui.drawer.PieceDrawer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @author tatow
 */
public class FormeIrregulierPanel extends JPanel {

    private final List<Point> points = new ArrayList<>();
    private Point pointActuel = null;
    private boolean modeDessin = false;
    private Consumer<List<Point>> onFormeTerminee;

    public FormeIrregulierPanel() {
        this(null, true);
    }

    /**
     * Constructeur avec points initiaux, sans interaction utilisateur
     */
    public FormeIrregulierPanel(List<Point> pointsInitiaux) {
        this(pointsInitiaux, false);
    }

    private FormeIrregulierPanel(List<Point> pointsInitiaux, boolean modeDessinInitial) {

        if (pointsInitiaux != null) {
            this.points.addAll(pointsInitiaux);
        }
        this.modeDessin = modeDessinInitial;

        setBackground(Color.WHITE);

        if (modeDessinInitial) {
            MouseAdapter ma = new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2 && points.size() > 2) {
                        modeDessin = false;
                        repaint();
                        if (onFormeTerminee != null) {
                            onFormeTerminee.accept(points);
                        }
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
                        
                        Canvas canvas = (Canvas) getParent();
                        canvas.dispatchEvent(SwingUtilities.convertMouseEvent(
                        com.heatmyfloor.gui.FormeIrregulierPanel.this, e, canvas));
                    }
                }
            };
            addMouseListener(ma);
            addMouseMotionListener(ma);
        }

    }

    public void activerModeDessin(boolean actif) {
        modeDessin = actif;
        if (!actif) {
            pointActuel = null;
        
        // listeners qui propagent au Canvas parent
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Canvas canvas = (Canvas) getParent();
                canvas.dispatchEvent(SwingUtilities.convertMouseEvent(
                    FormeIrregulierPanel.this, e, canvas));
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                Canvas canvas = (Canvas) getParent();
                canvas.dispatchEvent(SwingUtilities.convertMouseEvent(
                    FormeIrregulierPanel.this, e, canvas));
            }
        });
        
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Canvas canvas = (Canvas) getParent();
                canvas.dispatchEvent(SwingUtilities.convertMouseEvent(
                    FormeIrregulierPanel.this, e, canvas));
            }
            @Override
            public void mouseMoved(MouseEvent e) {
                Canvas canvas = (Canvas) getParent();
                canvas.dispatchEvent(SwingUtilities.convertMouseEvent(
                    FormeIrregulierPanel.this, e, canvas));
            }
        });
    
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

        Canvas canvas = (Canvas) getParent();
        if(canvas != null){
            g2.translate(canvas.getOriginePx().getX(), canvas.getOriginePx().getY());
            g2.scale(canvas.getZoom(), canvas.getZoom());
        }
        List<java.awt.Point> pointsADessiner;
        if(modeDessin){
            pointsADessiner = this.points;
        }else{
            pointsADessiner = new ArrayList<>();
            if(canvas != null && canvas.getMainWindow() != null){
                PieceReadOnly piece = canvas.getMainWindow().controllerActif.getPiece();
                if(piece instanceof PieceIrreguliere pieceIrreg){
                    for(Point sommet : PointMapper.toAwtList(pieceIrreg.getSommets())){
                        pointsADessiner.add(sommet);
                    }
                }
            }
            if(pointsADessiner.isEmpty()){
                pointsADessiner =  this.points;
            }
        }
      
        
        if (pointsADessiner.size() > 2) {
            Polygon polygon = new Polygon();
            for (Point p : pointsADessiner) {
                polygon.addPoint(p.x, p.y);
            }

            if (modeDessin) {
                g2.setColor(Color.ORANGE);
                g2.drawPolyline(polygon.xpoints, polygon.ypoints, pointsADessiner.size());

                if (pointActuel != null) {
                    Point dernier = points.get(points.size() - 1);
                    g2.setColor(Color.GRAY);
                    g2.drawLine(dernier.x, dernier.y, pointActuel.x, pointActuel.y);
                }
            } else {
                g2.setColor(new Color(255, 232, 200, 120));  
                g2.fillPolygon(polygon);

                g2.setColor(Color.ORANGE);
                g2.drawPolygon(polygon);
            }
        }

        // Dessine les points rouges
        g2.setColor(Color.ORANGE);
        for (Point p : pointsADessiner) {
            g2.fillOval(p.x - 3, p.y - 3, 6, 6);
        }
        
        if (canvas != null && canvas.getMainWindow() != null && canvas.getMainWindow().controllerActif != null) {
            PieceDrawer drawer = new PieceDrawer(canvas.getMainWindow());
            drawer.dessinerPieceItems(g2);
        }
        
        PieceReadOnly piece = canvas.getMainWindow().controllerActif.getPiece();
        Graphe graphe = piece.getGraphe();
        if(graphe != null){
            List<Intersection> intersectionsValide = graphe.getListIntersectionsValide((Piece)piece);
            for(Intersection intersect : intersectionsValide){
                com.heatmyfloor.domain.Point point = intersect.getCoordonees();
                int x = (int) point.getX();
                int y = (int) point.getY();
                int rayon = (int) graphe.getRayonIntersection();
                g2.fillOval(x, y, rayon*2, rayon*2);
            }
        }
        g2.dispose();
    }

    public void setOnFormeTerminee(Consumer<List<Point>> listener) {
        this.onFormeTerminee = listener;
    }
}
