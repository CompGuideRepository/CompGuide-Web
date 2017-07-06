/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Composite;

import com.compguide.web.Persistence.Entities.*;
import com.compguide.web.Persistence.SessionBeans.CyclePartDefinitionFacade;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

/**
 *
 * @author António
 */
//@Singleton
//@Startup
//@LocalBean
@ManagedBean
@SessionScoped
public class CyclePartDefinitionComposite implements Serializable {

    private CyclePartDefinition cyclePartDefinition;
    private Duration duration;
    private CyclePartPeriodicity cyclePartPeriodicity;
    @EJB
    private com.compguide.web.Persistence.SessionBeans.CyclePartDefinitionFacade ejbCyclePartDefinitionFacade;
    @Inject
    private DurationComposite durationComposite;
    @Inject
    private CyclePartPeriodicityComposite cyclePartPeriodicityComposite;

    public CyclePartDefinitionComposite() {
        cyclePartDefinition = new CyclePartDefinition();
    }

    public CyclePartDefinition getCyclePartDefinition() {
        return cyclePartDefinition;
    }

    public void setCyclePartDefinition(CyclePartDefinition cyclePartDefinition) {
        this.cyclePartDefinition = cyclePartDefinition;
    }

    private CyclePartDefinitionFacade getCyclePartDefinitionFacade() {
        return ejbCyclePartDefinitionFacade;
    }

    public void setDuration(Double minDurationValue, Double maxDurationValue,
            Double durationValue, TemporalUnit temporalDurationUnit) {
        this.duration = new Duration(minDurationValue, maxDurationValue, durationValue, temporalDurationUnit);
        this.cyclePartDefinition.setDurationID(duration);

    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
        this.cyclePartDefinition.setDurationID(duration);
    }

    public CyclePartPeriodicity getCyclePartPeriodicity() {
        return cyclePartPeriodicity;
    }

    public void setCyclePartPeriodicity(CyclePartPeriodicity cyclePartPeriodicity) {
        this.cyclePartPeriodicity = cyclePartPeriodicity;
        this.cyclePartDefinition.setCyclePartPeriodicityID(cyclePartPeriodicity);
    }

    public void setCyclePartPeriodicity(Integer repetitionValue, Double periodicityValue,
            Double minCycleDurationValue, Double maxCycleDurationValue,
            Double durationCycleValue, TemporalUnit temporalCycleDurationUnit,
            TemporalUnit temporalCyclePeriodicityUnit) {
        this.cyclePartPeriodicity = new CyclePartPeriodicity(repetitionValue, periodicityValue,
                new Duration(minCycleDurationValue, maxCycleDurationValue, durationCycleValue, temporalCycleDurationUnit), temporalCyclePeriodicityUnit);
        this.cyclePartDefinition.setCyclePartPeriodicityID(cyclePartPeriodicity);
    }

    public void check() {
        CyclePartDefinition partDefinition = checkCyclePartDefinition(cyclePartDefinition);
        cyclePartDefinition = partDefinition;
    }

    public CyclePartDefinition checkCyclePartDefinition(CyclePartDefinition cyclePartDefinit) {
        CyclePartDefinition partDefinition = null;

        if (cyclePartDefinit.asDuration()) {
            Duration dur = durationComposite.checkDuration(cyclePartDefinit.getDurationID());

            cyclePartDefinit.setDurationID(dur);
            partDefinition = getCyclePartDefinitionFacade().findByDurationID(dur);
        }

        if (cyclePartDefinit.asPeriodicity()) {
            CyclePartPeriodicity partPeriodicity = cyclePartPeriodicityComposite.checkCyclePartPeriodicity(
                    cyclePartDefinit.getCyclePartPeriodicityID());

            cyclePartDefinit.setCyclePartPeriodicityID(partPeriodicity);
            partDefinition = getCyclePartDefinitionFacade().findByCyclePartPeriodicityID(
                    partPeriodicity);
        }

        if (partDefinition == null) {
            getCyclePartDefinitionFacade().create(cyclePartDefinit);
            partDefinition = cyclePartDefinit;
        }

        return partDefinition;
    }

    public CyclePartDefinitionComposite checkCyclePartDefinition(com.compguide.web.Execution.Entities.CyclePartDefinition cyclePartDefinition) {
        CyclePartDefinitionComposite cyclePartDefinitionComposite = new CyclePartDefinitionComposite();

        if (cyclePartDefinition.asDuration()) {
            DurationComposite durationComposite = this.durationComposite.checkDuration(
                    cyclePartDefinition.getDuration()
            );

            cyclePartDefinitionComposite.setDuration(
                    durationComposite.getDuration()
            );
        }
        if (cyclePartDefinition.asPeriodicity()) {
            CyclePartPeriodicityComposite cyclePartPeriodicityComposite
                    = this.cyclePartPeriodicityComposite.checkCyclePartPeriodicity(
                            cyclePartDefinition.getCyclePartPeriodicity()
                    );

            cyclePartDefinitionComposite.setCyclePartPeriodicity(
                    cyclePartPeriodicityComposite.getCyclePartPeriodicity()
            );
        }

        cyclePartDefinitionComposite.check();

        return cyclePartDefinitionComposite;
    }

}
