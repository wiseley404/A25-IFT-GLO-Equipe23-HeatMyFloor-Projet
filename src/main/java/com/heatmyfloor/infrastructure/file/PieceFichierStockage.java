/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heatmyfloor.infrastructure.file;

import com.heatmyfloor.domain.piece.Piece;
import com.heatmyfloor.domain.ports.PieceStockage;
import java.nio.file.Path;

/**
 *
 * @author petit
 */
public class PieceFichierStockage implements PieceStockage{
    public PieceFichierStockage(){}
    
    @Override
    public Piece ouvrirFichier(Path fichierCharge){
        throw new UnsupportedOperationException("Méthode non implémentée !");
    }
    
    @Override
    public void saveFichier(Piece piece, Path fichier){
        
    }
    
    @Override
    public void exporterFichierPng(Piece piece, Path fichier){
        
    } 
}
