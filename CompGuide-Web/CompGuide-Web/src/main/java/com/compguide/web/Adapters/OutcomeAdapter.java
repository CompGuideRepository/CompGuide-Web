/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Adapters;

import com.compguide.web.Composite.ConditionSetComposite;
import com.compguide.web.Composite.OutcomeComposite;
import com.compguide.web.Execution.Entities.Action;
import com.compguide.web.Execution.Entities.ClinicalTask;
import com.compguide.web.Execution.Entities.Plan;
import com.compguide.web.Persistence.Entities.Condition;
import com.compguide.web.Persistence.Entities.Outcome;
import com.compguide.web.Persistence.Entities.ScheduleTask;
import com.compguide.web.Persistence.SessionBeans.ConditionFacade;
import com.compguide.web.Persistence.SessionBeans.ConditionSetFacade;
import com.compguide.web.Persistence.SessionBeans.OutcomeFacade;
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
public class OutcomeAdapter implements GuidelineInterface, Serializable {

    @EJB
    com.compguide.web.Persistence.SessionBeans.OutcomeFacade outcomeFacade;
    @EJB
    com.compguide.web.Persistence.SessionBeans.ConditionFacade conditionFacade;
    @EJB
    com.compguide.web.Persistence.SessionBeans.ConditionSetFacade conditionSetFacade;
    @Inject
    private OutcomeComposite outcomeComposite;
    @Inject
    private ConditionSetComposite conditionSetComposite;
    private Outcome outcome;
    private ScheduleTask scheduleTask;

    public OutcomeAdapter() {
    }

    public OutcomeFacade getOutcomeFacade() {
        return outcomeFacade;
    }

    public void setOutcomeFacade(OutcomeFacade outcomeFacade) {
        this.outcomeFacade = outcomeFacade;
    }

    public ConditionFacade getConditionFacade() {
        return conditionFacade;
    }

    public void setConditionFacade(ConditionFacade conditionFacade) {
        this.conditionFacade = conditionFacade;
    }

    public ConditionSetFacade getConditionSetFacade() {
        return conditionSetFacade;
    }

    public OutcomeComposite getOutcomeComposite() {
        return outcomeComposite;
    }

    public ConditionSetComposite getConditionSetComposite() {
        return conditionSetComposite;
    }

    @Override
    public GuidelineInterface fetchTemporalPatternFromClinicaltask(ClinicalTask task) {
        if (task.getTaskType().toLowerCase().equals("action")) {
            if (((Action) task).getOutcome() != null && !((Action) task).getOutcome().isEmpty()) {

                com.compguide.web.Execution.Entities.Outcome aux = ((Action) task).getOutcome();

                getOutcomeComposite().setOutcome(new Outcome(Boolean.FALSE, scheduleTask));

                if (!aux.getOutcomeConditionSet().isEmpty()) {
                    for (com.compguide.web.Execution.Entities.Condition condition : aux.getOutcomeConditionSet().get(0).getCondition()) {
                        if (condition.getIsNumeric() == true) {
                            getConditionSetComposite().addCondition(new Condition(
                                    Double.parseDouble(condition.getValue()), null,
                                    condition.getComparisonOperator(), condition.getParameterIdentifier().get(0),
                                    condition.getConditionParameter(), Boolean.FALSE,
                                    Boolean.FALSE, condition.getUnit()));
                        } else {
                            getConditionSetComposite().addCondition(new Condition(
                                    null, condition.getValue(),
                                    condition.getComparisonOperator(), condition.getParameterIdentifier().get(0),
                                    condition.getConditionParameter(), Boolean.FALSE,
                                    Boolean.FALSE, condition.getUnit()));
                        }
                    }
                }

                getConditionSetComposite().check();
                getOutcomeComposite().setOutcome(new Outcome(Boolean.FALSE, scheduleTask));
                getOutcomeComposite().setConditionSet(getConditionSetComposite().getConditionSet());
                getOutcomeComposite().check();

                outcome = getOutcomeComposite().getOutcome();
            }
        }
        if (task.getTaskType().toLowerCase().equals("plan")) {
            if (((Plan) task).getOutcome().size() > 0) {
                com.compguide.web.Execution.Entities.Outcome aux = ((Plan) task).getOutcome().get(0);

                if (!aux.getOutcomeConditionSet().isEmpty()) {
                    for (com.compguide.web.Execution.Entities.Condition condition : aux.getOutcomeConditionSet().get(0).getCondition()) {
                        if (condition.getIsNumeric() == true) {
                            getConditionSetComposite().addCondition(new Condition(
                                    Double.parseDouble(condition.getValue()), null,
                                    condition.getComparisonOperator(), condition.getParameterIdentifier().get(0),
                                    condition.getConditionParameter(), Boolean.FALSE,
                                    Boolean.FALSE, condition.getUnit()));
                        } else if (condition.getParameterIdentifier().size() > 0) {
                            getConditionSetComposite().addCondition(new Condition(
                                    null, condition.getValue(),
                                    condition.getComparisonOperator(), condition.getParameterIdentifier().get(0),
                                    condition.getConditionParameter(), Boolean.FALSE,
                                    Boolean.FALSE, condition.getUnit()));
                        } else {
                            getConditionSetComposite().addCondition(new Condition(
                                    null, condition.getValue(),
                                    condition.getComparisonOperator(), "",
                                    condition.getConditionParameter(), Boolean.FALSE,
                                    Boolean.FALSE, condition.getUnit()));
                        }
                    }
                }

                getConditionSetComposite().check();
                getOutcomeComposite().setOutcome(new Outcome(Boolean.FALSE, scheduleTask));
                getOutcomeComposite().setConditionSet(getConditionSetComposite().getConditionSet());
                getOutcomeComposite().check();

                outcome = getOutcomeComposite().getOutcome();
            }
        }
        return this;
    }

    @Override
    public Object getObject() {
        return outcome;
    }

    @Override
    public void passObject(Object object
    ) {
        if (object instanceof ScheduleTask) {
            scheduleTask = (ScheduleTask) object;
        }
    }

}
