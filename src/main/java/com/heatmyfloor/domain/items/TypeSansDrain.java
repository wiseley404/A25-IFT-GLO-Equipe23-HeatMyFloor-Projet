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
    
    PLACARD("/images/placard_dessin.png", "/images/placard_realiste.png"),
    ARMOIRE("/images/armoire_dessin.png", "/images/armoire_realiste.png");
    
    private final String imageDessin;
    private final String imageRealiste;
    
    TypeSansDrain(String imageDessin, String imageRealiste){
        this.imageDessin = imageDessin;
        this.imageRealiste = imageRealiste;
    }
    
    public String getImage(boolean modeRealiste){
        return modeRealiste? imageRealiste : imageDessin;
    }
    
}