/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heatmyfloor.domain;
import java.io.Serializable;
import java.util.Objects;
/**
 *
 * @author petit
 */
public class Point implements Serializable {

    public Point() {
    }

    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {

        return "(" + x + " , " + y + ")";
    }
    
    @Override
    public boolean equals(Object obj){
        if(this == obj)return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Point autre = (Point) obj;
        return Double.compare(this.x, autre.x) == 0 &&
                Double.compare(this.y, autre.y) == 0;
    }
    
    @Override
    public int hashCode(){
        return Objects.hash(x, y);
    }
}
