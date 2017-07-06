/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Persistence.SessionBeans;

import com.compguide.web.Persistence.Entities.Condition;
import com.compguide.web.Persistence.Entities.ConditionSet;
import com.compguide.web.Persistence.Entities.TemporalRestriction;
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
public class ConditionFacade extends AbstractFacade<Condition> {

    @PersistenceContext(unitName = "com.compguide_CompGuide-Web_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConditionFacade() {
        super(Condition.class);
    }

    public List<Condition> findByConditionSetID(ConditionSet conditionSetID) {
        List<Condition> conditions = new ArrayList<>();
        try {
            Query query = em.createNamedQuery("Condition.findByConditionSetID", Condition.class);
            query.setParameter("conditionSetID", conditionSetID);
            conditions = query.getResultList();
        } catch (javax.ejb.EJBException | javax.persistence.NoResultException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return conditions;
    }

    public List<Condition> findByTemporalRestriction(TemporalRestriction temporalRestrictionID) {
        List<Condition> conditions = new ArrayList<>();
        try {
            Query query = em.createNamedQuery("Condition.findByTemporalRestrictionID", Condition.class);
            query.setParameter("temporalRestrictionID", temporalRestrictionID);
            conditions = query.getResultList();
        } catch (javax.ejb.EJBException | javax.persistence.NoResultException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return conditions;
    }

    public Condition findByValues(Condition condition) {
        try {
            Query query = em.createNamedQuery("Condition.findByValues", Condition.class);
            query.setParameter("numericalValue", condition.getNumericalValue());
            query.setParameter("qualitativeValue", condition.getQualitativeValue());
            query.setParameter("comparisonOperator", condition.getComparisonOperator());
            query.setParameter("parameterIdentifier", condition.getParameterIdentifier());
            query.setParameter("conditionParameter", condition.getConditionParameter());

            return (Condition) query.getSingleResult();
        } catch (javax.ejb.EJBException | javax.persistence.NoResultException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Condition findByValuesAndTemporalRestrictionID(Condition condition, TemporalRestriction temporalRestrictionID) {
        try {
            Query query = em.createNamedQuery("Condition.findByValues", Condition.class);
            query.setParameter("numericalValue", condition.getNumericalValue());
            query.setParameter("qualitativeValue", condition.getQualitativeValue());
            query.setParameter("comparisonOperator", condition.getComparisonOperator());
            query.setParameter("parameterIdentifier", condition.getParameterIdentifier());
            query.setParameter("conditionParameter", condition.getConditionParameter());
            query.setParameter("temporalRestrictionID", temporalRestrictionID);

            return (Condition) query.getSingleResult();
        } catch (javax.ejb.EJBException | javax.persistence.NoResultException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
