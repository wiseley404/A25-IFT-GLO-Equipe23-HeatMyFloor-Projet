package com.heatmyfloor.domain.piece;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.heatmyfloor.domain.Point;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.List;
import java.awt.Shape;
/**
 *
 * @author petit
 */
public class PieceRectangulaire extends Piece {
    
     public PieceRectangulaire() {
        super(); 
    }

    public PieceRectangulaire(double largeur, double hauteur){   
        super(largeur, hauteur, creerMurs(largeur, hauteur));
    }
    
    public static List<Mur> creerMurs(double largeur, double hauteur){
        Rectangle2D pieceContour = new Rectangle2D.Double(
                                    0, 0, largeur, hauteur
                                   ).getBounds2D();
        Point a = new Point(pieceContour.getX(), pieceContour.getY());
        Point b = new Point(pieceContour.getX()+ largeur, pieceContour.getY());
        Point c = new Point(pieceContour.getX(), pieceContour.getY() + hauteur);
        Point d = new Point(pieceContour.getX() + largeur, pieceContour.getY() + hauteur);
        
        return Arrays.asList(
                new Mur(a, b),
                new Mur(a, c),
                new Mur(c, d),
                new Mur(d, b)
              );        
    }
     @JsonIgnore
    public Rectangle2D getForme(){
        Rectangle2D pieceForme = new Rectangle2D.Double(
                                    this.getPosition().getX(),
                                    this.getPosition().getY(),
                                    this.getLargeur(),
                                    this.getHauteur());
        return pieceForme;
    }
    
    @Override
    public Point getCentre(){
        double x = getForme().getCenterX();
        double y = getForme().getCenterY();
        return new Point(x, y);
    }
    
    @Override
    public boolean contientLePoint(Point position){
        return getForme().contains(position.getX(), position.getY());
    }
    
    @Override 
    public boolean contientLaForme(Shape itemRotation){
        return this.getForme().contains(itemRotation.getBounds2D());
    }

}
