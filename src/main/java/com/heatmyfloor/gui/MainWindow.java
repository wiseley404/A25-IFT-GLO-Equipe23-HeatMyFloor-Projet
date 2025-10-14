/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heatmyfloor.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
//import javax.swing.ImageIcon;

/**
 *
 * @author tatow
 */
public class MainWindow extends javax.swing.JFrame {

    public MainWindow() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        createFruitButtonGroup = new javax.swing.ButtonGroup();
        mainPanel = new javax.swing.JPanel();
        buttonTopPanel = new javax.swing.JPanel(new FlowLayout(FlowLayout.LEFT));
        selectionButton = new javax.swing.JToggleButton();
        additionButton = new javax.swing.JToggleButton();
        itemTypeBox = new javax.swing.JComboBox();
        jSplitPane1 = new javax.swing.JSplitPane();
        mainScrollPane = new javax.swing.JScrollPane();
        //drawingPanel = new ca.ulaval.glo2004.gui.DrawingPanel(this);
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        topMenuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu("Fichier");
        JMenuItem newItem = new JMenuItem("Nouveau");
        JMenuItem openItem = new JMenuItem("Ouvrir");
        JMenuItem exportItem = new JMenuItem("Exporter");
        openMenuItem = new javax.swing.JMenuItem();
        quitMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        JToolBar toolBar = new JToolBar();

        createFruitButtonGroup.add(selectionButton);
        createFruitButtonGroup.add(additionButton);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("HeatMyfloor");

        mainPanel.setLayout(new java.awt.BorderLayout());
        
        //Menu du bouton fichier
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(exportItem);
        quitMenuItem.setText("Quitter");
        
        fileMenu.add(quitMenuItem);

        topMenuBar.add(fileMenu);

        editMenu.setText("Editer");
        topMenuBar.add(editMenu);
        
        //Menu du toolbar
        
        mainPanel.add(new BarreOutils(), BorderLayout.NORTH);
        
          JPanel center = new JPanel(new BorderLayout());
        Proprietes props = new Proprietes();
        center.add(props, BorderLayout.WEST);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Projet 1", new Canvas());
        tabs.addTab("Projet 2", new JPanel());
        center.add(tabs, BorderLayout.CENTER);

        mainPanel.add(center, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(new PositionPanel(), BorderLayout.CENTER);
        bottom.add(new TableauErreur(), BorderLayout.EAST);
        mainPanel.add(bottom, BorderLayout.SOUTH);
        
        
//
//        buttonTopPanel.setPreferredSize(new java.awt.Dimension(400, 35));
//
//        selectionButton.setSelected(true);
//        selectionButton.setText("Mode Sélection");
//        /**
//         * selectionButton.addActionListener(new java.awt.event.ActionListener()
//         * { public void actionPerformed(java.awt.event.ActionEvent evt) {
//         * selectionButtonActionPerformed(evt); } });*
//         */
//        buttonTopPanel.add(selectionButton);
//
//        additionButton.setText("Mode Ajout");
//        additionButton.setToolTipText("");
//        additionButton.setPreferredSize(new java.awt.Dimension(105, 23));
        /**
         * additionButton.addActionListener(new java.awt.event.ActionListener()
         * { public void actionPerformed(java.awt.event.ActionEvent evt) {
         * additionButtonActionPerformed(evt); } });*
         */
//        buttonTopPanel.add(additionButton);
//
//        itemTypeBox.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"APPLE", "ORANGE"}));
//        itemTypeBox.setPreferredSize(new java.awt.Dimension(105, 23));
//        buttonTopPanel.add(itemTypeBox);
//
//        mainPanel.add(buttonTopPanel, java.awt.BorderLayout.NORTH);

//        jSplitPane1.setMinimumSize(new java.awt.Dimension(0, 202));
//        jSplitPane1.setPreferredSize(new Dimension(java.awt.Toolkit.getDefaultToolkit().getScreenSize().width, (int) (java.awt.Toolkit.getDefaultToolkit().getScreenSize().height * 0.5)));
//
//        mainScrollPane.setMinimumSize(new java.awt.Dimension(0, 0));
//        mainScrollPane.setPreferredSize(new Dimension((int) (java.awt.Toolkit.getDefaultToolkit().getScreenSize().width * 0.85), (int) (java.awt.Toolkit.getDefaultToolkit().getScreenSize().height * 0.5)));

        /**
         * drawingPanel.setPreferredSize(new java.awt.Dimension(0, 0));
         * drawingPanel.addMouseListener(new java.awt.event.MouseAdapter() {
         * public void mousePressed(java.awt.event.MouseEvent evt) {
         * drawingPanelMousePressed(evt); } });
         * drawingPanel.addMouseMotionListener(new
         * java.awt.event.MouseMotionAdapter() { public void
         * mouseDragged(java.awt.event.MouseEvent evt) {
         * drawingPanelMouseDragged(evt); } });
         *
         * javax.swing.GroupLayout drawingPanelLayout = new
         * javax.swing.GroupLayout(drawingPanel);
         * drawingPanel.setLayout(drawingPanelLayout);
         * drawingPanelLayout.setHorizontalGroup(
         * drawingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         * .addGap(0, 1598, Short.MAX_VALUE) );
         * drawingPanelLayout.setVerticalGroup(
         * drawingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         * .addGap(0, 538, Short.MAX_VALUE) );
         *
         * mainScrollPane.setViewportView(drawingPanel);*
         */
//        jSplitPane1.setLeftComponent(mainScrollPane);
//
//        jTabbedPane1.setPreferredSize(new java.awt.Dimension(0, 540));
//
//        jPanel1.setPreferredSize(new Dimension((int) (java.awt.Toolkit.getDefaultToolkit().getScreenSize().width * 0.15), (int) (java.awt.Toolkit.getDefaultToolkit().getScreenSize().height * 0.75)));
//
//        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
//        jPanel1.setLayout(jPanel1Layout);
//        jPanel1Layout.setHorizontalGroup(
//                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                        .addGap(0, 975, Short.MAX_VALUE)
//        );
//        jPanel1Layout.setVerticalGroup(
//                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                        .addGap(0, 512, Short.MAX_VALUE)
//        );
//
//        //ImageIcon gearIcon = new ImageIcon(getClass().getResource("/com/heatmyfloor/gui/Icons/Setting.png"));
//        jTabbedPane1.addTab("Propriétés", null, jPanel1, "Propriétées de la pièce");
//
//        jSplitPane1.setRightComponent(jTabbedPane1);
//
//        mainPanel.add(jSplitPane1, java.awt.BorderLayout.CENTER);

        

      
        /**
         * quitMenuItem.addActionListener(new java.awt.event.ActionListener() {
         * public void actionPerformed(java.awt.event.ActionEvent evt) {
         * quitMenuItemActionPerformed(evt); } });*
         */
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

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private javax.swing.JToggleButton additionButton;
    private javax.swing.JPanel buttonTopPanel;
    private javax.swing.ButtonGroup createFruitButtonGroup;
    //private ca.ulaval.glo2004.gui.DrawingPanel drawingPanel;
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
    private javax.swing.JToggleButton selectionButton;
    private javax.swing.JMenuBar topMenuBar;
}
