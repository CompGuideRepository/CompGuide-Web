/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Composite;

import com.compguide.web.Persistence.Entities.Duration;
import com.compguide.web.Persistence.SessionBeans.DurationFacade;
import com.compguide.web.Persistence.Entities.TemporalUnit;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author António
 */
//@Singleton
//@Startup
//@LocalBean
@Named
@SessionScoped
public class DurationComposite implements Serializable {

    private Duration duration;
    private TemporalUnit temporalUnit;
    @EJB
    private com.compguide.web.Persistence.SessionBeans.DurationFacade ejbDurationFacade;
    @Inject
    private TemporalUnitComposite temporalUnitComposite;

    public DurationComposite() {
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setDuration(Double minDurationValue, Double maxDurationValue,
            Double durationValue) {
        this.duration = new Duration(minDurationValue, maxDurationValue, durationValue);
    }

    public void setTemporalUnit(String value) {
        this.temporalUnit = new TemporalUnit(value);
        this.duration.setTemporalUnitID(temporalUnit);
    }

    private DurationFacade getDurationFacade() {
        return ejbDurationFacade;
    }

    public TemporalUnitComposite getTemporalUnitComposite() {
        return temporalUnitComposite;
    }

    public void setTemporalUnitComposite(TemporalUnitComposite temporalUnitComposite) {
        this.temporalUnitComposite = temporalUnitComposite;
    }

    public void check() {
        checkDuration(duration);
    }

    public DurationComposite checkDuration(com.compguide.web.Execution.Entities.Duration duration) {

        this.setDuration(
                duration.getMinDurationValue(),
                duration.getMaxDurationValue(),
                duration.getDurationValue()
        );

        this.setTemporalUnit(duration.getTemporalUnit());
        this.check();

        return this;
    }

    public Duration checkDuration(Duration durat) {
        Duration dur = null;

        if (durat != null) {
            if (durat.asExactValue()) {
                TemporalUnit temporalUnitID = temporalUnitComposite.checkTemporalUnit(
                        durat.getTemporalUnitID()
                );

                durat.setTemporalUnitID(temporalUnitID);

                dur = getDurationFacade().findByDurationValueAndTemporalUnit(
                        temporalUnitID, durat.getDurationValue()
                );

            }
            if (durat.isInterval()) {
                TemporalUnit temporalUnitID = temporalUnitComposite.checkTemporalUnit(
                        durat.getTemporalUnitID()
                );

                durat.setTemporalUnitID(temporalUnitID);

                dur = getDurationFacade().findByMinMaxValueAndTemporalUnit(temporalUnitID,
                        durat.getMinDurationValue(),
                        durat.getMaxDurationValue());

            }

            if (dur == null) {
                getDurationFacade().create(durat);
                dur = durat;
            }
        }

        duration = dur;
        return dur;
    }

}
