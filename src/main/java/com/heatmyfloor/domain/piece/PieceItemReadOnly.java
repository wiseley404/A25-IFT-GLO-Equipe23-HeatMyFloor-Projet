package com.heatmyfloor.domain.piece;

import com.heatmyfloor.domain.Point;
import java.util.UUID;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author petit
 */
public interface PieceItemReadOnly {
    UUID getID();
    double getHauteur();
    double getLargeur();
    Point getPosition();
    Rectangle2D getItemForme();
    boolean contientLePoint(Point position);
    double getAngle();
    String getImage();
    boolean estSelectionne();
}
