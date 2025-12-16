/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heatmyfloor.domain.items;

import com.heatmyfloor.domain.Point;
import com.heatmyfloor.domain.Util;
import com.heatmyfloor.domain.piece.PieceItem;

/**
 *
 * @author petit
 */
public class MeubleSansDrain extends PieceItem {

    //Attributs
    private double distanceAvecFil;
    private TypeSansDrain type;

    //Constructeur
    public MeubleSansDrain(double largeur, double hauteur, Point pos, TypeSansDrain type) {
        super(largeur, hauteur, pos, type.getImage());
        this.distanceAvecFil = Util.enPixels(3.0);
        this.type = type;
    }

    public MeubleSansDrain() {
        super();
    }

    //Méthode
    @Override
    public double getDistanceAvecFil() {
        return this.distanceAvecFil;
    }

    public TypeSansDrain getType() {
        return this.type;
    }
    
}
