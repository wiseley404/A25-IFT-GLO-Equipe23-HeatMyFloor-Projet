/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heatmyfloor.domain.graphe;
import java.util.List;
import java.util.ArrayList;
import com.heatmyfloor.domain.Point;
import com.heatmyfloor.domain.items.Drain;
import com.heatmyfloor.domain.items.MeubleAvecDrain;
import com.heatmyfloor.domain.items.Thermostat;
import com.heatmyfloor.domain.items.Zone;
import com.heatmyfloor.domain.items.Zone.TypeZone;
import com.heatmyfloor.domain.piece.Mur;
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
    private static final long serialVersionUID = 4523369905546364818L;
    private Piece piece;
    private List<Intersection> intersections;
    private Chemin cheminActuel;
    public double distanceIntersection;
    private double rayonIntersection;
    private Point offsetTranslation = new Point(0, 0);
    private List<Fil> aretes;
    private transient GenerateurChemin generateurChemin;
    
    public Graphe(double distanceIntersection, double rayonIntersection, Piece piece){
        this.distanceIntersection = distanceIntersection;
        this.rayonIntersection = rayonIntersection;
        this.piece = piece;
        this.intersections = new ArrayList<>();
        this.generateurChemin = new GenerateurChemin(this);
    }

    public Graphe(){}
    
    public boolean estIntersectionValide(Intersection intersection, Piece piece){
        Point position = intersection.getCoordonees();
        List<PieceItem> items = piece.getItemsList();

        if (!piece.contientLePoint(position)){
            return false;
        }
        
        double distanceMinMur = piece.getMurs().getFirst().getDistanceAvecFil();
        for (Mur mur : piece.getMurs()) {
            double dist = calculerDistancePointAvecMur(position, mur);
            if (dist < distanceMinMur) {
                return false;
            }
        }
        
        for(PieceItem item : items){
            if (!(item instanceof Thermostat) && !(item instanceof Zone zone && zone.getType() == TypeZone.TAMPON)){
                if (item instanceof MeubleAvecDrain) {
                    MeubleAvecDrain meubleAvecDrain = (MeubleAvecDrain) item;
                    Drain drain = meubleAvecDrain.getDrain();
                    if(estTropProcheDuDrain(drain, position)){
                        return false;
                    }
                }
                
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
        }
        return true;
    }
   
    private boolean estTropProcheDuDrain(Drain drain, Point position) {
        if (drain == null) {
            return false;
        }
        double distanceDrainFil = drain.getDistanceAvecFil();

        double interCentreX = position.getX() + this.rayonIntersection;
        double interCentreY = position.getY() + this.rayonIntersection;
        double drainCentreX = drain.getPosition().getX() + drain.getDiametre() / 2.0;
        double drainCentreY = drain.getPosition().getY() + drain.getDiametre() / 2.0;

        double distanceCentres = Math.sqrt(
            Math.pow(interCentreX - drainCentreX, 2) + 
            Math.pow(interCentreY - drainCentreY, 2)
        );
        double rayonDrain = drain.getDiametre() / 2.0;
        double distanceBords = distanceCentres - rayonDrain - this.rayonIntersection;

        return distanceBords < distanceDrainFil;
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
            xMin = piece.getPosition().getX() + offsetTranslation.getX();
            xMax = piece.getPosition().getX() + piece.getLargeur();
            yMin = piece.getPosition().getY() + offsetTranslation.getY();
            yMax = piece.getPosition().getY() + piece.getHauteur();
        }else{
            return intersectionsValide;
        }
        
        double distance = this.distanceIntersection + rayonIntersection;
        for(double y = yMin; y < yMax; y+= distance){
            for(double x = xMin; x < xMax; x += distance){
                 Intersection intersect = new Intersection(new Point(x , y ));
                 if(piece instanceof PieceIrreguliere){
                     if(estIntersectionValideIrreguliere(intersect, (PieceIrreguliere) piece)){
                         intersectionsValide.add(intersect);
                     }
                 }else{
                    if(this.estIntersectionValide(intersect, piece)){
                        intersectionsValide.add(intersect);
                    }
                 }

            }       
        }
        this.intersections = intersectionsValide;
        this.piece = piece;
        return intersectionsValide;
    } 

    private boolean estIntersectionValideIrreguliere(Intersection intersect, 
                                                      PieceIrreguliere piece) {
        
            Point p = intersect.getCoordonees();
            double distanceMinMur = piece.getMurs().getFirst().getDistanceAvecFil();

            if (!piece.contientLePoint(p)) {
                return false;
            }
            for (Mur mur : piece.getMurs()) {
                double distanceAuMur = calculerDistancePointAvecMur(p, mur);
                if (distanceAuMur < distanceMinMur) {
                    return false;
                }
            }
            return estIntersectionValide(intersect, piece);
        }

    
    private double calculerDistancePointAvecMur(Point p, Mur mur) {
        double centreX = p.getX() + this.rayonIntersection;
        double centreY = p.getY() + this.rayonIntersection;

        double x1 = mur.getDebut().getX();
        double y1 = mur.getDebut().getY();
        double x2 = mur.getFin().getX();
        double y2 = mur.getFin().getY();

        double dx = x2 - x1;
        double dy = y2 - y1;
        double longueurCarree = dx * dx + dy * dy;

        if (longueurCarree == 0) {
            return Math.sqrt((centreX - x1) * (centreX - x1) + (centreY - y1) * (centreY - y1)) 
               - this.rayonIntersection;
        }
        
        double t = ((centreX - x1) * dx + (centreY - y1) * dy) / longueurCarree;
        t = Math.max(0, Math.min(1, t));
        double projX = x1 + t * dx;
        double projY = y1 + t * dy;
        double distanceCentre = Math.sqrt((centreX - projX) * (centreX - projX) + 
                                      (centreY - projY) * (centreY - projY));
        return distanceCentre - this.rayonIntersection;
    }

    
    public void ajouterIntersection(Intersection i){}
    
    public void supprimerIntersection(Intersection i){}
    
    public void translater(Point delta){
        Point nouvelOffset = new Point(
           this.offsetTranslation.getX() + delta.getX(),
           this.offsetTranslation.getY() + delta.getY()
        );
        this.offsetTranslation = nouvelOffset;
        if (piece != null) {
            getListIntersectionsValide(piece);
        }
    }
    
    public Chemin genererChemin(double longueurFilMax, double distFil) {
        List<Intersection> intersectionsValides = getListIntersectionsValide(piece);

        if (intersectionsValides.isEmpty()) {
            return null;
        }
        Chemin chemin = generateurChemin.genererChemin(longueurFilMax, distFil, piece);
        if (chemin == null) {
            return null;
        }
        if (!this.generateurChemin.validerChemin(chemin)) {
            return null;
        }
        this.cheminActuel = chemin;
        return chemin;
    }
    
    
    public boolean modifierDirectionFil(int indexIntersection, Intersection nouvelleIntersection) {
        if (cheminActuel == null) {
            return false;
        }
        boolean reussi = generateurChemin.modifierDirection(
                cheminActuel, indexIntersection, nouvelleIntersection, piece);
        if (!reussi) {
            return false;
        }
        if (!this.generateurChemin.validerChemin(cheminActuel)) {
            return false;
        }
        return true;
    }
    

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
