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
import com.heatmyfloor.domain.Point;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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

    public MainWindow() {
        barreOutils = new BarreOutils(this);
        tabs = new JTabbedPane();
        initComponents();
        controllerActif = new Controller();

    }
    
    
    // --- Conversion coordonnées (Zoom)
private com.heatmyfloor.domain.Point toWorld(Canvas c, java.awt.event.MouseEvent e) {
    double zx = c.getZoom();
    var o  = c.getOriginePx();
    return new com.heatmyfloor.domain.Point(
        (e.getX() - o.getX()) / zx,
        (e.getY() - o.getY()) / zx
    );
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
            Component componaint = tabs.getSelectedComponent();
            if (componaint instanceof Canvas canvas) {
                controllerActif = controllers.get(canvas);
                controllerActif.setPiece(new PieceRectangulaire(500, 300));
                currentCanvas = canvas;
                currentCanvas.nettoyerModeDessin();
                currentCanvas.repaint();
                props.afficherProprietesPiece();
            } else {
                JOptionPane.showMessageDialog(this, "Aucun projet ouvert.",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        barreOutils.onSansDrainClicked();
        barreOutils.onAvecDrainClicked();

        barreOutils.onIrregularButtonClick(() -> {

            Component componaint = tabs.getSelectedComponent();
            if (componaint instanceof Canvas canvas) {
                controllerActif = controllers.get(canvas);
                controllerActif.setPiece(new PieceIrreguliere());
                currentCanvas = canvas;
                currentCanvas.dessinerFormeIrreguliere();
            } else {
                JOptionPane.showMessageDialog(this, "Aucun projet ouvert.",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel center = new JPanel(new BorderLayout());
        props = new Proprietes(this);
        props.dimensionItemListener();
        props.dimensionPieceListener();
        center.add(props, BorderLayout.WEST);
        center.add(tabs, BorderLayout.CENTER);

        mainPanel.add(center, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout());
        panelPosition = new PositionPanel(this);
        panelPosition.positionListener();
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
                System.exit(0); // quitte proprement le programme
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
        String title = "Projet " + i++;
        Canvas canvas = new Canvas();
        canvas.setMainWindow(this);
        currentCanvas = canvas;

        controllerActif = new Controller(new PieceRectangulaire(900, 400));
        controllers.put(currentCanvas, controllerActif);

        tabs.addTab(title, currentCanvas);
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
    }

    public void suppressionListener() {

        currentCanvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_DELETE) {

                    controllerActif.supprimerItemSelectionne();
                    props.afficherProprietesItemSelectionne();
                    panelPosition.afficherCoordItemSelectionne();
                    panelPosition.afficherAngleItemSelectionne();
                    currentCanvas.repaint();
                }
            }
        });
    }

    public void sourisListener() {

        currentCanvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //Point cliqueSouris = new Point(e.getX(), e.getY());
                //controllerActif.changerStatutSelection(cliqueSouris);
                var pWorld = toWorld(currentCanvas, e);     // coord. monde (après zoom)
                controllerActif.changerStatutSelection(pWorld);

                props.afficherProprietesItemSelectionne();
                panelPosition.afficherCoordItemSelectionne();
                panelPosition.afficherAngleItemSelectionne();
                currentCanvas.repaint();

            }
        });

        currentCanvas.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                //positionSouris = new Point(e.getX(), e.getY());
                positionSouris = toWorld(currentCanvas, e); // coord. monde

                List<PieceItemReadOnly> items = controllerActif.getItemsList();

                PieceItemReadOnly ancienItemSurvole = currentCanvas.getItemSurvole();
                PieceItemReadOnly itemSurvole = null;

                for (int i = items.size() - 1; i >= 0; i--) {
                    PieceItemReadOnly item = items.get(i);
                    if (item.contientLePoint(positionSouris)) {
                        itemSurvole = item;
                        break;
                    }
                }

                if (ancienItemSurvole != itemSurvole) {
                    currentCanvas.setItemSurvole(itemSurvole);
                    currentCanvas.repaint();
                }
            }
        });
    }

    private void tabListener() {
        tabs.addChangeListener(e -> {
            Component component = tabs.getSelectedComponent();
            if (component instanceof Canvas canvas) {
                currentCanvas = canvas;
                this.controllerActif = controllers.get(canvas);

                SwingUtilities.invokeLater(() -> {
                    props.afficherProprietesPiece();
                    props.afficherProprietesItemSelectionne();
                    panelPosition.afficherAngleItemSelectionne();
                    panelPosition.afficherCoordItemSelectionne();
                });
            }
        });
    }

    private void disableButton() {
        if (tabs.getTabCount() == 0) {
            UiUtils.setEnabledRecursively(barreOutils, false);
            UiUtils.setEnabledRecursively(barreOutils.btnNouveau, true);
            UiUtils.setEnabledRecursively(barreOutils.btnOuvrir, true);
        }
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
        // Évite les doublons exacts
        for (int i = 0; i < tabs.getTabCount(); i++) {
            if (i != idx && name.equalsIgnoreCase(tabs.getTitleAt(i))) {
                JOptionPane.showMessageDialog(this, "Un onglet porte déjà ce nom.",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        tabs.setTitleAt(idx, name);
    }

    private void closeTabAt(int idx) {
        tabs.removeTabAt(idx);
        disableButton();
    }

    public Canvas getSelectedCanvas() {
        Component comp = tabs.getSelectedComponent();
        if (comp instanceof Canvas) {
            return (Canvas) comp;
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
        tabsErreur = new TableauErreur();

    }

    private void addButton() {

        JToolBar toolBar = new JToolBar();
        JMenuItem newItem = new JMenuItem("Nouveau");
        JMenuItem openItem = new JMenuItem("Ouvrir");
        JMenuItem exportItem = new JMenuItem("Exporter");

        createFruitButtonGroup.add(selectionButton);
        createFruitButtonGroup.add(additionButton);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("HeatMyfloor");

        mainPanel.setLayout(new java.awt.BorderLayout());

        //Menu du bouton fichier
        newItem.setText("nouveau projet");
        fileMenu.add(newItem);
        newItem.addActionListener(e -> handleNewProject());
        fileMenu.add(openItem);
        fileMenu.add(exportItem);
        HelpMenuItem.setText("Aide");
        fileMenu.add(HelpMenuItem);
        quitMenuItem.setText("Quitter");

        fileMenu.add(quitMenuItem);

        topMenuBar.add(fileMenu);

        editMenu.setText("Editer");
        topMenuBar.add(editMenu);
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

}
