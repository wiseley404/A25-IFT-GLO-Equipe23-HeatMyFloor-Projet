/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heatmyfloor.domain;

import java.awt.geom.Rectangle2D;

/**
 *
 * @author tatow
 */
public class Rect2D {

    private Rectangle2D rect2D;
    public double x, y, width, height;

    public Rect2D(Rectangle2D r) {
        this.rect2D = r;
        this.x = r.getX();
        this.y = r.getY();
        this.width = r.getWidth();
        this.height = r.getHeight();
    }

    public boolean Contains(double x, double y) {
        return this.rect2D.contains(x, y);
    }

    public Rectangle2D GetBounds2D() {
        return this.rect2D.getBounds2D();
    }
}
