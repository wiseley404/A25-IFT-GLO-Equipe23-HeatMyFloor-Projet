/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heatmyfloor.domain.graphe;
import com.heatmyfloor.domain.Point;
import java.io.Serializable;
/**
 *
 * @author petit
 */
public class Fil implements Serializable{
    private Intersection depart ;
    private Intersection arrivee;
    
    public Fil(Intersection depart, Intersection arrivee){
        this.depart=depart;
        this.arrivee=arrivee;
    }
    
    /*public double calculerLongeur(){
        return 0.0;
    }*/
    
    public Intersection getDepart() { return depart; }
    public Intersection getArrivee() { return arrivee; }

    public double calculerLongueur() {
    double dx = arrivee.getCoordonees().getX() - depart.getCoordonees().getX();
    double dy = arrivee.getCoordonees().getY() - depart.getCoordonees().getY();
    return Math.sqrt(dx*dx + dy*dy);
}


    
}
