/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heatmyfloor.gui.drawer;

import com.heatmyfloor.domain.Point;
import com.heatmyfloor.domain.piece.Controller;
import com.heatmyfloor.gui.MainWindow;
import com.heatmyfloor.domain.piece.PieceItemReadOnly;
import com.heatmyfloor.gui.PositionPanel;
import com.heatmyfloor.gui.Canvas;
import com.heatmyfloor.gui.TableauErreur;
import com.heatmyfloor.gui.Proprietes;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;


/**
 *
 * @author petit
 */
public class PieceDrawer {
    private final Controller controller;
    private Dimension canvasDimension;
    private Proprietes props;
    private PositionPanel panelPosition;
    private Canvas currentCanvas;
    
    public PieceDrawer(MainWindow mainWindow){
        this.controller = mainWindow.controller;
        this.canvasDimension = new Dimension(mainWindow.currentCanvas.getWidth(), mainWindow.currentCanvas.getHeight());
        this.props = mainWindow.props;
        this.panelPosition = mainWindow.panelPosition;
        this.currentCanvas = mainWindow.currentCanvas;
    }
    
    
    public void dessiner(Graphics g){
        dessinerPieceRectangulaire(g);
        dessinerPieceItems(g);
    }
    

    public void dessinerPieceRectangulaire(Graphics g){
      Graphics2D g2 = (Graphics2D) g;
      double largeur = controller.getPiece().getLargeur();
      double hauteur = controller.getPiece().getHauteur();
      double x = (canvasDimension.getWidth() - largeur) / 2;
      double y = (canvasDimension.getHeight() - hauteur) / 2;
      controller.repositionnerPiece(new Point(x,y));
      
      Rectangle2D pieceRectangulaire = new Rectangle2D.Double(x, y, largeur, hauteur);

      g2.setColor(new Color(220, 220, 220));
      g2.fill(pieceRectangulaire);
      g2.setColor(Color.BLACK);
      g2.setStroke(new BasicStroke(1.5f));
      g2.draw(pieceRectangulaire);
    }
    
    
    
    public void dessinerPieceItems(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        
        List<PieceItemReadOnly> items = this.controller.getItemsList();
        for(PieceItemReadOnly item : items){
            if(item.estSelectionne()){
                g2.setColor(Color.BLUE);
                Rectangle2D contourSelection = item.getItemForme().getBounds2D();
                g2.draw(contourSelection);
                
                int ovalCoinSize = 15;
                g2.setColor(Color.WHITE);
                g2.fillOval(
                        (int)contourSelection.getX() - ovalCoinSize/2,
                        (int)contourSelection.getY() - ovalCoinSize/2,
                        ovalCoinSize, ovalCoinSize
                );
                g2.fillOval(
                        (int)(contourSelection.getX() + contourSelection.getWidth()) - ovalCoinSize/2,
                        (int)contourSelection.getY() - ovalCoinSize/2,
                        ovalCoinSize, ovalCoinSize
                );
                g2.fillOval(
                        (int)contourSelection.getX() - ovalCoinSize/2,
                        (int)(contourSelection.getY() + contourSelection.getHeight()) - ovalCoinSize/2,
                        ovalCoinSize, ovalCoinSize
                );
                g2.fillOval(
                        (int)(contourSelection.getX()+ contourSelection.getWidth()) - ovalCoinSize/2,
                        (int)(contourSelection.getY() + contourSelection.getHeight()) - ovalCoinSize/2,
                        ovalCoinSize, ovalCoinSize
                );   
            }
            
            
            if (currentCanvas.getItemSurvole() == item && !item.estSelectionne()){
                g2.setColor(Color.BLUE);
                g2.draw(item.getItemForme().getBounds2D());
            }
            
            BufferedImage itemImage = null;
            URL imageUrl = getClass().getResource(item.getImage());
            if(imageUrl != null){
                try {
                    itemImage = ImageIO.read(imageUrl);
                }catch(IOException e){
                    throw new RuntimeException("Echec du chargement de l'image", e);
                }
            }
            
            if(itemImage != null){
                g2.drawImage(itemImage, 
                        (int)120, 
                        (int)120, 
                        null);
            }
            
        }
        if(controller.trouverItemSelectionne() != null){
            props.afficherProprietesItemSelectionne();
            panelPosition.afficherCoordItemSelectionne();
            panelPosition.afficherAngleItemSelectionne();
        }
        
    }
}
