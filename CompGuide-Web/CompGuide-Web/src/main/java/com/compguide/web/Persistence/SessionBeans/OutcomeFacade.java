/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Persistence.SessionBeans;

import com.compguide.web.Persistence.Entities.Outcome;
import com.compguide.web.Persistence.Entities.ScheduleTask;
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
public class OutcomeFacade extends AbstractFacade<Outcome> {

    @PersistenceContext(unitName = "com.compguide_CompGuide-Web_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OutcomeFacade() {
        super(Outcome.class);
    }

    public Outcome findByScheduleTaskID(ScheduleTask scheduleTaskID) {
        try {
            Query query = em.createNamedQuery("Outcome.findByScheduleTaskID", Outcome.class);
            query.setParameter("scheduleTaskID", scheduleTaskID);

            return (Outcome) query.getSingleResult();
        } catch (javax.ejb.EJBException | javax.persistence.NoResultException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
