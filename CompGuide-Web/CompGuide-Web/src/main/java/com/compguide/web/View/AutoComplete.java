/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.View;

import com.compguide.web.Persistence.Entities.Guideline;
import com.compguide.web.Persistence.Entities.Patient;
import com.compguide.web.Persistence.Entities.User;
import com.compguide.web.Service.GuidelineService;
import com.compguide.web.Service.PatientService;
import com.compguide.web.Service.UserService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

/**
 *
 * @author António
 */
@ManagedBean
public class AutoComplete {

    private User user;
    private Patient patient;
    private Guideline guideline;

    @ManagedProperty("#{userService}")
    private UserService service;

    @ManagedProperty("#{patientService}")
    private PatientService servicePatient;

    @ManagedProperty("#{guidelineService}")
    private GuidelineService serviceGuideline;

    public List<User> complete(String query) {
        List<User> allUsers = service.getUsers();
        List<User> filteredUsers = new ArrayList<User>();

        System.out.println("======================AUTOCOMPLETE METHOD============================");
        System.out.println("Size of Users List:" + allUsers.size());

        for (int i = 0; i < allUsers.size(); i++) {
            User skin = allUsers.get(i);
            if (skin.getUsername().toLowerCase().startsWith(query)) {
                filteredUsers.add(skin);
            }
        }
        System.out.println("Size of Filtered List:" + filteredUsers.size());

        System.out.println("==================================================");

        return filteredUsers;
    }

    public List<Patient> completePatient(String query) {
        List<Patient> allPatients = servicePatient.getPatients();
        List<Patient> filteredPatients = new ArrayList<Patient>();

        System.out.println("======================AUTOCOMPLETE METHOD============================");
        System.out.println("Size of Users List:" + allPatients.size());

        for (int i = 0; i < allPatients.size(); i++) {
            Patient skin = allPatients.get(i);
            if (skin.getName().toLowerCase().startsWith(query)) {
                filteredPatients.add(skin);
            }
        }
        System.out.println("Size of Filtered List:" + filteredPatients.size());

        System.out.println("==================================================");

        return filteredPatients;
    }

    public List<Guideline> completeGuideline(String query) {
        List<Guideline> allGuidelines = serviceGuideline.getGuidelines();
        List<Guideline> filteredGuidelines = new ArrayList<Guideline>();

        System.out.println("======================AUTOCOMPLETE METHOD============================");
        System.out.println("Size of Users List:" + allGuidelines.size());

        for (int i = 0; i < allGuidelines.size(); i++) {
            Guideline skin = allGuidelines.get(i);
            if (skin.getIdentifier().toLowerCase().startsWith(query)) {
                filteredGuidelines.add(skin);
            }
        }
        System.out.println("Size of Filtered List:" + filteredGuidelines.size());

        System.out.println("==================================================");

        return filteredGuidelines;
    }

    public GuidelineService getServiceGuideline() {
        return serviceGuideline;
    }

    public void setServiceGuideline(GuidelineService serviceGuideline) {
        this.serviceGuideline = serviceGuideline;
    }

    public Guideline getGuideline() {
        if (guideline == null) {
            guideline = new Guideline();
        }
        return guideline;
    }

    public void setGuideline(Guideline guideline) {
        this.guideline = guideline;
    }

    public Patient getPatient() {
        if (patient == null) {
            patient = new Patient();
        }
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public User getUser() {
        System.out.println("======================GET USER============================");

        if (user == null) {
            user = new User();
            System.out.println(user.toString());
        }
        System.out.println(user.toString());
        System.out.println("==================================================");

        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PatientService getServicePatient() {
        return servicePatient;
    }

    public void setServicePatient(PatientService servicePatient) {
        this.servicePatient = servicePatient;
    }

    public UserService getService() {
        return service;
    }

    public void setService(UserService service) {
        this.service = service;
    }

}
