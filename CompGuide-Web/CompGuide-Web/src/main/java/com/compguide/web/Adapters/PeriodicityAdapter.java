/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Adapters;

import com.compguide.web.Execution.Entities.Periodicity;
import com.compguide.web.Execution.Entities.ClinicalTask;
import com.compguide.web.Execution.Entities.Plan;
import com.compguide.web.Execution.Entities.Action;
import com.compguide.web.Composite.PeriodicityComposite;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author António
 */
public class PeriodicityAdapter implements GuidelineInterface {

    private List<com.compguide.web.Persistence.Entities.Periodicity> periodicities;
    @EJB
    private PeriodicityComposite periodicityComposite;

    public PeriodicityAdapter() {
    }

    @Override
    public GuidelineInterface fetchTemporalPatternFromClinicaltask(ClinicalTask task) {

        if (task.getTaskType().toLowerCase().equals("plan")) {
            Periodicity per = ((Plan) task).getPeriodicity();

            PeriodicityComposite periodicityComposite = this.periodicityComposite.checkPeriodicity(per);

            periodicities.add(periodicityComposite.getPeriodicity());

        }

        if (task.getTaskType().toLowerCase().equals("action")) {
            Periodicity per = ((Action) task).getPeriodicity();

            PeriodicityComposite periodicityComposite = this.periodicityComposite.checkPeriodicity(per);

            periodicities.add(periodicityComposite.getPeriodicity());

        }

        return this;
    }

    @Override
    public Object getObject() {
        return periodicities;
    }

    @Override
    public void passObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
