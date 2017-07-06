/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Persistence.Entities;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author António
 */
@Entity
@Table(name = "`condition`")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Condition.findAll", query = "SELECT c FROM Condition c"),
    @NamedQuery(name = "Condition.findByConditionID", query = "SELECT c FROM Condition c WHERE c.conditionID = :conditionID"),
    @NamedQuery(name = "Condition.findByConditionSetID", query = "SELECT c FROM Condition c WHERE c.conditionSetID = :conditionSetID"),
    @NamedQuery(name = "Condition.findByNumericalValue", query = "SELECT c FROM Condition c WHERE c.numericalValue = :numericalValue"),
    @NamedQuery(name = "Condition.findByQualitativeValue", query = "SELECT c FROM Condition c WHERE c.qualitativeValue = :qualitativeValue"),
    @NamedQuery(name = "Condition.findByComparisonOperator", query = "SELECT c FROM Condition c WHERE c.comparisonOperator = :comparisonOperator"),
    @NamedQuery(name = "Condition.findByParameterIdentifier", query = "SELECT c FROM Condition c WHERE c.parameterIdentifier = :parameterIdentifier"),
    @NamedQuery(name = "Condition.findByConditionParameter", query = "SELECT c FROM Condition c WHERE c.conditionParameter = :conditionParameter"),
    @NamedQuery(name = "Condition.findByUnit", query = "SELECT c FROM Condition c WHERE c.unit = :unit"),
    @NamedQuery(name = "Condition.findByCanAsk", query = "SELECT c FROM Condition c WHERE c.canAsk = :canAsk"),
    @NamedQuery(name = "Condition.findByAsked", query = "SELECT c FROM Condition c WHERE c.asked = :asked")})
public class Condition implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ConditionID")
    private Integer conditionID;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "NumericalValue")
    private Double numericalValue;
    @Size(max = 255)
    @Column(name = "QualitativeValue")
    private String qualitativeValue;
    @Size(max = 255)
    @Column(name = "ComparisonOperator")
    private String comparisonOperator;
    @Size(max = 255)
    @Column(name = "ParameterIdentifier")
    private String parameterIdentifier;
    @Size(max = 255)
    @Column(name = "ConditionParameter")
    private String conditionParameter;
    @Size(max = 255)
    @Column(name = "Unit")
    private String unit;
    @Column(name = "CanAsk")
    private Boolean canAsk;
    @Column(name = "Asked")
    private Boolean asked;
    @JoinColumn(name = "ConditionSetID", referencedColumnName = "idconditionset")
    @ManyToOne
    private ConditionSet conditionSetID;
    @JoinColumn(name = "TemporalRestrictionID", referencedColumnName = "TemporalRestrictionID")
    @ManyToOne
    private TemporalRestriction temporalRestrictionID;
    @JoinColumn(name = "StopConditionSetID", referencedColumnName = "StopConditionSetID")
    @ManyToOne
    private StopConditionSet stopConditionSetID;

    public Condition() {
    }

    public Condition(Integer conditionID) {
        this.conditionID = conditionID;
    }

    public Condition(Double numericalValue, String qualitativeValue, String comparisonOperator, String parameterIdentifier, String conditionParameter, Boolean canAsk, Boolean asked, String unit) {
        this.numericalValue = numericalValue;
        this.qualitativeValue = qualitativeValue;
        this.comparisonOperator = comparisonOperator;
        this.parameterIdentifier = parameterIdentifier;
        this.conditionParameter = conditionParameter;
        this.canAsk = canAsk;
        this.asked = asked;
        this.unit = unit;
    }

    public Integer getConditionID() {
        return conditionID;
    }

    public void setConditionID(Integer conditionID) {
        this.conditionID = conditionID;
    }

    public Double getNumericalValue() {
        return numericalValue;
    }

    public void setNumericalValue(Double numericalValue) {
        this.numericalValue = numericalValue;
    }

    public String getQualitativeValue() {
        return qualitativeValue;
    }

    public void setQualitativeValue(String qualitativeValue) {
        this.qualitativeValue = qualitativeValue;
    }

    public String getComparisonOperator() {
        return comparisonOperator;
    }

    public void setComparisonOperator(String comparisonOperator) {
        this.comparisonOperator = comparisonOperator;
    }

    public String getParameterIdentifier() {
        return parameterIdentifier;
    }

    public void setParameterIdentifier(String parameterIdentifier) {
        this.parameterIdentifier = parameterIdentifier;
    }

    public String getConditionParameter() {
        return conditionParameter;
    }

    public void setConditionParameter(String conditionParameter) {
        this.conditionParameter = conditionParameter;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Boolean getCanAsk() {
        return canAsk;
    }

    public void setCanAsk(Boolean canAsk) {
        this.canAsk = canAsk;
    }

    public Boolean getAsked() {
        return asked;
    }

    public void setAsked(Boolean asked) {
        this.asked = asked;
    }

    public ConditionSet getConditionSetID() {
        return conditionSetID;
    }

    public void setConditionSetID(ConditionSet conditionSetID) {
        this.conditionSetID = conditionSetID;
    }

    public TemporalRestriction getTemporalRestrictionID() {
        return temporalRestrictionID;
    }

    public void setTemporalRestrictionID(TemporalRestriction temporalRestrictionID) {
        this.temporalRestrictionID = temporalRestrictionID;
    }

    public StopConditionSet getStopConditionSetID() {
        return stopConditionSetID;
    }

    public void setStopConditionSetID(StopConditionSet stopConditionSetID) {
        this.stopConditionSetID = stopConditionSetID;
    }

    public boolean isTemporalRestriction() {
        if (temporalRestrictionID != null) {
            return true;
        }
        return false;
    }

    public boolean isNumeric() {
        if (qualitativeValue == null && numericalValue != null) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (conditionID != null ? conditionID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Condition)) {
            return false;
        }
        Condition other = (Condition) object;
        if ((this.conditionID == null && other.conditionID != null) || (this.conditionID != null && !this.conditionID.equals(other.conditionID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.compguide.web.Persistence.Entities.Condition[ conditionID=" + conditionID + " ]";
    }

}
