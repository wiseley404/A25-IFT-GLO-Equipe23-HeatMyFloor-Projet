

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.heatmyfloor.domain.items;

/**
 *
 * @author Kimberly
 */
public enum TypeAvecDrain {
    
    TOILETTE("/images/toilette.png"),
    DOUCHE("/images/douche.png"),
    VANITE("/images/vanite.png"),
    BAIN("/images/bain.png");
    
    private final String image;
    
    TypeAvecDrain(String image){
        this.image = image;
    }
    
    public String getImage(){
        return this.image;
    }
}