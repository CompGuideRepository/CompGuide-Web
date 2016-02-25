/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Composite;

import com.compguide.web.Persistence.Entities.CyclePartPeriodicity;
import com.compguide.web.Persistence.SessionBeans.WaitingTimeFacade;
import com.compguide.web.Persistence.Entities.TemporalUnit;
import com.compguide.web.Persistence.Entities.WaitingTime;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 *
 * @author António
 */
@Singleton
@Startup
@LocalBean
public class WaitingTimeComposite implements Serializable {

    private WaitingTime waitingTime;
    private TemporalUnit temporalUnit;
    @EJB
    private com.compguide.web.Persistence.SessionBeans.WaitingTimeFacade ejbWaitingTimeFacade;
    @Inject
    private TemporalUnitComposite temporalUnitComposite;

    public WaitingTimeComposite() {
        temporalUnitComposite = new TemporalUnitComposite();
    }

    @PostConstruct
    public void onStartup() {
        temporalUnitComposite = new TemporalUnitComposite();
    }

    public WaitingTimeFacade getWaitingTimeFacade() {
        return ejbWaitingTimeFacade;
    }

    public void setWaitingTime(Double minWaitingTime, Double maxWaitingTime) {
        this.waitingTime = new WaitingTime(minWaitingTime, maxWaitingTime);
    }

    public void setWaitingTime(Double exactWaitingTime) {
        this.waitingTime = new WaitingTime(exactWaitingTime);
    }

    public void setTemporalUnit(String value) {
        this.temporalUnit = new TemporalUnit(value);
    }

    public void check() {
        WaitingTime time = checkWaitingTime(waitingTime);
        waitingTime = time;
    }

    public WaitingTime getWaitingTime() {
        return waitingTime;
    }

    public WaitingTime checkWaitingTime(WaitingTime waitingTime) {
        WaitingTime time = null;

        if (waitingTime.asExactValue()) {
            TemporalUnit temporalUnit = temporalUnitComposite.checkTemporalUnit(waitingTime.getTemporalUnitID());

            waitingTime.setTemporalUnitID(temporalUnit);

            time = getWaitingTimeFacade().findByExactWaitingTimeAndTemporalUnitID(
                    temporalUnit,
                    waitingTime.getExactWaitingTime()
            );
        }
        if (waitingTime.asInterval()) {
            TemporalUnit temporalUnit = temporalUnitComposite.checkTemporalUnit(waitingTime.getTemporalUnitID());

            waitingTime.setTemporalUnitID(temporalUnit);

            time = getWaitingTimeFacade().findByMinMaxWaitingTimeAndTemporalUnitID(
                    temporalUnit,
                    waitingTime.getMinWaitingTime(),
                    waitingTime.getMaxWaitingTime()
            );
        }

        if (time == null) {
            getWaitingTimeFacade().create(waitingTime);
            time = waitingTime;
        }
        return time;
    }
}
