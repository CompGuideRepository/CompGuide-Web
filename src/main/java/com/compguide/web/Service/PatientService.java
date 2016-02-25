/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Service;

import com.compguide.web.Persistence.SessionBeans.PatientFacade;
import com.compguide.web.Persistence.Entities.Patient;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author António
 */
@ManagedBean(name = "patientService")
@ApplicationScoped
public class PatientService {

    private List<Patient> patients;
    @EJB
    private com.compguide.web.Persistence.SessionBeans.PatientFacade ejbFacade;

    @PostConstruct
    public void init() {
        patients = new ArrayList<Patient>();
        patients = getFacade().findAll();
    }

    public List<Patient> getPatients() {
        return patients;
    }

    private PatientFacade getFacade() {
        if (ejbFacade == null) {
            ejbFacade = new PatientFacade();
        }
        return ejbFacade;
    }

}
