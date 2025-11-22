/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.heatmyfloor;

import com.heatmyfloor.gui.MainWindow;
import javax.swing.JFrame;
import javax.swing.ImageIcon;

/**
 *
 * @author tatow
 */
public class HeatMyFloor {

    public static void main(String[] args) {
        
        LoadingScreen.show();
        MainWindow mainWindow = new MainWindow();
        mainWindow.setExtendedState(mainWindow.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        mainWindow.setVisible(true);
        
        ImageIcon logo = new ImageIcon(HeatMyFloor.class.getResource("/Icons/HeatMyFloor_logo.png"));
        mainWindow.setIconImage(logo.getImage());
        mainWindow.restaurerSession();
    }
}
