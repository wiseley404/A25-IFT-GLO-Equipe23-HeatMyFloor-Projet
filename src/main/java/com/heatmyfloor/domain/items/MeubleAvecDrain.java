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
    private double distanceAvecFil;
    private TypeAvecDrain type;
    
    //Constructeur
    
    public MeubleAvecDrain(double longueur, double larg, Point pos, TypeAvecDrain type){
        super(longueur, larg, pos);
        this.drainList = new ArrayList<>();
        this.distanceAvecFil = 0.0;
        this.type = type;
    }
    
    
    //Méthodes
    
    public void ajouterDrain(){}
    
    public Drain trouverDrain(UUID idDrain){
        
        throw new UnsupportedOperationException("trouverDrain non implémentée");
    }
    public void deplacerDrainSelectionne(Point nouvPos){}
    
    public boolean estPositionDrainValide(Point pos){
        
        throw new UnsupportedOperationException("estPositionDrainValide non implémentée");
    }
    
    public boolean estDrainPresent(UUID idDrain){
        
        throw new UnsupportedOperationException("estDrainPresent non implémentée");
    }
    public void redimensionnerDrainSelectionne(double nouvDiam){}
    
    public void supprimerDrainSelectionne(){}
    
    public boolean contientLePoint(Point pos){
        
        throw new UnsupportedOperationException("contientLePoint non implémentée");
    }
    public boolean estClique(double x_pouce, double y_pouce){
        
        throw new UnsupportedOperationException("estClique non implémentée");
    }
    
    
    
}
