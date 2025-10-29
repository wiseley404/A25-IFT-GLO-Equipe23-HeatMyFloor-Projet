package com.heatmyfloor.domain.piece;

import com.heatmyfloor.domain.Point;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author petit
 */
public class PieceRectangulaire extends Piece {
    private double longueur;
    private double largeur;
    
    public PieceRectangulaire(double longueur, double largeur){   
        super(creerMurs(longueur, largeur));
        this.longueur = longueur;
        this.largeur = largeur;
    }
    
    public static List<Mur> creerMurs(double longueur, double largeur){
        Rectangle2D pieceContour = new Rectangle2D.Double(
                                    0, 0, longueur, largeur
                                   ).getBounds2D();
//        Point a = new Point(pieceContour.getX(), pieceContour.getY());
//        Point b = new Point(pieceContour.getX()+ largeur, pieceContour.getY());
//        Point c = new Point(pieceContour.getX(), pieceContour.getY() + longueur);
//        Point d = new Point(pieceContour.getX() + largeur, pieceContour.getY() + longueur);
        
        return Arrays.asList(
//                new Mur(a, b),
//                new Mur(a, c),
//                new Mur(c, d),
//                new Mur(d, b)
              );        
    }
    
    
    public double getLongueur(){
        throw new UnsupportedOperationException("Méthode non implémentée !");
    }
    
    
    public double getLargeur(){
        throw new UnsupportedOperationException("Méthode non implémentée !");
    } 
}
