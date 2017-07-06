/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Adapters;

import com.compguide.web.Execution.Entities.Action;
import com.compguide.web.Execution.Entities.ClinicalTask;
import com.compguide.web.Execution.Entities.Periodicity;
import com.compguide.web.Execution.Entities.Plan;

/**
 *
 * @author António
 */
public class StopConditionSetAdapter implements GuidelineInterface {

    private com.compguide.web.Persistence.Entities.StopConditionSet stopConditionSet;

    public StopConditionSetAdapter() {
    }

    @Override
    public GuidelineInterface fetchTemporalPatternFromClinicaltask(ClinicalTask task) {

        if (task.getTaskType().toLowerCase().equals("plan")) {
            Periodicity per = ((Plan) task).getPeriodicity();
        }

        if (task.getTaskType().toLowerCase().equals("action")) {
            Periodicity per = ((Action) task).getPeriodicity();
        }
        return this;
    }

    @Override
    public Object getObject() {
        return stopConditionSet;
    }

    @Override
    public void passObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
