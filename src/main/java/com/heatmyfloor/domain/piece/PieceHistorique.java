package com.heatmyfloor.domain.piece;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 *
 * @author petit
 */
public class PieceHistorique {
    private Deque<Piece> undoPile;
    private Deque<Piece> redoPile;
    
    public PieceHistorique(){
        this.undoPile = new ArrayDeque<Piece>();
        this.redoPile = new ArrayDeque<Piece>();
    }
    
    public void sauvegarder(Piece pieceCopie){
        
    }
    
    
    public Piece annuler(){
        throw new UnsupportedOperationException("Méthode non implémentée !");
    }
    
    
    public Piece retablir(){
        throw new UnsupportedOperationException("Méthode non implémentée !");
    }
    
    
    public boolean estVide(){
        throw new UnsupportedOperationException("Méthode non implémentée !");
    }   
}
