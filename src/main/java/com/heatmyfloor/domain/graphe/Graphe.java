/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heatmyfloor.domain.graphe;
import java.util.List;
import java.util.ArrayList;
import com.heatmyfloor.domain.Point;
import com.heatmyfloor.domain.piece.Piece;
import com.heatmyfloor.domain.piece.PieceItem;
import java.io.Serializable;
/**
 *
 * @author petit
 */
public class Graphe implements Serializable{
    private List<Intersection> intersections;
    private Chemin cheminActuel;
    private double distanceIntersection;
    private List<Fil> aretes;
    
    public Graphe(double distanceIntersection){
//    this.cheminActuel = new Chemin(aretes);
    this.distanceIntersection = distanceIntersection;
    this.intersections = new ArrayList<> ();
    
    }
    
    public Graphe(){}
    
    public Graphe generer(double distanceIntersection,Piece p){
        return null;
    }
    
    public boolean estIntersectionValide(Intersection intersection, Piece piece){
        Point position = intersection.getCoordonees();
        List<PieceItem> items = piece.getItemsList();
        
        boolean valide =  false;
        if (piece.contientLePoint(position)){
            for(PieceItem item : items){
                double posXMin = item.getPosition().getX();
                double posXMax = posXMin + item.getLargeur();
                double posYMin = item.getPosition().getY();
                double posYMax = posYMin + item.getHauteur();
                
                boolean respecteContrainteX = posXMin >= position.getX() + item.getDistanceAvecFil() || position.getX() >= posXMax + item.getDistanceAvecFil();
                boolean respecteContrainteY = posYMin >= position.getY() + item.getDistanceAvecFil() || position.getY() >= posYMax + item.getDistanceAvecFil();
                if(!item.contientLePoint(position) && respecteContrainteX && respecteContrainteY){
                    valide = true;
                }
            }
        }
        return valide;
    }
    
    public List<Intersection> ListIntersectionsValide(Piece piece){
        
       List<Intersection> intersectionsValide = new ArrayList<>();
       double xMin = piece.getPosition().getX() + 3;
       double xMax = xMin + piece.getLargeur() - 3;
       double yMin = piece.getPosition().getY() + 3;
       double yMax = yMin + piece.getHauteur() - 3;
       
       while(yMin < yMax){
           Intersection intersect = new Intersection(new Point(xMin, yMin));
           if(this.estIntersectionValide(intersect, piece)){
               intersectionsValide.add(intersect);
           }
           if(xMin < xMax){
               xMin  = xMin + this.distanceIntersection; 
           }else{
               xMin = piece.getPosition().getX() + 3;
               yMin = yMin + this.distanceIntersection;
           }    
           
       }
       return intersectionsValide;
    } 
    
    
    public void ajouterIntersection(Intersection i){}
    
    public void supprimerIntersection(Intersection i){}
    
    public void translater(Point delta){}
    
    public Chemin genererChemin(double longFilTotal){
        return null;
    }
    
    public Chemin modifierChemin(){
        return null;
    }
    
    public Chemin mettreAjoutChemin(){
        return null;
    }
    
  }
