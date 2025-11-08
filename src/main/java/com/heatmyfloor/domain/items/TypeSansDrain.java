/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.heatmyfloor.domain.items;

/**
 *
 * @author Kimberly
 */
public enum TypeSansDrain {
    
    PLACARD("/images/placard.png"),
    ARMOIRE("/images/armoire.png");
    
    private final String image;
    
    TypeSansDrain(String image){
        this.image = image;
    }
    
    public String getImage(){
        return this.image;
    }
    
}