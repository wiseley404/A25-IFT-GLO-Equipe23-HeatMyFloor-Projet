package com.heatmyfloor.domain.piece;

import com.heatmyfloor.domain.Point;
import java.awt.geom.Rectangle2D;
import java.util.UUID;
import java.io.Serializable;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
/**
 *
 * @author petit
 */
public class PieceItem implements PieceItemReadOnly, Serializable{
    private final UUID id;
    private double largeur;
    private double hauteur;
    private Point position;
    private double angle;
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
        
        if (nouvPosition == null) throw new IllegalArgumentException("Position nulle");
        this.position = nouvPosition;

    }
    
    
    public void translater(Point delta){
        
        if (delta == null) throw new IllegalArgumentException("Delta nul");
        this.position = new Point(
            this.position.getX() + delta.getX(),
            this.position.getY() + delta.getY()
        );
        
    }
    
    
    public void translater(double facteurX, double facteurY){
        
        if(facteurX <= 0 || facteurY <= 0) throw new IllegalArgumentException("Facteurs invalides");
        this.position = new Point(
            this.position.getX() * facteurX,
            this.position.getY() * facteurY
        );
    }
    
    
    public void setDimension(double nouvLarg, double nouvHaut){
        
        if(nouvLarg <= 0 || nouvHaut <= 0)
            throw new IllegalArgumentException("Dimensions invalides");
        
        this.largeur = nouvLarg;
        this.hauteur = nouvHaut;
    }
    
    
    public void redimensionner(double facteurX, double facteurY){
        
        if(facteurX <= 0 || facteurY <= 0)
            throw new IllegalArgumentException("Facteurs invalides");
        
        this.largeur *= facteurX;
        this.hauteur *= facteurY;
    }
    
    
    public void redimensionner(Point delta){
        
        if(delta == null) throw new IllegalArgumentException("Delta nul");
        double nouvLong = this.largeur + delta.getX();
        double nouvLarg = this.hauteur + delta.getY();
        
        if(nouvLong <= 0 || nouvLarg <= 0)
            throw new IllegalArgumentException("Dimension invalides");
        
        this.largeur = nouvLong;
        this.hauteur = nouvLarg;

    }
    
    
    @Override
    public Rectangle2D getItemForme(){
        Rectangle2D itemForme = new Rectangle2D.Double(
                        this.getPosition().getX(), this.getPosition().getY(),
                        this.getLargeur(), this.getHauteur());
        return itemForme;
    }
    
    
    @Override
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
    
    
    public void setAngle(double angle){
        angle = angle % 360;
        this.angle = angle;
    }
    
    public void pivoter(){
        setAngle(this.angle + 90);
    }
     
    @Override
    public UUID getID(){
        return this.id;
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
    public double getAngle(){
        return this.angle;
    }
    
    
    @Override
    public String getImage(){
        return this.image;
    }

    @Override
    public boolean estSelectionne(){
        return estSelectionne;
    }
}

