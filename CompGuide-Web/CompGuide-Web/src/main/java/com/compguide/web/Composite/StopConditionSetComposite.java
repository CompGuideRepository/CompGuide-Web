/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Composite;

import com.compguide.web.Persistence.Entities.Condition;
import com.compguide.web.Persistence.Entities.Periodicity;
import com.compguide.web.Persistence.SessionBeans.ConditionFacade;
import com.compguide.web.Persistence.SessionBeans.StopConditionSetFacade;
import com.compguide.web.Persistence.Entities.StopConditionSet;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author António
 */
@Named
@SessionScoped
public class StopConditionSetComposite implements Serializable {

    @EJB
    private com.compguide.web.Persistence.SessionBeans.StopConditionSetFacade stopConditionSetFacade;
    @EJB
    private com.compguide.web.Persistence.SessionBeans.ConditionFacade conditionFacade;
    @Inject
    private ConditionComposite conditionComposite;
    private StopConditionSet stopConditionSet;

    public StopConditionSetComposite() {
        stopConditionSet = new StopConditionSet();
    }

    public StopConditionSetFacade getStopConditionSetFacade() {
        return stopConditionSetFacade;
    }

    public ConditionComposite getConditionComposite() {
        return conditionComposite;
    }

    public StopConditionSet getStopConditionSet() {
        return stopConditionSet;
    }

    public ConditionFacade getConditionFacade() {
        return conditionFacade;
    }

    public void addCondition(Condition condition) {
        stopConditionSet.getConditionList().add(condition);
    }

    public void setStopConditionSet(StopConditionSet stopConditionSet) {
        this.stopConditionSet = stopConditionSet;
    }

    void setPeriodicity(Periodicity periodicity) {
        this.stopConditionSet.setPeriodicityID(periodicity);
    }

    public void check() {
        StopConditionSet conditionSet = checkStopConditionSet(stopConditionSet);
        stopConditionSet = conditionSet;
    }

    private StopConditionSet checkStopConditionSet(StopConditionSet stopConditionSet) {

        List<Condition> list = stopConditionSet.getConditionList();
        List<Condition> conditions = new ArrayList<Condition>();

        StopConditionSet aux = getStopConditionSetFacade().findByPeriodicityID(stopConditionSet.getPeriodicityID());

        if (aux == null) {
            getStopConditionSetFacade().create(stopConditionSet);
            aux = stopConditionSet;
        }

        for (Condition condition : list) {
            condition.setStopConditionSetID(aux);
            getConditionFacade().create(condition);
            conditions.add(condition);
        }

        aux.setConditionList(conditions);
        this.stopConditionSet = aux;

        return aux;
    }
}
