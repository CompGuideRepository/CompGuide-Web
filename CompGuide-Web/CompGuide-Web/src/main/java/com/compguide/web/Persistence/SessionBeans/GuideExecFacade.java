/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Persistence.SessionBeans;

import com.compguide.web.Persistence.Entities.User;
import com.compguide.web.Persistence.Entities.GuideExec;
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
public class GuideExecFacade extends AbstractFacade<GuideExec> {

    @PersistenceContext(unitName = "com.compguide_CompGuide-Web_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GuideExecFacade() {
        super(GuideExec.class);
    }

    public List<GuideExec> findByUserActiveGuidelines(User userID, Short completed) {
        List<GuideExec> guideExecs = new ArrayList<GuideExec>();
        try {
            Query query = em.createNamedQuery("GuideExec.findByUserCompletedGuidelines", GuideExec.class);
            query.setParameter("iduser", userID);
            query.setParameter("completed", completed);
            guideExecs = query.getResultList();
        } catch (javax.ejb.EJBException | javax.persistence.NoResultException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }

        return guideExecs;
    }
}
