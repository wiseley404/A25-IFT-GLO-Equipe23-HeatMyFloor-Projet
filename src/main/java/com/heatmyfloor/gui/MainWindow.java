/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heatmyfloor.gui;

import com.heatmyfloor.domain.piece.Controller;
import com.heatmyfloor.domain.piece.PieceItemReadOnly;
import com.heatmyfloor.domain.piece.PieceRectangulaire;
import com.heatmyfloor.domain.piece.PieceIrreguliere;
import javax.swing.*;
import javax.swing.KeyStroke;
import java.awt.event.InputEvent;
import com.heatmyfloor.domain.Point;
import com.heatmyfloor.domain.items.Drain;
import com.heatmyfloor.domain.items.MeubleAvecDrain;
import com.heatmyfloor.gui.UiUtils.ToastType;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import javax.swing.SwingUtilities;

/**
 *
 * @author tatow
 */
public class MainWindow extends javax.swing.JFrame {

    private javax.swing.JToggleButton additionButton;
    private javax.swing.JPanel buttonTopPanel;
    private javax.swing.ButtonGroup createFruitButtonGroup;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JComboBox itemTypeBox;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JScrollPane mainScrollPane;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JMenuItem quitMenuItem;
    private javax.swing.JMenuItem HelpMenuItem;
    private javax.swing.JToggleButton selectionButton;
    private javax.swing.JMenuBar topMenuBar;

    private BarreOutils barreOutils;
    private JTabbedPane tabs;
    private int i = 1;

    public Map<Canvas, Controller> controllers = new HashMap<>();
    public Controller controllerActif;
    public Canvas currentCanvas;
    public Proprietes props;
    public PositionPanel panelPosition;
    public Point positionSouris = new Point();
    public TableauErreur tabsErreur;

    private double dragOffsetX = 0; 
    private double dragOffsetY = 0; 
    private final int STEP = 5;

    public MainWindow() {
        barreOutils = new BarreOutils(this);
        tabs = new JTabbedPane();
        initComponents();
        controllerActif = new Controller();
        setupTabListeners();
        setupKeyBindings();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                sauvegarderSession();
                System.exit(0);
            }
        });
    }

    //Conversion coordonnées (Zoom)
    private com.heatmyfloor.domain.Point toWorld(Canvas c, java.awt.event.MouseEvent e) {
        double zx = c.getZoom();
        var o = c.getOriginePx();
        return new com.heatmyfloor.domain.Point(
                (e.getX() - o.getX()) / zx,
                (e.getY() - o.getY()) / zx
        );
    }

    public void updateZoomLabel() {
        if (currentCanvas != null && barreOutils != null) {
            barreOutils.setZoomPercent(currentCanvas.getZoom());
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        initialiserComponent();
        addButton();

        //Menu du toolbar
        mainPanel.add(barreOutils, BorderLayout.NORTH);
        barreOutils.btnNouveau.setOnClick(e -> handleNewProject());

        //
        //action pour le bouton rectangle
        barreOutils.onRectangleClick(() -> {
            Component component = tabs.getSelectedComponent();
            if (component instanceof JScrollPane sp) {
                Component comp = sp.getViewport().getView();
                if(comp instanceof Canvas canvas){
                    controllerActif = controllers.get(canvas);
                    controllerActif.setPiece(new PieceRectangulaire(1000, 500));
                    currentCanvas = canvas;

                    double largeur = controllerActif.getPiece().getLargeur();
                    double hauteur = controllerActif.getPiece().getHauteur();
                    double x = (currentCanvas.getWidth() - largeur) / 2;
                    double y = (currentCanvas.getHeight() - hauteur) / 2;
                    controllerActif.centrerPiece(new Point(x,y));
                    panelPosition.afficherCoordItemSelectionne();

                    currentCanvas.nettoyerModeDessin();
                    currentCanvas.repaint();
                    props.afficherProprietesPiece();
                    props.updateUndoRedoButtons();
                }
                
            } else {
                JOptionPane.showMessageDialog(this, "Aucun projet ouvert.",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        barreOutils.onSansDrainClicked();
        barreOutils.onAvecDrainClicked();
        barreOutils.onThermostatClicked();
        barreOutils.onElementChauffant();
        barreOutils.onZonesClicked();


        barreOutils.onIrregularButtonClick(() -> {

            Component component = tabs.getSelectedComponent();
            if (component instanceof JScrollPane sp) {
                Component comp = sp.getViewport().getView();
                if(comp instanceof Canvas canvas){
                    controllerActif = controllers.get(canvas);
                    controllerActif.setPiece(new PieceIrreguliere());
                    currentCanvas = canvas;
                    currentCanvas.dessinerFormeIrreguliere();
                }
            } else {
                UiUtils.showToastTopRight(this, ToastType.ERROR, "Aucun projet ouvert.");
            }
        });

        barreOutils.onEnregistrerProjetClick(() -> {

            Path fichierSauvegarde = null;

            enregistrerProjet(fichierSauvegarde);

        });

        barreOutils.onExportPngClick(() -> {
            Path fichierSauvegarde;
            fichierSauvegarde = UiUtils.choisirDossierSauvegarde(this);
            if (fichierSauvegarde == null) {

                UiUtils.showToastTopRight(this, ToastType.ERROR, "Aucun dossier de sauvegarde sélectionné.");
                return;
            }

            try {
                controllerActif.exporterProjetPng(fichierSauvegarde);
                UiUtils.showToastTopRight(this, ToastType.SUCCESS, "La sauvegarde a réussi");
            } catch (RuntimeException e) {
                UiUtils.showToastTopRight(this, ToastType.ERROR, e.getMessage());
            }

        });

        barreOutils.onOuvrirProjetClick(() -> {

            Path fichier;
            fichier = UiUtils.choisirFichierJson(this);
            if (fichier == null) {

                UiUtils.showToastTopRight(this, ToastType.ERROR, "Aucun fichier Json nà été sélectionné.");
                return;
            }

            try {

                controllerActif = new Controller();
                controllerActif.ouvrirProjet(fichier);
                controllers.put(currentCanvas, controllerActif);

                Canvas canvas = new Canvas();
                canvas.setMainWindow(this);
                currentCanvas = canvas;
                tabs.addTab(controllerActif.GetProjetNom(), currentCanvas);
                tabs.setSelectedComponent(currentCanvas);
                int idx = tabs.indexOfComponent(currentCanvas);
                tabs.setTabComponentAt(idx, new ClosableTabHeader(tabs, this::closeTabAt, this::renameTabAt));
                tabs.setSelectedIndex(idx);

                SwingUtilities.invokeLater(() -> {
                    currentCanvas.repaint();
                });
                props.afficherProprietesPiece();
                sourisListener();
                suppressionListener();
                disableButton();

                UiUtils.showToastTopRight(this, ToastType.SUCCESS, "La sauvegarde a réussi");
            } catch (RuntimeException e) {
                UiUtils.showToastTopRight(this, ToastType.ERROR, e.getMessage());
            }
        });

        barreOutils.onExportPngClick(() -> {
            Path fichierSauvegarde;
            fichierSauvegarde = UiUtils.choisirDossierSauvegarde(this);
            if (fichierSauvegarde == null) {

                UiUtils.showToastTopRight(this, ToastType.ERROR, "Aucun dossier de sauvegarde sélectionné.");
                return;
            }

            try {
                controllerActif.exporterProjetPng(fichierSauvegarde);
                UiUtils.showToastTopRight(this, ToastType.SUCCESS, "La sauvegarde a réussi");
            } catch (RuntimeException e) {
                UiUtils.showToastTopRight(this, ToastType.ERROR, e.getMessage());
            }

        });

        barreOutils.onOuvrirProjetClick(() -> {

            Path fichier;
            fichier = UiUtils.choisirFichierJson(this);
            if (fichier == null) {

                UiUtils.showToastTopRight(this, ToastType.ERROR, "Aucun fichier Json nà été sélectionné.");
                return;
            }

            try {

                controllerActif = new Controller();
                controllerActif.ouvrirProjet(fichier);

                Canvas canvas = new Canvas();
                canvas.setMainWindow(this);
                currentCanvas = canvas;
                controllers.put(currentCanvas, controllerActif);

                tabs.addTab(controllerActif.GetProjetNom(), currentCanvas);
                tabs.setSelectedComponent(currentCanvas);
                int idx = tabs.indexOfComponent(currentCanvas);
                tabs.setTabComponentAt(idx, new ClosableTabHeader(tabs, this::closeTabAt, this::renameTabAt));
                tabs.setSelectedIndex(idx);

                SwingUtilities.invokeLater(() -> {
                    currentCanvas.repaint();
                });
                props.afficherProprietesPiece();
                sourisListener();
                suppressionListener();

                UiUtils.showToastTopRight(this, ToastType.SUCCESS, "La sauvegarde a réussi");
            } catch (RuntimeException e) {
                UiUtils.showToastTopRight(this, ToastType.ERROR, e.getMessage());
            }

        });

        JPanel center = new JPanel(new BorderLayout());
        props = new Proprietes(this);
        props.dimensionItemListener();
        props.dimensionPieceListener();
        props.updateUndoRedoButtons();
        props.diametreDrainListener();
        props.positionDrainListener();
        center.add(props, BorderLayout.WEST);
        center.add(tabs, BorderLayout.CENTER);

        mainPanel.add(center, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout());
        panelPosition = new PositionPanel(this);
        panelPosition.positionListener();
        panelPosition.angleListener();
        bottom.add(panelPosition, BorderLayout.CENTER);
        bottom.add(tabsErreur, BorderLayout.EAST);
        mainPanel.add(bottom, BorderLayout.SOUTH);

        quitMenuItem.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Voulez-vous vraiment quitter l’application ?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                if (!this.controllers.isEmpty()) {
                    this.sauvegarderSession();
                }
                System.exit(0);
            }
        });

        HelpMenuItem.addActionListener(e -> {
            String message
                    = "Aide - Gestion de la pièce\n\n"
                    + "1) Ajouter un nouveau projet :\n"
                    + "   - Menu Fichier > Nouveau projet.\n"
                    + "   - Vous pouvez ensuite ajuster les dimensions de la pièce dans la section « Pièce » des propriétés.\n\n"
                    + "2) Modifier la forme de la pièce :\n"
                    + "   - Cliquez sur « Irrégulière » dans la barre d’outils.\n"
                    + "   - Placer les sommets du polygone et double-cliquez sur le canevas pour définir la forme.\n\n"
                    + "3) Ajouter un meuble :\n"
                    + "   - Sélectionnez l’outil meuble (avec ou sans drain).\n"
                    + "   - Cliquez sur le meuble désiré et il s'afficheras dans la pièce.\n\n"
                    + "4) Déplacer un meuble :\n"
                    + "   - Cliquez sur un meuble pour le sélectionner.\n"
                    + "   - Dans la section Position, entrez de nouvelles valeurs pour X et Y.\n"
                    + "   - Un message d’erreur s’affichera si les positions choisies sont en dehors de la pièce.\n\n"
                    + "5) Supprimer un meuble :\n"
                    + "   - Sélectionnez le meuble à supprimer.\n"
                    + "   - Appuyez sur la touche Suppr du clavier.\n";

            JOptionPane.showMessageDialog(
                    this,
                    message,
                    "Aide",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        setJMenuBar(topMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1877, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 577, Short.MAX_VALUE)
        );

        disableButton();
        tabListener();
        pack();
    }

    private void addNewProjet() {
        addNewProjet(null);
    }

    private void addNewProjet(Controller ctrl) {

        Canvas canvas = new Canvas();
        canvas.setMainWindow(this);
        currentCanvas = canvas;
        String title;
        if (ctrl == null) {
            title = "Projet " + i++;
            controllerActif = new Controller();
            controllerActif.SetProjetNom(title);
        } else {
            controllerActif = ctrl;
            title = controllerActif.GetProjetNom();
        }
   
        controllers.put(currentCanvas, controllerActif);
        JScrollPane sp = new JScrollPane(currentCanvas, 
        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        tabs.addTab(title, sp);
        tabs.setSelectedComponent(sp);
        int idx = tabs.indexOfComponent(sp);
        tabs.setTabComponentAt(idx, new ClosableTabHeader(tabs, this::closeTabAt, this::renameTabAt));
        tabs.setSelectedIndex(idx);
        props.afficherMurItemSelectionne();

        SwingUtilities.invokeLater(() -> {
            double largeur = controllerActif.getPiece().getLargeur();
            double hauteur = controllerActif.getPiece().getHauteur();
            double x = (currentCanvas.getWidth() - largeur) / 2;
            double y = (currentCanvas.getHeight() - hauteur) / 2;
            controllerActif.centrerPiece(new Point(x, y));
            panelPosition.afficherCoordItemSelectionne();
            currentCanvas.repaint();
        });

        props.afficherProprietesPiece();
        props.updateUndoRedoButtons();
        sourisListener();

        currentCanvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_DELETE) {
                    supprimerItem();
                }
            }
        });

        clavierListener();
        suppressionListener();
    }

    public void suppressionListener() {

        currentCanvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_DELETE) {

                    SwingUtilities.invokeLater(() -> {
                        supprimerItem();
                        currentCanvas.repaint();
                    });
                }
            }
        });
    }

    public void supprimerItem() {
        controllerActif.supprimerItemSelectionne();
        props.afficherProprietesDrainSelectionne();
        props.afficherProprietesItemSelectionne();
        props.afficherMurItemSelectionne();
        panelPosition.afficherCoordItemSelectionne();
        panelPosition.afficherAngleItemSelectionne();
        props.updateUndoRedoButtons();    
    }

    public void sourisListener() {

    // --- Sélection au clic ---
    currentCanvas.addMouseListener(new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            Point pWorld = toWorld(currentCanvas, e);

            // Sélectionne l’item (meuble ou autre)
            controllerActif.changerStatutSelection(pWorld); 
            PieceItemReadOnly sel = controllerActif.trouverItemSelectionne();
            if (sel != null) {
                if (sel instanceof MeubleAvecDrain meuble) {
                    Drain drain = meuble.getDrain();
                   if (drain != null) {
                        // Offset relatif au drain
                        dragOffsetX = pWorld.getX() - drain.getPosition().getX();
                        dragOffsetY = pWorld.getY() - drain.getPosition().getY();
                   }else {
                       // Offset relatif au meuble
                       dragOffsetX = pWorld.getX() - sel.getPosition().getX();
                       dragOffsetY = pWorld.getY() - sel.getPosition().getY();
                   }
                } else {
                    // Item classique
                    dragOffsetX = pWorld.getX() - sel.getPosition().getX();
                    dragOffsetY = pWorld.getY() - sel.getPosition().getY();
                }
            }

            props.afficherProprietesItemSelectionne();
            panelPosition.afficherCoordItemSelectionne();
            panelPosition.afficherAngleItemSelectionne();
            SwingUtilities.invokeLater(() -> {
            props.afficherProprietesDrainSelectionne();
             });
            currentCanvas.repaint();
        }
    });

    // --- Survol souris ---
    currentCanvas.addMouseMotionListener(new MouseMotionAdapter() {
        @Override
        public void mouseMoved(MouseEvent e) {
            Point pWorld = toWorld(currentCanvas, e);

            List<PieceItemReadOnly> items = controllerActif.getItemsList();
            PieceItemReadOnly ancienItemSurvole = currentCanvas.getItemSurvole();
            PieceItemReadOnly itemSurvole = null;

            for (int i = items.size() - 1; i >= 0; i--) {
                PieceItemReadOnly item = items.get(i);
                if (item.contientLePoint(pWorld)) {
                    itemSurvole = item;
                    break;
                }
            }

            if (ancienItemSurvole != itemSurvole) {
                currentCanvas.setItemSurvole(itemSurvole);
                currentCanvas.repaint();
            }
        }

        // --- Drag pour déplacer meuble ou drain ---
        @Override
        public void mouseDragged(MouseEvent e) {
            PieceItemReadOnly selObj = controllerActif.trouverItemSelectionne();
            if (selObj == null) return;

            Point pWorld = toWorld(currentCanvas, e);
            double targetX = pWorld.getX() - dragOffsetX;
            double targetY = pWorld.getY() - dragOffsetY;

            if (selObj instanceof MeubleAvecDrain meuble) {
                Drain drain = meuble.getDrain();
                if (drain.contientLePoint(pWorld)) {  
                    double dx = targetX - drain.getPosition().getX();
                    double dy = targetY - drain.getPosition().getY();
                    meuble.deplacerDrain(new Point(dx, dy));
               } else {
                    // Déplacement du meuble entier
                    panelPosition.moveSelectedTo(targetX, targetY);   
                }
            } else {
                panelPosition.moveSelectedTo(targetX, targetY);     
            }

            panelPosition.afficherCoordItemSelectionne();
            props.afficherProprietesDrainSelectionne();
            currentCanvas.repaint();
        }
    });
}



    public void clavierListener() {

        currentCanvas.setFocusable(true);
        currentCanvas.requestFocusInWindow();

        currentCanvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                PieceItemReadOnly sel = controllerActif.trouverItemSelectionne();
                if (sel == null) {
                    return;
                }

                double x = sel.getPosition().getX();
                double y = sel.getPosition().getY();

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        y -= STEP;
                        break;

                    case KeyEvent.VK_DOWN:
                        y += STEP;
                        break;

                    case KeyEvent.VK_LEFT:
                        x -= STEP;
                        break;

                    case KeyEvent.VK_RIGHT:
                        x += STEP;
                        break;
                    default:
                        return;

                }

                panelPosition.moveSelectedTo(x, y);
                panelPosition.afficherAngleItemSelectionne();
                currentCanvas.repaint();

            }

        });
    }

    private void tabListener() {
        tabs.addChangeListener(e -> {
            Component component = tabs.getSelectedComponent();
            if (component instanceof JScrollPane sp) {
                Component comp = sp.getViewport().getView();
                if(comp instanceof Canvas canvas){
                    currentCanvas = canvas;
                    this.controllerActif = controllers.get(canvas);

                    SwingUtilities.invokeLater(() -> {
                        props.afficherProprietesPiece();
                        props.afficherMurItemSelectionne();
                        props.updateUndoRedoButtons();
                        props.afficherProprietesItemSelectionne();
                        props.afficherProprietesDrainSelectionne();
                        panelPosition.afficherAngleItemSelectionne();
                        panelPosition.afficherCoordItemSelectionne();
                    });          
                }
            }
        });
    }

    
    private void disableButton() {
        if (tabs.getTabCount() == 0 || controllerActif == null) {
            UiUtils.setEnabledRecursively(barreOutils, false);
            UiUtils.setEnabledRecursively(barreOutils.btnNouveau, true);
            UiUtils.setEnabledRecursively(barreOutils.btnOuvrir, true);
        } else {
            UiUtils.setEnabledRecursively(barreOutils, true);
        }
    }

    private void setupTabListeners() {

        tabs.addChangeListener(e -> disableButton());

        tabs.addContainerListener(new ContainerAdapter() {
            @Override
            public void componentAdded(ContainerEvent e) {
                disableButton();
            }

            @Override
            public void componentRemoved(ContainerEvent e) {
                disableButton();
            }
        });
    }

    private void handleNewProject() {
        addNewProjet();
        UiUtils.setEnabledRecursively(barreOutils, true);
        UiUtils.setEnabledRecursively(barreOutils.btnRectangle, true);
    }

    private void renameTabAt(int idx) {
        String current = tabs.getTitleAt(idx);
        String name = (String) JOptionPane.showInputDialog(
                this, "Nouveau nom :", "Renommer l’onglet",
                JOptionPane.PLAIN_MESSAGE, null, null, current);

        if (name == null) {
            return;
        }
        name = name.trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Le nom ne peut pas être vide.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (int i = 0; i < tabs.getTabCount(); i++) {
            if (i != idx && name.equalsIgnoreCase(tabs.getTitleAt(i))) {
                JOptionPane.showMessageDialog(this, "Un onglet porte déjà ce nom.",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        tabs.setTitleAt(idx, name);
        controllerActif.SetProjetNom(name);
    }

    private void closeTabAt(int idx) {
        tabs.removeTabAt(idx);
        disableButton();
    }

    public Canvas getSelectedCanvas() {
        Component comp = tabs.getSelectedComponent();
        if (comp instanceof JScrollPane sp) {
            Component component = sp.getViewport().getView();
            if(component instanceof Canvas canvas){
            return canvas;
            }
        }
        return null;
    }

    private void initialiserComponent() {

        createFruitButtonGroup = new javax.swing.ButtonGroup();

        mainPanel = new javax.swing.JPanel();
        mainPanel.setOpaque(true);
        mainPanel.setBackground(Color.WHITE);
        buttonTopPanel = new javax.swing.JPanel(new FlowLayout(FlowLayout.LEFT));
        selectionButton = new javax.swing.JToggleButton();
        additionButton = new javax.swing.JToggleButton();
        itemTypeBox = new javax.swing.JComboBox();
        jSplitPane1 = new javax.swing.JSplitPane();
        mainScrollPane = new javax.swing.JScrollPane();

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        topMenuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu("Fichier");
        openMenuItem = new javax.swing.JMenuItem();
        quitMenuItem = new javax.swing.JMenuItem();
        HelpMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        helpMenu = new javax.swing.JMenu();
        tabsErreur = new TableauErreur();

    }

    private void addButton() {

        JToolBar toolBar = new JToolBar();
        JMenuItem newItem = new JMenuItem("Nouveau Projet");
        JMenuItem openItem = new JMenuItem("Ouvrir Projet");
        JMenuItem saveItem = new JMenuItem("Enregistrer Projet");
        JMenuItem exportItem = new JMenuItem("Exporter en PNG");

        createFruitButtonGroup.add(selectionButton);
        createFruitButtonGroup.add(additionButton);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("HeatMyfloor");

        mainPanel.setLayout(new java.awt.BorderLayout());

        //Menu du bouton fichier
        newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        fileMenu.add(newItem);
        newItem.addActionListener(e -> handleNewProject());

        openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        fileMenu.add(openItem);
        fileMenu.addSeparator();

        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        fileMenu.add(saveItem);
        fileMenu.add(exportItem);
        fileMenu.addSeparator();
        quitMenuItem.setText("Quitter");
        quitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
        fileMenu.add(quitMenuItem);
        topMenuBar.add(fileMenu);

        //Menu du bouton Editer
        editMenu.setText("Edition");
        JMenuItem undoItem = new JMenuItem("Undo");
        undoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
        undoItem.addActionListener(e -> props.undoListener());
        editMenu.add(undoItem);

        JMenuItem redoItem = new JMenuItem("Redo");
        redoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK));
        redoItem.addActionListener(e -> props.redoListener());
        editMenu.add(redoItem);
        editMenu.addSeparator();

        JMenuItem zoomItem = new JMenuItem("Zoomer");
        zoomItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
        zoomItem.addActionListener(e -> barreOutils.zoomListener());
        editMenu.add(zoomItem);

        JMenuItem dezoomItem = new JMenuItem("Dezoomer");
        dezoomItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, InputEvent.CTRL_DOWN_MASK));
        dezoomItem.addActionListener(e -> barreOutils.dezoomListener());
        editMenu.add(dezoomItem);
        editMenu.addSeparator();

        JMenuItem deleteItem = new JMenuItem("Supprimer");
        deleteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
        deleteItem.addActionListener(e -> supprimerItem());
        editMenu.add(deleteItem);

        //Menu du bouton Aide
        helpMenu.setText("Aide");
        JMenuItem aboutMenu = new JMenuItem("A propos");
        HelpMenuItem.setText("Documentation");
        helpMenu.add(aboutMenu);
        helpMenu.add(HelpMenuItem);

        topMenuBar.add(editMenu);
        topMenuBar.add(helpMenu);
    }

    private void enregistrerProjet(Path fichierSauvegarde) {
        if (controllerActif.GetCheminFichier() == null) {
             fichierSauvegarde= UiUtils.choisirDossierSauvegarde(MainWindow.this);
            if (fichierSauvegarde == null) {

                UiUtils.showToastTopRight(this, ToastType.ERROR, "Aucun dossier de sauvegarde sélectionné.");
                return;
            }

        } else {
            fichierSauvegarde = controllerActif.GetCheminFichier();
        }

        controllerActif.sauvegarderProjet(fichierSauvegarde);
        UiUtils.showToastTopRight(this, ToastType.SUCCESS, "La sauvegarde a réussi");
    }

    private static class ClosableTabHeader extends JPanel {

        public ClosableTabHeader(JTabbedPane tabs,
                java.util.function.IntConsumer onCloseIndex,
                java.util.function.IntConsumer onRenameIndex) {
            setOpaque(false);
            setLayout(new FlowLayout(FlowLayout.LEFT, 6, 3));

            JLabel title = new JLabel() {
                @Override
                public String getText() {
                    int i = tabs.indexOfTabComponent(ClosableTabHeader.this);
                    return i >= 0 ? tabs.getTitleAt(i) : "";
                }
            };

            // Sélectionner l’onglet sur simple clic dans le header
            java.awt.event.MouseAdapter selectOnClick = new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(java.awt.event.MouseEvent e) {
                    int i = tabs.indexOfTabComponent(ClosableTabHeader.this);
                    if (i >= 0) {
                        tabs.setSelectedIndex(i);
                    }
                }
            };
            addMouseListener(selectOnClick);
            title.addMouseListener(selectOnClick);

            // Double-clic -> renommer
            title.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    if (e.getClickCount() == 2 && onRenameIndex != null) {
                        int i = tabs.indexOfTabComponent(ClosableTabHeader.this);
                        if (i >= 0) {
                            onRenameIndex.accept(i);
                        }
                    }
                }
            });

            JButton close = new JButton("×");
            close.setFocusable(false);
            close.setBorder(BorderFactory.createEmptyBorder());
            close.setContentAreaFilled(false);
            close.setToolTipText("Fermer l’onglet");
            close.addActionListener(e -> {
                int i = tabs.indexOfTabComponent(ClosableTabHeader.this);
                if (i >= 0 && onCloseIndex != null) {
                    onCloseIndex.accept(i);
                }
            });

            // Menu contextuel
            JPopupMenu menu = new JPopupMenu();
            JMenuItem rename = new JMenuItem("Renommer…");
            JMenuItem closeIt = new JMenuItem("Fermer");
            rename.addActionListener(e -> {
                int i = tabs.indexOfTabComponent(ClosableTabHeader.this);
                if (i >= 0 && onRenameIndex != null) {
                    onRenameIndex.accept(i);
                }
            });
            closeIt.addActionListener(e -> {
                int i = tabs.indexOfTabComponent(ClosableTabHeader.this);
                if (i >= 0 && onCloseIndex != null) {
                    onCloseIndex.accept(i);
                }
            });
            menu.add(rename);
            menu.add(closeIt);

            java.awt.event.MouseAdapter showMenu = new java.awt.event.MouseAdapter() {
                private void maybe(java.awt.event.MouseEvent e) {
                    if (e.isPopupTrigger()) {
                        menu.show(e.getComponent(), e.getX(), e.getY());
                    }
                }

                @Override
                public void mousePressed(java.awt.event.MouseEvent e) {
                    maybe(e);
                }

                @Override
                public void mouseReleased(java.awt.event.MouseEvent e) {
                    maybe(e);
                }
            };
            title.addMouseListener(showMenu);
            close.addMouseListener(showMenu);
            addMouseListener(showMenu);

            add(title);
            add(close);
        }
    }

    private void sauvegarderSession() {
        try {
            Path fichierSession = Paths.get("sauvegardes", "autosaves.json");

            FileOutputStream fo = new FileOutputStream(fichierSession.toFile(), false);
            ObjectOutputStream out = new ObjectOutputStream(fo);

            out.writeInt(controllers.size());

            for (Map.Entry<Canvas, Controller> entry : controllers.entrySet()) {
                Controller ctrl = entry.getValue();

                Map<String, Object> data = ctrl.getSessionData();

                out.writeObject(data);
            }

            out.close();
            fo.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void restaurerSession() {

        try {
            Path fichierSession = Paths.get("sauvegardes", "autosaves.json");

            if (!Files.exists(fichierSession) || Files.size(fichierSession) == 0) {
                return;
            }

            FileInputStream fi = new FileInputStream(fichierSession.toFile());
            ObjectInputStream in = new ObjectInputStream(fi);

            int nb = in.readInt();

            for (int i = 0; i < nb; i++) {

                Map<String, Object> data = (Map<String, Object>) in.readObject();

                Controller ctrl = new Controller();
                ctrl.restaurerDepuisSession(data);

                addNewProjet(ctrl);
            }

            Files.write(fichierSession, new byte[0]);

            in.close();
            fi.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void setupKeyBindings() {
        InputMap inputMap = this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.getRootPane().getActionMap();

        // On lie Ctrl+S à la clé "saveSession"
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK), "saveSession");

        actionMap.put("saveSession", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                Path fichierSauvegarde = null;
               

                enregistrerProjet(fichierSauvegarde);
            }
        });
    }

}
