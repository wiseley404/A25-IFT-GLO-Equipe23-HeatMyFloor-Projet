/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heatmyfloor.infrastructure.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.heatmyfloor.domain.Point;
import com.heatmyfloor.domain.piece.Piece;
import com.heatmyfloor.domain.piece.PieceIrreguliere;
import com.heatmyfloor.domain.piece.PieceItem;
import com.heatmyfloor.domain.ports.PieceStockage;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 * @author petit
 */
public class PieceFichierStockage implements PieceStockage {

    private final ObjectMapper mapper;

    public PieceFichierStockage() {
        this.mapper = new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT)

            .findAndRegisterModules(); 
    }

    @Override
    public Piece ouvrirFichier(Path fichierCharge) {
        Piece piece = null;
        try {
            
            FileInputStream fi = new FileInputStream(fichierCharge.toFile());
            ObjectInputStream in =  new ObjectInputStream(fi);
            
            piece = (Piece) in.readObject();
            in.close();
            fi.close();

            return piece;

        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Impossible de lire le fichier : " + fichierCharge, ex);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Impossible de lire le fichier : " + fichierCharge, ex);
        }
    }

    @Override
    public void saveFichier(Piece piece, Path fichier) {

        try {
            
            FileOutputStream fo = new FileOutputStream(fichier.toFile());
            ObjectOutputStream out = new ObjectOutputStream(fo);
            out.writeObject(piece);
            out.close();
            fo.close();

        } catch (IOException ex) {
            throw new RuntimeException("Erreur lors de la sauvegarde de la pièce : " + fichier, ex);
        }

    }

    @Override
    public void exporterFichierPng(Piece piece, Path fichier, String nomProjet) {
        try {
            int width = 1600;
            int height = 1200;

            BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = img.createGraphics();

            // Joli rendu
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            // Fond blanc
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, width, height);

            g.setFont(new Font("Arial", Font.BOLD, 28));
            g.setColor(Color.BLACK);
            g.drawString("Projet : " + nomProjet, 40, 50);

            g.setFont(new Font("Arial", Font.PLAIN, 22));

            if (piece instanceof PieceIrreguliere) {
                double[] box = calculerRectangleEnglobantPiece(((PieceIrreguliere) piece).getSommets());
                double largeur = box[2] - box[0];
                double hauteur = box[3] - box[1];

                g.drawString("Dimensions : " + (int) largeur + " x " + (int) hauteur + " mm", 40, 90);
            } else {
                g.drawString("Dimensions : " + (int) piece.getLargeur()
                        + " x " + (int) piece.getHauteur() + " mm", 40, 90);
            }

            dessinerPiece(g, piece);

            dessinerItems(g, piece);

            g.dispose();

            Files.createDirectories(fichier.getParent());
            ImageIO.write(img, "png", fichier.toFile());

        } catch (IOException ex) {
            throw new RuntimeException("Erreur lors de l'export PNG : " + fichier, ex);
        }
    }

    private double[] calculerRectangleEnglobantPiece(List<Point> sommets) {
        double minX = Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        double maxY = Double.MIN_VALUE;

        for (Point p : sommets) {
            minX = Math.min(minX, p.getX());
            minY = Math.min(minY, p.getY());
            maxX = Math.max(maxX, p.getX());
            maxY = Math.max(maxY, p.getY());
        }

        return new double[]{minX, minY, maxX, maxY};
    }

    private void dessinerItems(Graphics2D g, Piece piece) {
        for (PieceItem item : piece.getItemsList()) {

            try {

                String chemin = item.getImage();

                InputStream stream = getClass().getResourceAsStream(chemin);

                if (stream == null) {
                    System.err.println("Image introuvable dans les ressources : " + chemin);
                    continue;
                }

                BufferedImage img = ImageIO.read(stream);
                // Charger l’image associée à l’item

                if (img == null) {
                    continue;
                }

                double x = item.getPosition().getX();
                double y = item.getPosition().getY();

                double w = item.getLargeur();
                double h = item.getHauteur();

                double angle = Math.toRadians(item.getAngle());

                AffineTransform old = g.getTransform();
                AffineTransform tr = new AffineTransform();

                tr.translate(x, y);
                tr.rotate(angle, w / 2.0, h / 2.0);
                tr.scale(w / img.getWidth(), h / img.getHeight());

                g.setTransform(tr);
                g.drawImage(img, 0, 0, null);

                g.setTransform(old);

            } catch (IOException e) {
                System.err.println("Impossible de charger l'image pour l'item : " + item);
            }
        }
    }

    private void dessinerPiece(Graphics2D g, Piece piece) {

        if (piece instanceof PieceIrreguliere) {
            Path2D poly = new Path2D.Double();
            PieceIrreguliere irr = (PieceIrreguliere) piece;
            Point p0 = irr.getSommets().get(0);
            poly.moveTo(p0.getX(), p0.getY());

            for (int i = 1; i < irr.getSommets().size(); i++) {
                Point p = irr.getSommets().get(i);
                poly.lineTo(p.getX(), p.getY());
            }
            poly.closePath();

            g.setColor(new Color(255, 240, 200));
            g.fill(poly);

            g.setColor(Color.BLACK);
            g.setStroke(new BasicStroke(3));
            g.draw(poly);

        } else {
            double x = piece.getPosition().getX();
            double y = piece.getPosition().getY();
            double w = piece.getLargeur();
            double h = piece.getHauteur();

            g.setColor(new Color(255, 240, 200));
            g.fillRect((int) x, (int) y, (int) w, (int) h);

            g.setColor(Color.BLACK);
            g.setStroke(new BasicStroke(3));
            g.drawRect((int) x, (int) y, (int) w, (int) h);
        }
    }

}
