package com.heatmyfloor.domain.piece;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
              getContour(sommets).getHeight(), 
              getContour(sommets).getWidth(),
              creerMurs(sommets));
        this.sommets = sommets;
    }
    
    public PieceIrreguliere(){
        super(0, 0, new ArrayList<>());
        this.sommets = new ArrayList<>(); 
    }

    public List<Point> getSommets(){
        return this.sommets;
    }   
    
    @Override
    public void mettreAJourMurs(){
        this.setMurs(creerMurs(this.sommets));
    }
    
    public static List<Mur> creerMurs(List<Point> sommets){
        List<Mur> murs = new ArrayList<>();
        for (int i=1; i <= sommets.size(); i++){
            Mur mur = new Mur(sommets.get(i-1), sommets.get(i));
            murs.add(mur);
        }
        murs.add(new Mur(sommets.getLast(), sommets.getFirst()));
        return murs;
    }
    
    
    public static Path2D getForme(List<Point> sommets){
        Path2D polygone = new Path2D.Double();
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
}
