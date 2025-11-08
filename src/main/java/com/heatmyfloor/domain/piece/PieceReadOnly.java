/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.heatmyfloor.domain.piece;
import com.heatmyfloor.domain.Point;

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
}
