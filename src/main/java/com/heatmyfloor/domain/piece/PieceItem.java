package com.heatmyfloor.domain.piece;

import com.heatmyfloor.domain.Point;
import java.awt.geom.Rectangle2D;
import java.util.UUID;

/**
 *
 * @author petit
 */
public class PieceItem implements PieceItemReadOnly{
    private final UUID id;
    private double largeur;
    private double hauteur;
    private Point position;
    private int angle;
    private final String image;
    private boolean estSelectionne;
    
    
    public PieceItem(double largeur, double hauteur, Point pos, String img){
        this.id = UUID.randomUUID();
        this.largeur = largeur;
        this.hauteur = hauteur;
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
    public Rectangle2D getItemForme(){
        Rectangle2D itemForme = new Rectangle2D.Double(
                        this.getPosition().getX(), this.getPosition().getY(),
                        this.getLargeur(), this.getHauteur());
        return itemForme;
    }
    
    public boolean contientLePoint(Point position){
        return this.getItemForme().contains(position.getX(), position.getY());
    }
    
    
    public void setEstSelectionne(boolean statutSelection){
        this.estSelectionne = statutSelection;
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
        angle = angle % 360;
        this.angle = angle;
    }
    
    public void pivoter(){
        setAngle(this.angle + 90);
    }
    
    
    @Override
    public UUID getID(){
        throw new UnsupportedOperationException("Méthode non implémentée !");
    }
    
    
    @Override
    public double getLargeur(){
        return this.largeur;
    }
    
    
    @Override
    public double getHauteur(){
        return this.hauteur;
    }
    
    
    @Override
    public Point getPosition(){
        return this.position;
    }
    
    
    @Override
    public int getAngle(){
        return this.angle;
    }
    
    
    @Override
    public String getImage(){
        return this.image;
    }
    
    
    @Override
    public boolean estSelectionne(){
        return this.estSelectionne;
    }
}
