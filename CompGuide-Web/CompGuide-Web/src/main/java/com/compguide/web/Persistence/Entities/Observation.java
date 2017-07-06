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
@Table(name = "observation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Observation.findAll", query = "SELECT o FROM Observation o"),
    @NamedQuery(name = "Observation.findByIdobservation", query = "SELECT o FROM Observation o WHERE o.idobservation = :idobservation"),
    @NamedQuery(name = "Observation.findByUnit", query = "SELECT o FROM Observation o WHERE o.unit = :unit"),
    @NamedQuery(name = "Observation.findByIsnumeric", query = "SELECT o FROM Observation o WHERE o.isnumeric = :isnumeric"),
    @NamedQuery(name = "Observation.findByParameterIdentifier", query = "SELECT o FROM Observation o WHERE o.parameterIdentifier = :parameterIdentifier"),
    @NamedQuery(name = "Observation.findByVariableName", query = "SELECT o FROM Observation o WHERE o.variableName = :variableName"),
    @NamedQuery(name = "Observation.findByTask", query = "SELECT o FROM Observation o WHERE o.task = :task"),
    @NamedQuery(name = "Observation.findByTime", query = "SELECT o FROM Observation o WHERE o.time = :time"),
    @NamedQuery(name = "Observation.findByIdentifier", query = "SELECT o FROM Observation o WHERE o.identifier = :identifier")})
public class Observation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idobservation")
    private Integer idobservation;
    @Lob
    @Size(max = 65535)
    @Column(name = "parameter")
    private String parameter;
    @Size(max = 45)
    @Column(name = "unit")
    private String unit;
    @Column(name = "isnumeric")
    private Short isnumeric;
    @Size(max = 45)
    @Column(name = "parameterIdentifier")
    private String parameterIdentifier;
    @Size(max = 45)
    @Column(name = "variableName")
    private String variableName;
    @Lob
    @Size(max = 65535)
    @Column(name = "parameterValue")
    private String parameterValue;
    @Size(max = 45)
    @Column(name = "task")
    private String task;
    @Column(name = "time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    @Size(max = 45)
    @Column(name = "identifier")
    private String identifier;
    @JoinColumn(name = "idtask", referencedColumnName = "idtask")
    @ManyToOne(optional = false)
    private Task idtask;

    public Observation() {
    }

    public Observation(Integer idobservation) {
        this.idobservation = idobservation;
    }

    public Integer getIdobservation() {
        return idobservation;
    }

    public void setIdobservation(Integer idobservation) {
        this.idobservation = idobservation;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Short getIsnumeric() {
        return isnumeric;
    }

    public void setIsnumeric(Short isnumeric) {
        this.isnumeric = isnumeric;
    }

    public String getParameterIdentifier() {
        return parameterIdentifier;
    }

    public void setParameterIdentifier(String parameterIdentifier) {
        this.parameterIdentifier = parameterIdentifier;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
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
        hash += (idobservation != null ? idobservation.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Observation)) {
            return false;
        }
        Observation other = (Observation) object;
        if ((this.idobservation == null && other.idobservation != null) || (this.idobservation != null && !this.idobservation.equals(other.idobservation))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.compguide.web.Persistence.Entities.Observation[ idobservation=" + idobservation + " ]";
    }

}
