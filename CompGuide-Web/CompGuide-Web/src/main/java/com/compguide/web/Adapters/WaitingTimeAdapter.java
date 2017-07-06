/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Adapters;

import com.compguide.web.Execution.Entities.Action;
import com.compguide.web.Execution.Entities.ClinicalTask;
import com.compguide.web.Execution.Entities.Decision;
import com.compguide.web.Execution.Entities.Plan;
import com.compguide.web.Execution.Entities.Question;
import com.compguide.web.Execution.Entities.WaitingTime;
import com.compguide.web.Composite.WaitingTimeComposite;
import java.util.List;

/**
 *
 * @author António
 */
public class WaitingTimeAdapter implements GuidelineInterface {

    private List<com.compguide.web.Persistence.Entities.WaitingTime> waitingTime;

    public WaitingTimeAdapter() {
    }

    @Override
    public GuidelineInterface fetchTemporalPatternFromClinicaltask(ClinicalTask task) {

        if (task.getTaskType().toLowerCase().equals("plan")) {
            WaitingTime wtg = ((Plan) task).getWaitingTime();
            WaitingTimeComposite composite = new WaitingTimeComposite();

            if (wtg.asExactValue()) {
                composite.setWaitingTime(
                        wtg.getExactWaitingTime()
                );
            }

            if (wtg.asInterval()) {
                composite.setWaitingTime(
                        wtg.getMinWaitingTime(),
                        wtg.getMaxWaitingTime()
                );

            }

            composite.setTemporalUnit(wtg.getTemporalUnit());
            composite.check();

            waitingTime.add(composite.getWaitingTime());
        }
        if (task.getTaskType().toLowerCase().equals("question")) {
            WaitingTime wtg = ((Question) task).getWaitingTime();
            WaitingTimeComposite composite = new WaitingTimeComposite();

            if (wtg.asExactValue()) {
                composite.setWaitingTime(
                        wtg.getExactWaitingTime()
                );
            }

            if (wtg.asInterval()) {
                composite.setWaitingTime(
                        wtg.getMinWaitingTime(),
                        wtg.getMaxWaitingTime()
                );

            }

            composite.setTemporalUnit(wtg.getTemporalUnit());
            composite.check();

            waitingTime.add(composite.getWaitingTime());
        }
        if (task.getTaskType().toLowerCase().equals("decision")) {
            WaitingTime wtg = ((Decision) task).getWaitingTime();
            WaitingTimeComposite composite = new WaitingTimeComposite();

            if (wtg.asExactValue()) {
                composite.setWaitingTime(
                        wtg.getExactWaitingTime()
                );
            }

            if (wtg.asInterval()) {
                composite.setWaitingTime(
                        wtg.getMinWaitingTime(),
                        wtg.getMaxWaitingTime()
                );

            }

            composite.setTemporalUnit(wtg.getTemporalUnit());
            composite.check();
        }
        if (task.getTaskType().toLowerCase().equals("action")) {
            WaitingTime wtg = ((Action) task).getWaitingTime();
            WaitingTimeComposite composite = new WaitingTimeComposite();

            if (wtg.asExactValue()) {
                composite.setWaitingTime(
                        wtg.getExactWaitingTime()
                );
            }

            if (wtg.asInterval()) {
                composite.setWaitingTime(
                        wtg.getMinWaitingTime(),
                        wtg.getMaxWaitingTime()
                );

            }

            composite.setTemporalUnit(wtg.getTemporalUnit());
            composite.check();

            waitingTime.add(composite.getWaitingTime());

        }
        return this;
    }

    @Override
    public Object getObject() {
        return waitingTime;
    }

    @Override
    public void passObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
