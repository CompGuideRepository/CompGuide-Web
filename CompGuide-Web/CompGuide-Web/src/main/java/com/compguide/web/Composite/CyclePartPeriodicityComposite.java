/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Composite;

import com.compguide.web.Persistence.Entities.ConditionSet;
import com.compguide.web.Persistence.Entities.CyclePartPeriodicity;
import com.compguide.web.Persistence.Entities.Duration;
import com.compguide.web.Persistence.SessionBeans.CyclePartPeriodicityFacade;
import com.compguide.web.Persistence.Entities.TemporalUnit;
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
public class CyclePartPeriodicityComposite implements Serializable {

    private CyclePartPeriodicity cyclePartPeriodicity;
    private Duration duration;
    private TemporalUnit temporalUnit;
    private ConditionSet conditionSet;
    @EJB
    private com.compguide.web.Persistence.SessionBeans.CyclePartPeriodicityFacade ejbCyclePartPeriodicityFacade;
    @Inject
    private TemporalUnitComposite temporalUnitComposite;
    @Inject
    private DurationComposite durationComposite;

    public CyclePartPeriodicityComposite() {
    }

    private CyclePartPeriodicityFacade getCyclePartPeriodicityFacade() {
        return ejbCyclePartPeriodicityFacade;
    }

    public CyclePartPeriodicity getCyclePartPeriodicity() {
        return cyclePartPeriodicity;
    }

    public void setDuration(Double minDurationValue, Double maxDurationValue,
            Double durationValue, TemporalUnit temporalDurationUnit) {
        this.duration = new Duration(minDurationValue, maxDurationValue, durationValue, temporalDurationUnit);
        this.cyclePartPeriodicity.setDurationID(duration);
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
        this.cyclePartPeriodicity.setDurationID(duration);
    }

    public void setTemporalUnit(String value) {
        this.temporalUnit = new TemporalUnit(value);
    }

    public void setCyclePartPeriodicity(double periodicityValue) {
        this.cyclePartPeriodicity = new CyclePartPeriodicity(periodicityValue);
    }

    public void setCyclePartPeriodicity(Integer repetitionValue, double periodicityValue) {
        this.cyclePartPeriodicity = new CyclePartPeriodicity(periodicityValue, repetitionValue);
    }

    public void check() {
        CyclePartPeriodicity partPeriodicity = checkCyclePartPeriodicity(cyclePartPeriodicity);
        cyclePartPeriodicity = partPeriodicity;
    }

    public CyclePartPeriodicity checkCyclePartPeriodicity(CyclePartPeriodicity cyclePartPeriodicity) {
        CyclePartPeriodicity cpp = null;

        if (cyclePartPeriodicity.asDuration()) {
            TemporalUnit temporalUnitID = temporalUnitComposite.checkTemporalUnit(
                    cyclePartPeriodicity.getTemporalUnitID()
            );

            Duration dur = durationComposite.checkDuration(cyclePartPeriodicity.getDurationID());

            cyclePartPeriodicity.setDurationID(dur);
            cyclePartPeriodicity.setTemporalUnitID(temporalUnitID);

            cpp = getCyclePartPeriodicityFacade().findByPeriodicityValueTemporalUnitAndDurationID(
                    dur,
                    temporalUnitID,
                    cyclePartPeriodicity.getPeriodicityValue()
            );
        }

        if (cyclePartPeriodicity.asRepetition()) {
            TemporalUnit temporalUnitID = temporalUnitComposite.checkTemporalUnit(
                    cyclePartPeriodicity.getTemporalUnitID()
            );

            cyclePartPeriodicity.setTemporalUnitID(temporalUnitID);

            cpp = getCyclePartPeriodicityFacade().findByPeriodicityValueTemporalUnitAndRepetitonValue(
                    cyclePartPeriodicity.getRepetitionValue(),
                    temporalUnitID,
                    cyclePartPeriodicity.getPeriodicityValue()
            );

        }

        if (cpp == null) {
            getCyclePartPeriodicityFacade().create(cyclePartPeriodicity);
            cpp = cyclePartPeriodicity;
        }

        return cpp;
    }

    public CyclePartPeriodicityComposite checkCyclePartPeriodicity(com.compguide.web.Execution.Entities.CyclePartPeriodicity cyclePartPeriodicity) {
        CyclePartPeriodicityComposite cyclePartPeriodicityComposite = new CyclePartPeriodicityComposite();

        cyclePartPeriodicity.setTemporalUnit(cyclePartPeriodicity.getTemporalUnit());

        if (cyclePartPeriodicity.asDuration()) {
            DurationComposite durationComposite = this.durationComposite.checkDuration(
                    cyclePartPeriodicity.getDuration()
            );

            cyclePartPeriodicityComposite.setCyclePartPeriodicity(
                    cyclePartPeriodicity.getPeriodicityValue()
            );
            cyclePartPeriodicityComposite.setDuration(
                    durationComposite.getDuration()
            );
        }

        if (cyclePartPeriodicity.asRepetition()) {
            cyclePartPeriodicityComposite.setCyclePartPeriodicity(
                    cyclePartPeriodicity.getRepetitionValue(),
                    cyclePartPeriodicity.getPeriodicityValue()
            );
        }

        cyclePartPeriodicityComposite.check();

        return cyclePartPeriodicityComposite;
    }

}
