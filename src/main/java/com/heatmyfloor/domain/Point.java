/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heatmyfloor.domain;

/**
 *
 * @author petit
 */
public class Point {
    public Point(){}
    

    private double x;
    private double y;
    
    
    //Constructeur
    
    public Point(double x, double y){
        
        this.x = x;
        this.y = y;
    }
    
    
    //Getters
    
    public double getX(){
        
        return x;
    }
    
    
    public double getY(){
        
        return y;
    }
    
    
    //Setters
    
    public void setX(double x){
        
        this.x = x;
    }
    
    
    public void setY(double y){
        
        this.y = y;
    }
    
    @Override
    public String toString(){
        
        return "(" + x + " , " + y + ")";
    }
    

}
