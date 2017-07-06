/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Persistence.SessionBeans;

import com.compguide.web.Persistence.Entities.GuideExec;
import com.compguide.web.Persistence.Entities.ScheduleTask;
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
public class ScheduleTaskFacade extends AbstractFacade<ScheduleTask> {

    @PersistenceContext(unitName = "com.compguide_CompGuide-Web_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ScheduleTaskFacade() {
        super(ScheduleTask.class);
    }

    public List<ScheduleTask> findByGuideExecID(GuideExec guideExecID) {
        List<ScheduleTask> tasks = new ArrayList<>();
        try {
            Query query = em.createNamedQuery("ScheduleTask.findByGuideExecID", ScheduleTask.class);
            query.setParameter("guideExecID", guideExecID);
            tasks = query.getResultList();
        } catch (javax.ejb.EJBException | javax.persistence.NoResultException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }

        return tasks;
    }
}
