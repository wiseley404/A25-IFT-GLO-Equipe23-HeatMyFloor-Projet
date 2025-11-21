package com.heatmyfloor.domain.piece;

import com.heatmyfloor.domain.Point;
import com.heatmyfloor.domain.Rect2D;
import java.awt.geom.Rectangle2D;
import java.util.UUID;

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
