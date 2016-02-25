/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Adapters;

import com.compguide.web.Execution.Entities.Action;
import com.compguide.web.Execution.Entities.ClinicalTask;
import com.compguide.web.Execution.Entities.Duration;
import com.compguide.web.Execution.Entities.Plan;
import com.compguide.web.Composite.DurationComposite;
import com.compguide.web.Persistence.SessionBeans.TemporalUnitFacade;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author António
 */
public class DurationAdapter implements GuidelineInterface {

    private List<com.compguide.web.Persistence.Entities.Duration> durations = new ArrayList<com.compguide.web.Persistence.Entities.Duration>();
    private com.compguide.web.Persistence.SessionBeans.TemporalUnitFacade ejbTemporalUnitFacade;

    public DurationAdapter() {
    }

    public TemporalUnitFacade getTemporalUnitFacade() {
        if (ejbTemporalUnitFacade == null) {
            ejbTemporalUnitFacade = new TemporalUnitFacade();
        }
        return ejbTemporalUnitFacade;
    }

    @Override
    public GuidelineInterface fetchTemporalPatternFromClinicaltask(ClinicalTask task) {

        if (task.getTaskType().toLowerCase().equals("plan")) {
            Duration dur = ((Plan) task).getDuration();

            DurationComposite composite = new DurationComposite();
            composite.setDuration(dur.getMinDurationValue(),
                    dur.getMaxDurationValue(),
                    dur.getDurationValue());
            composite.setTemporalUnit(dur.getTemporalUnit());

            composite.check();

            durations.add(composite.getDuration());
        }

        if (task.getTaskType().toLowerCase().equals("action")) {
            Duration dur = ((Action) task).getDuration();

            DurationComposite composite = new DurationComposite();
            composite.setDuration(dur.getMinDurationValue(),
                    dur.getMaxDurationValue(),
                    dur.getDurationValue());
            composite.setTemporalUnit(dur.getTemporalUnit());

            composite.check();

            durations.add(composite.getDuration());
        }
        return this;
    }

    @Override
    public Object getObject() {
        return durations;
    }

    @Override
    public void passObject(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
