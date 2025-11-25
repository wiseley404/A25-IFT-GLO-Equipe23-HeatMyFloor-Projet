/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.heatmyfloor.domain.piece;
import com.heatmyfloor.domain.Point;
import com.heatmyfloor.domain.items.DrainReadOnly;
import java.awt.Shape;
import java.util.List;
/**
 *
 * @author petit
 */
public interface PieceReadOnly {
    double getLargeur();
    double getHauteur();
    Point getPosition();
    Point getCentre();
    boolean contientLePoint(Point position);
    boolean contientLaForme(Shape itemRotation);
    Point getExtremiteHautGauche();
    Point getExtremiteHautDroite();
    Point getExtremiteBasGauche();
    Point getExtremiteBasDroite();
    Point getExtremiteHautMilieu();
    Point getExtremiteBasMilieu();
    
    
}
