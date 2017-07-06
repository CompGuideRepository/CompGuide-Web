/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Persistence.SessionBeans;

import com.compguide.web.Persistence.Entities.Periodicity;
import com.compguide.web.Persistence.Entities.StopConditionSet;
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
public class StopConditionSetFacade extends AbstractFacade<StopConditionSet> {

    @PersistenceContext(unitName = "com.compguide_CompGuide-Web_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StopConditionSetFacade() {
        super(StopConditionSet.class);
    }

    public StopConditionSet findByIdentifier(String identifier) {
        try {
            Query query = em.createNamedQuery("StopConditionSet.findByIdentifier", StopConditionSet.class);
            query.setParameter("identifier", identifier);

            return (StopConditionSet) query.getSingleResult();
        } catch (javax.ejb.EJBException | javax.persistence.NoResultException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public StopConditionSet findByPeriodicityID(Periodicity periodicityID) {
        try {
            Query query = em.createNamedQuery("StopConditionSet.findByPeriodicityID", StopConditionSet.class);
            query.setParameter("periodicityID", periodicityID);

            return (StopConditionSet) query.getSingleResult();
        } catch (javax.ejb.EJBException | javax.persistence.NoResultException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
