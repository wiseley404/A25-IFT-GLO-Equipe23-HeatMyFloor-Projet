package com.heatmyfloor.gui;

import com.heatmyfloor.domain.piece.PieceIrreguliere;
import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import com.heatmyfloor.domain.piece.PieceItemReadOnly;
import com.heatmyfloor.gui.drawer.PieceDrawer;
import com.heatmyfloor.domain.Point;
import com.heatmyfloor.domain.piece.PieceReadOnly;
import java.util.List;

public class Canvas extends JPanel implements Serializable {

    private MainWindow mainWindow;
    private FormeIrregulierPanel dessinPanel;
    private PieceItemReadOnly itemSurvole;
    public PieceDrawer dessinateurPiece;
    private boolean modeRealiste;

    // Zoom
    private double zoom = 1.0;
    private Point originePx = new Point(0, 0);

    private double clamp(double v) {
        if (v > 1e12) {
            return 1e12;
        }
        if (v < -1e12) {
            return -1e12;
        }
        return v;
    }

    private void applyZoom(double mx, double my, double factor) {
        if (factor == 0) {
            return;
        }

        double oldZoom = zoom;
        double proposed = oldZoom * factor;

        // bornes infinies
        double newZoom = proposed;
        if (newZoom < 1e-6) {
            newZoom = 1e-6;
        }
        if (newZoom > 1e6) {
            newZoom = 1e6;
        }

        double k = newZoom / oldZoom;

        originePx = new Point(
                clamp(mx - k * (mx - originePx.getX())),
                clamp(my - k * (my - originePx.getY()))
        );

        zoom = newZoom;

        if (mainWindow != null) {
            mainWindow.updateZoomLabel();
        }
        repaint();

    }

    public void zoomDepuisCentre(double factor) {
        double mx = getWidth() / 2.0;
        double my = getHeight() / 2.0;
        applyZoom(mx, my, factor);
    }

    public void resetZoomAndCenter() {
        zoom = 1.0;
        originePx = new Point(0, 0);
        repaint();
    }

    public double getZoom() {
        return zoom;
    }

    public Point getOriginePx() {
        return originePx;
    }

    public void resetZoom() {
        zoom = 1.0;
        originePx = new Point(0, 0);
    }

    /* Convertit un point de la souris (pixels écran) en coordonnées monde */
    public Point toWorld(double sx, double sy) {
        double x = (sx - originePx.getX()) / zoom;
        double y = (sy - originePx.getY()) / zoom;
        return new Point(x, y);
    }

    /* Convertit un point du monde en position sur l’écran */
    public Point toScreen(double wx, double wy) {
        double x = wx * zoom + originePx.getX();
        double y = wy * zoom + originePx.getY();
        return new Point(x, y);
    }

    public Canvas() {
        setBackground(Color.white);
        setLayout(null);
        addMouseWheelListener(e -> {
            int steps = e.getWheelRotation();
            if (steps == 0) {
                return;
            }

            double factor = Math.pow(1.1, Math.abs(steps));
            if (steps > 0) {
                factor = 1.0 / factor;
            }

            applyZoom(e.getX(), e.getY(), factor);
        });
    }

    public MainWindow getMainWindow() {
        return this.mainWindow;
    }

    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    @Override
    public Dimension getPreferredSize() {
        if (mainWindow.controllerActif != null && mainWindow.controllerActif.getPiece() != null) {
            PieceReadOnly piece = mainWindow.controllerActif.getPiece();
            double margeX = mainWindow.controllerActif.getPiece().getPosition().getX();
            double margeY = mainWindow.controllerActif.getPiece().getPosition().getY();
            return new Dimension((int) (margeX + piece.getLargeur() + margeX), (int) (margeY + piece.getHauteur() + margeY));
        }
        return new Dimension(800, 500); // Taille par défaut
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(new Color(255, 232, 200, 120));
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.translate(originePx.getX(), originePx.getY());
        g2.scale(zoom, zoom);

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (mainWindow != null) {
            dessinateurPiece = new PieceDrawer(mainWindow);
            dessinateurPiece.dessiner(g2);
        }
    }

    public PieceItemReadOnly getItemSurvole() {
        return this.itemSurvole;
    }

    public void setItemSurvole(PieceItemReadOnly item) {
        this.itemSurvole = item;
    }

    public void nettoyerModeDessin() {
        if (dessinPanel != null) {
            remove(dessinPanel);
            dessinPanel = null;
        }
        mainWindow.props.afficherProprietesItemSelectionne();
        mainWindow.props.afficherMurItemSelectionne();
        mainWindow.props.afficherProprietesPiece();
        mainWindow.props.afficherProprietesDrainSelectionne();
        mainWindow.panelPosition.afficherCoordItemSelectionne();
        mainWindow.panelPosition.afficherAngleItemSelectionne();
    }

    void dessinerFormeIrreguliere() {
        nettoyerModeDessin();
        dessinPanel = new FormeIrregulierPanel();
        dessinPanel.setOpaque(false);
        dessinPanel.setBounds(0, 0, getWidth(), getHeight());
        dessinPanel.activerModeDessin(true);
        if(dessinPanel != null){
            add(dessinPanel);
        }
        revalidate();
        repaint();
        dessinPanel.setOnFormeTerminee(points -> {
            if(points == null || points.size() < 3){
                return;
            }
            List<Point> sommets = com.heatmyfloor.domain.PointMapper.toDomainList(points);
            mainWindow.controllerActif.ajouterPiece(new PieceIrreguliere(sommets));
            mainWindow.props.afficherProprietesPiece();
            mainWindow.panelPosition.afficherCoordItemSelectionne();
            dessinPanel.activerModeDessin(false);
        });
    }

    public void dessinerFormeIrreguliere(PieceReadOnly piece) {
        // Supprime un ancien panel si existant

        if (!(piece instanceof PieceIrreguliere pir)) {
            throw new IllegalArgumentException("La pièce n'est pas irrégulière !");
        }
        nettoyerModeDessin();

        dessinPanel = new FormeIrregulierPanel(com.heatmyfloor.domain.PointMapper.toAwtList(pir.getSommets()));
        dessinPanel.setOpaque(false);
        dessinPanel.setBounds(0, 0, getWidth(), getHeight());
        dessinPanel.activerModeDessin(false);
        if(dessinPanel != null){
            add(dessinPanel);
        }
        revalidate();
        repaint();

            dessinPanel.getPoints().clear();
            for (com.heatmyfloor.domain.Point p : pir.getSommets()) {
                dessinPanel.getPoints().add(com.heatmyfloor.domain.PointMapper.toAwt(new Point((int) p.getX(), (int) p.getY())));
            }

        dessinPanel.setOnFormeTerminee(points -> {
            if(points == null || points.size() < 3){
                return;
            }
            List<Point> sommets = com.heatmyfloor.domain.PointMapper.toDomainList(points);
            mainWindow.controllerActif.ajouterPiece(new PieceIrreguliere(sommets));
            dessinPanel.activerModeDessin(false);
            mainWindow.props.afficherProprietesPiece();
            mainWindow.panelPosition.afficherCoordItemSelectionne();
            repaint();
        });
    }
    
    public FormeIrregulierPanel getDessinPanel(){
        return this.dessinPanel;
    }
    
    public boolean getModeRealiste(){
        return this.modeRealiste;
    }
    
    public void setModeRealiste(boolean modeRealiste){
        this.modeRealiste = modeRealiste;
    }

} 
