package com.heatmyfloor.gui;

import javax.swing.*;
import java.awt.*;
import com.heatmyfloor.domain.items.TypeSansDrain;
import com.heatmyfloor.domain.items.TypeAvecDrain;
import com.heatmyfloor.domain.items.Zone;
import java.awt.event.ComponentAdapter;
import com.heatmyfloor.domain.Point;

/**
 *
 * @author tatow
 */
public class BarreOutils extends JPanel {

    private MainWindow mainWindow;
    private BarreOutilsActions actions;
    public ButtonCard btnNouveau;
    public ButtonCard btnOuvrir;
    public ButtonCard btnExporter;
    public ButtonCard btnEnregistrer;
    public ButtonCard btnRectangle;
    private ButtonCard btnDrain;
    private ButtonCard btnIrregulier;
    private ButtonCard btnMenuSansDrain;
    private ButtonCard btnMenuAvecDrain;
    private ButtonCard btnThermostat;
    private ButtonCard btnElementChauffant;
    private ButtonCard btnMenuZones;
    private FormeIrregulierPanel dessinPanel;
    public ButtonCard btnZoomIn;
    public ButtonCard btnZoomOut;
    private JLabel valeurZoom;




    public void setZoomPercent(double zoomFactor) {
    if (valeurZoom != null) {
        valeurZoom.setText(String.format("%.0f %%", zoomFactor * 100.0));
    }
}
    public BarreOutils(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 200, 160)));
        setOpaque(true);
        setBackground(Color.white);

        addButton();

    }

    public void onRectangleClick(Runnable r) {
        btnRectangle.setOnClick(e -> r.run());
    }

    public void onIrregularButtonClick(Runnable r) {
        btnIrregulier.setOnClick(e -> r.run());
    }

    private void addGroup(JPanel parent, GridBagConstraints base, int gridx, JComponent group, double weightx) {
        GridBagConstraints gc = (GridBagConstraints) base.clone();
        gc.gridx = gridx;
        gc.weightx = weightx;
        parent.add(group, gc);
    }

    private void addSep(JPanel parent, GridBagConstraints base, int gridx) {
        GridBagConstraints gc = (GridBagConstraints) base.clone();
        gc.gridx = gridx;
        gc.weightx = 0;
        gc.insets = new Insets(8, 0, 8, 0);
        parent.add(vSep(), gc);
    }

    private JComponent makeGroup(String title, ButtonCard... cards) {
        JPanel col = new JPanel();
        col.setOpaque(false);
        col.setLayout(new BoxLayout(col, BoxLayout.Y_AXIS));

        JPanel row = new JPanel(new GridLayout(1, cards.length, 12, 0));
        row.setOpaque(false);
        for (ButtonCard c : cards) {
            row.add(c);
        }

        JLabel lbl = new JLabel(title, SwingConstants.CENTER);
        lbl.setAlignmentX(0.5f);
        lbl.setForeground(new Color(70, 70, 70));
        lbl.setFont(lbl.getFont().deriveFont(Font.PLAIN, 13f));

        col.add(row);
        col.add(Box.createVerticalStrut(6));
        col.add(lbl);
        return col;
    }

    private JComponent vSep() {
        JSeparator s = new JSeparator(SwingConstants.VERTICAL);
        s.setForeground(new Color(210, 190, 160));
        s.setPreferredSize(new Dimension(1, 64));
        return s;
    }

    private ButtonCard card(String text, String resPath) {
        return new ButtonCard(text, loadIcon(resPath));
    }

    private ImageIcon loadIcon(String path) {
        java.net.URL url = getClass().getResource(path);
        return (url != null) ? new ImageIcon(url) : null;
    }

    private void addButton() {
        JPanel ribbon = new JPanel(new GridBagLayout());
        ribbon.setOpaque(false);

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridy = 0;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(8, 12, 8, 12);

        int col = 0;

        //Projet
        btnNouveau = card("Nouveau", "/Icons/NouveauProjet.png");
        btnOuvrir = card("Ouvrir", "/Icons/OuvrirProjet.png");
        btnEnregistrer = card("Enregistrer", "/Icons/save.png");
        btnExporter = card("Exporter", "/Icons/Exporter.png");

        addGroup(ribbon, gc, col++, makeGroup("Projet",
                btnNouveau, btnOuvrir, btnEnregistrer, btnExporter
        ), 4);
        addSep(ribbon, gc, col++);

        //Affichage
        addGroup(ribbon, gc, col++, makeGroup("Affichage",
                card("Vue 2D", "/Icons/2D.png"),
                card("Vue 3D", "/Icons/3D.png")
        ), 2);
        addSep(ribbon, gc, col++);

        //Formes
        btnRectangle = card("Rectangle", "/Icons/Rectangle.png");
        btnDrain = card("Drain", "/Icons/Drain.png");
        btnIrregulier = card("Irregulière", "/Icons/Polygone.png");
        addGroup(ribbon, gc, col++, makeGroup("Formes",
                btnRectangle,
                btnDrain,
                btnIrregulier
        ), 3);
        addSep(ribbon, gc, col++);

        //Meubles
        btnMenuSansDrain = card("Sans drain", "/Icons/MeubleSansDrain.png");
        btnMenuAvecDrain = card("Avec drain", "/Icons/MeubleAvecDrain.png");
        addGroup(ribbon, gc, col++, makeGroup("Meubles",
                btnMenuSansDrain,
                btnMenuAvecDrain
        ), 2);
        addSep(ribbon, gc, col++);

        //Autres
        btnThermostat = card("Thermostat", "/Icons/Thermostat.png");
        btnElementChauffant = card("A. Chauffants", "/Icons/ElementChauffant.png");
        btnMenuZones = card("Zones", "/Icons/zone.png");
        addGroup(ribbon, gc, col++, makeGroup("Autres",
                btnThermostat,
                btnElementChauffant,
                btnMenuZones
                
        ), 3);
        
        // Zoom
        JLayeredPane layeredZoom = new JLayeredPane();
        layeredZoom.setLayout(null);
        ribbon.setBounds(0, 0, 2000, 120);
        layeredZoom.add(ribbon, JLayeredPane.DEFAULT_LAYER);
        
        JPanel panelZoom = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 2));
        panelZoom.setOpaque(false);
        
        ImageIcon zoomImage = new ImageIcon(getClass().getResource("/Icons/zoomer.png"));
        Image zoomImg = zoomImage.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
        JButton btnZoom = new JButton(new ImageIcon(zoomImg));
        btnZoom.setToolTipText("Zoomer");
        btnZoom.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnZoom.setPreferredSize(new Dimension(40, 25));
        btnZoom.setBorderPainted(false);
        btnZoom.setContentAreaFilled(false);
        btnZoom.setFocusPainted(false);
        
        ImageIcon dezoomImage = new ImageIcon(getClass().getResource("/Icons/dezoomer.png"));
        Image dezoomImg = dezoomImage.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
        JButton btnDezoom = new JButton(new ImageIcon(dezoomImg));
        btnDezoom.setToolTipText("Dézoomer");
        btnDezoom.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnDezoom.setPreferredSize(new Dimension(40, 25));
        btnDezoom.setBorderPainted(false);
        btnDezoom.setContentAreaFilled(false);
        btnDezoom.setFocusPainted(false);
        
        valeurZoom = new JLabel("100%");
         
        panelZoom.add(btnDezoom);
        panelZoom.add(valeurZoom);
        panelZoom.add(btnZoom);
        panelZoom.setBounds(0, 70, 150, 35);  
        layeredZoom.add(panelZoom, JLayeredPane.PALETTE_LAYER);  
        add(layeredZoom, BorderLayout.CENTER);
        
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                layeredZoom.setSize(getWidth(), getHeight());
                ribbon.setSize(getWidth(), getHeight());
                panelZoom.setLocation(getWidth() - 155, getHeight() - 30);
            }
        });

        btnZoom.addActionListener(e -> zoomListener());
        btnDezoom.addActionListener(e -> dezoomListener());

        add(ribbon, BorderLayout.CENTER);
        
    }
    
    public void zoomListener(){
        Canvas c = mainWindow.currentCanvas;
        if (c != null) {
            c.zoomDepuisCentre(1.1);
            mainWindow.updateZoomLabel();
        }
    }
    
    public void dezoomListener(){
        Canvas c = mainWindow.currentCanvas;
        if (c != null) {
            c.zoomDepuisCentre(1.0 / 1.1);
            mainWindow.updateZoomLabel();
        }
    }
    
    

    public void onSansDrainClicked() {
        btnMenuSansDrain.setOnClick(e -> {
            JPopupMenu menuItemSansDrain = new JPopupMenu();
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setPreferredSize(new Dimension(130, 200));
            ButtonCard card1 = card("Armoire", "/images/armoire.png");
            ButtonCard card2 = card("Placard", "/images/placard.png");
            card1.setMinimumSize(new Dimension(120, 100));
            card2.setMinimumSize(new Dimension(120, 100));
            panel.add(card1);
            panel.add(card2);
            menuItemSansDrain.add(panel);
            menuItemSansDrain.show(btnMenuSansDrain, btnMenuSansDrain.getWidth() - 5, btnMenuSansDrain.getHeight() - 5);

            ButtonCard[] cards = {card1, card2};
            for (ButtonCard c : cards) {
                c.setOnClick(event -> {
                    String nom = event.getActionCommand().toUpperCase();
                    mainWindow.controllerActif.ajouterMeubleSansDrain(mainWindow.controllerActif.getPiece().getCentre(), TypeSansDrain.valueOf(nom));
                    mainWindow.currentCanvas.repaint();
                    menuItemSansDrain.setVisible(false);
                });
            }
        });

    }

    public void onAvecDrainClicked() {
        btnMenuAvecDrain.setOnClick(e -> {
            JPopupMenu menuItemAvecDrain = new JPopupMenu();
            JPanel panel = new JPanel(new GridLayout(2, 2));
            panel.setPreferredSize(new Dimension(260, 200));

            ButtonCard card1 = card("Bain", "/images/bain.png");
            ButtonCard card2 = card("Douche", "/images/douche.png");
            ButtonCard card3 = card("Toilette", "/images/toilette.png");
            ButtonCard card4 = card("Vanité", "/images/vanite.png");

            card1.setMinimumSize(new Dimension(120, 100));
            card2.setMinimumSize(new Dimension(120, 100));
            card3.setMinimumSize(new Dimension(120, 100));
            card4.setMinimumSize(new Dimension(120, 100));

            panel.add(card1);
            panel.add(card2);
            panel.add(card3);
            panel.add(card4);
            menuItemAvecDrain.add(panel);
            menuItemAvecDrain.show(btnMenuAvecDrain, btnMenuAvecDrain.getWidth() - 5, btnMenuAvecDrain.getHeight() - 5);

            ButtonCard[] cards = {card1, card2, card3, card4};
            for (ButtonCard c : cards) {
                c.setOnClick(event -> {
                    String nom = event.getActionCommand().toUpperCase();
                    mainWindow.controllerActif.ajouterMeubleAvecDrain(mainWindow.controllerActif.getPiece().getCentre(), TypeAvecDrain.valueOf(nom));
                    mainWindow.currentCanvas.repaint();
                    menuItemAvecDrain.setVisible(false);
                });
            }
        });
    }
    
    public void onThermostatClicked(){
        btnThermostat.setOnClick( e -> {
            Point position = mainWindow.controllerActif.getPiece().getCentre();
            mainWindow.controllerActif.ajouterThermostat(position);
            mainWindow.props.afficherProprietesItemSelectionne();
            mainWindow.currentCanvas.repaint();
        });
    }
    
    public void onElementChauffant(){
        btnElementChauffant.setOnClick( e -> {
            Point position = mainWindow.controllerActif.getPiece().getCentre();
            mainWindow.controllerActif.ajouterElementChauffant(position);
            mainWindow.props.afficherProprietesItemSelectionne();
            mainWindow.currentCanvas.repaint();
        });
    }
    
    
    public void onZonesClicked(){
        btnMenuZones.setOnClick( e -> {
            JPopupMenu menuItemZones = new JPopupMenu();
            
            JPanel panelZones = new JPanel();
            panelZones.setLayout(new BoxLayout(panelZones, BoxLayout.Y_AXIS));
            panelZones.setPreferredSize(new Dimension(130, 200));
            
            ButtonCard card1 = card("Interdite", "/images/zoneInterdite.png");
            ButtonCard card2 = card("Tampon", "/images/zoneTampon.png");
            
            card1.setMinimumSize(new Dimension(120, 100));
            card2.setMinimumSize(new Dimension(120, 100));
            
            panelZones.add(card1);
            panelZones.add(card2);
            
            menuItemZones.add(panelZones);
            menuItemZones.show(btnMenuZones, btnMenuZones.getWidth() - 5, btnMenuZones.getHeight() - 5);
            
            ButtonCard[] cards = {card1, card2};
            for(ButtonCard c : cards){
                c.setOnClick( event -> {
                    String nom = event.getActionCommand().toUpperCase();
                    mainWindow.controllerActif.ajouterZone(mainWindow.controllerActif.getPiece().getCentre(), Zone.TypeZone.valueOf(nom));
                    mainWindow.currentCanvas.repaint();
                    menuItemZones.setVisible(false);
                });
            }
            
        });
    }

    public void onEnregistrerProjetClick(Runnable r) {
        btnEnregistrer.setOnClick(e -> r.run());
    }
    
    public void onExportPngClick(Runnable r) {
        btnExporter.setOnClick(e -> r.run());
    }
    
    public void onOuvrirProjetClick(Runnable r) {
        btnOuvrir.setOnClick(e -> r.run());
    }
}
