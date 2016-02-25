/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Persistence.Controllers;

import java.io.Serializable;
import java.util.Date;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author António Silva
 */
@Stateless
@LocalBean
@Named
public class DataSelected implements Serializable {

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public DataSelected() {
    }

    public DataSelected(Date date) {
        this.date = date;
    }

    public Date getDate() {
        if (date == null) {
            date = new Date();
        }
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
