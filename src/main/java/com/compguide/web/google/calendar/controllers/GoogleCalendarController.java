/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.google.calendar.controllers;

import com.compguide.web.Persistence.Entities.User;
import com.compguide.web.Persistence.SessionBeans.UserFacade;
import static com.compguide.web.google.calendar.api.GoogleCalendar.getCalendarService;
import static com.compguide.web.google.calendar.api.GoogleCalendar.loadCredentialFromAccessToken;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import java.io.IOException;
import java.io.Serializable;
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
    private com.google.api.services.calendar.Calendar service;
    private User user;
    private boolean hasCode = false;

    public GoogleCalendarController() {
    }

    public UserFacade getUserFacade() {
        return ejbUserFacade;
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

            } catch (IOException ex) {
                Logger.getLogger(GoogleCalendarController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void exportEventsToCalendar() throws IOException {

        // List the next 10 events from the primary calendar.
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events().list("primary")
                .setMaxResults(10)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();
        if (items.size() == 0) {
            System.out.println("No upcoming events found.");
        } else {
            System.out.println("Upcoming events");
            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    start = event.getStart().getDate();
                }
                System.out.printf("%s (%s)\n", event.getSummary(), start);
            }
        }
        

    }
}
