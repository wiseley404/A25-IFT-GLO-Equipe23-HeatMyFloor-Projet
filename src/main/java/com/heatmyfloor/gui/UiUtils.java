/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heatmyfloor.gui;

import java.awt.Component;
import java.awt.Container;

/**
 *
 * @author tatow
 */
public class UiUtils {
     private UiUtils() {}

    public static void setEnabledRecursively(Component c, boolean enabled) {
        c.setEnabled(enabled);
        if (c instanceof Container cont) {
            for (Component child : cont.getComponents()) {
                setEnabledRecursively(child, enabled);
            }
        }
    }
}

