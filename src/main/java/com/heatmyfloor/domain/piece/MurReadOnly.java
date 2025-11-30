/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.heatmyfloor.domain.piece;

import com.heatmyfloor.domain.Point;
import java.util.UUID;

/**
 *
 * @author petit
 */
public interface MurReadOnly {
    Point getDebut();
    Point getFin();
    double getDistanceAvecFil();
    UUID getId();
    double getAngle();
    Point getCentre();
}
