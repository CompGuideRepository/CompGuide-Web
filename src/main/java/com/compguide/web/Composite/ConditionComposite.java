/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Composite;

import com.compguide.web.Persistence.Entities.Condition;
import com.compguide.web.Persistence.Entities.ConditionSet;
import com.compguide.web.Persistence.SessionBeans.ConditionFacade;
import com.compguide.web.Persistence.Entities.TemporalRestriction;
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
public class ConditionComposite implements Serializable {

    private Condition condition;
    @EJB
    com.compguide.web.Persistence.SessionBeans.ConditionFacade conditionFacade;

    public ConditionComposite() {
        condition = new Condition();
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public ConditionFacade getConditionFacade() {
        return conditionFacade;
    }

    public void setTemporalRestriction(TemporalRestriction temporalRestriction) {
        condition.setTemporalRestrictionID(temporalRestriction);
    }

    public void setConditionSet(ConditionSet conditionSet) {
        condition.setConditionSetID(conditionSet);
    }

    public void check() {
        Condition aux = checkCondition(condition);
    }

    private Condition checkCondition(Condition condition) {

        Condition aux = null;

        if (condition.isTemporalRestriction()) {
            aux = getConditionFacade().findByValuesAndTemporalRestrictionID(condition, condition.getTemporalRestrictionID());
        } else {
            aux = getConditionFacade().findByValues(condition);
        }

        if (aux == null) {
            getConditionFacade().create(condition);
            aux = condition;
        }

        this.condition = aux;

        return aux;
    }

}
