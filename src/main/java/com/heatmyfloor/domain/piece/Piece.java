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
public abstract class Piece implements PieceReadOnly{
    private List<PieceItem> itemsList;
    private Graphe graphe;
    private List<Mur> murs;
    private double largeur;
    private double hauteur;
    private Point position;
    
    
    public Piece(double largeur, double hauteur, List<Mur> murs){
        this.itemsList = new ArrayList<PieceItem>();
        this.graphe = new Graphe();
        this.murs = murs;    
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.position = new Point();
    }
    
    
    public abstract boolean contientLePoint(Point position);
    
    public abstract Point getCentre();
    
    public void ajouterItem(PieceItem item){
        for(PieceItem i : itemsList){
            i.setEstSelectionne(false);
        }
        if(this.contientLePoint(item.getPosition())){
            item.setEstSelectionne(true);
            this.itemsList.add(item);
        }
    }
    
    public void changerStatutSelectionItem(Point position){
        boolean itemTrouve = false; 
        for(int i = itemsList.size()-1; i >=0; i--){
            PieceItem item = itemsList.get(i);
            if(item.contientLePoint(position)){
                for(PieceItem autre : itemsList){
                    if (autre != item){
                        autre.setEstSelectionne(false);
                    }
                }
                item.setEstSelectionne(true);
                itemTrouve = true;
                break;
            }
        }
        if(!itemTrouve){
            for(PieceItem item :itemsList){
                item.setEstSelectionne(false);
            }
        }
    }
    
    
    public void ajouterDrain(Point position){
        
    }
    
    public void repositionnerDrainSelectionne(Point nouvPosition){
        
    }
    
    public void deplacerDrainSelectionne(Point delta){
        
    }
    
    public void deplacerDrain(double facteurX, double facteurY){}
    
    public void redimensionnerDrainSelectionne(double nouvDiametre){
        
    }
    
    public void redimensionnerDrainSelectionne(Point delta){
        
    }
    
    public void redimensionnerDrain(double facteurX, double facteurY){
        
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
        return this.itemsList.isEmpty();
    }
    
    
    public void supprimerItemSelectionne(){
        
    }
    
    
    public void deplacerItemSelectionne(Point nouvPosition){
        
        if(nouvPosition == null) return;
        for(PieceItem it : itemsList){
            if(it.estSelectionne()){
                it.setPosition(nouvPosition);
                break;
            }
        }
        
    }
    
    
    public void redimensionner(double nouvLarg, double nouvHaut){
        
    }
    
    
    public void redimensionnerItemSelectionne(double nouvLarg, double nouvHauteur){
        
        for(PieceItem it : itemsList){
            if(it.estSelectionne()){
                it.setDimension(nouvLarg, nouvHauteur);
                break;
            }
        }
        
    }
    
    
    public void genererGraphe(double distIntersections){
        
    }
    
    
    public void appliquerGrapheTranslation(Point delta){
        
    }
    

    public PieceItem trouverItemSelectionne(){
        for(PieceItem item : itemsList){
            if(item.estSelectionne()){
                return item;
            }
        }
        return null;
    }    
                

    
    public PieceItem trouverCible(double xPouce, double yPouce){
        
        for(PieceItem element : itemsList){
            if(element.contientLePoint(new Point(xPouce, yPouce))){
                return element;

            }
        }
        return null;
    }
    

    
    @Override
    public double getLargeur(){
        return this.largeur;
    }
    
    public void setLargeur(double nouvLarg){
        this.largeur = nouvLarg;
    }
    
    @Override
    public double getHauteur(){
        return this.hauteur;
    }
    
    public void setHauteur(double nouvHaut){
        this.hauteur = nouvHaut;
    }
    
    @Override
    public Point getPosition(){
        return this.position;
    }
    
    public void setPosition(Point nouvPosition){
        this.position = nouvPosition;
    }
    
    public List<PieceItem> getItemsList(){

        return this.itemsList;


    }
    
    public Graphe getGraphe(){
        throw new UnsupportedOperationException("Méthode non implémentée !");
    }
    
    public List<Mur> getMurs(){
        throw new UnsupportedOperationException("Méthode non implémentée !");
    }    
}
