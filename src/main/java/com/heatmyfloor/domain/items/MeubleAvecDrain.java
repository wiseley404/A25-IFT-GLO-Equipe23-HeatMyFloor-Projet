/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heatmyfloor.domain.items;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import com.heatmyfloor.domain.Point;
import com.heatmyfloor.domain.piece.PieceItem;


/**
 *
 * @author petit
 */
public class MeubleAvecDrain extends PieceItem {
    
    //Attributs
    
    private List<Drain> drainList;
    private final double distanceAvecFil;
    private TypeAvecDrain type;
    
    //Constructeur
    
    public MeubleAvecDrain(double largeur, double hauteur, Point pos, TypeAvecDrain type){
        super(largeur, hauteur, pos, type.getImage());
        this.drainList = new ArrayList<>();
        this.distanceAvecFil = 0.0;
        this.type = type;
    }
    
    
    //Méthodes
    
    public void ajouterDrain(Drain drain){
        this.drainList.add(drain);
    }
    
    public Drain trouverDrain(UUID idDrain){
        
        throw new UnsupportedOperationException("trouverDrain non implémentée");
    }
    
    public Drain trouverDrainSelectionne(){
        for(Drain drain : drainList){
            if(drain.estSelectionne()){
                return drain;   
            }
        }
        return null;
    }
    
    public void repositionnerDrainSelectionne(Point nouvPos){}
    public void deplacerDrain(double facteurX, double facteurY){}
    public void deplacerDrainSelectionne(Point delta){}
    
    public boolean estPositionDrainValide(Point pos){
        throw new UnsupportedOperationException("estPositionDrainValide non implémentée");
    }
    
    public boolean estDrainPresent(UUID idDrain){
        throw new UnsupportedOperationException("estDrainPresent non implémentée");
    }
    
    public void redimensionnerDrainSelectionne(double nouvDiam){}
    public void redimensionnerDrainSelectionne(Point delta){}
    public void redimensionnerDrain(double facteur){}
    
    public void supprimerDrainSelectionne(){}
    
    
    @Override
    public boolean contientLePoint(Point pos){
        
        throw new UnsupportedOperationException("contientLePoint non implémentée");
    }

    
    public List<Drain> getDrainList(){
        return this.drainList;
    }
    
    
    public double getDistanceAvecFil(){
        return this.distanceAvecFil;
    }
    
    
    public TypeAvecDrain getType(){
        return this.type;
    }
    
    
}
