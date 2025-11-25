/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heatmyfloor.domain.items;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.UUID;
import com.heatmyfloor.domain.Point;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;
/**
 *
 * @author petit
 */
public class Drain implements DrainReadOnly, Serializable {
    
    //Attributs
    
    private UUID id;
    private double diametre;
    private Point position;
    private final double distanceAvecFil;
    private boolean estSelectionne;
    
    
    //Constructeur
    
    public Drain(double diam, Point pos){
        this.id = UUID.randomUUID();
        this.diametre = diam;
        this.position = pos;
        this.distanceAvecFil = 0.0;
        this.estSelectionne = true;
    }
    
    
    //Méthodes
    
    @Override
    public void translater(Point delta) {
        this.position = new Point(
            this.position.getX() + delta.getX(),
            this.position.getY() + delta.getY()
        );
    }

    
    public void translater(double facteurX, double facteurY){
        this.position = new Point(
        this.getPosition().getX()*facteurX,
        this.getPosition().getY() * facteurY
        );
    }
     
    public void redimensionner(Point delta){
        this.setDiametre(diametre + delta.getX());
    }
    
    public void redimensionner(double facteur){
        this.setDiametre(diametre * facteur);
    }
    
    @Override
    @JsonIgnore
    public Ellipse2D getForme(){
        
       Ellipse2D drainForme = new Ellipse2D.Double(
               this.getPosition().getX(), this.getPosition().getY(),
               this.diametre, this.diametre);
       return drainForme;
    }
    
    @Override
    public boolean contientLePoint(Point pos){
        return getForme().contains(pos.getX(), pos.getY());
    }
    
    public void changerStatutSelection(){
    this.estSelectionne = !this.estSelectionne;}

    @Override
    public double getDiametre() {
        return this.diametre;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public Point getPosition() {
        return this.position;
    }

    @Override
    public double getDistanceAvecFil() {
        return this.distanceAvecFil;
    }
    
    @Override
    public boolean estSelectionne(){
        return this.estSelectionne;
    }
    

    public void setDiametre(double nouvDiametre){   
        this.diametre=nouvDiametre;
    }
    
    public void setPosition(Point nouvPosition){
       this.position = nouvPosition; 
    }
    
    public void setEstSelectionne(boolean StatutSelection){
         this.estSelectionne = StatutSelection;
    
    }
    
    


    
    
    
}
