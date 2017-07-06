/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Persistence.SessionBeans;

import com.compguide.web.Persistence.Entities.Duration;
import com.compguide.web.Persistence.Entities.Periodicity;
import com.compguide.web.Persistence.Entities.ScheduleTask;
import com.compguide.web.Persistence.Entities.Task;
import com.compguide.web.Persistence.Entities.TemporalElement;
import com.compguide.web.Persistence.Entities.WaitingTime;
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
public class TemporalElementFacade extends AbstractFacade<TemporalElement> {

    @PersistenceContext(unitName = "com.compguide_CompGuide-Web_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TemporalElementFacade() {
        super(TemporalElement.class);
    }

    public TemporalElement findByScheduleTaskID(ScheduleTask scheduleTaskID) {
        TemporalElement temporalElement = null;

        Query query = em.createNamedQuery("TemporalElement.findByScheduleTaskID", TemporalElement.class);
        query.setParameter("scheduleTaskID", scheduleTaskID);
        try {
            temporalElement = (TemporalElement) query.getSingleResult();
        } catch (javax.ejb.EJBException | javax.persistence.NoResultException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return temporalElement;
    }

    public TemporalElement findByTaskID(Task taskID) {
        TemporalElement temporalElement = null;

        Query query = em.createNamedQuery("TemporalElement.findByTaskID", TemporalElement.class);
        query.setParameter("taskID", taskID);
        try {
            temporalElement = (TemporalElement) query.getSingleResult();
        } catch (javax.ejb.EJBException | javax.persistence.NoResultException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return temporalElement;
    }

    public TemporalElement findByWaitingTimeID(WaitingTime waitingTimeID) {
        TemporalElement temporalElement = null;

        Query query = em.createNamedQuery("TemporalElement.findByWaitingTimeID", TemporalElement.class);
        query.setParameter("waitingTimeID", waitingTimeID);
        try {
            temporalElement = (TemporalElement) query.getSingleResult();
        } catch (javax.ejb.EJBException | javax.persistence.NoResultException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return temporalElement;
    }

    public TemporalElement findByDurationID(Duration durationID) {
        TemporalElement temporalElement = null;

        Query query = em.createNamedQuery("TemporalElement.findByDurationID", TemporalElement.class);
        query.setParameter("durationID", durationID);
        try {
            temporalElement = (TemporalElement) query.getSingleResult();
        } catch (javax.ejb.EJBException | javax.persistence.NoResultException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return temporalElement;
    }

    public TemporalElement findByPeriodicityID(Periodicity periodicityID) {
        TemporalElement temporalElement = null;

        Query query = em.createNamedQuery("TemporalElement.findByPeriodicityID", TemporalElement.class);
        query.setParameter("periodicityID", periodicityID);
        try {
            temporalElement = (TemporalElement) query.getSingleResult();
        } catch (javax.ejb.EJBException | javax.persistence.NoResultException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return temporalElement;
    }

    public TemporalElement findByDurationIDAndWaitingTimeID(Duration durationID, WaitingTime waitingTimeID) {
        TemporalElement temporalElement = null;

        Query query = em.createNamedQuery("TemporalElement.findByDurationIDAndWaitingTimeID", TemporalElement.class);
        query.setParameter("durationID", durationID);
        query.setParameter("waitingTimeID", waitingTimeID);

        try {
            temporalElement = (TemporalElement) query.getSingleResult();
        } catch (javax.ejb.EJBException | javax.persistence.NoResultException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return temporalElement;
    }

    public TemporalElement findByPeriodicityIDAndWaitingTimeID(Periodicity periodicityID, WaitingTime waitingTimeID) {
        TemporalElement temporalElement = null;

        Query query = em.createNamedQuery("TemporalElement.findByPeriodicityIDAndWaitingTimeID", TemporalElement.class);
        query.setParameter("periodicityID", periodicityID);
        query.setParameter("waitingTimeID", waitingTimeID);

        try {
            temporalElement = (TemporalElement) query.getSingleResult();
        } catch (javax.ejb.EJBException | javax.persistence.NoResultException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return temporalElement;
    }
}
