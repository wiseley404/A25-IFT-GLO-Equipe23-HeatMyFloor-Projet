/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heatmyfloor;
import java.awt.*;
import java.awt.SplashScreen;
/**
 *
 * @author petit
 */

//Crédit : Documentation Officielle Java de Splash Screen sur docs.oracle.com avec quelques modifications
public class LoadingScreen {
    static void renderSplashFrame(Graphics2D g, int frame){
        
        Color couleurBarreArrierePlan = Color.BLACK;
        Color couleurBarreProgression = new Color (0xF3B24E);
        
        int largeurBarre = 400;
        int hauteurBarre = 20;
        int xPosition = 50;
        int yPosition = 350;
        int coinArrondi = 10;
        
        g.setColor(couleurBarreArrierePlan);
        g.fillRoundRect(xPosition, yPosition, largeurBarre, hauteurBarre, coinArrondi, coinArrondi);
        
        
        int largeur = (int) ((frame / 100) * largeurBarre);
        g.setColor(couleurBarreProgression);
        g.fillRoundRect(xPosition, yPosition, largeur, hauteurBarre, coinArrondi, coinArrondi);
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 14));
        g.drawString("Loading " + frame + "%", xPosition + largeurBarre / 2 - 30, yPosition + hauteurBarre -5);
        
    }
    
    public static void show(){
        final SplashScreen splash =  SplashScreen.getSplashScreen();
        if(splash == null){
            System.out.println("SplashScreen.getSplashScreen() returned null");
            return;
        }
        
        Graphics2D g = splash.createGraphics();
        if(g == null){
            System.out.println("g is null");
            return;
        }
        
        for(int i = 0; i <= 100; i++){
            renderSplashFrame(g, i);
            splash.update();
            try{
                Thread.sleep(30);
            }catch(InterruptedException e){
                
            }
        }
        
        splash.close();
    }
}
