/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Persistence.Entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author António
 */
@Entity
@Table(name = "cyclepartdefinition")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CyclePartDefinition.findAll", query = "SELECT c FROM CyclePartDefinition c"),
    @NamedQuery(name = "CyclePartDefinition.findByDurationID", query = "SELECT c FROM CyclePartDefinition c WHERE c.durationID = :durationID"),
    @NamedQuery(name = "CyclePartDefinition.findByCyclePartPeriodicityID", query = "SELECT c FROM CyclePartDefinition c WHERE c.cyclePartPeriodicityID = :cyclePartPeriodicityID"),
    @NamedQuery(name = "CyclePartDefinition.findByCyclePartDefinitionID", query = "SELECT c FROM CyclePartDefinition c WHERE c.cyclePartDefinitionID = :cyclePartDefinitionID")})
public class CyclePartDefinition implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CyclePartDefinitionID")
    private Integer cyclePartDefinitionID;
    @OneToMany(mappedBy = "cyclePartDefinitionID")
    private List<Periodicity> periodicityList;
    @JoinColumn(name = "DurationID", referencedColumnName = "DurationID")
    @ManyToOne
    private Duration durationID;
    @JoinColumn(name = "CyclePartPeriodicityID", referencedColumnName = "CyclePartPeriodicityID")
    @ManyToOne
    private CyclePartPeriodicity cyclePartPeriodicityID;

    public CyclePartDefinition() {
    }

    public CyclePartDefinition(Integer cyclePartDefinitionID) {
        this.cyclePartDefinitionID = cyclePartDefinitionID;
    }

    public CyclePartDefinition(Duration durationID, CyclePartPeriodicity cyclePartPeriodicityID) {
        this.durationID = durationID;
        this.cyclePartPeriodicityID = cyclePartPeriodicityID;
    }

    public Integer getCyclePartDefinitionID() {
        return cyclePartDefinitionID;
    }

    public void setCyclePartDefinitionID(Integer cyclePartDefinitionID) {
        this.cyclePartDefinitionID = cyclePartDefinitionID;
    }

    @XmlTransient
    public List<Periodicity> getPeriodicityList() {
        return periodicityList;
    }

    public void setPeriodicityList(List<Periodicity> periodicityList) {
        this.periodicityList = periodicityList;
    }

    public Duration getDurationID() {
        return durationID;
    }

    public void setDurationID(Duration durationID) {
        this.durationID = durationID;
    }

    public CyclePartPeriodicity getCyclePartPeriodicityID() {
        return cyclePartPeriodicityID;
    }

    public void setCyclePartPeriodicityID(CyclePartPeriodicity cyclePartPeriodicityID) {
        this.cyclePartPeriodicityID = cyclePartPeriodicityID;
    }

    public boolean asDuration() {
        if (durationID != null) {
            return true;
        }

        return false;
    }

    public boolean asPeriodicity() {
        if (cyclePartPeriodicityID != null) {
            return true;
        }
        return false;
    }

    public long getTotalTime() {
        long duration = 0;

        if (asDuration()) {
            duration = durationID.getExecutionTotalTime();
        }

        if (asPeriodicity()) {
            duration = cyclePartPeriodicityID.getExecutionTotalTime();
        }

        return duration;

    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cyclePartDefinitionID != null ? cyclePartDefinitionID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CyclePartDefinition)) {
            return false;
        }
        CyclePartDefinition other = (CyclePartDefinition) object;
        if ((this.cyclePartDefinitionID == null && other.cyclePartDefinitionID != null) || (this.cyclePartDefinitionID != null && !this.cyclePartDefinitionID.equals(other.cyclePartDefinitionID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.compguide.web.Persistence.Entities.CyclePartDefinition[ cyclePartDefinitionID=" + cyclePartDefinitionID + " ]";
    }

}
