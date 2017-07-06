/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Composite;

import com.compguide.web.Persistence.Entities.Condition;
import com.compguide.web.Persistence.Entities.ConditionSet;
import com.compguide.web.Persistence.SessionBeans.ConditionFacade;
import com.compguide.web.Persistence.SessionBeans.ConditionSetFacade;
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
public class ConditionSetComposite implements Serializable {

    private ConditionSet conditionSet;
    @EJB
    private com.compguide.web.Persistence.SessionBeans.ConditionSetFacade conditionSetFacade;
    @EJB
    private com.compguide.web.Persistence.SessionBeans.ConditionFacade conditionFacade;

    @Inject
    private ConditionComposite conditionComposite;

    public ConditionSetComposite() {
        conditionSet = new ConditionSet();
    }

    public ConditionSetFacade getConditionSetFacade() {
        return conditionSetFacade;
    }

    public ConditionFacade getConditionFacade() {
        return conditionFacade;
    }

    public ConditionSet getConditionSet() {
        return conditionSet;
    }

    public void setConditionSet(ConditionSet conditionSet) {
        this.conditionSet = conditionSet;
    }

    public void addCondition(Condition condition) {
        if (conditionSet.getConditionList() == null) {
            conditionSet.setConditionList(new ArrayList<Condition>());
        }
        conditionSet.getConditionList().add(condition);
    }

    public ConditionSetComposite(ConditionFacade conditionFacade) {
        this.conditionFacade = conditionFacade;
    }

    public void check() {
        ConditionSet aux = checkConditionSet(conditionSet);
    }

    private ConditionSet checkConditionSet(ConditionSet conditionSet) {

        List<Condition> list = conditionSet.getConditionList();
        List<Condition> conditions = new ArrayList<Condition>();

        ConditionSet aux = getConditionSetFacade().findByIdentifier(conditionSet.getIdentifier());

        if (aux == null) {
            getConditionSetFacade().create(conditionSet);
            aux = conditionSet;
        }

        if (list != null) {
            for (Condition condition : list) {
                condition.setConditionSetID(aux);
                getConditionFacade().create(condition);
                conditions.add(condition);
            }
        }

        aux.setConditionList(conditions);
        this.conditionSet = aux;

        return aux;
    }

}
