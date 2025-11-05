/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heatmyfloor.domain.graphe;
import java.util.List;
import java.util.ArrayList;
import com.heatmyfloor.domain.Point;
import com.heatmyfloor.domain.piece.Piece;

/**
 *
 * @author petit
 */
public class Graphe {
    private List<Intersection> intersections;
    private Chemin cheminActuel;
    private double distanceIntersection;
    private List<Fil> aretes;
    
    public Graphe(){
    this.cheminActuel = new Chemin(aretes);
    this.distanceIntersection = 0.0;
    this.intersections = new ArrayList<Intersection> ();
    
    }
    
    public Graphe generer(double distanceIntersection,Piece p){
        return null;
    }
    
    public boolean estIntersectionValide(Intersection i, Piece p){
        return true ;
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
