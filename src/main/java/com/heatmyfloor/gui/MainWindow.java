/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heatmyfloor.gui;

import java.awt.BorderLayout;
import java.awt.Color;
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
        mainPanel.setOpaque(true);
        mainPanel.setBackground(Color.WHITE);
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
        tabs.addTab("Projet 2", new Canvas());
        center.add(tabs, BorderLayout.CENTER);

        mainPanel.add(center, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(new PositionPanel(), BorderLayout.CENTER);
        bottom.add(new TableauErreur(), BorderLayout.EAST);
        mainPanel.add(bottom, BorderLayout.SOUTH);

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
