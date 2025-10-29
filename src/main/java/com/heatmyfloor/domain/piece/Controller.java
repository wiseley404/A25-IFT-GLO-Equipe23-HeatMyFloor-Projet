package com.heatmyfloor.domain.piece;

import com.heatmyfloor.domain.Point;
import com.heatmyfloor.domain.items.TypeAvecDrain;
import com.heatmyfloor.domain.items.TypeSansDrain;
import com.heatmyfloor.domain.ports.PieceStockage;
import java.nio.file.Path;
import java.util.List;

/**
 *
 * @author petit
 */
public class Controller {
    private Piece piece;
    private PieceHistorique historique;
    private PieceStockage stockage;
    
    public Controller(PieceStockage stockage){
        this.stockage = stockage;
        this.piece = new PieceRectangulaire(25, 15);
        this.historique = new PieceHistorique();
    }
    
    
    public Controller(Piece piece, PieceStockage stockage){
        this.piece = piece;
        this.historique = new PieceHistorique();
        this.stockage = stockage;
    }
    
    
    public void ajouterMeubleAvecDrain(Point sourisPosition, TypeAvecDrain type){
        
    }
    
    
    public void ajouterMeubleSansDrain(Point sourisPosition, TypeSansDrain type){
        
    }
    
    
    public void ajouterElementChauffant(Point sourisPosition){
        
    }
    
    
    public void ajouterThermostat(Point sourisPosition){
        
    }
    
    
    public void supprimerItemSelectionne(){
        
    }
    
    
    public void deplacerItemSelectionne(Point nouvPosition){
        
    }
    
    
    public void redimensionnerPiece(double nouvLong, double nouvLarg){
        
    }
    
    
    public void redimensionnerItemSelectionne(double nouvLong, double nouvLarg){
        
    }
    
    
    public List<PieceItemReadOnly> getItemsList(){
        throw new UnsupportedOperationException("Méthode non implémentée !");
    }
    
    
    public void annulerModif(){
        
    }
    
    
    public void retablirModif(){
        
    }
    
    
    public void ajouterDrain(Point sourisPosition){
        
    }
    
    
    public void deplacerDrainSelectionne(Point nouvPosition){
        
    }
    
    
    public void redimensionnerDrainSelectionne(double nouvDiametre){
        
    }
    
    
    public void supprimerDrainSelectionne(){
        
    }
    
    
    public void genererGraphe(double distIntersections){
        
    }
    
    
    public void appliquerGrapheTranslation(Point delta){
        
    }
    
    
    public void genererChemin(double longFilTotal){
        
    }
    
    
    public void modifierChemin(){
        
    }
    
    
    public void ouvrirProjet(Path cheminFichier){

    }
    
    
    public void sauvegarderProjet(Path fichierSauvegarde){
        
    }
    
    
    public void exporterProjetPng(Path fichierExport){
        
    }
    
    
    public void determinerElementDeClic(double xPouce, double yPouce){
        
    }
    
}
