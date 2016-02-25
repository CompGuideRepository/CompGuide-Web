/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Service;

import com.compguide.web.Persistence.SessionBeans.GuidelineFacade;
import com.compguide.web.Persistence.Entities.Guideline;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author António
 */
@ManagedBean(name = "guidelineService")
@ApplicationScoped
public class GuidelineService {

    private List<Guideline> guidelines;
    @EJB
    private com.compguide.web.Persistence.SessionBeans.GuidelineFacade ejbFacade;

    @PostConstruct
    public void init() {
        guidelines = new ArrayList<Guideline>();
        guidelines = getFacade().findAll();
    }

    public List<Guideline> getGuidelines() {
        return guidelines;
    }

    private GuidelineFacade getFacade() {
        if (ejbFacade == null) {
            ejbFacade = new GuidelineFacade();
        }
        return ejbFacade;
    }

}
