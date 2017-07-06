/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.ScheduleTimers;

import com.compguide.web.Persistence.Entities.Event;
import com.compguide.web.Persistence.Entities.Notification;
import com.compguide.web.Persistence.SessionBeans.EventFacade;
import com.compguide.web.Persistence.SessionBeans.NotificationFacade;
import com.compguide.web.Persistence.SessionBeans.ScheduleTaskFacade;
import com.compguide.web.TemporalPattern.TemporalPattern;
import java.net.ConnectException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
@Named("userNotification")
@Singleton
public class UserNotification {

    @EJB
    private com.compguide.web.Persistence.SessionBeans.EventFacade ejbEventFacade;
    @EJB
    private com.compguide.web.Persistence.SessionBeans.NotificationFacade ejbNotificationFacade;
    @EJB
    private com.compguide.web.Persistence.SessionBeans.ScheduleTaskFacade ejbScheduleTaskFacade;

    public UserNotification() {
    }

    public EventFacade getEventFacade() {
        return ejbEventFacade;
    }

    public NotificationFacade getNotificationFacade() {
        return ejbNotificationFacade;
    }

    public ScheduleTaskFacade getScheduleTaskFacade() {
        return ejbScheduleTaskFacade;
    }

    @Schedule(hour = "*", minute = "*/5", second = "*/30", persistent = false)
    public synchronized void run() {
        try {
            List<Event> events = getEventFacade().findAll();

            Date dateNow = new Date();

            for (Event event : events) {
                Date dateVoluntario = new Date();

                if (dateNow.after(event.getEndDate()) == true
                        && event.getChecked() == false) {

                    long diferenceMilliSeconds = dateNow.getTime() - event.getPostPonedDate().getTime();

                    if (getNotificationFacade().findByEventID(event).isEmpty()) {
                        event.setCanCheck(true);
                        getEventFacade().edit(event);

                        Notification notification = new Notification();
                        notification.setEventID(event);
                        notification.setViewed(false);

                        getNotificationFacade().create(notification);
                    } else if (diferenceMilliSeconds
                            >= TemporalPattern.temporalUnitToMilliseconds("hour", 6)) {

                        event.setPostPonedDate(dateNow);
                        // alterar a data de começo e finalizacao dos eventos referentes a Task
                        List<Event> eventList = getEventFacade().findByScheduleTaskID(event.getScheduleTaskID());
                        for (Event e : eventList) {
                            if (!Objects.equals(event, e)) {
                                long durationEventMilliSeconds = e.getPostPonedDate().getTime() - e.getStartDate().getTime();
                                long starDateMilliSeconds = e.getStartDate().getTime() + diferenceMilliSeconds;
                                long endDateMilliSeconds = starDateMilliSeconds + durationEventMilliSeconds;

                                e.setStartDate(new Date(starDateMilliSeconds));
                                e.setPostPonedDate(new Date(endDateMilliSeconds));
                                getEventFacade().edit(e);
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
