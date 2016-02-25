/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Persistence.Entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author António
 */
@Entity
@Table(name = "temporalunit")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TemporalUnit.findAll", query = "SELECT t FROM TemporalUnit t"),
    @NamedQuery(name = "TemporalUnit.findByTemporalUnitID", query = "SELECT t FROM TemporalUnit t WHERE t.temporalUnitID = :temporalUnitID"),
    @NamedQuery(name = "TemporalUnit.findByValue", query = "SELECT t FROM TemporalUnit t WHERE t.value = :value")})
public class TemporalUnit implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TemporalUnitID")
    private Integer temporalUnitID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "Value")
    private String value;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "temporalUnitID")
    private List<Duration> durationList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "temporalUnitID")
    private List<CyclePartPeriodicity> cyclePartPeriodicityList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "temporalUnitID")
    private List<WaitingTime> waitingTimeList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "temporalUnitID")
    private List<TemporalRestriction> temporalRestrictionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "temporalUnitID")
    private List<Periodicity> periodicityList;

    public TemporalUnit() {
    }

    public TemporalUnit(Integer temporalUnitID) {
        this.temporalUnitID = temporalUnitID;
    }

    public TemporalUnit(Integer temporalUnitID, String value) {
        this.temporalUnitID = temporalUnitID;
        this.value = value;
    }

    public TemporalUnit(String value) {
        this.value = value;
    }

    public Integer getTemporalUnitID() {
        return temporalUnitID;
    }

    public void setTemporalUnitID(Integer temporalUnitID) {
        this.temporalUnitID = temporalUnitID;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @XmlTransient
    public List<Duration> getDurationList() {
        return durationList;
    }

    public void setDurationList(List<Duration> durationList) {
        this.durationList = durationList;
    }

    @XmlTransient
    public List<CyclePartPeriodicity> getCyclePartPeriodicityList() {
        return cyclePartPeriodicityList;
    }

    public void setCyclePartPeriodicityList(List<CyclePartPeriodicity> cyclePartPeriodicityList) {
        this.cyclePartPeriodicityList = cyclePartPeriodicityList;
    }

    @XmlTransient
    public List<WaitingTime> getWaitingTimeList() {
        return waitingTimeList;
    }

    public void setWaitingTimeList(List<WaitingTime> waitingTimeList) {
        this.waitingTimeList = waitingTimeList;
    }

    @XmlTransient
    public List<TemporalRestriction> getTemporalRestrictionList() {
        return temporalRestrictionList;
    }

    public void setTemporalRestrictionList(List<TemporalRestriction> temporalRestrictionList) {
        this.temporalRestrictionList = temporalRestrictionList;
    }

    @XmlTransient
    public List<Periodicity> getPeriodicityList() {
        return periodicityList;
    }

    public void setPeriodicityList(List<Periodicity> periodicityList) {
        this.periodicityList = periodicityList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (temporalUnitID != null ? temporalUnitID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TemporalUnit)) {
            return false;
        }
        TemporalUnit other = (TemporalUnit) object;
        if ((this.temporalUnitID == null && other.temporalUnitID != null) || (this.temporalUnitID != null && !this.temporalUnitID.equals(other.temporalUnitID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.compguide.web.Persistence.Entities.TemporalUnit[ temporalUnitID=" + temporalUnitID + " ]";
    }

}
