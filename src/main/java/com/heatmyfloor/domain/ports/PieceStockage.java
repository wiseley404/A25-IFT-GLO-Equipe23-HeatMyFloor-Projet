/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.heatmyfloor.domain.ports;

import com.heatmyfloor.domain.piece.Piece;
import java.nio.file.Path;

/**
 *
 * @author petit
 */
public interface PieceStockage {
    Piece ouvrirFichier(Path cheminFichier);
    void saveFichier(Piece p, Path cheminSauvegarde);
    void exporterFichierPng(Piece p, Path cheminExport);
}
