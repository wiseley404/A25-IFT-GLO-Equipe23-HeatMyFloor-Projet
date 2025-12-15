/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heatmyfloor.domain.graphe;
import java.util.List;
import java.util.ArrayList;
import com.heatmyfloor.domain.Point;
import com.heatmyfloor.domain.piece.Piece;
import com.heatmyfloor.domain.piece.PieceIrreguliere;
import com.heatmyfloor.domain.piece.PieceItem;
import com.heatmyfloor.domain.piece.PieceRectangulaire;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
/**
 *
 * @author petit
 */
public class Graphe implements Serializable{
    private Piece piece;
    private List<Intersection> intersections;
    private Chemin cheminActuel;
    private double distanceIntersection;
    private double rayonIntersection;
    private Point offsetTranslation = new Point(0, 0);
    private List<Fil> aretes;
    
    public Graphe(double distanceIntersection, double rayonIntersection, Piece piece){
    //    this.cheminActuel = new Chemin(aretes);
        this.distanceIntersection = distanceIntersection;
        this.rayonIntersection = rayonIntersection;
        this.piece = piece;
        this.intersections = new ArrayList<>();
    
    }
    
    public Graphe(){}
    
    public boolean estIntersectionValide(Intersection intersection, Piece piece){
        Point position = intersection.getCoordonees();
        List<PieceItem> items = piece.getItemsList();

        if (!piece.contientLePoint(position)){
            return false;
        }
        for(PieceItem item : items){
            double distance = item.getDistanceAvecFil();
            double posXMin = item.getPosition().getX() - distance ;
            double posYMin = item.getPosition().getY() - distance ;

            double largeur = item.getLargeur() + 2*distance;
            double hauteur = item.getHauteur() + 2*distance;
            Rectangle2D espaceInterdite = new Rectangle2D.Double(posXMin ,
                                                                 posYMin ,
                                                                 largeur,
                                                                 hauteur
                                        );
            //Avec rotation
            double centreX = item.getCentre().getX();
            double centreY = item.getCentre().getY();
            double angleRad = Math.toRadians(item.getAngle());
            
            AffineTransform transf = new AffineTransform();
            transf.rotate(angleRad, centreX, centreY);
            Shape espaceInterditeTournee = transf.createTransformedShape(espaceInterdite);
            
            Rectangle2D intersectionContour = new Rectangle2D.Double(position.getX(),
                                                                position.getY(),
                                                                2*rayonIntersection,
                                                                2*rayonIntersection
                                              );

            if(espaceInterditeTournee.intersects(intersectionContour)){
                return false;
            }
        }
        return true;
    }
    
    
    public List<Intersection> getListIntersectionsValide(Piece piece){
        
        List<Intersection> intersectionsValide = new ArrayList<>();
        if(this.distanceIntersection <= 0) return intersectionsValide;
        
        double distanceFilAvecMur = piece.getMurs().getFirst().getDistanceAvecFil();
        
        double xMin;
        double xMax;
        double yMin;
        double yMax;
        if(piece instanceof PieceRectangulaire){
            xMin = piece.getPosition().getX() + distanceFilAvecMur + offsetTranslation.getX();
            xMax = piece.getPosition().getX() + piece.getLargeur() - distanceFilAvecMur - (2*this.rayonIntersection);
            yMin = piece.getPosition().getY() + distanceFilAvecMur + offsetTranslation.getY();
            yMax = piece.getPosition().getY() + piece.getHauteur() - distanceFilAvecMur - (2*this.rayonIntersection);
        }else if(piece instanceof PieceIrreguliere){
            xMin = piece.getPosition().getX() + distanceFilAvecMur;
            xMax = piece.getPosition().getX() + piece.getLargeur() - distanceFilAvecMur;
            yMin = piece.getPosition().getY() + distanceFilAvecMur;
            yMax = piece.getPosition().getY() + piece.getHauteur() - distanceFilAvecMur;
        }else{
            return intersectionsValide;
        }
        
        double distance = this.distanceIntersection + rayonIntersection;
        for(double y = yMin; y < yMax; y+= distance){
            for(double x = xMin; x < xMax; x += distance){
                 Intersection intersect = new Intersection(new Point(x , y ));
                 if(this.estIntersectionValide(intersect, piece)){
                     intersectionsValide.add(intersect);
                 }
            }       
        }
        this.intersections = intersectionsValide;
        return intersectionsValide;
    } 

    
    public void ajouterIntersection(Intersection i){}
    
    public void supprimerIntersection(Intersection i){}
    
    public void translater(Point delta){
        this.offsetTranslation = delta;
    }
    
    
    public Chemin genererChemin(double longFilTotal){
        return null;
    }
    
    public Chemin modifierChemin(){
        return null;
    }
    
    public Chemin mettreAjoutChemin(){
        return null;
    }
    
    public double getRayonIntersection(){
        return this.rayonIntersection;
    }
    
  }
