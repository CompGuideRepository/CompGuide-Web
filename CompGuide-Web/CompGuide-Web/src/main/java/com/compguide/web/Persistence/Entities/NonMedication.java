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
@Table(name = "nonmedication")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NonMedication.findAll", query = "SELECT n FROM NonMedication n"),
    @NamedQuery(name = "NonMedication.findByIdnonmedication", query = "SELECT n FROM NonMedication n WHERE n.idnonmedication = :idnonmedication"),
    @NamedQuery(name = "NonMedication.findByName", query = "SELECT n FROM NonMedication n WHERE n.name = :name"),
    @NamedQuery(name = "NonMedication.findByTime", query = "SELECT n FROM NonMedication n WHERE n.time = :time"),
    @NamedQuery(name = "NonMedication.findByIdentifier", query = "SELECT n FROM NonMedication n WHERE n.identifier = :identifier")})
public class NonMedication implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idnonmedication")
    private Integer idnonmedication;
    @Size(max = 128)
    @Column(name = "name")
    private String name;
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
    @JoinColumn(name = "idtask", referencedColumnName = "idtask")
    @ManyToOne(optional = false)
    private Task idtask;

    public NonMedication() {
    }

    public NonMedication(Integer idnonmedication) {
        this.idnonmedication = idnonmedication;
    }

    public Integer getIdnonmedication() {
        return idnonmedication;
    }

    public void setIdnonmedication(Integer idnonmedication) {
        this.idnonmedication = idnonmedication;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Task getIdtask() {
        return idtask;
    }

    public void setIdtask(Task idtask) {
        this.idtask = idtask;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idnonmedication != null ? idnonmedication.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NonMedication)) {
            return false;
        }
        NonMedication other = (NonMedication) object;
        if ((this.idnonmedication == null && other.idnonmedication != null) || (this.idnonmedication != null && !this.idnonmedication.equals(other.idnonmedication))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.compguide.web.Persistence.Entities.NonMedication[ idnonmedication=" + idnonmedication + " ]";
    }

}
