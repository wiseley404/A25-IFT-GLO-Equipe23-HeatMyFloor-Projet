/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heatmyfloor.domain.items;

import java.util.UUID;
import com.heatmyfloor.domain.piece.Point;
/**
 *
 * @author petit
 */
public class Drain implements DrainReadOnly {
    
    //Attributs
    
    private UUID id;
    private double diametre;
    private Point position;
    private double distanceAvecFil;
    private boolean estSelectionne;
    
    
    //Constructeur
    
    public Drain(double diam, Point pos){
        this.id = UUID.randomUUID();
        this.diametre = diam;
        this.position = pos;
        this.distanceAvecFil = 0.0;
        this.estSelectionne = false;
    }
    
    
    //Méthodes
    
    public void deplacer(Point nouvPos){}
    
    public void redimensionner(double nouvDiam){}
    
    public boolean estSelectionne(){
        
        throw new UnsupportedOperationException("estSelectionne non implémentée");
    }
    
    public boolean contientLePoint(Point pos){
        
        throw new UnsupportedOperationException("contientLePoint non implémentée");
    }
    
    public void changerStatutSelection(){}
    
    
    
}
