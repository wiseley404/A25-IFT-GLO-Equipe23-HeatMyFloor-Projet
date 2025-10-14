package com.heatmyfloor.gui;

import javax.swing.*;
import java.awt.*;


/**
 *
 * @author tatow
 */
public class BarreOutils extends JPanel {

    public BarreOutils() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createMatteBorder(0,0,1,0,new Color(230,200,160)));
        setOpaque(false);

        // Ruban plein écran : GridBagLayout -> 100% de la largeur
        JPanel ribbon = new JPanel(new GridBagLayout());
        ribbon.setOpaque(false);

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridy = 0;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(8, 12, 8, 12);

        int col = 0;

        // --- Projet ---
        addGroup(ribbon, gc, col++, makeGroup("Projet",
            card("Nouveau",     "resources/Icons/new.png"),
            card("Ouvrir",      "/icons/open.png"),
            card("Enregistrer", "/icons/save.png"),
            card("Exporter",    "/icons/export.png")
        ), /*weightx=*/4); // poids proportionnel au nb de cards
        addSep(ribbon, gc, col++);

        // --- Modélisation ---
        addGroup(ribbon, gc, col++, makeGroup("Modélisation",
            card("Pièce", "/icons/room.png"),
            card("Fil",   "/icons/coil.png")
        ), 2);
        addSep(ribbon, gc, col++);

        // --- Affichage ---
        addGroup(ribbon, gc, col++, makeGroup("Affichage",
            card("Vue 2D", "/icons/view2d.png"),
            card("Vue 3D", "/icons/view3d.png")
        ), 2);
        addSep(ribbon, gc, col++);

        // --- Formes ---
        addGroup(ribbon, gc, col++, makeGroup("Formes",
            card("Rectangle", "/icons/rect.png"),
            card("Cercle",    "/icons/circle.png")
        ), 2);
        addSep(ribbon, gc, col++);

        // --- Meubles ---
        addGroup(ribbon, gc, col++, makeGroup("Meubles",
            card("Sans drain", "/icons/cabinet.png"),
            card("Avec drain", "/icons/sink.png")
        ), 2);
        addSep(ribbon, gc, col++);

        // --- Autres ---
        addGroup(ribbon, gc, col++, makeGroup("Autres",
            card("Thermostat", "/icons/thermo.png"),
            card("Zones",      "/icons/zones.png")
        ), 2);

        add(ribbon, BorderLayout.CENTER);
    }

    /* ---------- Helpers layout ---------- */

    // Ajoute un "groupe" dans la grille avec un weightx donné (il s'étire)
    private void addGroup(JPanel parent, GridBagConstraints base, int gridx, JComponent group, double weightx) {
        GridBagConstraints gc = (GridBagConstraints) base.clone();
        gc.gridx = gridx;
        gc.weightx = weightx;                  // <= clé : s'étire pour remplir 100%
        parent.add(group, gc);
    }

    // Ajoute un séparateur vertical à largeur fixe (ne s'étire pas)
    private void addSep(JPanel parent, GridBagConstraints base, int gridx) {
        GridBagConstraints gc = (GridBagConstraints) base.clone();
        gc.gridx = gridx;
        gc.weightx = 0;                        // <= pas d'étirement
        gc.insets = new Insets(8, 0, 8, 0);
        parent.add(vSep(), gc);
    }

    // Groupe = ligne de cards étirables + titre centré dessous
    private JComponent makeGroup(String title, ButtonCard... cards) {
        JPanel col = new JPanel();
        col.setOpaque(false);
        col.setLayout(new BoxLayout(col, BoxLayout.Y_AXIS));

        // Étirement horizontal égal des cards
        JPanel row = new JPanel(new GridLayout(1, cards.length, 12, 0));
        row.setOpaque(false);
        for (ButtonCard c : cards) row.add(c);

        JLabel lbl = new JLabel(title, SwingConstants.CENTER);
        lbl.setAlignmentX(0.5f);
        lbl.setForeground(new Color(70,70,70));
        lbl.setFont(lbl.getFont().deriveFont(Font.PLAIN, 13f));

        col.add(row);
        col.add(Box.createVerticalStrut(6));
        col.add(lbl);
        return col;
    }

    private JComponent vSep() {
        JSeparator s = new JSeparator(SwingConstants.VERTICAL);
        s.setForeground(new Color(210,190,160));
        s.setPreferredSize(new Dimension(1, 64));
        return s;
    }

    // Fabrique une ButtonCard (sans action par défaut)
    private ButtonCard card(String text, String resPath) {
        return new ButtonCard(text, loadIcon(resPath));
    }

    private ImageIcon loadIcon(String path) {
        java.net.URL url = getClass().getResource(path);
        return (url != null) ? new ImageIcon(url) : null;
    }
}