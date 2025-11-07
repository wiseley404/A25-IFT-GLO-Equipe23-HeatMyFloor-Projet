package com.heatmyfloor.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 *
 * @author tatow
 */
public class BarreOutils extends JPanel {

    private BarreOutilsActions actions;          // <— callback vers MainWindow
    public ButtonCard btnNouveau;
    public ButtonCard btnOuvrir;
    public ButtonCard btnExporter;
    public ButtonCard btnEnregistrer;
    public ButtonCard btnRectangle;
    private ButtonCard btnIrregulier;
    private FormeIrregulierPanel dessinPanel;

    public BarreOutils() {
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
        btnExporter = card("Exporter", "/Icons/exporter.png");

        addGroup(ribbon, gc, col++, makeGroup("Projet",
                btnNouveau, btnOuvrir, btnEnregistrer, btnExporter
        ), 4);
        addSep(ribbon, gc, col++);

        // --- Modélisation ---
        addGroup(ribbon, gc, col++, makeGroup("Modélisation",
                card("Pièce", "/Icons/piece.png"),
                card("Fil", "/Icons/Fil.png")
        ), 2);
        addSep(ribbon, gc, col++);

        // --- Affichage ---
        addGroup(ribbon, gc, col++, makeGroup("Affichage",
                // card("Vue 2D", "/icons/view2d.png"),
                card("Vue 3D", "/Icons/3d.png")
        ), 2);
        addSep(ribbon, gc, col++);

        // --- Formes ---
        btnRectangle = card("Rectangle", "/Icons/Rectangle.png");
        btnIrregulier = card("Irregulière", "/Icons/Polygone.png");
        addGroup(ribbon, gc, col++, makeGroup("Formes",
                btnRectangle,
                btnIrregulier
        ), 2);
        addSep(ribbon, gc, col++);

        // --- Meubles ---
        addGroup(ribbon, gc, col++, makeGroup("Meubles",
                card("Sans drain", "/icons/MeubleSansDrain.png"),
                card("Avec drain", "/icons/MeubleAvecDrain.png")
        ), 2);
        addSep(ribbon, gc, col++);

        // --- Autres ---
        addGroup(ribbon, gc, col++, makeGroup("Autres",
                card("Thermostat", "/icons/thermostat.png"),
                card("Zones", "/icons/zone.png")
        ), 2);

        add(ribbon, BorderLayout.CENTER);
    }

}
