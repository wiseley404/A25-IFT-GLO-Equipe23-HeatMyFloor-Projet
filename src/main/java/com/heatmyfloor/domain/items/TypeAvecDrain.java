

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
    
    TOILETTE("/images/toilette_dessin.png", "/images/toilette_realiste"),
    DOUCHE("/images/douche_dessin.png", "/images/douche_realiste.png"),
    VANITÉ("/images/vanite_dessin.png", "/images/vanite_realiste.png"),
    BAIN("/images/bain_dessin.png", "/images/bain_realiste.png");
    
    private final String imageDessin;
    private final String imageRealiste;
    
    TypeAvecDrain(String imageDessin, String imageRealiste){
        this.imageDessin = imageDessin;
        this.imageRealiste = imageRealiste;
    }
    
    public String getImage(boolean modeRealiste){
        return modeRealiste? this.imageRealiste : this.imageDessin;
    }
}