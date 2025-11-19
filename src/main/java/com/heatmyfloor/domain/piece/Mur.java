package com.heatmyfloor.domain.piece;

import com.heatmyfloor.domain.Point;
import java.util.UUID;
import java.io.Serializable;
/**
 *
 * @author petit
 */
public class Mur implements Serializable{
    private final UUID id;
    private Point debut, fin;
    private final double distanceAvecFil;
    
    public Mur(Point debut, Point fin){
        this.fin = fin;
        this.debut = debut;
        this.id = UUID.randomUUID();
        this.distanceAvecFil = 3;
    }
    
    public void positionnerItem(PieceItem item){

    }
    
    public UUID getId(){
        return this.id;
    }
    
    public Point getDebut(){
        return debut;
    }
    
    public Point getFin(){
        return fin;
    }
    
    public double getDistanceAvecFil(){
        return this.distanceAvecFil;
    }

    
}
