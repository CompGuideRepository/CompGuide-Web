/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Adapters;

import com.compguide.web.Execution.Entities.Periodicity;
import com.compguide.web.Execution.Entities.CyclePartDefinition;
import com.compguide.web.Execution.Entities.ClinicalTask;
import com.compguide.web.Execution.Entities.Plan;
import com.compguide.web.Execution.Entities.CyclePartPeriodicity;
import com.compguide.web.Execution.Entities.Action;
import com.compguide.web.Composite.CyclePartPeriodicityComposite;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author António
 */
public class CyclePartPeriodicityAdapter implements GuidelineInterface {

    private List<com.compguide.web.Persistence.Entities.CyclePartPeriodicity> cyclePartPeriodicity = new ArrayList<com.compguide.web.Persistence.Entities.CyclePartPeriodicity>();
    private ClinicalTask clinicalTask;
    @EJB
    private CyclePartPeriodicityComposite cyclePartPeriodicityComposite;

    public CyclePartPeriodicityAdapter() {
    }

    @Override
    public GuidelineInterface fetchTemporalPatternFromClinicaltask(ClinicalTask task) {

        if (task.getTaskType().toLowerCase().equals("plan")) {
            Periodicity per = ((Plan) task).getPeriodicity();
            if (per.haveCyclePart()) {
                CyclePartDefinition cyclePartDef = ((Plan) task).getPeriodicity().getCyclePartDefinition();

                if (cyclePartDef.asPeriodicity()) {
                    CyclePartPeriodicity cpp = cyclePartDef.getCyclePartPeriodicity();

                    CyclePartPeriodicityComposite cyclePartDefinitionComposite = this.cyclePartPeriodicityComposite.checkCyclePartPeriodicity(cpp);

                    cyclePartPeriodicity.add(cyclePartDefinitionComposite.getCyclePartPeriodicity());
                }
            }

        }

        if (task.getTaskType().toLowerCase().equals("action")) {
            Periodicity per = ((Action) task).getPeriodicity();
            if (per.haveCyclePart()) {
                CyclePartDefinition cyclePartDef = ((Action) task).getPeriodicity().getCyclePartDefinition();

                if (cyclePartDef.asPeriodicity()) {
                    CyclePartPeriodicity cpp = cyclePartDef.getCyclePartPeriodicity();

                    CyclePartPeriodicityComposite cyclePartDefinitionComposite = this.cyclePartPeriodicityComposite.checkCyclePartPeriodicity(cpp);

                    cyclePartPeriodicity.add(cyclePartDefinitionComposite.getCyclePartPeriodicity());

                }

            }
        }
        return this;
    }

    @Override
    public Object getObject() {
        return cyclePartPeriodicity;
    }

    @Override
    public void passObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
