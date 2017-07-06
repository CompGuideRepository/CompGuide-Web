/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Persistence.SessionBeans;

import com.compguide.web.Persistence.Entities.CyclePartDefinition;
import com.compguide.web.Persistence.Entities.Duration;
import com.compguide.web.Persistence.Entities.Periodicity;
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
public class PeriodicityFacade extends AbstractFacade<Periodicity> {

    @PersistenceContext(unitName = "com.compguide_CompGuide-Web_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PeriodicityFacade() {
        super(Periodicity.class);
    }

    public Periodicity findByPeriodicityValueTemporalUnitAndDuration(Duration durationID, TemporalUnit temporalUnitID, Double periodicityValue) {
        Periodicity periodicity = null;

        Query query = em.createNamedQuery("Periodicity.findByPeriodicityValueTemporalUnitAndDurationID", Periodicity.class);
        query.setParameter("durationID", durationID);
        query.setParameter("temporalUnitID", temporalUnitID);
        query.setParameter("periodicityValue", periodicityValue);

        try {
            periodicity = (Periodicity) query.getSingleResult();
        } catch (javax.ejb.EJBException | javax.persistence.NoResultException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return periodicity;
    }

    public Periodicity findByPeriodicityValueTemporalUnitAndRepetitonValue(Integer repetitionValue, TemporalUnit temporalUnitID, Double periodicityValue) {
        Periodicity periodicity = null;

        Query query = em.createNamedQuery("Periodicity.findByPeriodicityValueTemporalUnitAndRepetitionValue", Periodicity.class);
        query.setParameter("repetitionValue", repetitionValue);
        query.setParameter("temporalUnitID", temporalUnitID);
        query.setParameter("periodicityValue", periodicityValue);

        try {
            periodicity = (Periodicity) query.getSingleResult();
        } catch (javax.ejb.EJBException | javax.persistence.NoResultException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return periodicity;
    }

    public Periodicity findByPeriodicityValueTemporalUnitDurationAndCyclePartDefinition(CyclePartDefinition cyclePartDefinitionID, Duration durationID, TemporalUnit temporalUnitID, Double periodicityValue) {
        Periodicity periodicity = null;

        Query query = em.createNamedQuery("Periodicity.findByPeriodicityValueTemporalUnitDurationIDAndCyclePartDefinitionID", Periodicity.class);
        query.setParameter("durationID", durationID);
        query.setParameter("temporalUnitID", temporalUnitID);
        query.setParameter("periodicityValue", periodicityValue);
        query.setParameter("cyclePartDefinitionID", cyclePartDefinitionID);

        try {
            periodicity = (Periodicity) query.getSingleResult();
        } catch (javax.ejb.EJBException | javax.persistence.NoResultException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return periodicity;
    }

    public Periodicity findByPeriodicityValueTemporalUnitRepetitonValueAndCyclePartDefinition(CyclePartDefinition cyclePartDefinitionID, Integer repetitionValue, TemporalUnit temporalUnitID, Double periodicityValue) {
        Periodicity periodicity = null;

        Query query = em.createNamedQuery("Periodicity.findByPeriodicityValueTemporalUnitRepetitionValueAndCyclePartDefinition", Periodicity.class);
        query.setParameter("repetitionValue", repetitionValue);
        query.setParameter("temporalUnitID", temporalUnitID);
        query.setParameter("periodicityValue", periodicityValue);
        query.setParameter("cyclePartDefinitionID", cyclePartDefinitionID);

        try {
            periodicity = (Periodicity) query.getSingleResult();
        } catch (javax.ejb.EJBException | javax.persistence.NoResultException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return periodicity;
    }
}
