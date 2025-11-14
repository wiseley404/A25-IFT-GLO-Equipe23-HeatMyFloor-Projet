/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heatmyfloor.domain;

import java.awt.Toolkit;

/**
 *
 * @author petit
 */
public class Util {
   // public static Point pixelsVersPouces(double pxX, double pxY, Point originePx, double zoom, double ppp) {
     //   return null;
  //  }
    
    public static final double Pixel_Par_Pouce;
    static{
        int dpi;
        try{
            dpi = Toolkit.getDefaultToolkit().getScreenResolution();
        } catch(Exception e) {
            dpi = 96; // Valeur par defaut
        }
        Pixel_Par_Pouce = dpi;
    }
    
    public static final double METRE_PAR_POUCE = 0.0254;
    public static final double POUCES_PAR_PIED = 12.0;
 
    public enum Unite {
        METRE(1.0 / METRE_PAR_POUCE),
        POUCE(1.0),
        PIEDS(POUCES_PAR_PIED);
        //PIXELS;
        
        private final double pouces;
        
        Unite(double pouces){
            this.pouces = pouces;
        }
        
        public double enPouces(double valeur){
            return valeur * pouces;
        }
    }
    
    public Util () {}
    
    public static double enPixels(double valeur, Unite unite){
        double pouces = unite.enPouces(valeur);
        return pouces * Pixel_Par_Pouce;
    }
    
    public static double enUnite(double pixels, Unite unite){
        double pouces = pixels / Pixel_Par_Pouce;
        return pouces /unite.pouces;
    }
    
    
}
