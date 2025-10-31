/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heatmyfloor.domain.items;

import com.heatmyfloor.domain.Point;
import com.heatmyfloor.domain.piece.PieceItem;

/**
 *
 * @author petit
 */
public class MeubleSansDrain extends PieceItem {
    
    //Attributs
    
    private double distanceAvecFil;
    private TypeSansDrain type;
    
    //Constructeur
    
    public MeubleSansDrain(double longueur, double larg, Point pos, TypeSansDrain type){
        super(longueur, larg, pos,"Icons/MeubleSansDrain");
        this.distanceAvecFil = 0.0;
        this.type = type;
    }
    
    
    //Méthode
    
    public boolean estClique(double x_pouce, double y_pouce){
        
        throw new UnsupportedOperationException("estClique non implémentée");
    }
    
    
    
}
