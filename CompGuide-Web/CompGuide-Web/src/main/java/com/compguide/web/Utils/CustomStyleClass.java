/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author António
 */
public class CustomStyleClass {

    private static final Map<Integer, String> styleClass = new HashMap<Integer, String>();
    private int styleIndex;
    private static CustomStyleClass customStyleClass;

    static {
        /*0*/ styleClass.put(0, "clinical");
        /*1*/ styleClass.put(1, "global");
        /*2*/ styleClass.put(2, "personal");
    }

    private CustomStyleClass() {
        styleIndex = 0;
    }

    public static CustomStyleClass instance() {
        if (customStyleClass == null) {
            customStyleClass = new CustomStyleClass();
        }

        return customStyleClass;
    }

    public synchronized String getStyleClass() {
        if (styleIndex > styleClass.size()) {
            styleIndex = 0;
        }

        String response = styleClass.get(styleIndex);
        styleIndex++;

        return response;
    }

}
