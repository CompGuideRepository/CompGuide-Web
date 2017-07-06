/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Persistence.SessionBeans;

import com.compguide.web.Persistence.Entities.Event;
import com.compguide.web.Persistence.Entities.ScheduleTask;
import com.compguide.web.Persistence.Entities.Task;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author António
 */
@Stateless
public class EventFacade extends AbstractFacade<Event> {

    @PersistenceContext(unitName = "com.compguide_CompGuide-Web_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EventFacade() {
        super(Event.class);
    }

    public List<Event> findByTaskID(Task taskID) {
        List<Event> events = new ArrayList<Event>();

        try {
            Query query = em.createNamedQuery("Event.findByTaskID", Event.class);
            query.setParameter("taskID", taskID);
            events = query.getResultList();
        } catch (javax.ejb.EJBException | javax.persistence.NoResultException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }

        return events;
    }

    public List<Event> findByScheduleTaskID(ScheduleTask scheduleTaskID) {
        List<Event> events = new ArrayList<>();

        try {
            Query query = em.createNamedQuery("Event.findByScheduleTaskID", Event.class);
            query.setParameter("scheduleTaskID", scheduleTaskID);
            events = query.getResultList();
        } catch (javax.ejb.EJBException | javax.persistence.NoResultException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }

        return events;
    }
}
