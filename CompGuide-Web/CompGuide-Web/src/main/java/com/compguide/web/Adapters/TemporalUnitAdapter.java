/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Adapters;

import com.compguide.web.Execution.Entities.Action;
import com.compguide.web.Execution.Entities.ClinicalTask;
import com.compguide.web.Execution.Entities.Decision;
import com.compguide.web.Execution.Entities.Duration;
import com.compguide.web.Execution.Entities.Periodicity;
import com.compguide.web.Execution.Entities.Plan;
import com.compguide.web.Execution.Entities.Question;
import com.compguide.web.Execution.Entities.WaitingTime;

/**
 *
 * @author António
 */
public class TemporalUnitAdapter implements GuidelineInterface {

    private com.compguide.web.Persistence.Entities.TemporalUnit temporalUnit;

    public TemporalUnitAdapter() {
    }

    @Override
    public GuidelineInterface fetchTemporalPatternFromClinicaltask(ClinicalTask task) {

        if (task.getTaskType().toLowerCase().equals("plan")) {
            Periodicity per = ((Plan) task).getPeriodicity();
            Duration dur = ((Plan) task).getDuration();
            WaitingTime wtg = ((Plan) task).getWaitingTime();
        }
        if (task.getTaskType().toLowerCase().equals("question")) {
            WaitingTime wtg = ((Question) task).getWaitingTime();
        }
        if (task.getTaskType().toLowerCase().equals("decision")) {
            WaitingTime wtg = ((Decision) task).getWaitingTime();
        }
        if (task.getTaskType().toLowerCase().equals("action")) {
            Periodicity per = ((Action) task).getPeriodicity();
            Duration dur = ((Action) task).getDuration();
            WaitingTime wtg = ((Action) task).getWaitingTime();
        }
        return this;
    }

    @Override
    public Object getObject() {
        return temporalUnit;
    }

    @Override
    public void passObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
