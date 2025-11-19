/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heatmyfloor.domain.graphe;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
/**
 *
 * @author petit
 */
public class Chemin implements Serializable{
   private  List<Fil> aretes;
    
   public Chemin(List<Fil> aretes){
       this.aretes = new ArrayList<Fil>();
   }
   
   public void ajouterFil(Fil f){}
   
   public void supprimerFil(Fil f){}
    
}
