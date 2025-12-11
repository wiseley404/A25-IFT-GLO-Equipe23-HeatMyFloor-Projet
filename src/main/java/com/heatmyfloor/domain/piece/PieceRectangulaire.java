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
        super(largeur, hauteur, null);
        this.setMurs(creerMurs(largeur, hauteur, this.getPosition()));
    }
    
    public static List<Mur> creerMurs(double largeur, double hauteur, Point position){
        Rectangle2D pieceContour = new Rectangle2D.Double(
                                    position.getX(), position.getY(), largeur, hauteur
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
    
    @Override
    public void mettreAJourMurs(){
        double x = this.getPosition().getX();
        double y = this.getPosition().getY();
        double larg = this.getLargeur();
        double haut = this.getHauteur();
        
        Point a = new Point(x, y);
        Point b = new Point(x + larg, y);
        Point c = new Point(x, y + haut);
        Point d = new Point(x + larg, y + haut);
        
        this.getMurs().get(0).setPosition(a, b);
        this.getMurs().get(1).setPosition(a, c);
        this.getMurs().get(2).setPosition(c, d);
        this.getMurs().get(3).setPosition(d, b);
    }
    
    
    @Override
    public void setPosition(Point position){
        super.setPosition(position);
        this.setMurs(creerMurs(this.getLargeur(), this.getHauteur(), this.getPosition()));
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

    @Override
    public TypePiece getType() {
        return TypePiece.RECTANGULAIRE;
    }
    
    @Override
    public List<Point> getSommets(){
        Point a = new Point(this.getPosition().getX(), this.getPosition().getY());
        Point b = new Point(this.getPosition().getX()+ this.getLargeur(), this.getPosition().getY());
        Point c = new Point(this.getPosition().getX(), this.getPosition().getY() + this.getHauteur());
        Point d = new Point(this.getPosition().getX() + getLargeur(), this.getPosition().getY() + getHauteur());
    return Arrays.asList(a, b, c, d);
    }
}
