/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heatmyfloor.domain.graphe;
import java.util.List;
import java.util.ArrayList;
import com.heatmyfloor.domain.Point;
import com.heatmyfloor.domain.items.Thermostat;
import com.heatmyfloor.domain.piece.Piece;
import com.heatmyfloor.domain.piece.PieceIrreguliere;
import com.heatmyfloor.domain.piece.PieceItem;
import com.heatmyfloor.domain.piece.PieceRectangulaire;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

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
            if (!(item instanceof Thermostat)){
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
        }}
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
        this.piece = piece;
        return intersectionsValide;
    } 
    
    public void ajouterIntersection(Intersection i){}
    
    public void supprimerIntersection(Intersection i){}
    
    public void translater(Point delta){
        this.offsetTranslation = delta;
    }
    
    
    public Chemin genererChemin(double longFilTotal){
        List <Intersection> listes = new ArrayList <>(intersections);
        List <Point> pointsChemin = new ArrayList <>();
        boolean trouve = false;
        Thermostat thermostat = null;
        Point pointdepart = null;
        double d = distanceIntersection;
        Point prochain = null;
        for(PieceItem pieceItem : piece.getItemsList()){
            if (pieceItem instanceof Thermostat th){
                trouve = true;
                thermostat = th;
                break;
            }
        }
        if (trouve && thermostat!= null ){
            for(Intersection intersection : intersections ){
                Point pts = intersection.getCoordonees();
                if (thermostat.contientLePoint(pts)){
                    pointdepart = pts;
                    pointsChemin.add(pointdepart);
                    listes.removeIf(i ->
                        i.getCoordonees().getX() == pts.getX() &&
                        i.getCoordonees().getY() == pts.getY()
                    );
                    break;
                }
            }
            while (!listes.isEmpty()){
                Point voisinGauche = new Point(pointdepart.getX()-d, pointdepart.getY());
                Point voisinDroite = new Point(pointdepart.getX()+d, pointdepart.getY());
                Point voisinHaut = new Point(pointdepart.getX(), pointdepart.getY()+d);
                Point voisinBas = new Point(pointdepart.getX(), pointdepart.getY()-d);

                Random rand = new Random();


                Point[] points = { voisinGauche, voisinDroite, voisinHaut, voisinBas };

            //  Point choisi;
              /*  do{
                   choisi = points[rand.nextInt(points.length)];
                }
                while(!estIntersectionValide(new Intersection(choisi), piece));*/
              
                    Point choisi = null;
                      int essais = 0;
                      while (essais < 20) {
                          Point cand = points[rand.nextInt(points.length)];
                          if (estIntersectionValide(new Intersection(cand), piece)) {
                              choisi = cand;
                              break;
                          }
                          essais++;
                      }
                      if (choisi == null) break;

                pointsChemin.add(choisi);

                pointdepart = choisi;
                Point depart = pointdepart;
                listes.removeIf(i ->
                    i.getCoordonees().getX() == depart.getX() &&
                    i.getCoordonees().getY() == depart.getY()
                );

            }
            
            List <Fil> arretes = new ArrayList <>();
            for(int i = 1; i < pointsChemin.size(); i++){
                Intersection p1 = new Intersection(pointsChemin.get(i-1));
                Intersection p2 = new Intersection(pointsChemin.get(i));
                Fil fil = new Fil(p1, p2);
                arretes.add(fil);
            }
            
    
            Chemin chemin = new Chemin(arretes);
            cheminActuel = chemin;
          
    }
        return cheminActuel;
    }
    
    
  /*public Chemin genererChemin(double longFilTotal) {

   
    List<Intersection> valides = ListIntersectionsValide((Piece) piece); 
  

    if (valides == null || valides.isEmpty()) return new Chemin();

 
    valides.sort((a, b) -> {
        int cy = Double.compare(a.getCoordonees().getY(), b.getCoordonees().getY());
        if (cy != 0) return cy;
        return Double.compare(a.getCoordonees().getX(), b.getCoordonees().getX());
    });

    Chemin chemin = new Chemin();
    double longueur = 0;

  
    double d = distanceIntersection;

    double eps = d * 0.25;

    List<List<Intersection>> rangees = new ArrayList<>();
    List<Intersection> current = new ArrayList<>();
    double yRef = valides.get(0).getCoordonees().getY();

    for (Intersection it : valides) {
        double y = it.getCoordonees().getY();
        if (Math.abs(y - yRef) <= eps) {
            current.add(it);
        } else {
            rangees.add(current);
            current = new ArrayList<>();
            current.add(it);
            yRef = y;
        }
    }
    rangees.add(current);

    
    boolean gaucheVersDroite = true;
    Intersection prev = null;

    for (List<Intersection> row : rangees) {
        row.sort((a, b) -> Double.compare(a.getCoordonees().getX(), b.getCoordonees().getX()));
        if (!gaucheVersDroite) {
            java.util.Collections.reverse(row);
        }

        for (Intersection it : row) {
            if (prev != null) {
                Fil f = new Fil(prev, it);
                double seg = f.calculerLongueur();
                if (longueur + seg > longFilTotal) {
                    this.cheminActuel = chemin;
                    return chemin;
                }
                chemin.ajouterFil(f);
                longueur += seg;
            }
            prev = it;
        }
        gaucheVersDroite = !gaucheVersDroite;
    }

    this.cheminActuel = chemin; 
    return chemin;
}

private String keyOf(Point p) {
   
    double x = Math.round(p.getX() * 1000.0) / 1000.0;
    double y = Math.round(p.getY() * 1000.0) / 1000.0;
    return x + ";" + y;
}
*/
    public Chemin getCheminActuel() {
        return cheminActuel;
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
