/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Persistence.SessionBeans;

import com.compguide.web.Persistence.Entities.TemporalUnit;
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
public class WaitingTimeFacade extends AbstractFacade<WaitingTime> {

    @PersistenceContext(unitName = "com.compguide_CompGuide-Web_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public WaitingTimeFacade() {
        super(WaitingTime.class);
    }

    public WaitingTime findByMinMaxWaitingTimeAndTemporalUnitID(TemporalUnit temporalUnitID, double minWaitingTime, double maxWaitingTime) {
        WaitingTime waitingTime = null;

        Query query = em.createNamedQuery("WaitingTime.findByMinMaxWaitingTimeAndTemporalUnitID", WaitingTime.class);
        query.setParameter("minWaitingTime", minWaitingTime);
        query.setParameter("maxWaitingTime", maxWaitingTime);
        query.setParameter("temporalUnitID", temporalUnitID);

        try {
            waitingTime = (WaitingTime) query.getSingleResult();
        } catch (javax.ejb.EJBException | javax.persistence.NoResultException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return waitingTime;
    }

    public WaitingTime findByExactWaitingTimeAndTemporalUnitID(TemporalUnit temporalUnitID, double exactWaitingTime) {
        WaitingTime waitingTime = null;

        Query query = em.createNamedQuery("WaitingTime.findByExactWaitingTimeAndTemporalUnitID", WaitingTime.class);
        query.setParameter("exactWaitingTime", exactWaitingTime);
        query.setParameter("temporalUnitID", temporalUnitID);

        try {
            waitingTime = (WaitingTime) query.getSingleResult();
        } catch (javax.ejb.EJBException | javax.persistence.NoResultException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return waitingTime;
    }
}
