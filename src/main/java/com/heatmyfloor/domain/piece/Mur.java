package com.heatmyfloor.domain.piece;

import com.heatmyfloor.domain.Point;
import com.heatmyfloor.domain.Util;
import java.awt.geom.Line2D;
import java.util.UUID;
import java.io.Serializable;

/**
 *
 * @author petit
 */
public class Mur implements Serializable, MurReadOnly {

    private final UUID id;
    private Point debut, fin;
    private final double distanceAvecFil;

    public Mur(Point debut, Point fin) {
        this.fin = fin;
        this.debut = debut;
        this.id = UUID.randomUUID();
        //3" en pixels
        this.distanceAvecFil = Util.enPixels(3);
    }

    public Mur() {
        this.id = UUID.randomUUID();
        this.distanceAvecFil = Util.enPixels(3);
    }

    public void positionnerItem(PieceItem item, Piece piece) {

        double largeur = item.getLargeur();
        double hauteur = item.getHauteur();

        // Points du mur
        Point p1 = this.getDebut();
        Point p2 = this.getFin();

        // Direction du mur
        double dx = p2.getX() - p1.getX();
        double dy = p2.getY() - p1.getY();
        double len = Math.sqrt(dx * dx + dy * dy);
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
        double centreX = centreMur.getX() + nx * (hauteur / 2.0);
        double centreY = centreMur.getY() + ny * (hauteur / 2.0);

        // Conversion du centre en coin haut-gauche
        double px = centreX - largeur / 2.0;
        double py = centreY - hauteur / 2.0;

        item.setPosition(new Point(px, py));
        item.setAngle(this.getAngle());
    }
    
    
    public Point projetterPositionItemSurMur(Point nouvPosition, PieceItem item, Piece piece){
        double largeur = item.getLargeur();
        double hauteur = item.getHauteur();
        
        double centreX = nouvPosition.getX() + largeur / 2;
        double centreY = nouvPosition.getY() + hauteur / 2;
        
        // Mur
        Point p1 = this.debut;
        Point p2 = this.fin;
        
        // Directions
        double dx = p2.getX() - p1.getX();
        double dy = p2.getY() - p1.getY();
        double len = Math.sqrt(dx*dx + dy* dy);
        dx /= len;
        dy /= len;
        
        // Projection
        double vx = centreX - p1.getX();
        double vy = centreY - p1.getY();
        double proj = vx*dx + vy*dy;
        
        double tol = largeur*2;
        proj = Math.max(-tol, Math.min(len + tol, proj));
        
        double projX = p1.getX() + proj*dx;
        double projY = p1.getY() + proj*dy;
        
        
        // Normale
        double nx = -dy;
        double ny = dx;
            System.out.println("Mur de (" + p1 + ") à (" + p2 + ")");
    System.out.println("Direction mur: dx=" + dx + ", dy=" + dy);
    System.out.println("Projection: " + proj + " / longueur: " + len);
    System.out.println("Point projeté: (" + projX + ", " + projY + ")");
    System.out.println("Normale: nx=" + nx + ", ny=" + ny);
        // Test direction
        double px = projX + nx*5;
        double py = projY + ny*5;
        if(!piece.contientLePoint(new Point(px, py))){
            nx = -nx;
            ny = -ny;
        }
        
        // Centre Item apres projection
        double nouvCentreX = projX + nx*(hauteur / 2);
        double nouvCentreY = projY + ny*(hauteur / 2);
        
        // Nouvelle position sur Mur
        return new Point(
                nouvCentreX - largeur / 2,
                nouvCentreY - hauteur / 2
        );
        
    }
    
    
    public void setPosition(Point debut, Point fin) {
        this.debut = debut;
        this.fin = fin;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public Point getDebut() {
        return debut;
    }

    @Override
    public Point getFin() {
        return fin;
    }

    @Override
    public double getDistanceAvecFil() {
        return this.distanceAvecFil;
    }

    @Override
    public double getAngle() {
        return Math.toDegrees(Math.atan2(
                fin.getY() - debut.getY(),
                fin.getX() - debut.getX())
        );
    }

    public Line2D getForme() {
        Line2D murForme = new Line2D.Double(debut.getX(), debut.getY(),
                fin.getX(), fin.getY());
        return murForme;
    }

    @Override
    public Point getCentre() {
        return new Point((debut.getX() + fin.getX()) / 2,
                (debut.getY() + fin.getY()) / 2);

    }

    @Override
    public double getLongueur() {
        double dx = fin.getX() - debut.getX();
        double dy = fin.getY() - debut.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

}
