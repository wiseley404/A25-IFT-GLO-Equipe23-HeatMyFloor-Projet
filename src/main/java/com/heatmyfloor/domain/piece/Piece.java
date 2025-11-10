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
        this.itemsList = new ArrayList<>();
        this.graphe = new Graphe();
        this.murs = murs;    
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.position = new Point();
    }
    
    @Override
    public abstract boolean contientLePoint(Point position);
    
    @Override
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
        
        boolean valide = false;
        if(contientLePoint(itemPosition)){
            
            valide = true;
        }
        return valide;   
        
    }
    
    
    public boolean estItemPresent(UUID idItem){
        throw new UnsupportedOperationException("Méthode non implémentée !");
    }
    
    
    public boolean estVide(){
        return this.itemsList.isEmpty();
    }
    
    
    public void supprimerItemSelectionne(){
        if (itemsList.isEmpty())
            return ;
        
        for (var it = itemsList.iterator(); it.hasNext();){
            PieceItem item = it.next();
            
            if (item.estSelectionne()){
                it.remove();
                return;
            }
        }
        
    }
    
    
    public void deplacerItemSelectionne(Point nouvPosition){
        
        if(nouvPosition == null) return;
        
        for (PieceItem it : itemsList){
            if(it.estSelectionne()){
                
                //valide la position
                if(estPositionItemValide(nouvPosition)){
                    it.setPosition(nouvPosition);
                }
                break;
            }
        }        
    }
    
    
    public void redimensionner(double nouvLarg, double nouvHaut){
        setLargeur(nouvLarg);
        setHauteur(nouvHaut);
    }
    
    
    public void redimensionnerItemSelectionne(double nouvLarg, double nouvHauteur){
        
         PieceItem itemSelectionne = trouverItemSelectionne();
         itemSelectionne.setDimension(nouvLarg, nouvHauteur);
        
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
        
        Point p = new Point(xPouce, yPouce);
        for(PieceItem element : itemsList){
            if(element.contientLePoint(p)){
                
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
