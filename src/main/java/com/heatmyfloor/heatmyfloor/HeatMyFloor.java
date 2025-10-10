/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.heatmyfloor.heatmyfloor;

import com.heatmyfloor.view.MainView;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author tatow
 */
public class HeatMyFloor {

    public static void main(String[] args) {
        //System.out.println("Hello World!");
        
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("HeatMyfloor");

            // Ajout de ton JPanel (MainView) dans la fenêtre
            frame.setContentPane(new MainView());

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack(); // ajuste la taille automatiquement
            frame.setLocationRelativeTo(null); // centre la fenêtre
            frame.setVisible(true);
        });
    }
}
