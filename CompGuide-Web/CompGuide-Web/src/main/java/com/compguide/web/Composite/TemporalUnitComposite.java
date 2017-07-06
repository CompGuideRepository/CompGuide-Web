/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Composite;

import com.compguide.web.Persistence.SessionBeans.TemporalUnitFacade;
import com.compguide.web.Persistence.Entities.TemporalUnit;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author António
 */
@Named
@SessionScoped
public class TemporalUnitComposite implements Serializable {

    @EJB
    private com.compguide.web.Persistence.SessionBeans.TemporalUnitFacade ejbTemporalUnitFacade;

    public TemporalUnitComposite() {
    }

    private TemporalUnitFacade getTemporalUnitFacade() {
        return ejbTemporalUnitFacade;
    }

    public TemporalUnit checkTemporalUnit(TemporalUnit temporalUnit) {

        TemporalUnit temporalUnitID = getTemporalUnitFacade().findByValue(
                temporalUnit.getValue()
        );

        if (temporalUnitID == null) {
            getTemporalUnitFacade().create(temporalUnit);
            temporalUnitID = temporalUnit;
        } else {
// do something
        }

        return temporalUnitID;
    }
}
