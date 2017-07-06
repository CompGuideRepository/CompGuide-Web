/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Persistence.SessionBeans;

import com.compguide.web.Persistence.Entities.CyclePartPeriodicity;
import com.compguide.web.Persistence.Entities.Duration;
import com.compguide.web.Persistence.Entities.Task;
import com.compguide.web.Persistence.Entities.TemporalUnit;
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
public class CyclePartPeriodicityFacade extends AbstractFacade<CyclePartPeriodicity> {

    @PersistenceContext(unitName = "com.compguide_CompGuide-Web_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CyclePartPeriodicityFacade() {
        super(CyclePartPeriodicity.class);
    }

    public List<CyclePartPeriodicity> findByDurationID(Duration durationID) {
        List<CyclePartPeriodicity> cyclePartPeriodicity = new ArrayList<CyclePartPeriodicity>();

        Query query = em.createNamedQuery("CyclePartPeriodicity.findByDurationID", CyclePartPeriodicity.class);
        query.setParameter("durationID", durationID);
        try {
            cyclePartPeriodicity = query.getResultList();
        } catch (javax.ejb.EJBException | javax.persistence.NoResultException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return cyclePartPeriodicity;
    }

    public List<CyclePartPeriodicity> findByRepetitionValue(Integer repetitionValue) {
        List<CyclePartPeriodicity> cyclePartPeriodicity = null;

        Query query = em.createNamedQuery("CyclePartPeriodicity.findByRepetitionValue", CyclePartPeriodicity.class);
        query.setParameter("repetitionValue", repetitionValue);
        try {
            cyclePartPeriodicity = query.getResultList();
        } catch (javax.ejb.EJBException | javax.persistence.NoResultException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }

        return cyclePartPeriodicity;
    }

    public CyclePartPeriodicity findByTaskID(Task taskID) {
        CyclePartPeriodicity cyclePartPeriodicity = null;

        Query query = em.createNamedQuery("CyclePartPeriodicity.findByTaskID", CyclePartPeriodicity.class);
        query.setParameter("taskID", taskID);
        try {
            cyclePartPeriodicity = (CyclePartPeriodicity) query.getSingleResult();
        } catch (javax.ejb.EJBException | javax.persistence.NoResultException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return cyclePartPeriodicity;
    }

    public CyclePartPeriodicity findByPeriodicityValueTemporalUnitAndDurationID(Duration durationID, TemporalUnit temporalUnitID, Double periodicityValue) {
        CyclePartPeriodicity cyclePartPeriodicity = null;

        Query query = em.createNamedQuery("CyclePartPeriodicity.findByPeriodicityValueTemporalUnitAndDurationID", CyclePartPeriodicity.class);
        query.setParameter("durationID", durationID);
        query.setParameter("temporalUnitID", temporalUnitID);
        query.setParameter("periodicityValue", periodicityValue);

        try {
            cyclePartPeriodicity = (CyclePartPeriodicity) query.getSingleResult();
        } catch (javax.ejb.EJBException | javax.persistence.NoResultException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return cyclePartPeriodicity;
    }

    public CyclePartPeriodicity findByPeriodicityValueTemporalUnitAndRepetitonValue(Integer repetitionValue, TemporalUnit temporalUnitID, Double periodicityValue) {
        CyclePartPeriodicity cyclePartPeriodicity = null;

        Query query = em.createNamedQuery("CyclePartPeriodicity.findByPeriodicityValueTemporalUnitAndRepetitionValue", CyclePartPeriodicity.class);
        query.setParameter("repetitionValue", repetitionValue);
        query.setParameter("temporalUnitID", temporalUnitID);
        query.setParameter("periodicityValue", periodicityValue);

        try {
            cyclePartPeriodicity = (CyclePartPeriodicity) query.getSingleResult();
        } catch (javax.ejb.EJBException | javax.persistence.NoResultException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return cyclePartPeriodicity;
    }
}
