package com.heatmyfloor.gui;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import com.heatmyfloor.domain.piece.PieceItemReadOnly;
import com.heatmyfloor.gui.drawer.PieceDrawer;

public class Canvas extends JPanel implements Serializable{

    private MainWindow mainWindow;
    private FormeIrregulierPanel dessinPanel;
    private PieceItemReadOnly itemSurvole;
    public PieceDrawer dessinateurPiece;

    public Canvas() {
        setBackground(Color.white);
        setLayout(null); 
        setBorder(BorderFactory.createLineBorder(new Color(140, 140, 140), 2));      
    }
    
    public MainWindow getMainWindow(){
        return this.mainWindow;
    }
    
    public void setMainWindow(MainWindow mainWindow){
        this.mainWindow = mainWindow;
    }
    

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(new Color(255, 232, 200, 120));
        super.paintComponent(g);
        if (mainWindow != null){
            dessinateurPiece = new PieceDrawer(mainWindow);
            dessinateurPiece.dessiner(g);
        }
    }
    
    public PieceItemReadOnly getItemSurvole(){
        return this.itemSurvole;
    }
    
    public void setItemSurvole(PieceItemReadOnly item){
        this.itemSurvole = item;
    }
    
    public void nettoyerModeDessin(){
        if (dessinPanel != null) {
            remove(dessinPanel);
            dessinPanel = null;
        }
    }
           
    void dessinerFormeIrreguliere() {
        nettoyerModeDessin();
        dessinPanel = new FormeIrregulierPanel();
        dessinPanel.setOpaque(false);
        dessinPanel.setBounds(0, 0, getWidth(), getHeight());
        dessinPanel.activerModeDessin(true);

        add(dessinPanel);
        revalidate();
        repaint();

        JOptionPane.showMessageDialog(this,
                "Cliquez pour placer des points.\nDouble-cliquez pour fermer la forme.",
                "Mode dessin", JOptionPane.INFORMATION_MESSAGE);
    }
}
