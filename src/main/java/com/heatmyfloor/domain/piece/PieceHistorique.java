package com.heatmyfloor.domain.piece;

import java.util.ArrayDeque;
import java.util.Deque;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author petit
 */
public class PieceHistorique {
    private Deque<Piece> undoPile;
    private Deque<Piece> redoPile;
    
    public PieceHistorique(){
        this.undoPile = new ArrayDeque<>();
        this.redoPile = new ArrayDeque<>();
    }
    
    private Piece copie(Piece piece){
        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(piece);
            
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (Piece) ois.readObject();
        }catch(Exception e){
            return null;
        }
    }
    
    
    public void sauvegarder(Piece piece){
        this.undoPile.add(copie(piece));
        this.redoPile.clear();
    }
    
    
    public Piece annuler(Piece pieceActuelle){
        if(!undoPileEstVide()){
            Piece anciennePiece = undoPile.removeLast();
            redoPile.add(copie(pieceActuelle));
            return anciennePiece;
        }
        return null;
    }
    
    
    public Piece retablir(Piece pieceActuelle){
        if(!redoPileEstVide()){
            Piece pieceRecuperee = redoPile.removeLast();
            undoPile.add(copie(pieceActuelle));
            return pieceRecuperee;
        }
        return null;
    }
    
    
    public boolean undoPileEstVide(){
        return this.undoPile.isEmpty();
    }   
    
    public boolean redoPileEstVide(){
        return this.redoPile.isEmpty();
    }
    
    public Deque<Piece> getUndoPile(){
        return this.undoPile;
    }
    
    public Deque<Piece> getRedoPile(){
        return this.redoPile;
    }
    
    public void setUndoPile(Deque<Piece> undoPile){
        this.undoPile = undoPile;
    }
    
    public void setRedoPile(Deque<Piece> redoPile){
        this.redoPile = redoPile;
    }
}
