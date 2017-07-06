/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Composite;

import com.compguide.web.Persistence.Entities.ConditionSet;
import com.compguide.web.Persistence.Entities.Outcome;
import com.compguide.web.Persistence.SessionBeans.OutcomeFacade;
import static com.google.gson.reflect.TypeToken.get;
import java.io.Serializable;
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
public class OutcomeComposite implements Serializable {

    private Outcome outcome;
    @Inject
    private ConditionSetComposite conditionSetComposite;
    @EJB
    private com.compguide.web.Persistence.SessionBeans.OutcomeFacade outcomeFacade;

    public OutcomeComposite() {
    }

    public Outcome getOutcome() {
        return outcome;
    }

    public OutcomeFacade getOutcomeFacade() {
        return outcomeFacade;
    }

    public void setOutcome(Outcome outcome) {
        this.outcome = outcome;
    }

    public ConditionSetComposite getConditionSetComposite() {
        return conditionSetComposite;
    }

    public void setConditionSet(ConditionSet conditionSet) {
        outcome.setConditionSetID(conditionSet);
    }

    public void check() {
        Outcome aux = checkOutcome(outcome);
    }

    private Outcome checkOutcome(Outcome outcome) {
        Outcome aux = getOutcomeFacade().findByScheduleTaskID(outcome.getScheduleTaskID());

        if (aux == null) {
            getOutcomeFacade().create(outcome);
            aux = outcome;

            conditionSetComposite.setConditionSet(outcome.getConditionSetID());
            conditionSetComposite.check();

            outcome.setConditionSetID(conditionSetComposite.getConditionSet());
        }

        return outcome;
    }

}
