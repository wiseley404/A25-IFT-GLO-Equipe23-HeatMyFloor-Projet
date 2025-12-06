/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heatmyfloor.domain;


/**
 *
 * @author petit
 */
public class Util {
    public static final double PIXEL_PAR_POUCE;
    static{
     PIXEL_PAR_POUCE = 4.5;
    }

    public static final double POUCES_PAR_PIED = 12.0;
 

    public static double enPixels(String raw){
        if (raw == null) {
            throw new IllegalArgumentException ("Données invalides : Saisie vide");
        }
        raw = raw.trim();

        double totalInches = 0.0;

        String rest = raw;
        rest = rest.replaceAll("[\\u2018\\u2019\\u2032`’]", "'");
        rest = rest.replaceAll("[\\u201C\\u201D\\u2033″“]", "\"");
        rest = rest.replace('\u00A0',' ');
        
        //Conversion Pieds
        int idxFeet = rest.indexOf("'");
        if (idxFeet >= 0) { 
            String feetStr = rest.substring(0, idxFeet).trim();
            if (!feetStr.matches("\\d+")){
                throw new IllegalArgumentException ("Données invalides : Entrez une valeur entière pour pieds");          
            }
            int feet = Integer.parseInt(feetStr);
            totalInches += feet * 12.0;
            rest = rest.substring(idxFeet + 1).trim();
        }
        
        //Coversion Pouces
        int idxInch = rest.indexOf('"');
        if (idxInch >=0) {
           String inchStr = rest.substring(0, idxInch).trim();
           if (!inchStr.isEmpty()) {
               if (!inchStr.matches("\\d+")){
                   throw new IllegalArgumentException ("Données invalides : Entrez une valeur entière pour pouces");          
               }
               int inches = Integer.parseInt(inchStr);
               totalInches += inches;

            rest = rest.substring(idxInch + 1).trim();
        }}

        // Conversion Fraction de pouces
        if (!rest.isEmpty()) {
            if (!rest.matches("\\d+\\/\\d+")) {
                throw new IllegalArgumentException ("Données invalides : Fraction de pouces invalide");          
            }
            String[] frac = rest.split("/");
            double num = Double.parseDouble(frac[0]);
            double den = Double.parseDouble(frac[1]);
            if (den == 0) throw new IllegalArgumentException ("Données invalides : Le dénominateur ne peut pas etre zéro");          

            totalInches += num / den;

        }
        
        return roundTo32nd(totalInches)*PIXEL_PAR_POUCE;
    }

    public static double enPixels(double inches){
        return inches*PIXEL_PAR_POUCE;
    }     

    private static double roundTo32nd(double inches) {
        double step = 1.0 / 32.0;
        return Math.round(inches / step) * step;
    }
        

    public static double enPouces(double enPixels){
        return enPixels/PIXEL_PAR_POUCE;
    }
    
    
    public static String formatImperial(double inches) {
        inches = roundTo32nd(inches);

        int feet = (int) (inches / 12.0);
        double rem = inches - feet * 12.0;

        int wholeInches = (int) rem;
        double frac = rem - wholeInches;

        int num = (int) Math.round(frac * 32); // précision au 1/32
        int den = 32;

        // Si l’arrondi donne 32/32, on ajoute 1" et remet la fraction à 0
        if (num == den) {
            wholeInches += 1;
            num = 0;
        }

        int g = gcd(num, den);
        if (g != 0) {
            num /= g;
            den /= g;
        }

        StringBuilder sb = new StringBuilder();
        if (feet > 0) sb.append(feet).append("' ");
        if (wholeInches > 0 || (feet == 0 && num == 0)) {
            sb.append(wholeInches).append("\" ");
        }
        if (num > 0) {
            sb.append(num).append("/").append(den);
        }

        return sb.toString().trim();
    }
        
        
    private static int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        if (a == 0) return b;
        if (b == 0) return a;
        while (b != 0) {
            int tmp = a % b;
            a = b;
            b = tmp;
        }
        return a;
    }
     

}
        
       
        
        
        

