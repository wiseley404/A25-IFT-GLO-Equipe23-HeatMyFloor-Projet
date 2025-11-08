package com.heatmyfloor.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import com.heatmyfloor.domain.items.TypeSansDrain;
import com.heatmyfloor.domain.items.TypeAvecDrain;

/**
 *
 * @author tatow
 */
public class BarreOutils extends JPanel {
    
    private MainWindow mainWindow;
    private BarreOutilsActions actions;          // <— callback vers MainWindow
    public ButtonCard btnNouveau;
    public ButtonCard btnOuvrir;
    public ButtonCard btnExporter;
    public ButtonCard btnEnregistrer;
    public ButtonCard btnRectangle;
    private ButtonCard btnIrregulier;
    private ButtonCard btnMenuSansDrain;
    private ButtonCard btnMenuAvecDrain;
    

    public BarreOutils(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 200, 160)));
        setOpaque(true);
        setBackground(Color.white);

        // Ruban plein écran : GridBagLayout -> 100% de la largeur
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
        btnMenuSansDrain  = card("Sans drain", "/icons/MeubleSansDrain.png");
        btnMenuAvecDrain = card("Avec drain", "/icons/MeubleAvecDrain.png");
        addGroup(ribbon, gc, col++, makeGroup("Meubles",
                btnMenuSansDrain,
                btnMenuAvecDrain
        ), 2);
        addSep(ribbon, gc, col++);

        // --- Autres ---
        addGroup(ribbon, gc, col++, makeGroup("Autres",
                card("Thermostat", "/icons/thermostat.png"),
                card("Zones", "/icons/zone.png")
        ), 2);

        add(ribbon, BorderLayout.CENTER);
    }

    public void onRectangleClick(Runnable  r) {
        btnRectangle.setOnClick(e -> r.run());
    }
    
        public void onSansDrainClicked(){
        btnMenuSansDrain.setOnClick(e -> {
            JPopupMenu menuItemSansDrain = new JPopupMenu();
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setPreferredSize(new Dimension(130, 200));
            ButtonCard card1 = card("Armoire", "/images/armoire.png");
            ButtonCard card2 = card("Placard", "/images/placard.png");
            card1.setMinimumSize(new Dimension(120,100));
            card2.setMinimumSize(new Dimension(120,100));
            panel.add(card1);
            panel.add(card2);
            menuItemSansDrain.add(panel);
            menuItemSansDrain.show(btnMenuSansDrain, btnMenuSansDrain.getWidth()-5, btnMenuSansDrain.getHeight()-5);
            
            ButtonCard[] cards = {card1, card2};
            for (ButtonCard c: cards){
                c.setOnClick(event -> {
                    String nom = event.getActionCommand().toUpperCase();
                    mainWindow.controller.ajouterMeubleSansDrain(mainWindow.controller.getPiece().getCentre(), TypeSansDrain.valueOf(nom));
                    mainWindow.currentCanvas.repaint();
                });
            }
        });

    }
        
        
    public void onAvecDrainClicked(){
        btnMenuAvecDrain.setOnClick(e -> {
            JPopupMenu menuItemAvecDrain = new JPopupMenu();
            JPanel panel = new JPanel(new GridLayout(2,2));
            panel.setPreferredSize(new Dimension(260, 200));
            
            ButtonCard card1 = card("Bain", "/images/bain.png");
            ButtonCard card2 = card("Douche", "/images/douche.png");
            ButtonCard card3 = card("Toilette", "/images/toilette.png");
            ButtonCard card4 = card("Vanité", "/images/vanite.png");
            
            card1.setMinimumSize(new Dimension(120,100));
            card2.setMinimumSize(new Dimension(120,100));
            
            panel.add(card1);
            panel.add(card2);
            panel.add(card3);
            panel.add(card4);
            menuItemAvecDrain.add(panel);
            menuItemAvecDrain.show(btnMenuAvecDrain, btnMenuAvecDrain.getWidth()-5, btnMenuAvecDrain.getHeight()-5);
            
            ButtonCard[] cards = {card1, card2, card3, card4};
            for (ButtonCard c: cards){
                c.setOnClick(event -> {
                    String nom = event.getActionCommand().toUpperCase();
                    mainWindow.controller.ajouterMeubleAvecDrain(new com.heatmyfloor.domain.Point(100, 100), TypeAvecDrain.valueOf(nom));
                    mainWindow.currentCanvas.repaint();
                });
            }
        });
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

    // Fabrique une ButtonCard (sans action par défaut)
    private ButtonCard card(String text, String resPath) {
        return new ButtonCard(text, loadIcon(resPath));
    }

    private ImageIcon loadIcon(String path) {
        java.net.URL url = getClass().getResource(path);
        if (url == null) {
            System.err.println("⚠️ Image non trouvée : " + path);
        }
        return (url != null) ? new ImageIcon(url) : null;
    }

}
