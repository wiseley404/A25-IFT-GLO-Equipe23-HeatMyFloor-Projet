/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.heatmyfloor;

import com.heatmyfloor.gui.MainWindow;
import javax.swing.JFrame;

/**
 *
 * @author tatow
 */
public class HeatMyFloor {

    public static void main(String[] args) {
  
        
        MainWindow mainWindow = new MainWindow();
        mainWindow.setExtendedState(mainWindow.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        mainWindow.setVisible(true);
    }
}
