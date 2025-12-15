/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heatmyfloor.domain.graphe;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
import java.util.Collections;

/**
 *
 * @author petit
 */
/*public class Chemin implements Serializable{
   private  List<Fil> aretes;
    
   public Chemin(List<Fil> aretes){
       this.aretes = new ArrayList<Fil>();
   }
   
   public void ajouterFil(Fil f){}
   
   public void supprimerFil(Fil f){}
    
}*/

public class Chemin implements Serializable {
    private final List<Fil> aretes;

    public Chemin() {
        this.aretes = new ArrayList<>();
    }

    public Chemin(List<Fil> aretes) {
        this.aretes = (aretes == null) ? new ArrayList<>() : new ArrayList<>(aretes);
    }

    public void ajouterFil(Fil f) {
        if (f != null) aretes.add(f);
    }

    public void supprimerFil(Fil f) {
        aretes.remove(f);
    }

    public List<Fil> getAretes() {
        return aretes;
    }
}
