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
import com.compguide.web.Composite.DurationComposite;
import com.compguide.web.Composite.PeriodicityComposite;
import com.compguide.web.Composite.TemporalElementComposite;
import com.compguide.web.Composite.WaitingTimeComposite;
import com.compguide.web.Persistence.Entities.ScheduleTask;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author António
 */
@Named
@SessionScoped
public class TemporalElementAdapter implements GuidelineInterface, Serializable {

    private com.compguide.web.Persistence.Entities.TemporalElement temporalElement;

    @Inject
    private DurationComposite durationComposite;
    @Inject
    private PeriodicityComposite periodicityComposite;
    @Inject
    private TemporalElementComposite temporalElementComposite;
    @Inject
    private WaitingTimeComposite waitingTimeComposite;
    private ScheduleTask scheduleTask;

    public TemporalElementAdapter() {
    }

    public DurationComposite getDurationComposite() {
        return durationComposite;
    }

    public void setDurationComposite(DurationComposite durationComposite) {
        this.durationComposite = durationComposite;
    }

    public PeriodicityComposite getPeriodicityComposite() {
        return periodicityComposite;
    }

    public void setPeriodicityComposite(PeriodicityComposite periodicityComposite) {
        this.periodicityComposite = periodicityComposite;
    }

    public TemporalElementComposite getTemporalElementComposite() {
        return temporalElementComposite;
    }

    public void setTemporalElementComposite(TemporalElementComposite temporalElementComposite) {
        this.temporalElementComposite = temporalElementComposite;
    }

    @Override
    public GuidelineInterface fetchTemporalPatternFromClinicaltask(ClinicalTask task) {

        if (task.getTaskType().toLowerCase().equals("plan")) {

            if (((Plan) task).isDuration()) {
                Duration dur = ((Plan) task).getDuration();

                durationComposite.checkDuration(dur);
                temporalElementComposite.setDuration(durationComposite.getDuration());
            }

            if (((Plan) task).isPeriodicity()) {
                Periodicity per = ((Plan) task).getPeriodicity();

                periodicityComposite.checkPeriodicity(per);
                temporalElementComposite.setPeriodicity(periodicityComposite.getPeriodicity());
            }

            if (((Plan) task).isWaitingTime()) {
                WaitingTime wtg = ((Plan) task).getWaitingTime();

                waitingTimeComposite.setTemporalUnit(wtg.getTemporalUnit());
                if (wtg.asExactValue()) {
                    waitingTimeComposite.setWaitingTime(wtg.getExactWaitingTime());
                    temporalElementComposite.setWaitingTime(waitingTimeComposite.getWaitingTime());
                }

                if (wtg.asInterval()) {
                    waitingTimeComposite.setWaitingTime(wtg.getMinWaitingTime(), wtg.getMaxWaitingTime());
                    temporalElementComposite.setWaitingTime(waitingTimeComposite.getWaitingTime());
                }
            }

            if (((Plan) task).isTemporalPattern()) {
                temporalElementComposite.check();
            } else {
                durationComposite.setDuration(null, null, 1.0);
                durationComposite.setTemporalUnit("day");
                durationComposite.check();

                temporalElementComposite.setDuration(durationComposite.getDuration());
            }

            temporalElement = temporalElementComposite.getTemporalElement();
        }
        if (task.getTaskType().toLowerCase().equals("question")) {
            if (((Question) task).isWaitingTime()) {
                WaitingTime wtg = ((Question) task).getWaitingTime();

                waitingTimeComposite.setTemporalUnit(wtg.getTemporalUnit());

                if (wtg.asExactValue()) {
                    waitingTimeComposite.setWaitingTime(wtg.getExactWaitingTime());
                    temporalElementComposite.setWaitingTime(waitingTimeComposite.getWaitingTime());
                }

                if (wtg.asInterval()) {
                    waitingTimeComposite.setWaitingTime(wtg.getMinWaitingTime(), wtg.getMaxWaitingTime());
                    temporalElementComposite.setWaitingTime(waitingTimeComposite.getWaitingTime());
                }

                temporalElementComposite.check();
                temporalElement = temporalElementComposite.getTemporalElement();
            }
        }

        if (task.getTaskType().toLowerCase().equals("decision")) {
            WaitingTime wtg = ((Decision) task).getWaitingTime();

            if (((Decision) task).isWaitingTime()) {
                waitingTimeComposite.setTemporalUnit(wtg.getTemporalUnit());
                if (wtg.asExactValue()) {
                    waitingTimeComposite.setWaitingTime(wtg.getExactWaitingTime());
                    temporalElementComposite.setWaitingTime(waitingTimeComposite.getWaitingTime());
                }

                if (wtg.asInterval()) {
                    waitingTimeComposite.setWaitingTime(wtg.getMinWaitingTime(), wtg.getMaxWaitingTime());
                    temporalElementComposite.setWaitingTime(waitingTimeComposite.getWaitingTime());
                }

                temporalElementComposite.check();
                temporalElement = temporalElementComposite.getTemporalElement();
            }
        }
        if (task.getTaskType().toLowerCase().equals("action")) {

            if (((Action) task).isDuration()) {
                Duration dur = ((Action) task).getDuration();

                durationComposite.checkDuration(dur);
                temporalElementComposite.setDuration(durationComposite.getDuration());
            }

            if (((Action) task).isPeriodicity()) {
                Periodicity per = ((Action) task).getPeriodicity();

                periodicityComposite.checkPeriodicity(per);
                temporalElementComposite.setPeriodicity(periodicityComposite.getPeriodicity());
            }

            if (((Action) task).isWaitingTime()) {
                WaitingTime wtg = ((Action) task).getWaitingTime();

                waitingTimeComposite.setTemporalUnit(wtg.getTemporalUnit());
                if (wtg.asExactValue()) {
                    waitingTimeComposite.setWaitingTime(wtg.getExactWaitingTime());
                    temporalElementComposite.setWaitingTime(waitingTimeComposite.getWaitingTime());
                }

                if (wtg.asInterval()) {
                    waitingTimeComposite.setWaitingTime(wtg.getMinWaitingTime(), wtg.getMaxWaitingTime());
                    temporalElementComposite.setWaitingTime(waitingTimeComposite.getWaitingTime());
                }
            }

            if (((Action) task).isTemporalPattern()) {
                temporalElementComposite.check();
                temporalElement = temporalElementComposite.getTemporalElement();
            } else {
                durationComposite.setDuration(null, null, 1.0);
                durationComposite.setTemporalUnit("day");
                durationComposite.check();

                temporalElementComposite.setDuration(durationComposite.getDuration());
            }
        }

        return this;
    }

    @Override
    public Object getObject() {
        return temporalElement;
    }

    @Override
    public void passObject(Object object) {
        if (object instanceof ScheduleTask) {
            scheduleTask = (ScheduleTask) object;
        }
    }

}
