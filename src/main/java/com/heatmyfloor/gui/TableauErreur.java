package com.heatmyfloor.gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public final class TableauErreur extends JPanel {

    private final JPanel messagesPanel;
    private final List<JLabel> messages;

    public TableauErreur() {
        setPreferredSize(new Dimension(700, 160)); // 🔹 plus large et plus haut
        setBorder(BorderFactory.createMatteBorder(10, 1, 0, 0, new Color(230, 230, 230)));
        setLayout(new BorderLayout());
        setBackground(Color.white);

        // 🔹 En-tête
        JPanel title = new JPanel(new FlowLayout(FlowLayout.LEFT));
        title.setOpaque(false);
        JLabel titleLabel = new JLabel("Messages d'erreur");
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 14f));
        title.add(titleLabel);
        add(title, BorderLayout.NORTH);

        // 🔹 Zone des messages
        messagesPanel = new JPanel();
        messagesPanel.setOpaque(false);
        messagesPanel.setLayout(new BoxLayout(messagesPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(messagesPanel);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getViewport().setBackground(Color.white);
        add(scrollPane, BorderLayout.CENTER);

        messages = new ArrayList<>();

        if(messages.isEmpty()){
            addErrorMessage("Aucun message d'erreur pour le moment.");
        }
        
    }

    /**
     * Ajoute un nouveau message d'erreur avec icône ⚠ et couleur rouge.
     */
    public void addErrorMessage(String text) {
        JLabel label = new JLabel("⚠ " + text);
        label.setForeground(new Color(180, 0, 0)); // rouge foncé
        label.setFont(label.getFont().deriveFont(13f));
        messages.add(label);
        messagesPanel.add(label);
        revalidate();
        repaint();
    }

    /**
     * Efface tous les messages.
     */
    public void clearMessages() {
        messages.clear();
        messagesPanel.removeAll();
        revalidate();
        repaint();
    }
}
