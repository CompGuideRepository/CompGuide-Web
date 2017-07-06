/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Persistence.Entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author António
 */
@Entity
@Table(name = "medication")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Medication.findAll", query = "SELECT m FROM Medication m"),
    @NamedQuery(name = "Medication.findByIdmedication", query = "SELECT m FROM Medication m WHERE m.idmedication = :idmedication"),
    @NamedQuery(name = "Medication.findByName", query = "SELECT m FROM Medication m WHERE m.name = :name"),
    @NamedQuery(name = "Medication.findByActiveIngredient", query = "SELECT m FROM Medication m WHERE m.activeIngredient = :activeIngredient"),
    @NamedQuery(name = "Medication.findByDosage", query = "SELECT m FROM Medication m WHERE m.dosage = :dosage"),
    @NamedQuery(name = "Medication.findByPharmaceuticalForm", query = "SELECT m FROM Medication m WHERE m.pharmaceuticalForm = :pharmaceuticalForm"),
    @NamedQuery(name = "Medication.findByPosology", query = "SELECT m FROM Medication m WHERE m.posology = :posology"),
    @NamedQuery(name = "Medication.findByTime", query = "SELECT m FROM Medication m WHERE m.time = :time"),
    @NamedQuery(name = "Medication.findByIdentifier", query = "SELECT m FROM Medication m WHERE m.identifier = :identifier"),
    @NamedQuery(name = "Medication.findByTimeend", query = "SELECT m FROM Medication m WHERE m.timeend = :timeend")})
public class Medication implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idmedication")
    private Integer idmedication;
    @Size(max = 128)
    @Column(name = "name")
    private String name;
    @Size(max = 128)
    @Column(name = "activeIngredient")
    private String activeIngredient;
    @Size(max = 128)
    @Column(name = "dosage")
    private String dosage;
    @Size(max = 128)
    @Column(name = "pharmaceuticalForm")
    private String pharmaceuticalForm;
    @Size(max = 128)
    @Column(name = "posology")
    private String posology;
    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    private String description;
    @Column(name = "time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    @Size(max = 45)
    @Column(name = "identifier")
    private String identifier;
    @Column(name = "timeend")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeend;
    @JoinColumn(name = "idtask", referencedColumnName = "idtask")
    @ManyToOne(optional = false)
    private Task idtask;

    public Medication() {
    }

    public Medication(Integer idmedication) {
        this.idmedication = idmedication;
    }

    public Integer getIdmedication() {
        return idmedication;
    }

    public void setIdmedication(Integer idmedication) {
        this.idmedication = idmedication;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActiveIngredient() {
        return activeIngredient;
    }

    public void setActiveIngredient(String activeIngredient) {
        this.activeIngredient = activeIngredient;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getPharmaceuticalForm() {
        return pharmaceuticalForm;
    }

    public void setPharmaceuticalForm(String pharmaceuticalForm) {
        this.pharmaceuticalForm = pharmaceuticalForm;
    }

    public String getPosology() {
        return posology;
    }

    public void setPosology(String posology) {
        this.posology = posology;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Date getTimeend() {
        return timeend;
    }

    public void setTimeend(Date timeend) {
        this.timeend = timeend;
    }

    public Task getIdtask() {
        return idtask;
    }

    public void setIdtask(Task idtask) {
        this.idtask = idtask;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmedication != null ? idmedication.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Medication)) {
            return false;
        }
        Medication other = (Medication) object;
        if ((this.idmedication == null && other.idmedication != null) || (this.idmedication != null && !this.idmedication.equals(other.idmedication))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.compguide.web.Persistence.Entities.Medication[ idmedication=" + idmedication + " ]";
    }

}
