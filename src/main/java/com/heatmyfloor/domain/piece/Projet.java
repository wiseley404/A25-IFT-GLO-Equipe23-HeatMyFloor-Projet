/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heatmyfloor.domain.piece;

/**
 *
 * @author tatow
 */
public class Projet {

    private String _nom;
    private Piece _piece;
    
    
    //Props

    public String getNom() {
        return _nom;
    }

    public void setNom(String nom) {
        _nom = nom;
    }
    
    public Piece getPiece() {
        return _piece;
    }

    public void setPiece(Piece piece) {
       _piece = piece;
    }

    public Projet(String nom, Piece piece) {
        _piece = piece;
        _nom = nom;
    }
    
    public Projet(){
        this("Projet ",new PieceRectangulaire(400,300));
    }

}
