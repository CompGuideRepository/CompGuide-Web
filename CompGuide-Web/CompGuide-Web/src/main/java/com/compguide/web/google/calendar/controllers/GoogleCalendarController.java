/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.google.calendar.controllers;

import com.compguide.web.Persistence.Entities.GuideExec;
import com.compguide.web.Persistence.Entities.ScheduleTask;
import com.compguide.web.Persistence.Entities.User;
import com.compguide.web.Persistence.SessionBeans.EventFacade;
import com.compguide.web.Persistence.SessionBeans.GuideExecFacade;
import com.compguide.web.Persistence.SessionBeans.ScheduleTaskFacade;
import com.compguide.web.Persistence.SessionBeans.UserFacade;
import static com.compguide.web.google.calendar.api.GoogleCalendar.getCalendarService;
import static com.compguide.web.google.calendar.api.GoogleCalendar.loadCredentialFromAccessToken;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author anton
 */
@Named("googleCalendarController")
@SessionScoped
public class GoogleCalendarController implements Serializable {

    @EJB
    private com.compguide.web.Persistence.SessionBeans.UserFacade ejbUserFacade;
    @EJB
    private com.compguide.web.Persistence.SessionBeans.ScheduleTaskFacade ejbScheduleTaskFacade;
    @EJB
    private com.compguide.web.Persistence.SessionBeans.EventFacade ejbEventFacade;
    @EJB
    private com.compguide.web.Persistence.SessionBeans.GuideExecFacade ejbGuideExecFacade;
    private com.google.api.services.calendar.Calendar service;
    private User user;
    private boolean hasCode = false;

    public GoogleCalendarController() {
    }

    public UserFacade getUserFacade() {
        return ejbUserFacade;
    }

    public ScheduleTaskFacade getScheduleTaskFacade() {
        return ejbScheduleTaskFacade;
    }

    public EventFacade getEventFacade() {
        return ejbEventFacade;
    }

    public GuideExecFacade getGuideExecFacade() {
        return ejbGuideExecFacade;
    }

    public void exportCalendar() {
        try {
            // Build a new authorized API client service.
            // Note: Do not confuse this class with the
            //   com.google.api.services.calendar.model.Calendar class.
            user = (User) FacesContext.getCurrentInstance().
                    getExternalContext().getSessionMap().get("userPersistence");

            service
                    = getCalendarService(user);

            String accessToken = loadCredentialFromAccessToken(user).getAccessToken();

            if (accessToken != user.getGoogleCalendarToken()) {
                user.setGoogleCalendarToken(accessToken);
                getUserFacade().edit(user);
            }

            if (accessToken != null && !accessToken.isEmpty()) {
                exportEventsToCalendar();
            }

        } catch (IOException ex) {
            Logger.getLogger(GoogleCalendarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void fetchCode() {
        String code = ((Map<String, String>) FacesContext.getCurrentInstance().getExternalContext()
                .getRequestParameterMap()).get("code");

        if (code != null && hasCode == false) {
            hasCode = true;

            try {
                FacesContext.getCurrentInstance().getExternalContext().
                        getSessionMap().put("code", code);

                service
                        = getCalendarService(user);

                user = (User) FacesContext.getCurrentInstance().
                        getExternalContext().getSessionMap().get("userPersistence");

                user.setGoogleCalendarToken(loadCredentialFromAccessToken(user).getAccessToken());
                getUserFacade().edit(user);

                exportEventsToCalendar();

            } catch (IOException ex) {
                Logger.getLogger(GoogleCalendarController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void exportEventsToCalendar() throws IOException {

        List<GuideExec> guideExecs = getGuideExecFacade().findByUserActiveGuidelines(user, (short) 0);

        for (GuideExec guideExec : guideExecs) {
            List<ScheduleTask> listScheduleTasks = getScheduleTaskFacade().findByGuideExecID(guideExec);

            for (ScheduleTask task : listScheduleTasks) {
                List<com.compguide.web.Persistence.Entities.Event> events = getEventFacade().findByScheduleTaskID(task);

                for (com.compguide.web.Persistence.Entities.Event event : events) {
                    if (event.getChecked() == false) {

                        Event ev = new Event()
                                .setSummary(event.getScheduleTaskID().getTaskIdentifier())
                                .setDescription(event.getScheduleTaskID().getTaskDescription());

                        DateTime startDateTime = new DateTime(event.getStartDate());
                        EventDateTime start = new EventDateTime()
                                .setDateTime(startDateTime)
                                .setTimeZone("Europe/Lisbon");
                        ev.setStart(start);

                        DateTime endDateTime = new DateTime(event.getPostPonedDate());
                        EventDateTime end = new EventDateTime()
                                .setDateTime(endDateTime)
                                .setTimeZone("Europe/Lisbon");
                        ev.setEnd(end);

//                        String[] recurrence = new String[]{"RRULE:FREQ=DAILY;COUNT=2"};
//                        ev.setRecurrence(Arrays.asList(recurrence));
//
//                        EventAttendee[] attendees = new EventAttendee[]{
//                            new EventAttendee().setEmail(user.getEmail()),};
//                        ev.setAttendees(Arrays.asList(attendees));
//
//                        EventReminder[] reminderOverrides = new EventReminder[]{
//                            new EventReminder().setMethod("email").setMinutes(24 * 60),
//                            new EventReminder().setMethod("popup").setMinutes(60),};
//                        Event.Reminders reminders = new Event.Reminders()
//                                .setUseDefault(false)
//                                .setOverrides(Arrays.asList(reminderOverrides));
//                        ev.setReminders(reminders);
                        String calendarId = "primary";
                        try {
                            ev = service.events().insert(calendarId, ev).execute();
                        } catch (IOException ex) {
                            Logger.getLogger(GoogleCalendarController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        System.out.printf("Event created: %s\n", ev.getHtmlLink());
                    }
                }
            }
        }
    }
}
