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
@Table(name = "duration")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Duration.findAll", query = "SELECT d FROM Duration d"),
    @NamedQuery(name = "Duration.findByDurationID", query = "SELECT d FROM Duration d WHERE d.durationID = :durationID"),
    @NamedQuery(name = "Duration.findByMinDurationValue", query = "SELECT d FROM Duration d WHERE d.minDurationValue = :minDurationValue"),
    @NamedQuery(name = "Duration.findByDurationValueANDTemporalUnit", query = "SELECT d FROM Duration d WHERE d.durationValue = :durationValue AND d.temporalUnitID = :temporalUnitID"),
    @NamedQuery(name = "Duration.findByIntervalAndTemporalUnit", query = "SELECT d FROM Duration d WHERE d.minDurationValue = :minDurationValue AND d.maxDurationValue = :maxDurationValue AND d.temporalUnitID = :temporalUnitID"),
    @NamedQuery(name = "Duration.findByMaxDurationValue", query = "SELECT d FROM Duration d WHERE d.maxDurationValue = :maxDurationValue"),
    @NamedQuery(name = "Duration.findByDurationValue", query = "SELECT d FROM Duration d WHERE d.durationValue = :durationValue")})
public class Duration implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "DurationID")
    private Integer durationID;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "MinDurationValue")
    private Double minDurationValue;
    @Column(name = "MaxDurationValue")
    private Double maxDurationValue;
    @Column(name = "DurationValue")
    private Double durationValue;
    @OneToMany(mappedBy = "durationID")
    private List<TemporalElement> temporalElementList;
    @JoinColumn(name = "TemporalUnitID", referencedColumnName = "TemporalUnitID")
    @ManyToOne(optional = false)
    private TemporalUnit temporalUnitID;
    @OneToMany(mappedBy = "durationID")
    private List<CyclePartPeriodicity> cyclePartPeriodicityList;
    @OneToMany(mappedBy = "durationID")
    private List<Periodicity> periodicityList;
    @OneToMany(mappedBy = "durationID")
    private List<CyclePartDefinition> cyclePartDefinitionList;

    public Duration() {
    }

    public Duration(Integer durationID) {
        this.durationID = durationID;
    }

    public Integer getDurationID() {
        return durationID;
    }

    public Duration(Double minDurationValue, Double maxDurationValue, Double durationValue) {
        this.minDurationValue = minDurationValue;
        this.maxDurationValue = maxDurationValue;
        this.durationValue = durationValue;
    }

    public Duration(Double minDurationValue, Double maxDurationValue, Double durationValue, TemporalUnit temporalUnitID) {
        this.minDurationValue = minDurationValue;
        this.maxDurationValue = maxDurationValue;
        this.durationValue = durationValue;
        this.temporalUnitID = temporalUnitID;
    }

    public void setDurationID(Integer durationID) {
        this.durationID = durationID;
    }

    public Double getMinDurationValue() {
        return minDurationValue;
    }

    public void setMinDurationValue(Double minDurationValue) {
        this.minDurationValue = minDurationValue;
    }

    public Double getMaxDurationValue() {
        return maxDurationValue;
    }

    public void setMaxDurationValue(Double maxDurationValue) {
        this.maxDurationValue = maxDurationValue;
    }

    public Double getDurationValue() {
        return durationValue;
    }

    public void setDurationValue(Double durationValue) {
        this.durationValue = durationValue;
    }

    @XmlTransient
    public List<TemporalElement> getTemporalElementList() {
        return temporalElementList;
    }

    public void setTemporalElementList(List<TemporalElement> temporalElementList) {
        this.temporalElementList = temporalElementList;
    }

    public TemporalUnit getTemporalUnitID() {
        return temporalUnitID;
    }

    public void setTemporalUnitID(TemporalUnit temporalUnitID) {
        this.temporalUnitID = temporalUnitID;
    }

    @XmlTransient
    public List<CyclePartPeriodicity> getCyclePartPeriodicityList() {
        return cyclePartPeriodicityList;
    }

    public void setCyclePartPeriodicityList(List<CyclePartPeriodicity> cyclePartPeriodicityList) {
        this.cyclePartPeriodicityList = cyclePartPeriodicityList;
    }

    @XmlTransient
    public List<Periodicity> getPeriodicityList() {
        return periodicityList;
    }

    public void setPeriodicityList(List<Periodicity> periodicityList) {
        this.periodicityList = periodicityList;
    }

    @XmlTransient
    public List<CyclePartDefinition> getCyclePartDefinitionList() {
        return cyclePartDefinitionList;
    }

    public void setCyclePartDefinitionList(List<CyclePartDefinition> cyclePartDefinitionList) {
        this.cyclePartDefinitionList = cyclePartDefinitionList;
    }

    public boolean asExactValue() {
        if (durationValue != null) {
            return true;
        }
        return false;
    }

    public boolean isInterval() {
        if (minDurationValue != null && maxDurationValue != null) {
            return true;
        }
        return false;
    }

    public long getExecutionTotalTime() {
        long value = 0;
        if (isInterval()) {
            value = com.compguide.web.TemporalPattern.TemporalPattern.temporalUnitToMilliseconds(
                    temporalUnitID.getValue(),
                    maxDurationValue);
        }

        if (asExactValue()) {
            value = com.compguide.web.TemporalPattern.TemporalPattern.temporalUnitToMilliseconds(
                    temporalUnitID.getValue(),
                    durationValue);
        }

        return value;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (durationID != null ? durationID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Duration)) {
            return false;
        }
        Duration other = (Duration) object;
        if ((this.durationID == null && other.durationID != null) || (this.durationID != null && !this.durationID.equals(other.durationID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.compguide.web.Persistence.Entities.Duration[ durationID=" + durationID + " ]";
    }

}
