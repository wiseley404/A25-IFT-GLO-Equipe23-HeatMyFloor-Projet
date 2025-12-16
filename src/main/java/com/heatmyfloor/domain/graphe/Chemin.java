/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heatmyfloor.domain.graphe;
import com.heatmyfloor.domain.Point;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
import java.util.Collections;

/**
 *
 * @author petit
 */
public class Chemin implements Serializable {

    private final List<Intersection> parcours = new ArrayList<>();

    public List<Intersection> getParcours() {
        return parcours;
    }

    public void ajouter(Intersection i) {
        parcours.add(i);
    }

    public double longueurTotale() {
        double total = 0;
        for (int i = 1; i < parcours.size(); i++) {
            Point a = parcours.get(i - 1).getCoordonees();
            Point b = parcours.get(i).getCoordonees();
            double dx = b.getX() - a.getX();
            double dy = b.getY() - a.getY();
            total += Math.sqrt(dx * dx + dy * dy);
        }
        return total;
    }
}

