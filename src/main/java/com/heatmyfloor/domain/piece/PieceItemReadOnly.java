package com.heatmyfloor.domain.piece;

import com.heatmyfloor.domain.Point;
import java.util.UUID;

/**
 *
 * @author petit
 */
public interface PieceItemReadOnly {
    UUID getID();
    double getLongueur();
    double getLargeur();
    Point getPosition();
    int getAngle();
    String getImage();
    boolean estSelectionne();
}
