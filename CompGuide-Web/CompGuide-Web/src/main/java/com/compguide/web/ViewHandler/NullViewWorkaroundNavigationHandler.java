/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.ViewHandler;

import javax.faces.application.NavigationHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

/**
 *
 * @author António
 */
public class NullViewWorkaroundNavigationHandler extends NavigationHandler {

    private NavigationHandler parent;

    public NullViewWorkaroundNavigationHandler() {
        super();
    }

    public NullViewWorkaroundNavigationHandler(NavigationHandler parent) {
        super();
        this.parent = parent;
    }

    @Override
    public void handleNavigation(FacesContext context, String fromAction, String outcome) {
        if (context.getViewRoot() == null) {
            final UIViewRoot viewRoot = (UIViewRoot) context.getApplication()
                    .createComponent(UIViewRoot.COMPONENT_TYPE);
            viewRoot.setViewId("/");
            context.setViewRoot(viewRoot);
        }
        parent.handleNavigation(context, fromAction, outcome);
    }

}
