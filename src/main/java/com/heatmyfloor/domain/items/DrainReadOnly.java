/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.heatmyfloor.domain.items;

import com.heatmyfloor.domain.Point;
import java.util.UUID;
import java.awt.geom.Ellipse2D;

/**
 *
 * @author petit
 */
public interface DrainReadOnly {
    
    double getDiametre();
    UUID getId();
    Point getPosition();
    double getDistanceAvecFil();
    boolean estSelectionne();
    Ellipse2D getForme();
    boolean contientLePoint(Point pos);
    void translater(Point delta);
    
}
