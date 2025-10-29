package com.heatmyfloor.domain.piece;

import com.heatmyfloor.domain.Point;
import java.util.UUID;
/**
 *
 * @author petit
 */
public class Mur {
    private final UUID id;
    private Point debut, fin;
    private double distanceAvecFil;
    
    public Mur(Point debut, Point fin){
        this.fin = fin;
        this.debut = debut;
        this.id = UUID.randomUUID();
        this.distanceAvecFil = 3;
    }
    
    public void positionnerItem(PieceItem item){

    }
    
    public UUID getId(){
        throw new UnsupportedOperationException("Méthode non implémentée !");
    }
    
    public Point getDebut(){
        throw new UnsupportedOperationException("Méthode non implémentée !");
    }
    
    public Point getFin(){
        throw new UnsupportedOperationException("Méthode non implémentée !");
    }
    
    public double getDistanceAvecFil(){
        throw new UnsupportedOperationException("Méthode non implémentée !");
    }  

    
}
