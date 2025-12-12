package com.heatmyfloor.domain.piece;

import com.heatmyfloor.domain.Point;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.awt.Shape;
/**
 *
 * @author petit
 */
public class PieceIrreguliere extends Piece {
    private List<Point> sommets;
    
    public PieceIrreguliere(List<Point> sommets){
        super(
              getContour(sommets).getWidth(), 
              getContour(sommets).getHeight(),
              creerMurs(sommets));
        this.sommets = sommets;
        Rectangle2D contour = getContour(sommets);
        super.setPosition(new Point(contour.getX(), contour.getY()));
    }
    
    public PieceIrreguliere(){
        super(0, 0, new ArrayList<>());
        this.sommets = new ArrayList<>(); 
    }
    
    @Override
    public List<Point> getSommets(){
        return this.sommets;
    }   
    
    @Override
    public Point getPosition(){
        double x = getForme().getBounds2D().getX();
        double y = getForme().getBounds2D().getY();
        return new Point(x, y);
    }
    
    @Override
    public void mettreAJourMurs(){
        this.setMurs(creerMurs(this.sommets));
    }
    
    public static List<Mur> creerMurs(List<Point> sommets){
        if(sommets == null || sommets.size() < 3){
            return new ArrayList<>();
        }
        List<Mur> murs = new ArrayList<>();
        for (int i=1; i < sommets.size(); i++){
            Mur mur = new Mur(sommets.get(i-1), sommets.get(i));
            murs.add(mur);
        }
        murs.add(new Mur(sommets.getLast(), sommets.getFirst()));
        return murs;
    }
    
    @Override
    public Path2D getForme(){
        return getForme(this.sommets);
    }
    
    @Override
    public void redimensionner(double nouvLarg, double nouvHaut){
        if(this.sommets == null || this.sommets.isEmpty())return;
        
        Rectangle2D contourPiece = getContour(this.sommets);
        
        if(super.getLargeur() == 0 || super.getHauteur() == 0) return;
        double facteurX = nouvLarg / super.getLargeur();
        double facteurY = nouvHaut / super.getHauteur();
        
        double posX = contourPiece.getX();
        double posY = contourPiece.getY();
        
        for(Point s : this.sommets){
            double deltaX = s.getX() - posX;
            double deltaY = s.getY() - posY;
            
            double nouvDeltaX = deltaX * facteurX;
            double nouvDeltaY = deltaY * facteurY;
            
            s.setX(posX + nouvDeltaX);
            s.setY(posY + nouvDeltaY);
        }
        super.setLargeur(nouvLarg);
        super.setHauteur(nouvHaut);
        for(PieceItem item : super.getItemsList()){
            item.translater(facteurX, facteurY);
        }
    }
    
    @Override
    public void setPosition(Point nouvPosition){
        Point anciennePosition = super.getPosition();
        double deltaX = nouvPosition.getX() - anciennePosition.getX();
        double deltaY = nouvPosition.getY() - anciennePosition.getY();

        for(Point s : this.sommets){
            s.setX(s.getX() + deltaX);
            s.setY(s.getY() + deltaY);
        }
        super.setPosition(nouvPosition);
    }
    
    public static Path2D getForme(List<Point> sommets){
        Path2D polygone = new Path2D.Double();
        
        if(sommets == null || sommets.size() < 3){
            return polygone;
        }
        boolean sommetDepart = true;
        for(Point sommet: sommets){
            if(sommetDepart){
                polygone.moveTo(sommet.getX(), sommet.getY());
                sommetDepart = false;
            }else{
                polygone.lineTo(sommet.getX(), sommet.getY());
            }
        }
        polygone.closePath(); 
        return polygone;
    }
    
    public static Rectangle2D getContour(List<Point> sommets){
        return getForme(sommets).getBounds2D();
    }
    @Override
    public Point getCentre(){
        double centreX = getContour(this.sommets).getCenterX();
        double centreY = getContour(this.sommets).getCenterY();
        return new Point(centreX, centreY);
    }
    @Override
    public boolean contientLePoint(Point position){
        return getForme(this.sommets).contains(position.getX(), position.getY());
    }   
    
    @Override
    public boolean contientLaForme(Shape itemRotation){
        return getForme(this.sommets).contains(itemRotation.getBounds2D());
    }
    
    public void setSommets(List<Point> sommets){
        this.sommets = sommets;
    }

    @Override
    public TypePiece getType() {
        return TypePiece.IRREGULIERE;
    }
}
