/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Persistence.SessionBeans;

import com.compguide.web.Persistence.Entities.CyclePartDefinition;
import com.compguide.web.Persistence.Entities.CyclePartPeriodicity;
import com.compguide.web.Persistence.Entities.Duration;
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
public class CyclePartDefinitionFacade extends AbstractFacade<CyclePartDefinition> {

    @PersistenceContext(unitName = "com.compguide_CompGuide-Web_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CyclePartDefinitionFacade() {
        super(CyclePartDefinition.class);
    }

    public CyclePartDefinition findByDurationID(Duration durationID) {
        CyclePartDefinition cyclePartDefinition = null;

        Query query = em.createNamedQuery("CyclePartDefinition.findByDurationID", CyclePartDefinition.class);
        query.setParameter("durationID", durationID);

        try {
            cyclePartDefinition = (CyclePartDefinition) query.getSingleResult();
        } catch (javax.ejb.EJBException | javax.persistence.NoResultException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return cyclePartDefinition;
    }

    public CyclePartDefinition findByCyclePartPeriodicityID(CyclePartPeriodicity cyclePartPeriodicityID) {
        CyclePartDefinition cyclePartDefinition = null;

        Query query = em.createNamedQuery("CyclePartDefinition.findByCyclePartPeriodicityID", CyclePartDefinition.class);
        query.setParameter("cyclePartPeriodicityID", cyclePartPeriodicityID);

        try {
            cyclePartDefinition = (CyclePartDefinition) query.getSingleResult();
        } catch (javax.ejb.EJBException | javax.persistence.NoResultException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return cyclePartDefinition;
    }
}
