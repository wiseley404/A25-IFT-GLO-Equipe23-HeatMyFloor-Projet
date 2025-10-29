/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heatmyfloor.domain.items;

import com.heatmyfloor.domain.piece.Point;
import com.heatmyfloor.domain.piece.PieceItem;

/**
 *
 * @author petit
 */
public class ElementChauffant extends PieceItem {
    
    //Constructeur
    
    public ElementChauffant(double longueur, double larg, Point pos){
        super(longueur, larg, pos);
    }
    
    
    //Méthode
    
    public boolean estClique(double x_pouce, double y_pouce){
        
        throw new UnsupportedOperationException("estClique non implémentée");
    }
}
