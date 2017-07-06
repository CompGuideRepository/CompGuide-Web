/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Persistence.SessionBeans;

import com.compguide.web.Persistence.Entities.Duration;
import com.compguide.web.Persistence.Entities.TemporalUnit;
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
public class DurationFacade extends AbstractFacade<Duration> {

    @PersistenceContext(unitName = "com.compguide_CompGuide-Web_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DurationFacade() {
        super(Duration.class);
    }

    public Duration findByDurationValueAndTemporalUnit(TemporalUnit temporalUnitID, Double durationValue) {
        Duration duration = null;

        Query query = em.createNamedQuery("Duration.findByDurationValueANDTemporalUnit", Duration.class);
        query.setParameter("temporalUnitID", temporalUnitID);
        query.setParameter("durationValue", durationValue);

        try {
            duration = (Duration) query.getSingleResult();
        } catch (javax.ejb.EJBException | javax.persistence.NoResultException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return duration;
    }

    public Duration findByMinMaxValueAndTemporalUnit(TemporalUnit temporalUnitID, Double minDurationValue, Double maxDurationValue) {
        Duration duration = null;

        Query query = em.createNamedQuery("Duration.findByIntervalAndTemporalUnit", Duration.class);
        query.setParameter("minDurationValue", minDurationValue);
        query.setParameter("maxDurationValue", maxDurationValue);
        query.setParameter("temporalUnitID", temporalUnitID);

        System.out.println(query);
        try {
            duration = (Duration) query.getSingleResult();
        } catch (javax.ejb.EJBException | javax.persistence.NoResultException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return duration;
    }
}
