package com.heatmyfloor.gui;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import com.heatmyfloor.domain.piece.PieceItemReadOnly;
import com.heatmyfloor.gui.drawer.PieceDrawer;
import com.heatmyfloor.domain.Point;

public class Canvas extends JPanel implements Serializable{

    private MainWindow mainWindow;
    private FormeIrregulierPanel dessinPanel;
    private PieceItemReadOnly itemSurvole;
    public PieceDrawer dessinateurPiece;
    
    // Zoom
    private double zoom = 1.0;                       // facteur de zoom (1 = 100 %)
    private Point originePx   // décalage (en pixels) du repère “monde” sur l’écran
            = new com.heatmyfloor.domain.Point(0, 0);
    
    private double clamp(double v) {
    if (v > 1e12) return 1e12;
    if (v < -1e12) return -1e12;
    return v;
}


    public double getZoom() { return zoom; }
    public com.heatmyfloor.domain.Point getOriginePx() { return originePx; }
    public void resetZoom() { zoom = 1.0; originePx = new com.heatmyfloor.domain.Point(0,0); }
    
    /** Convertit un point de la souris (pixels écran) → coordonnées monde */
    public Point toWorld(double sx, double sy) {
        double x = (sx - originePx.getX()) / zoom;
        double y = (sy - originePx.getY()) / zoom;
        return new com.heatmyfloor.domain.Point(x, y);
    }

    /** Convertit un point du monde → position sur l’écran (si besoin) */
    public Point toScreen(double wx, double wy) {
        double x = wx * zoom + originePx.getX();
        double y = wy * zoom + originePx.getY();
        return new com.heatmyfloor.domain.Point(x, y);
    }
    
    
    public Canvas() {
        setBackground(Color.white);
        setLayout(null); 
        //setBorder(BorderFactory.createLineBorder(new Color(140, 140, 140), 2)); 
        
         //Ajout gestion du zoom
        addMouseWheelListener(e -> {
    // rotation < 0 : on zoome / rotation > 0 : on dézoome
    int steps = e.getWheelRotation();
    if (steps == 0) return;

    double oldZoom = zoom;
    // facteur progressif (≈10% par “cran”)
    double factor = Math.pow(1.1, Math.abs(steps));
    double proposed = (steps < 0) ? oldZoom * factor : oldZoom / factor;

    // bornes : zoom infini
    double newZoom = proposed;
    if (newZoom < 1e-6) newZoom = 1e-6;   // presque zéro
    if (newZoom > 1e6)  newZoom = 1e6;    // zoom énorme

    // garder le point souris *immobile* à l’écran
    double mx = e.getX(), my = e.getY();
    double k = newZoom / oldZoom; // ratio de changement
    originePx = new com.heatmyfloor.domain.Point(
        clamp(mx - k * (mx - originePx.getX())),
        clamp(my - k * (my - originePx.getY()))
    );

    zoom = newZoom;
    repaint();
});

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
        
        Graphics2D g2 = (Graphics2D) g;

        g2.translate(originePx.getX(), originePx.getY());
        g2.scale(zoom, zoom);

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    
        if (mainWindow != null){
            dessinateurPiece = new PieceDrawer(mainWindow);
            dessinateurPiece.dessiner(g2);
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
