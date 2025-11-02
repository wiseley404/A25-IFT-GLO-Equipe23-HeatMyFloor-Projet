/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heatmyfloor.domain.graphe;

/**
 *
 * @author petit
 */
public class Fil {
    private Intersection depart ;
    private Intersection arrivee;
    
    public Fil(Intersection depart, Intersection arrivee){
        this.depart=depart;
        this.arrivee=arrivee;
    }
    
    public double calculerLongeur(){
        return 0.0;
    }
    
}
