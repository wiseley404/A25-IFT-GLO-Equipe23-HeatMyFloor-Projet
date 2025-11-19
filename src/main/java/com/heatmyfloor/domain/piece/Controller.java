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
        this.historique.sauvegarder(piece);
        this.piece.ajouterItem(new MeubleAvecDrain(120, 70, position, type));
    }

    
    public void ajouterMeubleSansDrain(Point position, TypeSansDrain type){
        this.historique.sauvegarder(piece);
        this.piece.ajouterItem(new MeubleSansDrain(120, 70, position, type));
    }
    
    
    public void ajouterElementChauffant(Point position){
        this.historique.sauvegarder(piece);
        this.piece.ajouterItem(new ElementChauffant(150, 50, position));
    }
    
    
    public void ajouterThermostat(Point position){
        this.historique.sauvegarder(piece);
        this.piece.ajouterItem(new Thermostat(70, 50, position));
    }
    
    public void ajouterZone(Point position, TypeZone type){
        this.historique.sauvegarder(piece);
        this.piece.ajouterItem(new Zone(150, 70, position, type));
    }
    
    public void repositionnerPiece(Point position){
        this.historique.sauvegarder(piece);
        this.piece.setPosition(position);
    }
    
    public void supprimerItemSelectionne(){
        this.historique.sauvegarder(piece);
        piece.supprimerItemSelectionne();
    }
      
    public void deplacerItemSelectionne(Point nouvPosition){
        this.historique.sauvegarder(piece);
        piece.deplacerItemSelectionne(nouvPosition);   
    }
    
    public boolean estPositionValide(Point p){
       return piece.estPositionItemValide(p);
    }
    
    
    public void redimensionnerPiece(double nouvLarg, double nouvHaut){
        this.historique.sauvegarder(piece);
        this.piece.redimensionner(nouvLarg, nouvHaut);
    }
    
    
    public void redimensionnerItemSelectionne(double nouvLarg, double nouvHaut){
        this.historique.sauvegarder(piece);
        piece.redimensionnerItemSelectionne(nouvLarg, nouvHaut);
    }
    
    public void redimensionnerItemSelectionne(Point delta){
        this.historique.sauvegarder(piece);
        //Ajout de ton appel en dessous (*KEMILA -> A Faire)
    }
    
    public void changerStatutSelection(Point pos){
        this.piece.changerStatutSelectionItem(pos);
    }
    
    public void changerAngleItemSelectionne(double nouvAngle){
        this.historique.sauvegarder(piece);
        this.piece.changerAngleItemSelectionne(nouvAngle);
    }
    
    public void pivoterItemSelectionne(){
        this.historique.sauvegarder(piece);
        this.piece.pivoterItemSelectionne();
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
        this.historique.sauvegarder(piece);
        this.piece = piece;
    }
    
    
    public void annulerModif(){
        Piece anciennePiece  = this.historique.annuler(piece);
        if(anciennePiece != null){
            this.piece = anciennePiece;
        }   
    }
    
    
    public void retablirModif(){
        Piece pieceRecuperee = this.historique.retablir(piece);
        if(pieceRecuperee != null){
            this.piece = pieceRecuperee;
        }
    }
    
    
    public void ajouterDrain(Point position){
        this.historique.sauvegarder(piece);
        //Appel a la methode en dessous 
    }
    
    
    public void repositionnerDrainSelectionne(Point nouvPosition){
        this.historique.sauvegarder(piece);
        //Appel a la methode en dessous
    }
    
    public void deplacerDrainSelectionne(Point delta){
        this.historique.sauvegarder(piece);
        //Appelez votre methode en dessous
    }
    
    
    public void redimensionnerDrainSelectionne(double nouvDiametre){
        this.historique.sauvegarder(piece);
        //Appelez votre methode en dessous
    }
    
    public void redimensionnerDrainSelectionne(Point delta){
        this.historique.sauvegarder(piece);
        //Appelez votre methode en dessous
    }
    
    
    public void supprimerDrainSelectionne(){
        this.historique.sauvegarder(piece);
        //Appelez votre methode en dessous
    }
    
    
    public void genererGraphe(double distIntersections){
        this.historique.sauvegarder(piece);
        //Appelez votre methode en dessous
    }
    
    
    public void appliquerGrapheTranslation(Point delta){
        this.historique.sauvegarder(piece);
        //Appelez votre methode en dessous
    }
    
    
    public void genererChemin(double longFilTotal){
        this.historique.sauvegarder(piece);
        //Appelez votre methode en dessous
    }
    
    
    public void modifierChemin(){
        this.historique.sauvegarder(piece);
        //Appelez votre methode en dessous
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
