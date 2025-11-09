package com.heatmyfloor.domain.piece;

import com.heatmyfloor.domain.Point;
import com.heatmyfloor.domain.items.TypeAvecDrain;
import com.heatmyfloor.domain.items.TypeSansDrain;
import com.heatmyfloor.domain.ports.PieceStockage;
import com.heatmyfloor.domain.items.ElementChauffant;
import com.heatmyfloor.domain.items.Thermostat;
import com.heatmyfloor.domain.items.MeubleAvecDrain;
import com.heatmyfloor.domain.items.MeubleSansDrain;
import java.nio.file.Path;
import java.util.List;

import java.util.stream.Collectors;

import java.util.UUID; //ajout


/**
 *
 * @author petit
 */
public class Controller {
    private Piece piece;
    private PieceHistorique historique;
    private PieceStockage stockage;
    private Projet _projet;
    
    private List<Projet> _projets;
    private UUID selectionId; //ajout
    
    public Controller(PieceStockage stockage){
        this.stockage = stockage;
        this.historique = new PieceHistorique();
    }
    
    
    public Controller(Piece piece, PieceStockage stockage){
        this.piece = piece;
        this.historique = new PieceHistorique();
        this.stockage = stockage;
    }
    
    public Controller(){
        //_projets = new ArrayList<>();
    }
    
    public Projet creerProjet(){
        _projet =new Projet();
        this.piece = _projet.getPiece();
                
        return _projet;
    }
    
    public Projet getProjet(){
        return _projet;
    }
    
    public Piece getProjetPiece(){
        return _projet.getPiece();
    }
    
    
    public void setProjetPiece(Piece piece){
        _projet.setPiece(piece);
    }
    
    public String getProjetNom(){
        return _projet.getNom();
    }
    
    public void setProjetNom(String nom){
        _projet.setNom(nom);
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
    
    public void repositionnerPiece(Point position){
        this.piece.setPosition(position);
    }
    
    public void supprimerItemSelectionne(){
        
    }
    
    
    public void deplacerItemSelectionne(Point nouvPosition){
        

        piece.deplacerItemSelectionne(nouvPosition);

        
    }
    
    
    public void redimensionnerPiece(double nouvLarg, double nouvHaut){
        
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
    
    
    public UUID getSelectionId(){  //ajout memorise l'id de l'item selectionne
        return selectionId;

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
