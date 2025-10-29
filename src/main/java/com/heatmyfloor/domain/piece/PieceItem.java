package com.heatmyfloor.domain.piece;

import com.heatmyfloor.domain.Point;
import java.util.UUID;

/**
 *
 * @author petit
 */
public class PieceItem implements PieceItemReadOnly{
    private final UUID id;
    private double longueur;
    private double largeur;
    private Point position;
    private int angle;
    private final String image;
    private boolean estSelectionne;
    
    
    public PieceItem(double longueur, double larg, Point pos, String img){
        this.id = UUID.randomUUID();
        this.longueur = longueur;
        this.largeur = larg;
        this.position = pos;
        this.angle = 0;
        this.image = img;
        this.estSelectionne = false;
    }
    
    
    public void setPosition(Point nouvPosition){

    }
    
    
    public void translater(Point delta){

    }
    
    
    public void translater(double facteurX, double facteurY){

    }
    
    
    public void setDimension(double nouvLong, double nouvLarg){

    }
    
    
    public void redimensionner(double facteurX, double facteurY){

    }
    
    
    public void redimensionner(Point delta){

    }
    
    
    public boolean contientLePoint(Point pos){
        throw new UnsupportedOperationException("Méthode non implémentée !");
    }
    
    
    public void setEstSelectionne(boolean statutSelection){

    }
    
    public void positionnerAGauche(){

    }
    
    
    public void positionnerADroite(){
        
    }
    
    
    public void positionnerEnHaut(){
        
    }
    
    
    public void positionnerEnBas(){
        
    }
    
    
    public void positionnerAuCentre(){
        
    }
    
    
    public void setAngle(int angle){

    }
    
    public void pivoter(){

    }
    
    
    @Override
    public UUID getID(){
        throw new UnsupportedOperationException("Méthode non implémentée !");
    }

    
    @Override
    public double getLongueur(){
        throw new UnsupportedOperationException("Méthode non implémentée !");
    }
    
    
    @Override
    public double getLargeur(){
        throw new UnsupportedOperationException("Méthode non implémentée !");
    }
    
    
    @Override
    public Point getPosition(){
        throw new UnsupportedOperationException("Méthode non implémentée !");
    }
    
    
    @Override
    public int getAngle(){
        throw new UnsupportedOperationException("Méthode non implémentée !");
    }
    
    
    @Override
    public String getImage(){
        throw new UnsupportedOperationException("Méthode non implémentée !");
    }
    
    
    @Override
    public boolean estSelectionne(){
        throw new UnsupportedOperationException("Méthode non implémentée !");
    }
}
