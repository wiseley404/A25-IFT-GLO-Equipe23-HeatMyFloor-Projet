package com.heatmyfloor.domain.piece;

import com.heatmyfloor.domain.Point;
import java.util.List;

/**
 *
 * @author petit
 */
public class PieceIrreguliere {
    private List<Point> sommets;
    
    public PieceIrreguliere(List<Point> points){
        this.sommets = points;
    }

    public List<Point> getSommets(){
        throw new UnsupportedOperationException("Méthode non implémentée !");
    }    
}
