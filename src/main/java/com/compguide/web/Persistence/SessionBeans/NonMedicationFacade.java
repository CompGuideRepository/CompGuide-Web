/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Persistence.SessionBeans;

import com.compguide.web.Persistence.Entities.NonMedication;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author António
 */
@Stateless
public class NonMedicationFacade extends AbstractFacade<NonMedication> {
    @PersistenceContext(unitName = "com.compguide_CompGuide-Web_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NonMedicationFacade() {
        super(NonMedication.class);
    }
    
}
