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
public class Thermostat extends PieceItem {
    
    //Constructeur
    
    public Thermostat(double longueur, double larg, Point pos){
        super(longueur, larg, pos,"Icons/Thermostat.png");
    }
    
    
}
