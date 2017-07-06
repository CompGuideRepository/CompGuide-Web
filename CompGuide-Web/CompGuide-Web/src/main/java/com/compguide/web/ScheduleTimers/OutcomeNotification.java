/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.ScheduleTimers;

import com.compguide.web.Persistence.Entities.Condition;
import com.compguide.web.Persistence.Entities.Outcome;
import com.compguide.web.Persistence.SessionBeans.ConditionFacade;
import com.compguide.web.Persistence.SessionBeans.OutcomeFacade;
import com.compguide.web.TemporalPattern.TemporalPattern;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Named;

/**
 *
 * @author António
 */
@Named("outcomeNotification")
@Singleton
public class OutcomeNotification {

    @EJB
    private com.compguide.web.Persistence.SessionBeans.OutcomeFacade ejbOutcomeFacade;

    @EJB
    private com.compguide.web.Persistence.SessionBeans.ConditionFacade ejbConditionFacade;

    public OutcomeNotification() {
    }

    public OutcomeFacade getOutcomeFacade() {
        return ejbOutcomeFacade;
    }

    public ConditionFacade getConditionFacade() {
        return ejbConditionFacade;
    }

    @Schedule(hour = "*", minute = "*/5", second = "*/30", persistent = false)
    public synchronized void run() {
        try {
            List<Outcome> outcomes = getOutcomeFacade().findAll();

            if (!outcomes.isEmpty()) {
                Date dateNow = new Date();

                for (Outcome outcome : outcomes) {
                    if (outcome.getScheduleTaskID().getCompleted() == true) {

                        List<Condition> conditions = getConditionFacade().findByConditionSetID(
                                outcome.getConditionSetID()
                        );

                        for (Condition condition : conditions) {
                            if (condition.getTemporalRestrictionID() != null) {
                                long differenceTime = (dateNow.getTime() - outcome.getScheduleTaskID().getCompletedDate().getTime());
                                long temporalRestrictionTime = TemporalPattern.temporalUnitToMilliseconds(
                                        condition.getTemporalRestrictionID().getTemporalUnitID().getValue(),
                                        condition.getTemporalRestrictionID().getTemporalRestrictionValue()
                                );
                                if (differenceTime >= temporalRestrictionTime
                                        && condition.getCanAsk() == false && condition.getAsked() == false) {
                                    condition.setCanAsk(Boolean.TRUE);
                                    getConditionFacade().edit(condition);

                                    outcome.setCanAsk(Boolean.TRUE);
                                    getOutcomeFacade().edit(outcome);
                                }
                            } else if (condition.getCanAsk() == false && condition.getAsked() == false) {
                                condition.setCanAsk(Boolean.TRUE);
                                getConditionFacade().edit(condition);

                                outcome.setCanAsk(Boolean.TRUE);
                                getOutcomeFacade().edit(outcome);
                            }
                        }

                    }
                }

            }
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }

    }

}
