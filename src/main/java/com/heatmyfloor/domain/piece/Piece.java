package com.heatmyfloor.domain.piece;

import com.heatmyfloor.domain.Point;
import com.heatmyfloor.domain.graphe.Graphe;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Wily
 */
public class Piece {
    private List<PieceItem> itemsList;
    private Graphe graphe;
    private List<Mur> murs;
    
    
    public Piece(List<Mur> murs){
        this.itemsList = new ArrayList<PieceItem>();
        this.graphe = new Graphe();
        this.murs = murs;     
    }
    
    
    public void ajouterItem(PieceItem item){
        
    }
    
    
    public PieceItem trouverItemParId(UUID idItem){
      throw new UnsupportedOperationException("Méthode non implémentée !");
    }
    
    
    public boolean estPositionItemValide(Point itemPosition){
        throw new UnsupportedOperationException("Méthode non implémentée !");
    }
    
    
    public boolean estItemPresent(UUID idItem){
        throw new UnsupportedOperationException("Méthode non implémentée !");
    }
    
    
    public boolean estVide(){
        throw new UnsupportedOperationException("Méthode non implémentée !");
    }
    
    
    public void supprimerItemSelectionne(){
        
    }
    
    
    public void deplacerItemSelectionne(Point nouvPosition){
        
    }
    
    
    public void redimensionner(double nouvLong, double nouvlarg){
        
    }
    
    
    public void redimensionnerItemSelectionne(double nouvLong, double nouvLarg){
        
    }
    
    
    public void genererGraphe(double distIntersections){
        
    }
    
    
    public void appliquerGrapheTranslation(Point delta){
        
    }
    
    
    public PieceItem trouverCible(double xPouce, double yPouce){
        throw new UnsupportedOperationException("Méthode non implémentée !");
    }
    
    
    public List<PieceItem> getItemsList(){
        throw new UnsupportedOperationException("Méthode non implémentée !");
    }
    
    public Graphe getGraphe(){
        throw new UnsupportedOperationException("Méthode non implémentée !");
    }
    
    public List<Mur> getMurs(){
        throw new UnsupportedOperationException("Méthode non implémentée !");
    }    
}
