/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Composite;

import com.compguide.web.Persistence.Entities.CyclePartDefinition;
import com.compguide.web.Persistence.Entities.Periodicity;
import com.compguide.web.Persistence.Entities.WaitingTime;
import com.compguide.web.Persistence.Entities.Duration;
import com.compguide.web.Persistence.SessionBeans.TemporalElementFacade;
import com.compguide.web.Persistence.Entities.TemporalElement;
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
public class TemporalElementComposite implements Serializable {

    private TemporalElement temporalElement;
    private WaitingTime waitingTime;
    private Duration duration;
    private Periodicity periodicity;
    @EJB
    private com.compguide.web.Persistence.SessionBeans.TemporalElementFacade ejbTemporalElementFacade;

    @Inject
    private DurationComposite durationComposite;
    @Inject
    private PeriodicityComposite periodicityComposite;
    @Inject
    private WaitingTimeComposite waitingTimeComposite;

    public TemporalElementComposite() {
        temporalElement = new TemporalElement();
    }

    public TemporalElementComposite(TemporalElement temporalElement) {
        this.temporalElement = temporalElement;
    }

    public TemporalElement getTemporalElement() {
        return temporalElement;
    }

    public void setPeriodicity(Integer repetitionValue, double periodicityValue, CyclePartDefinition cyclePartDefinitionID, TemporalUnit temporalUnitID, Duration durationID) {
        this.periodicity = new Periodicity(repetitionValue, periodicityValue, cyclePartDefinitionID, temporalUnitID, durationID);
    }

    public void setDuration(Double minDurationValue, Double maxDurationValue,
            Double durationValue, TemporalUnit temporalDurationUnit) {
        this.duration = new Duration(minDurationValue, maxDurationValue, durationValue, temporalDurationUnit);
    }

    public void setTemporalElement(TemporalElement temporalElement) {
        this.temporalElement = temporalElement;
    }

    public void setWaitingTime(WaitingTime waitingTime) {
        this.waitingTime = waitingTime;
        this.temporalElement.setWaitingTimeID(waitingTime);

    }

    public void setPeriodicity(Periodicity periodicity) {
        this.periodicity = periodicity;
        this.temporalElement.setPeriodicityID(periodicity);
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
        this.temporalElement.setDurationID(duration);
    }

    private TemporalElementFacade getTemporalElementFacade() {
        return ejbTemporalElementFacade;
    }

    public TemporalElement check() {
        if (temporalElement.isDuration()) {
            if (temporalElement.getDurationID().getDurationID() == null) {
                checkTemporalElement(temporalElement);
            }
        }

        if (temporalElement.isWaitingTime()) {
            if (temporalElement.getWaitingTimeID().getWaitingTimeID() == null) {
                checkTemporalElement(temporalElement);
            }
        }

        if (temporalElement.isPeriodicity()) {
            if (temporalElement.getPeriodicityID().getPeriodicityID() == null) {
                checkTemporalElement(temporalElement);
            }
        }

        return temporalElement;
    }

    public TemporalElement checkTemporalElement(TemporalElement temporalElement) {
        TemporalElement element = null;

        if (temporalElement.isDuration()) {
            Duration dur = durationComposite.checkDuration(temporalElement.getDurationID());

            if (temporalElement.isWaitingTime()) {
                WaitingTime waitingTime = waitingTimeComposite.checkWaitingTime(
                        temporalElement.getWaitingTimeID()
                );

                temporalElement.setWaitingTimeID(waitingTime);
                temporalElement.setDurationID(dur);

                element = getTemporalElementFacade().findByDurationIDAndWaitingTimeID(
                        dur,
                        waitingTime
                );
            } else {
                temporalElement.setDurationID(dur);

                element = getTemporalElementFacade().findByDurationID(dur);
            }
        }

        if (temporalElement.isPeriodicity()) {
            Periodicity per = periodicityComposite.checkPeriodicity(temporalElement.getPeriodicityID());

            if (temporalElement.isWaitingTime()) {
                WaitingTime waitingTime = waitingTimeComposite.checkWaitingTime(
                        temporalElement.getWaitingTimeID()
                );

                temporalElement.setWaitingTimeID(waitingTime);
                temporalElement.setPeriodicityID(per);

                element = getTemporalElementFacade().findByPeriodicityIDAndWaitingTimeID(
                        per,
                        waitingTime
                );
            } else {
                temporalElement.setPeriodicityID(per);

                element = getTemporalElementFacade().findByPeriodicityID(per);
            }
        }

        if (temporalElement.isWaitingTime()
                && !temporalElement.isDuration()
                && !temporalElement.isPeriodicity()) {
            WaitingTime waitingTime = waitingTimeComposite.checkWaitingTime(
                    temporalElement.getWaitingTimeID()
            );

            temporalElement.setWaitingTimeID(waitingTime);

            element = getTemporalElementFacade().findByWaitingTimeID(waitingTime);
        }

        if (element == null) {
            getTemporalElementFacade().create(temporalElement);
            element = temporalElement;
        }

        this.temporalElement = element;

        return element;
    }

}
