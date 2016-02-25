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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author António
 */
@Entity
@Table(name = "cyclepartperiodicity")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CyclePartPeriodicity.findAll", query = "SELECT c FROM CyclePartPeriodicity c"),
    @NamedQuery(name = "CyclePartPeriodicity.findByDurationID", query = "SELECT c FROM CyclePartPeriodicity c WHERE c.durationID = :durationID"),
    @NamedQuery(name = "CyclePartPeriodicity.findByCyclePartPeriodicityID", query = "SELECT c FROM CyclePartPeriodicity c WHERE c.cyclePartPeriodicityID = :cyclePartPeriodicityID"),
    @NamedQuery(name = "CyclePartPeriodicity.findByRepetitionValue", query = "SELECT c FROM CyclePartPeriodicity c WHERE c.repetitionValue = :repetitionValue"),
    @NamedQuery(name = "CyclePartPeriodicity.findByPeriodicityValueTemporalUnitAndDurationID", query = "SELECT c FROM CyclePartPeriodicity c WHERE c.periodicityValue = :periodicityValue AND c.temporalUnitID = :temporalUnitID AND c.durationID = :durationID"),
    @NamedQuery(name = "CyclePartPeriodicity.findByPeriodicityValueTemporalUnitAndRepetitionValue", query = "SELECT c FROM CyclePartPeriodicity c WHERE c.periodicityValue = :periodicityValue AND c.temporalUnitID = :temporalUnitID AND c.repetitionValue = :repetitionValue"),
    @NamedQuery(name = "CyclePartPeriodicity.findByPeriodicityValue", query = "SELECT c FROM CyclePartPeriodicity c WHERE c.periodicityValue = :periodicityValue")})
public class CyclePartPeriodicity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CyclePartPeriodicityID")
    private Integer cyclePartPeriodicityID;
    @Column(name = "RepetitionValue")
    private Integer repetitionValue;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PeriodicityValue")
    private double periodicityValue;
    @JoinColumn(name = "DurationID", referencedColumnName = "DurationID")
    @ManyToOne
    private Duration durationID;
    @JoinColumn(name = "TemporalUnitID", referencedColumnName = "TemporalUnitID")
    @ManyToOne(optional = false)
    private TemporalUnit temporalUnitID;
    @OneToMany(mappedBy = "cyclePartPeriodicityID")
    private List<CyclePartDefinition> cyclePartDefinitionList;

    public CyclePartPeriodicity() {
    }

    public CyclePartPeriodicity(Integer cyclePartPeriodicityID) {
        this.cyclePartPeriodicityID = cyclePartPeriodicityID;
    }

    public CyclePartPeriodicity(Integer cyclePartPeriodicityID, double periodicityValue) {
        this.cyclePartPeriodicityID = cyclePartPeriodicityID;
        this.periodicityValue = periodicityValue;
    }

    public CyclePartPeriodicity(double periodicityValue) {
        this.periodicityValue = periodicityValue;
    }

    public CyclePartPeriodicity(double periodicityValue, Integer repetitionValue) {
        this.repetitionValue = repetitionValue;
        this.periodicityValue = periodicityValue;
    }

    public CyclePartPeriodicity(Integer repetitionValue, double periodicityValue, Duration durationID, TemporalUnit temporalUnitID) {
        this.repetitionValue = repetitionValue;
        this.periodicityValue = periodicityValue;
        this.durationID = durationID;
        this.temporalUnitID = temporalUnitID;
    }

    public Integer getCyclePartPeriodicityID() {
        return cyclePartPeriodicityID;
    }

    public void setCyclePartPeriodicityID(Integer cyclePartPeriodicityID) {
        this.cyclePartPeriodicityID = cyclePartPeriodicityID;
    }

    public Integer getRepetitionValue() {
        return repetitionValue;
    }

    public void setRepetitionValue(Integer repetitionValue) {
        this.repetitionValue = repetitionValue;
    }

    public double getPeriodicityValue() {
        return periodicityValue;
    }

    public void setPeriodicityValue(double periodicityValue) {
        this.periodicityValue = periodicityValue;
    }

    public Duration getDurationID() {
        return durationID;
    }

    public void setDurationID(Duration durationID) {
        this.durationID = durationID;
    }

    public TemporalUnit getTemporalUnitID() {
        return temporalUnitID;
    }

    public void setTemporalUnitID(TemporalUnit temporalUnitID) {
        this.temporalUnitID = temporalUnitID;
    }

    @XmlTransient
    public List<CyclePartDefinition> getCyclePartDefinitionList() {
        return cyclePartDefinitionList;
    }

    public void setCyclePartDefinitionList(List<CyclePartDefinition> cyclePartDefinitionList) {
        this.cyclePartDefinitionList = cyclePartDefinitionList;
    }

    public boolean asRepetition() {
        if (repetitionValue != null) {
            return true;
        }

        return false;
    }

    public boolean asDuration() {
        if (durationID != null) {
            return true;
        }

        return false;
    }

    public long getExecutionTotalTime() {
        long value = 0;
        if (asDuration()) {
            value = durationID.getExecutionTotalTime();
        }

        if (asRepetition()) {
            value = com.compguide.web.TemporalPattern.TemporalPattern.temporalUnitToMilliseconds(
                    temporalUnitID.getValue(),
                    periodicityValue) * repetitionValue;
        }

        return value;
    }

    public long getPartExecutionTime() {
        return com.compguide.web.TemporalPattern.TemporalPattern.temporalUnitToMilliseconds(
                temporalUnitID.getValue(),
                periodicityValue);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cyclePartPeriodicityID != null ? cyclePartPeriodicityID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CyclePartPeriodicity)) {
            return false;
        }
        CyclePartPeriodicity other = (CyclePartPeriodicity) object;
        if ((this.cyclePartPeriodicityID == null && other.cyclePartPeriodicityID != null) || (this.cyclePartPeriodicityID != null && !this.cyclePartPeriodicityID.equals(other.cyclePartPeriodicityID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.compguide.web.Persistence.Entities.CyclePartPeriodicity[ cyclePartPeriodicityID=" + cyclePartPeriodicityID + " ]";
    }

}
