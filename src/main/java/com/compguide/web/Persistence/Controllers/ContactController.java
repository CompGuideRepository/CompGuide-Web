/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Persistence.Controllers;

import com.compguide.web.Email.CompGuideEmail;
import com.compguide.web.Persistence.Entities.Message;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;

/**
 *
 * @author António
 */
@Named("contactController")
@RequestScoped
public class ContactController implements Serializable {

    private Message selected;
    private CompGuideEmail compGuideEmail;

    public ContactController() {
    }

    public Message getSelected() {
        if (selected == null) {
            selected = new Message();
        }
        return selected;
    }

    public void validateName(AjaxBehaviorEvent event) {
        System.out.println("=====================================================================");
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Name invalid.");
        FacesContext.getCurrentInstance().addMessage("contact:name", msg);

    }

    public void validateEmail(AjaxBehaviorEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Email invalid.");
        FacesContext.getCurrentInstance().addMessage("contact:email", msg);

    }

    public void validateMessage(AjaxBehaviorEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Message invalid.");
        FacesContext.getCurrentInstance().addMessage("contact:message", msg);

    }

    public void send() {
        compGuideEmail = new CompGuideEmail(selected.getName(), selected.getMessage(), selected.getEmail());
        compGuideEmail.send();

        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", " Message sent successfully.");
        FacesContext.getCurrentInstance().addMessage("contact:buttoncontact", msg);
        selected = null;
    }

}
