/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heatmyfloor.domain.items;

import java.util.UUID;
import com.heatmyfloor.domain.Point;
import com.heatmyfloor.domain.Util;
import com.heatmyfloor.domain.piece.PieceItem;
import java.awt.geom.Rectangle2D;


/**
 *
 * @author petit
 */
public class MeubleAvecDrain extends PieceItem {
    
    //Attributs  
    private Drain drain;
    private final double distanceAvecFil;
    private TypeAvecDrain type;
    
    //Constructeur   
    public MeubleAvecDrain(double largeur, double hauteur, Point pos, TypeAvecDrain type){
        super(largeur, hauteur, pos, type.getImage());
        this.drain = new Drain(20.0,
                new Point(pos.getX() + (largeur/2) - 10,
                          pos.getY() + (hauteur/2) - 10
                )
        );    
        this.distanceAvecFil = Util.enPixels(3.0);
        this.type = type;
    }
    
    
    //Méthodes 
    @Override
    public void translater(double facteurX, double facteurY) {
        super.translater(facteurX, facteurY);
        // repositionner drain pour qu’il reste centré
        double nouvX = this.getItemForme().getCenterX() - drain.getDiametre() / 2;
        double nouvY = this.getItemForme().getCenterY() - drain.getDiametre() / 2;
        repositionnerDrain(new Point(nouvX, nouvY)); 
    }
    
    @Override
    public void translater(Point delta) {
        super.translater(delta);
        // repositionner drain pour qu’il reste centré
        double nouvX = this.getItemForme().getCenterX() - drain.getDiametre() / 2;
        double nouvY = this.getItemForme().getCenterY() - drain.getDiametre() / 2;
        repositionnerDrain(new Point(nouvX, nouvY)); 
    }
    
    @Override
    public void setPosition(Point nouvPosition){
        super.setPosition(nouvPosition);
        // repositionner drain pour qu’il reste centré
        double nouvX = this.getItemForme().getCenterX() - drain.getDiametre() / 2;
        double nouvY = this.getItemForme().getCenterY() - drain.getDiametre() / 2;
        repositionnerDrain(new Point(nouvX, nouvY)); 
    }
    
    public Drain trouverDrain(UUID idDrain){    
        throw new UnsupportedOperationException("trouverDrain non implémentée");
    }
    
    
    public void repositionnerDrain(Point nouvPos){ 
        if(!estPositionDrainValide(nouvPos)){
            throw new IllegalArgumentException("Déplacement refusé: Position Drain Invalide");
        }
        this.drain.setPosition(nouvPos);
    }   

    public void deplacerDrain(double facteurX, double facteurY){
        double newX = drain.getPosition().getX() * facteurX;
        double newY = drain.getPosition().getY() * facteurY;
        Point deplacement = new Point(newX, newY);
        if(!estPositionDrainValide(deplacement)){
            throw new IllegalArgumentException("Déplacement refusé: Position Drain Invalide");
        }
        this.drain.translater(facteurX, facteurY);
    }
    
   
    public void deplacerDrain(Point delta) {
        double newX = drain.getPosition().getX() + delta.getX();
        double newY = drain.getPosition().getY() + delta.getY();
        Point deplacement = new Point(newX, newY);
        
        if(estPositionDrainValide(deplacement)){
            drain.setPosition(deplacement);
        } 
    }

   
    public boolean estPositionDrainValide(Point pos){
        double posX = this.getPosition().getX() - drain.getDiametre();
        double posY = this.getPosition().getY() - drain.getDiametre();
        double largeur = this.getLargeur() + drain.getDiametre();
        double hauteur = this.getHauteur() + drain.getDiametre();
        
        Rectangle2D espaceDrain = new Rectangle2D.Double(posX, posY, largeur, hauteur);
        return espaceDrain.contains(pos.getX(), pos.getY());
    }
    
    public boolean estDrainPresent(UUID idDrain){
        throw new UnsupportedOperationException("estDrainPresent non implémentée");
    }
    
    public void redimensionnerDrain(double nouvDiametre){
        if(nouvDiametre >= this.getHauteur() || nouvDiametre >= this.getLargeur()){
            throw new IllegalArgumentException("Le diamètre du drain depasse les dimensions du meuble");
        }
        drain.setDiametre(nouvDiametre);
         // repositionner drain pour qu’il reste centré
        double nouvX = this.getItemForme().getCenterX() - drain.getDiametre() / 2;
        double nouvY = this.getItemForme().getCenterY() - drain.getDiametre() / 2;
        drain.setPosition(new Point(nouvX, nouvY)); 
    }
    
    @Override
    public void redimensionner(double facteurX, double facteurY) {
        super.redimensionner(facteurX, facteurY);
        drain.redimensionner(facteurY);
        // repositionner drain pour qu’il reste centré
        double nouvX = this.getItemForme().getCenterX() - drain.getDiametre() / 2;
        double nouvY = this.getItemForme().getCenterY() - drain.getDiametre() / 2;
        drain.setPosition(new Point(nouvX, nouvY));     
    }
    
     @Override
     public void setDimension(double nouvLarg, double nouvHaut) {
        super.setDimension(nouvLarg, nouvHaut);
         // repositionner drain pour qu’il reste centré
        double nouvX = this.getItemForme().getCenterX() - drain.getDiametre() / 2;
        double nouvY = this.getItemForme().getCenterY() - drain.getDiametre() / 2;
        drain.setPosition(new Point(nouvX, nouvY));
     }
    

    public void redimensionnerDrain(Point delta){
        this.drain.setDiametre(drain.getDiametre() + delta.getX());
    }

    
    public Drain getDrain(){
        return this.drain;
    }
    
    @Override
    public double getDistanceAvecFil(){
        return this.distanceAvecFil;
    }
    
    
    public TypeAvecDrain getType(){
        return this.type;
    }
    
}
