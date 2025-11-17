/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heatmyfloor.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.nio.file.Path;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.Timer;

/**
 *
 * @author tatow
 */
public class UiUtils {

    private UiUtils() {
    }

    public enum ToastType {
        INFO,
        SUCCESS,
        ERROR
    }

    public static void setEnabledRecursively(Component c, boolean enabled) {
        c.setEnabled(enabled);
        if (c instanceof Container cont) {
            for (Component child : cont.getComponents()) {
                setEnabledRecursively(child, enabled);
            }
        }
    }

    public static Path choisirDossierSauvegarde(JFrame parent) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setDialogTitle("Choisir un dossier de sauvegarde");

        int result = chooser.showOpenDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedDir = chooser.getSelectedFile();
            return selectedDir.toPath();
        } else {
            return null;
        }
    }

    public static Path choisirFichierJson(JFrame parent) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setDialogTitle("Choisir un fichier JSON");

        chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().toLowerCase().endsWith(".json");
            }

            @Override
            public String getDescription() {
                return "Fichiers JSON (*.json)";
            }
        });

        int result = chooser.showOpenDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile().toPath();
        } else {
            return null;
        }
    }

    public static void showToastTopRight(JFrame owner, ToastType typeNotification, String message) {

        Color bg;

        switch (typeNotification) {
            case SUCCESS ->
                bg = new Color(40, 180, 99, 220);     // vert
            case ERROR ->
                bg = new Color(231, 76, 60, 220);    // rouge
            default ->
                bg = new Color(52, 152, 219, 220);   // bleu info
        }

        JWindow window = new JWindow(owner);
        window.setLayout(new BorderLayout());

        JLabel label = new JLabel(message);
        label.setOpaque(true);
        label.setBackground(bg);
        label.setForeground(Color.WHITE);
        label.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        window.add(label);

        window.pack();

        Dimension frameSize = owner.getSize();
        Point frameLoc = owner.getLocationOnScreen();

        int x = frameLoc.x + frameSize.width - window.getWidth() - 20;
        int y = frameLoc.y + 20;

        window.setLocation(x, y);
        window.setAlwaysOnTop(true);

        window.setVisible(true);

        new Timer(2500, e -> window.dispose()).start();
    }
}
