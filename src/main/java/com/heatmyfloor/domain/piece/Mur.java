package com.heatmyfloor.domain.piece;

import com.heatmyfloor.domain.Point;
import java.awt.geom.Line2D;
import java.util.UUID;
import java.io.Serializable;
/**
 *
 * @author petit
 */
public class Mur implements Serializable, MurReadOnly{
    private final UUID id;
    private Point debut, fin;
    private final double distanceAvecFil;
    
    public Mur(Point debut, Point fin){
        this.fin = fin;
        this.debut = debut;
        this.id = UUID.randomUUID();
        this.distanceAvecFil = 3;
    }
    
    public Mur(){
        this.id = UUID.randomUUID();
        this.distanceAvecFil = 3;
    }
    
    public void positionnerItem(PieceItem item, Piece piece){

        double largeur = item.getLargeur();
        double hauteur = item.getHauteur();

        // Points du mur
        Point p1 = this.getDebut();
        Point p2 = this.getFin();

        // Direction du mur
        double dx = p2.getX() - p1.getX();
        double dy = p2.getY() - p1.getY();
        double len = Math.sqrt(dx*dx + dy*dy);
        dx /= len;
        dy /= len;

        // Normale pour positionner de facon perpendiculaire au mur
        double nx = -dy;
        double ny = dx;

        // Test pour voir si la normale va vers l'interieur de la piece
        Point centreMur = this.getCentre();
        double pX = centreMur.getX() + nx * 5;
        double pY = centreMur.getY() + ny * 5;

        if (!piece.contientLePoint(new Point(pX, pY))) {
            nx = -nx;
            ny = -ny;
        }
        
        //Centre Thermostat ou A.Chauffant
        double centreX = centreMur.getX() + nx * (hauteur/2.0);
        double centreY = centreMur.getY() + ny * (hauteur/2.0);

        // Conversion du centre en coin haut-gauche
        double px = centreX - largeur/2.0;
        double py = centreY - hauteur/2.0;

        item.setPosition(new Point(px, py));
        item.setAngle(this.getAngle());
    }
    
    public void setPosition(Point debut, Point fin){
        this.debut = debut;
        this.fin = fin;
    }
    
    @Override
    public UUID getId(){
        return this.id;
    }
    
    @Override
    public Point getDebut(){
        return debut;
    }
    
    @Override
    public Point getFin(){
        return fin;
    }
    
    @Override
    public double getDistanceAvecFil(){
        return this.distanceAvecFil;
    }
    
    @Override
    public double getAngle(){
        return Math.toDegrees(Math.atan2(
                                          fin.getY() - debut.getY(),
                                          fin.getX() - debut.getX())
                                        );
    }
    
    public Line2D getForme(){
        Line2D murForme = new Line2D.Double(debut.getX(), debut.getY(), 
                                            fin.getX(), fin.getY());
        return murForme;
    }
    
    @Override
    public Point getCentre(){
        return new Point((debut.getX() + fin.getX())/2,
                         (debut.getY() + fin.getY())/2 );
     
    }

    
}
