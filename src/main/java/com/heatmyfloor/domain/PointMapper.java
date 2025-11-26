/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heatmyfloor.domain;

import java.util.List;

/**
 *
 * @author tatow
 */
public class PointMapper {

    public static java.awt.Point toAwt(com.heatmyfloor.domain.Point p) {
        return new java.awt.Point(
                (int) Math.round(p.getX()),
                (int) Math.round(p.getY())
        );

    }

    public static com.heatmyfloor.domain.Point toDomain(java.awt.Point p) {
        return new com.heatmyfloor.domain.Point(p.x, p.y);
    }

    public static List<java.awt.Point> toAwtList(List<com.heatmyfloor.domain.Point> points) {
        return points.stream()
                .map(PointMapper::toAwt)
                .toList();
    }

    public static List<com.heatmyfloor.domain.Point> toDomainList(List<java.awt.Point> points) {
        return points.stream()
                .map(PointMapper::toDomain)
                .toList();
    }
}
