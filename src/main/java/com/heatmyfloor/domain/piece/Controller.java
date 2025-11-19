package com.heatmyfloor.domain.piece;

import com.heatmyfloor.domain.Point;
import com.heatmyfloor.domain.items.TypeAvecDrain;
import com.heatmyfloor.domain.items.TypeSansDrain;
import com.heatmyfloor.domain.items.Zone;
import com.heatmyfloor.domain.items.Zone.TypeZone;
import com.heatmyfloor.domain.ports.PieceStockage;
import com.heatmyfloor.domain.items.ElementChauffant;
import com.heatmyfloor.domain.items.Thermostat;
import com.heatmyfloor.domain.items.MeubleAvecDrain;
import com.heatmyfloor.domain.items.MeubleSansDrain;
import com.heatmyfloor.infrastructure.file.PieceFichierStockage;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author petit
 */
public class Controller {
    private Piece piece;
    private PieceHistorique historique;
    private PieceStockage stockage;

    
    public Controller(){
        this.piece = new PieceRectangulaire(500, 300);
        this.stockage = new PieceFichierStockage();
        this.historique = new PieceHistorique();
    }
    
    
    public Controller(Piece piece){
        this.piece = piece;
        this.historique = new PieceHistorique();
        this.stockage = new PieceFichierStockage();
    }
    

    public void ajouterMeubleAvecDrain(Point position, TypeAvecDrain type){
        this.piece.ajouterItem(new MeubleAvecDrain(120, 70, position, type));

    }

    
    public void ajouterMeubleSansDrain(Point position, TypeSansDrain type){
        this.piece.ajouterItem(new MeubleSansDrain(120, 70, position, type));
    }
    
    
    public void ajouterElementChauffant(Point position){
        this.piece.ajouterItem(new ElementChauffant(120, 70, position));
    }
    
    
    public void ajouterThermostat(Point position){
        this.piece.ajouterItem(new Thermostat(70, 50, position));
    }
    
    public void ajouterZone(Point position, TypeZone type){
        this.piece.ajouterItem(new Zone(150, 70, position, type));
    }
    
    public void repositionnerPiece(Point position){
        this.piece.setPosition(position);
    }
    
    public void supprimerItemSelectionne(){
        piece.supprimerItemSelectionne();
    }
      
    public void deplacerItemSelectionne(Point nouvPosition){
        piece.deplacerItemSelectionne(nouvPosition);   
    }
    
    public boolean estPositionValide(Point p){
       return piece.estPositionItemValide(p);
    }
    
    
    public void redimensionnerPiece(double nouvLarg, double nouvHaut){
        this.piece.redimensionner(nouvLarg, nouvHaut);
    }
    
    
    public void redimensionnerItemSelectionne(double nouvLarg, double nouvHaut){
        
        piece.redimensionnerItemSelectionne(nouvLarg, nouvHaut);
    }
    
    public void redimensionnerItemSelectionne(Point delta){
        
    }
    
    public void changerStatutSelection(Point pos){
        this.piece.changerStatutSelectionItem(pos);
    }
    
    public List<PieceItemReadOnly> getItemsList(){
        return this.piece.getItemsList()
                .stream()
                .map(pieceItem -> (PieceItemReadOnly) pieceItem)
                .collect(Collectors.toList());
    }
    
    public PieceReadOnly getPiece(){
        return this.piece;
    }
    
    public void setPiece(Piece piece){
        this.piece = piece;
    }
    
    
    public void annulerModif(){
        
    }
    
    
    public void retablirModif(){
        
    }
    
    
    public void ajouterDrain(Point position){
        
    }
    
    
    public void repositionnerDrainSelectionne(Point nouvPosition){
        
    }
    
    public void deplacerDrainSelectionne(Point delta){
        
    }
    
    
    public void redimensionnerDrainSelectionne(double nouvDiametre){
        
    }
    
    public void redimensionnerDrainSelectionne(Point delta){
        
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
    
    public PieceItemReadOnly trouverItemSelectionne(){
        return this.piece.trouverItemSelectionne();
    }
    

    public PieceItemReadOnly determinerElementDeClic(double xPouce, double yPouce){
        return this.piece.trouverCible(xPouce, yPouce);
        

    }
    
    
    
}
