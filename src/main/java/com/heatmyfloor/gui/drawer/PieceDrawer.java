/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heatmyfloor.gui.drawer;

import com.heatmyfloor.domain.Point;
import com.heatmyfloor.domain.graphe.Graphe;
import com.heatmyfloor.domain.graphe.Intersection;
import com.heatmyfloor.domain.items.Drain;
import com.heatmyfloor.domain.items.MeubleAvecDrain;
import com.heatmyfloor.domain.piece.Controller;
import com.heatmyfloor.domain.piece.Piece;
import com.heatmyfloor.gui.MainWindow;
import com.heatmyfloor.domain.piece.PieceItemReadOnly;
import com.heatmyfloor.domain.piece.PieceReadOnly;
import com.heatmyfloor.domain.piece.PieceRectangulaire;
import com.heatmyfloor.gui.PositionPanel;
import com.heatmyfloor.gui.Canvas;
import com.heatmyfloor.gui.Proprietes;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;
import javax.swing.SwingUtilities;
import com.heatmyfloor.domain.graphe.Chemin;
import com.heatmyfloor.domain.graphe.Fil;
import com.heatmyfloor.domain.items.ElementChauffant;
import com.heatmyfloor.domain.items.MeubleSansDrain;
import com.heatmyfloor.domain.items.Thermostat;
import com.heatmyfloor.domain.items.Zone;
import java.awt.Font;
import java.awt.FontMetrics;

/**
 *
 * @author petit
 */
public class PieceDrawer {

    private final Controller controller;
    private Dimension canvasDimension;
    private Proprietes props;
    private PositionPanel panelPosition;
    private Canvas currentCanvas;

    public PieceDrawer(MainWindow mainWindow) {
        this.controller = mainWindow.controllerActif;
        this.canvasDimension = new Dimension(mainWindow.currentCanvas.getWidth(), mainWindow.currentCanvas.getHeight());
        this.props = mainWindow.props;
        this.panelPosition = mainWindow.panelPosition;
        this.currentCanvas = mainWindow.currentCanvas;
    }

    public void dessiner(Graphics g) {
        PieceReadOnly piece = controller.getPiece();
        if (piece instanceof PieceRectangulaire) {
            dessinerPieceRectangulaire(g);
            dessinerPieceItems(g);

        }
        SwingUtilities.invokeLater(() -> {
            currentCanvas.requestFocusInWindow();
        });
    }

    public void dessinerPieceRectangulaire(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        double largeur = controller.getPiece().getLargeur();
        double hauteur = controller.getPiece().getHauteur();
        var pos = controller.getPiece().getPosition();

        Rectangle2D pieceRectangulaire = new Rectangle2D.Double(pos.getX(), pos.getY(), largeur, hauteur);

        g2.setColor(new Color(255, 232, 200, 120));
        g2.fill(pieceRectangulaire);
        g2.setColor(Color.ORANGE);
        g2.setStroke(new BasicStroke(1.5f));
        g2.draw(pieceRectangulaire);

        Graphe graphe = controller.getPiece().getGraphe();
        if(graphe != null){
            List<Intersection> intersectionsValide = graphe.getListIntersectionsValide((Piece)controller.getPiece());
            for(Intersection intersect : intersectionsValide){
                Point point = intersect.getCoordonees();
                int x = (int) point.getX();
                int y = (int) point.getY();
                int rayon = (int) graphe.getRayonIntersection();
                g2.fillOval(x, y, rayon*2, rayon*2);
            }

        Chemin ch = graphe.getCheminActuel();
        if (ch != null && ch.getParcours().size() > 1) {

            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke(2f));

            double r = graphe.getRayonIntersection();
            double offset = graphe.getRayonIntersection() * 0.8;
            List<Intersection> pts = ch.getParcours();

            for (int i = 1; i < pts.size(); i++) {
                Point a = pts.get(i - 1).getCoordonees();
                Point b = pts.get(i).getCoordonees();

                int ax = (int) (a.getX() + r);
                int ay = (int) (a.getY() + r + offset);
                int bx = (int) (b.getX() + r);
                int by = (int) (b.getY() + r + offset);

                g2.drawLine(ax, ay, bx, by);
            }
        }

        }
        props.afficherProprietesPiece();

      }

    public void dessinerPieceItems(Graphics g) {

        List<PieceItemReadOnly> items = this.controller.getItemsList();
        for (PieceItemReadOnly item : items) {
            Graphics2D g2 = (Graphics2D) g;
            AffineTransform transfParDefaut = g2.getTransform();

            double padding = 7.5;
            Rectangle2D contourSelection = item.getItemForme().getBounds2D();
            Rectangle2D contourAvecPadding = new Rectangle2D.Double(contourSelection.getX() - padding,
                    contourSelection.getY() - padding,
                    contourSelection.getWidth() + 2 * padding,
                    contourSelection.getHeight() + 2 * padding);

            double angleRad = Math.toRadians(item.getAngle());
            g2.rotate(angleRad, item.getItemForme().getCenterX(), item.getItemForme().getCenterY());

            if (item.estSelectionne()) {
                dessinerContourSelectionItem(g2, contourAvecPadding);
            }

            if (currentCanvas.getItemSurvole() == item && !item.estSelectionne()) {
                g2.setColor(Color.BLUE);
                g2.draw(contourAvecPadding);
            }
            if(currentCanvas.getModeRealiste()){
                BufferedImage itemImage = null;
                URL imageUrl = getClass().getResource(item.getImage());
                if (imageUrl != null) {
                    try {
                        itemImage = ImageIO.read(imageUrl);
                    } catch (IOException e) {
                        throw new RuntimeException("Echec du chargement de l'image", e);
                    }
                }

                if (itemImage != null) {
                    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.drawImage(itemImage, (int) item.getPosition().getX(),
                            (int) item.getPosition().getY(),
                            (int) item.getLargeur(),
                            (int) item.getHauteur(),
                            null);
                }
            }else{
                dessinerItemSchema(g2, item);
            }


            if (item instanceof MeubleAvecDrain meuble) {
                dessinerDrains(g2, meuble);
            }
            g2.setTransform(transfParDefaut);

        }

        PieceItemReadOnly item = controller.trouverItemSelectionne();
        if (item != null) {
            props.afficherProprietesItemSelectionne();
            panelPosition.afficherCoordItemSelectionne();
            panelPosition.afficherAngleItemSelectionne();
            if (item instanceof MeubleAvecDrain) {
                props.afficherProprietesDrainSelectionne();
            }
        }

    }

    private void dessinerContourSelectionItem(Graphics2D g2, Rectangle2D contourAvecPadding) {
        g2.setColor(Color.BLUE);
        g2.draw(contourAvecPadding);

        int ovalCoinSize = 15;
        g2.setColor(Color.WHITE);
        g2.fillOval(
                (int) contourAvecPadding.getX() - ovalCoinSize / 2,
                (int) contourAvecPadding.getY() - ovalCoinSize / 2,
                ovalCoinSize, ovalCoinSize
        );
        g2.fillOval(
                (int) (contourAvecPadding.getX() + contourAvecPadding.getWidth()) - ovalCoinSize / 2,
                (int) contourAvecPadding.getY() - ovalCoinSize / 2,
                ovalCoinSize, ovalCoinSize
        );
        g2.fillOval(
                (int) contourAvecPadding.getX() - ovalCoinSize / 2,
                (int) (contourAvecPadding.getY() + contourAvecPadding.getHeight()) - ovalCoinSize / 2,
                ovalCoinSize, ovalCoinSize
        );
        g2.fillOval(
                (int) (contourAvecPadding.getX() + contourAvecPadding.getWidth()) - ovalCoinSize / 2,
                (int) (contourAvecPadding.getY() + contourAvecPadding.getHeight()) - ovalCoinSize / 2,
                ovalCoinSize, ovalCoinSize
        );
    }

    private void dessinerDrains(Graphics2D g2, MeubleAvecDrain meuble) {
        Drain d = meuble.getDrain();
        g2.setColor(new Color(255, 232, 200, 120));
        g2.fill(d.getForme());
        if(currentCanvas.getModeRealiste()){
            g2.setColor(Color.ORANGE);
        }else{
            g2.setColor(new Color(101, 67, 33));
        }
        
        g2.setStroke(new BasicStroke(1f));
        g2.draw(d.getForme());
    }
    
    public void dessinerItemSchema(Graphics2D g, PieceItemReadOnly item) {
        double x = item.getPosition().getX();
        double y = item.getPosition().getY();
        double largeur = item.getLargeur();
        double hauteur = item.getHauteur();

        // Dessiner rectangle
        g.setColor(new Color(101, 67, 33));
        g.drawRect((int)x, (int)y, (int)largeur, (int)hauteur);


        String nom;
        if (item instanceof Thermostat) {
            nom = "THERMOSTAT";
        } else if (item instanceof ElementChauffant) {
            nom = "ELEMENT CHAUFFANT";
        } else if (item instanceof MeubleAvecDrain) {
            nom = ((MeubleAvecDrain) item).getType().name();
        } else if (item instanceof MeubleSansDrain) {
            nom = ((MeubleSansDrain) item).getType().name();
        } else if (item instanceof Zone) {
            nom = "ZONE " + ((Zone) item).getType().name();
        } else {
            nom = "ITEM";
        }

        int tailleFonte = 12;
        Font font = new Font("Arial", Font.PLAIN, tailleFonte);
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();

        while (fm.stringWidth(nom) > largeur - 10 && tailleFonte > 6) {
            tailleFonte--;
            font = new Font("Arial", Font.PLAIN, tailleFonte);
            g.setFont(font);
            fm = g.getFontMetrics();
        }

        int largeurTexte = fm.stringWidth(nom);
        int textX = (int)(x + (largeur - largeurTexte) / 2);
        int textY = (int)(y + fm.getAscent() + 5);

        g.drawString(nom, textX, textY);
    }
}
