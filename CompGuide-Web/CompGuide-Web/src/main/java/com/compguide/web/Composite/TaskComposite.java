/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Composite;

import com.compguide.web.Execution.Entities.Action;
import com.compguide.web.Execution.Entities.ClinicalTask;
import com.compguide.web.Execution.Entities.Decision;
import com.compguide.web.Execution.Entities.Plan;
import com.compguide.web.Execution.Entities.Question;
import com.compguide.web.Persistence.SessionBeans.TaskFacade;
import com.compguide.web.Persistence.SessionBeans.TemporalElementFacade;
import com.compguide.web.Persistence.Entities.Task;
import com.compguide.web.Persistence.Entities.TemporalElement;
import javax.ejb.EJB;
import javax.inject.Singleton;

/**
 *
 * @author António
 */
@Singleton
public class TaskComposite {

    private TemporalElement temporalElement;
    private ClinicalTask clinicalTask;
    @EJB
    private com.compguide.web.Persistence.SessionBeans.TemporalElementFacade ejbTemporalElementFacade;
    @EJB
    private com.compguide.web.Persistence.SessionBeans.TaskFacade ejbTaskFacade;

    public TaskComposite() {
    }

    public TemporalElementFacade getTemporalElementFacade() {
        if (ejbTemporalElementFacade == null) {
            ejbTemporalElementFacade = new TemporalElementFacade();
        }
        return ejbTemporalElementFacade;
    }

    public TaskFacade getTaskFacade() {
        if (ejbTaskFacade == null) {
            ejbTaskFacade = new TaskFacade();
        }
        return ejbTaskFacade;
    }

    public void setTemporalElement(TemporalElement temporalElement) {
        this.temporalElement = temporalElement;
    }

    public void create() {
        if (temporalElement.isDuration()) {
            TemporalElement temporal = getTemporalElementFacade().findByDurationID(temporalElement.getDurationID());

            if (temporal == null) {
                getTemporalElementFacade().create(temporal);
                temporalElement = temporal;
            }

            Task task = new Task();

            if (clinicalTask.getTaskType().toLowerCase().equals("action")) {
                Action action = ((Action) clinicalTask);

            }
            if (clinicalTask.getTaskType().toLowerCase().equals("decison")) {
                Decision deciosion = ((Decision) clinicalTask);

            }
            if (clinicalTask.getTaskType().toLowerCase().equals("plan")) {
                Plan plan = ((Plan) clinicalTask);
            }
            if (clinicalTask.getTaskType().toLowerCase().equals("question")) {
                Question question = ((Question) clinicalTask);
            }

            getTaskFacade().create(task);

        }

        if (temporalElement.isPeriodicity()) {

        }
    }
}
