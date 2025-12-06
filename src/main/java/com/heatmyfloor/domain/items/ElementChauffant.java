/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heatmyfloor.domain.items;

import com.heatmyfloor.domain.Point;
import com.heatmyfloor.domain.piece.Mur;
import com.heatmyfloor.domain.piece.Piece;
import com.heatmyfloor.domain.piece.PieceItem;

/**
 *
 * @author petit
 */
public class ElementChauffant extends PieceItem {
    private Mur mur;
    private double distanceAvecFil = 8.0;
    //Constructeur
    public ElementChauffant(double largeur, double hauteur, Point pos) {
        super(largeur, hauteur, pos, "/images/elementChauffant.png");
    }

    public ElementChauffant() {
        super();
    }

    public void positionnerSurMur(Mur mur, Piece piece){
        mur.positionnerItem(this, piece);
        this.mur = mur;
    }
    
    public void setMur(Mur mur){
        this.mur = mur;
    }
    
    public Mur getMur(){
        return this.mur;
    }
    
    @Override
    public double getDistanceAvecFil() {
        return this.distanceAvecFil;
    }
}
