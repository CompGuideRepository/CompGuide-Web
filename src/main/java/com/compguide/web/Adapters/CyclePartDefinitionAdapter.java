/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Adapters;

import com.compguide.web.Execution.Entities.CyclePartDefinition;
import com.compguide.web.Execution.Entities.Action;
import com.compguide.web.Execution.Entities.ClinicalTask;
import com.compguide.web.Execution.Entities.Periodicity;
import com.compguide.web.Execution.Entities.Plan;
import com.compguide.web.Composite.CyclePartDefinitionComposite;
import com.compguide.web.Composite.CyclePartPeriodicityComposite;
import com.compguide.web.Composite.DurationComposite;
import com.compguide.web.Utils.Utils;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author António
 */
public class CyclePartDefinitionAdapter implements GuidelineInterface {

    private List<com.compguide.web.Persistence.Entities.CyclePartDefinition> cyclePartDefinition = new ArrayList<com.compguide.web.Persistence.Entities.CyclePartDefinition>();
    @EJB
    private CyclePartDefinitionComposite cyclePartDefinitionComposite;

    public CyclePartDefinitionAdapter() {
    }

    @Override
    public GuidelineInterface fetchTemporalPatternFromClinicaltask(ClinicalTask task) {

        if (task.getTaskType().toLowerCase().equals("plan")) {
            Periodicity per = ((Plan) task).getPeriodicity();
            if (per.haveCyclePart()) {
                CyclePartDefinition cyclePartDef = ((Plan) task).getPeriodicity().getCyclePartDefinition();

                CyclePartDefinitionComposite cyclePartDefinitionComposite = this.cyclePartDefinitionComposite.checkCyclePartDefinition(cyclePartDef);

                cyclePartDefinition.add(cyclePartDefinitionComposite.getCyclePartDefinition());
            }

        }

        if (task.getTaskType().toLowerCase().equals("action")) {
            Periodicity per = ((Action) task).getPeriodicity();
            if (per.haveCyclePart()) {
                CyclePartDefinition cyclePartDef = ((Plan) task).getPeriodicity().getCyclePartDefinition();

                CyclePartDefinitionComposite cyclePartDefinitionComposite = this.cyclePartDefinitionComposite.checkCyclePartDefinition(cyclePartDef);

                cyclePartDefinition.add(cyclePartDefinitionComposite.getCyclePartDefinition());
            }
        }
        return this;
    }

    @Override
    public Object getObject() {
        return cyclePartDefinition;
    }

    @Override
    public void passObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
