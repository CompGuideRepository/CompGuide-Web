/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Factories;

import com.compguide.web.Execution.Entities.ClinicalTask;
import com.compguide.web.Adapters.CyclePartDefinitionAdapter;
import com.compguide.web.Adapters.CyclePartPeriodicityAdapter;
import com.compguide.web.Adapters.DurationAdapter;
import com.compguide.web.Adapters.GuidelineInterface;
import com.compguide.web.Adapters.PeriodicityAdapter;
import com.compguide.web.Adapters.StopConditionSetAdapter;
import com.compguide.web.Adapters.TemporalElementAdapter;
import com.compguide.web.Adapters.TemporalUnitAdapter;
import com.compguide.web.Adapters.WaitingTimeAdapter;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;

/**
 *
 * @author António
 */
public class GuidelineAdapterFactory {

    private static GuidelineAdapterFactory instance;

    @Inject
    private TemporalElementAdapter temporalElementAdapter;

    private Map<String, GuidelineInterface> services
            = new HashMap<String, GuidelineInterface>();

    public GuidelineAdapterFactory() {
        services.put("Duration",
                new DurationAdapter()
        );
        services.put("Periodicity",
                new PeriodicityAdapter()
        );
        services.put("CyclePartDefinition",
                new CyclePartDefinitionAdapter()
        );
        services.put("CyclePartPeriodicity",
                new CyclePartPeriodicityAdapter()
        );
        services.put("TemporalElement",
                temporalElementAdapter
        );
        services.put("TemporalUnit",
                new TemporalUnitAdapter()
        );
        services.put("StopConditionSet",
                new StopConditionSetAdapter()
        );
        services.put("WaitingTime",
                new WaitingTimeAdapter()
        );
    }

    public synchronized static GuidelineAdapterFactory instance() {
        if (instance == null) {
            instance = new GuidelineAdapterFactory();
        }
        return instance;
    }

    public GuidelineInterface getAdapter(String type) {
        return services.get(type);
    }

    public Object getTemporalElementFromClinicalTaskAdapter(ClinicalTask task, String type) {
        return services.get(type).fetchTemporalPatternFromClinicaltask(task).getObject();
    }
}
