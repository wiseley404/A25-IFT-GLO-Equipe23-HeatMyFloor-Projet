package com.heatmyfloor.gui;

import javax.swing.*;
import java.awt.*;
import javax.swing.JLabel;
import java.io.Serializable;
import com.heatmyfloor.domain.piece.PieceItemReadOnly;
import com.heatmyfloor.gui.drawer.PieceDrawer;

public class Canvas extends JPanel implements Serializable{



    private Rectangle rectangle;
    private MainWindow mainWindow;
    private FormeIrregulierPanel dessinPanel;
    private PieceItemReadOnly itemSurvole;
    public PieceDrawer dessinateurPiece;
      private int longueur;
    private int largeur;
    public Canvas(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }
    //private final Controller controller;

    public Canvas() {
        //this.controller = controller;
        setBackground(Color.white);
        setLayout(null); // absolute positioning for placeholder icons
        setBorder(BorderFactory.createLineBorder(new Color(140, 140, 140), 2));
//        JLabel tub = createIconLabel("/icons/tub.svg", 30, 20, 60, 160);
//        add(tub);
//        JLabel sink = createIconLabel("/icons/sink.svg", 200, 20, 80, 40);
//        add(sink);
//        JLabel toilet = createIconLabel("/icons/toilet.svg", 300, 20, 48, 48);
//        add(toilet);
//        JLabel cabinet = createIconLabel("/icons/cabinet.svg", 1020, 120, 60, 220);
//        add(cabinet);
              
    }
    
   /* public void ajusterRectangle(int longueur, int largeur) {
    // Sauvegarde des dimensions
    this.longueur = longueur;
    this.largeur = largeur;

    if (rectangle == null) {
        rectangle = new Rectangle(0, 0, longueur, largeur);
    } else {
        rectangle.setSize(longueur, largeur);
    }*/

    // Ajuste la taille du canvas
    /*Dimension taille = new Dimension(
        Math.max(longueur + 100, getWidth()),
        Math.max(largeur + 100, getHeight())
    );
    setPreferredSize(taille);
    revalidate();

    repaint();
}*/

    public void dessinerRectangle(int longueur, int largeur) {

        if (dessinPanel != null) {
            remove(dessinPanel);
            dessinPanel.effacer();
        }

        int x = (getWidth() - longueur) / 2;
        int y = (getHeight() - largeur) / 2;
        rectangle = new Rectangle(x, y, longueur, largeur);
        repaint();

        
    }
    

    
    
   /* public Dimension getTailleRectangle() {
    if (rectangle == null) return getSize();
    int largeur = rectangle.x + rectangle.width;
    int hauteur = rectangle.y + rectangle.height;
    return new Dimension(largeur, hauteur);
   
}*/
    

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(new Color(255, 232, 200, 120));
        super.paintComponent(g);
        if (rectangle != null) {
            g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        }
    }
    
    public PieceItemReadOnly getItemSurvole(){
        return this.itemSurvole;
    }
    
    public void setItemSurvole(PieceItemReadOnly item){
        this.itemSurvole = item;
    }
    
    private JLabel createIconLabel(String resPath, int x, int y, int w, int h) {
        JLabel l = new JLabel();
        l.setBounds(x, y, w, h);
        try {
            l.setIcon(new ImageIcon(getClass().getResource(resPath)));
        } catch (Exception e) {
        }
        l.setBorder(BorderFactory.createDashedBorder(Color.GRAY));
        return l;
    }
           
    void dessinerFormeIrreguliere() {
        rectangle = null;
        if (dessinPanel != null) {
            remove(dessinPanel);
        }

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
