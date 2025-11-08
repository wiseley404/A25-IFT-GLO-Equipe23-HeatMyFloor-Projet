package com.heatmyfloor.domain.piece;

import com.heatmyfloor.domain.Point;
import com.heatmyfloor.domain.items.TypeAvecDrain;
import com.heatmyfloor.domain.items.TypeSansDrain;
import com.heatmyfloor.domain.ports.PieceStockage;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author petit
 */
public class Controller {
    private Piece _piece;
    private PieceHistorique historique;
    private PieceStockage stockage;
    private Projet _projet;
    private PieceRectangulaire pieceRectangulaire;
    
    private List<Projet> _projets;
    
    public Controller(PieceStockage stockage){
        this.stockage = stockage;
        this.historique = new PieceHistorique();
    }
    
    
    public Controller(Piece piece, PieceStockage stockage){
        this._piece = piece;
        this.historique = new PieceHistorique();
        this.stockage = stockage;
    }
    
    public Controller(){
        //_projets = new ArrayList<>();
    }
    
    public Projet creerProjet(){
        _projet =new Projet();
        this._piece = _projet.getPiece();
                
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
    
    public String getProjetNon(){
        return _projet.getNom();
    }
    
    public void setProjetNom(String nom){
        _projet.setNom(nom);
    }
    public void creerPieceRectangulaire(double longueur,double largeur){
        
        if(_projet == null){
            JOptionPane.showMessageDialog(null,"Aucun projet ouvert","Erreur",JOptionPane.ERROR_MESSAGE);
        }
        pieceRectangulaire = new PieceRectangulaire(longueur, largeur);
        _projet.setPiece(pieceRectangulaire);
        
        
    }
    public double getLongeurPieceRectangulaire(){
        return pieceRectangulaire.getLongueur();
    }
    
    public double getLargeurPieceRectangulaire(){
        return pieceRectangulaire.getLargeur();
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
