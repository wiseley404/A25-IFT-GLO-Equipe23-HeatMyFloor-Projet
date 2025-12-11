/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heatmyfloor.domain.items;
import com.heatmyfloor.domain.Point;
import com.heatmyfloor.domain.piece.PieceItem;
import com.heatmyfloor.domain.piece.TypePiece;
/**
 *
 * @author petit
 */
public class Zone extends PieceItem{

   
    
    public enum TypeZone{
        INTERDITE("/images/zoneInterdite.png"),
        TAMPON("/images/zoneTampon.png");
        
        private final String image;
        
        TypeZone(String image){
            this.image = image;
        }
        
        public String getImage(){
            return this.image;
        }
    }
    
    public Zone(double largeur, double hauteur, Point position, TypeZone type){
        super(largeur, hauteur, position, type.getImage());
    }
}
