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
@Table(name = "periodicity")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Periodicity.findAll", query = "SELECT p FROM Periodicity p"),
    @NamedQuery(name = "Periodicity.findByPeriodicityID", query = "SELECT p FROM Periodicity p WHERE p.periodicityID = :periodicityID"),
    @NamedQuery(name = "Periodicity.findByRepetitionValue", query = "SELECT p FROM Periodicity p WHERE p.repetitionValue = :repetitionValue"),
    @NamedQuery(name = "Periodicity.findByPeriodicityValueTemporalUnitDurationIDAndCyclePartDefinitionID", query = "SELECT p FROM Periodicity p WHERE p.periodicityValue = :periodicityValue AND p.temporalUnitID = :temporalUnitID AND p.durationID = :durationID AND p.cyclePartDefinitionID = :cyclePartDefinitionID"),
    @NamedQuery(name = "Periodicity.findByPeriodicityValueTemporalUnitAndDurationID", query = "SELECT p FROM Periodicity p WHERE p.periodicityValue = :periodicityValue AND p.temporalUnitID = :temporalUnitID AND p.durationID = :durationID"),
    @NamedQuery(name = "Periodicity.findByPeriodicityValueTemporalUnitAndRepetitionValue", query = "SELECT p FROM Periodicity p WHERE p.periodicityValue = :periodicityValue AND p.temporalUnitID = :temporalUnitID AND p.repetitionValue = :repetitionValue"),
    @NamedQuery(name = "Periodicity.findByPeriodicityValueTemporalUnitRepetitionValueAndCyclePartDefinition", query = "SELECT p FROM Periodicity p WHERE p.periodicityValue = :periodicityValue AND p.temporalUnitID = :temporalUnitID AND p.repetitionValue = :repetitionValue AND p.cyclePartDefinitionID = :cyclePartDefinitionID"),
    @NamedQuery(name = "Periodicity.findByPeriodicityValue", query = "SELECT p FROM Periodicity p WHERE p.periodicityValue = :periodicityValue")})
public class Periodicity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PeriodicityID")
    private Integer periodicityID;
    @Column(name = "RepetitionValue")
    private Integer repetitionValue;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PeriodicityValue")
    private double periodicityValue;
    @OneToMany(mappedBy = "periodicityID")
    private List<TemporalElement> temporalElementList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "periodicityID")
    private List<StopConditionSet> stopConditionSetList;
    @JoinColumn(name = "CyclePartDefinitionID", referencedColumnName = "CyclePartDefinitionID")
    @ManyToOne
    private CyclePartDefinition cyclePartDefinitionID;
    @JoinColumn(name = "TemporalUnitID", referencedColumnName = "TemporalUnitID")
    @ManyToOne(optional = false)
    private TemporalUnit temporalUnitID;
    @JoinColumn(name = "DurationID", referencedColumnName = "DurationID")
    @ManyToOne
    private Duration durationID;

    public Periodicity() {
    }

    public Periodicity(Integer periodicityID) {
        this.periodicityID = periodicityID;
    }

    public Periodicity(Integer periodicityID, double periodicityValue) {
        this.periodicityID = periodicityID;
        this.periodicityValue = periodicityValue;
    }

    public Periodicity(double periodicityValue) {
        this.periodicityValue = periodicityValue;
    }

    public Periodicity(Integer repetitionValue, double periodicityValue, CyclePartDefinition cyclePartDefinitionID, TemporalUnit temporalUnitID, Duration durationID) {
        this.repetitionValue = repetitionValue;
        this.periodicityValue = periodicityValue;
        this.cyclePartDefinitionID = cyclePartDefinitionID;
        this.temporalUnitID = temporalUnitID;
        this.durationID = durationID;
    }

    public Periodicity(double periodicityValue, Integer repetitionValue) {
        this.repetitionValue = repetitionValue;
        this.periodicityValue = periodicityValue;
    }

    public Integer getPeriodicityID() {
        return periodicityID;
    }

    public void setPeriodicityID(Integer periodicityID) {
        this.periodicityID = periodicityID;
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

    @XmlTransient
    public List<TemporalElement> getTemporalElementList() {
        return temporalElementList;
    }

    public void setTemporalElementList(List<TemporalElement> temporalElementList) {
        this.temporalElementList = temporalElementList;
    }

    @XmlTransient
    public List<StopConditionSet> getStopConditionSetList() {
        return stopConditionSetList;
    }

    public void setStopConditionSetList(List<StopConditionSet> stopConditionSetList) {
        this.stopConditionSetList = stopConditionSetList;
    }

    public CyclePartDefinition getCyclePartDefinitionID() {
        return cyclePartDefinitionID;
    }

    public void setCyclePartDefinitionID(CyclePartDefinition cyclePartDefinitionID) {
        this.cyclePartDefinitionID = cyclePartDefinitionID;
    }

    public TemporalUnit getTemporalUnitID() {
        return temporalUnitID;
    }

    public void setTemporalUnitID(TemporalUnit temporalUnitID) {
        this.temporalUnitID = temporalUnitID;
    }

    public Duration getDurationID() {
        return durationID;
    }

    public void setDurationID(Duration durationID) {
        this.durationID = durationID;
    }

    public boolean isRepetition() {
        if (repetitionValue != null) {
            return true;
        }

        return false;
    }

    public boolean isDuration() {
        if (durationID != null) {
            return true;
        }

        return false;
    }

    public boolean isCyclePart() {
        if (cyclePartDefinitionID != null) {
            return true;
        }
        return false;
    }

    public long getExecutionTotalTime() {
        long value = 0;
        if (isDuration()) {
            value = durationID.getExecutionTotalTime();
        }
        if (isRepetition()) {
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

    public boolean isExecutionTimeValid() {
        if (isCyclePart()) {
            if (getExecutionTotalTime() > cyclePartDefinitionID.getTotalTime()
                    && getExecutionTotalTime() > getPartExecutionTime()) {
                return true;
            }
        } else if (getExecutionTotalTime() > com.compguide.web.TemporalPattern.TemporalPattern.
                temporalUnitToMilliseconds(temporalUnitID.getValue(), periodicityValue)) {

            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (periodicityID != null ? periodicityID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Periodicity)) {
            return false;
        }
        Periodicity other = (Periodicity) object;
        if ((this.periodicityID == null && other.periodicityID != null) || (this.periodicityID != null && !this.periodicityID.equals(other.periodicityID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.compguide.web.Persistence.Entities.Periodicity[ periodicityID=" + periodicityID + " ]";
    }

}
