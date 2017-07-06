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
@Table(name = "temporalrestriction")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TemporalRestriction.findAll", query = "SELECT t FROM TemporalRestriction t"),
    @NamedQuery(name = "TemporalRestriction.findByTemporalRestrictionID", query = "SELECT t FROM TemporalRestriction t WHERE t.temporalRestrictionID = :temporalRestrictionID"),
    @NamedQuery(name = "TemporalRestriction.findByMinTemporalRestrictionValue", query = "SELECT t FROM TemporalRestriction t WHERE t.minTemporalRestrictionValue = :minTemporalRestrictionValue"),
    @NamedQuery(name = "TemporalRestriction.findByMaxTemporalRestrictionValue", query = "SELECT t FROM TemporalRestriction t WHERE t.maxTemporalRestrictionValue = :maxTemporalRestrictionValue"),
    @NamedQuery(name = "TemporalRestriction.findByTemporalRestrictionValue", query = "SELECT t FROM TemporalRestriction t WHERE t.temporalRestrictionValue = :temporalRestrictionValue")})
public class TemporalRestriction implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TemporalRestrictionID")
    private Integer temporalRestrictionID;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "MinTemporalRestrictionValue")
    private Double minTemporalRestrictionValue;
    @Column(name = "MaxTemporalRestrictionValue")
    private Double maxTemporalRestrictionValue;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TemporalRestrictionValue")
    private double temporalRestrictionValue;
    @OneToMany(mappedBy = "temporalRestrictionID")
    private List<Condition> conditionList;
    @JoinColumn(name = "TemporalOperatorID", referencedColumnName = "TemporalOperatorID")
    @ManyToOne(optional = false)
    private TemporalOperator temporalOperatorID;
    @JoinColumn(name = "TemporalUnitID", referencedColumnName = "TemporalUnitID")
    @ManyToOne(optional = false)
    private TemporalUnit temporalUnitID;

    public TemporalRestriction() {
    }

    public TemporalRestriction(Integer temporalRestrictionID) {
        this.temporalRestrictionID = temporalRestrictionID;
    }

    public TemporalRestriction(Integer temporalRestrictionID, double temporalRestrictionValue) {
        this.temporalRestrictionID = temporalRestrictionID;
        this.temporalRestrictionValue = temporalRestrictionValue;
    }

    public Integer getTemporalRestrictionID() {
        return temporalRestrictionID;
    }

    public void setTemporalRestrictionID(Integer temporalRestrictionID) {
        this.temporalRestrictionID = temporalRestrictionID;
    }

    public Double getMinTemporalRestrictionValue() {
        return minTemporalRestrictionValue;
    }

    public void setMinTemporalRestrictionValue(Double minTemporalRestrictionValue) {
        this.minTemporalRestrictionValue = minTemporalRestrictionValue;
    }

    public Double getMaxTemporalRestrictionValue() {
        return maxTemporalRestrictionValue;
    }

    public void setMaxTemporalRestrictionValue(Double maxTemporalRestrictionValue) {
        this.maxTemporalRestrictionValue = maxTemporalRestrictionValue;
    }

    public double getTemporalRestrictionValue() {
        return temporalRestrictionValue;
    }

    public void setTemporalRestrictionValue(double temporalRestrictionValue) {
        this.temporalRestrictionValue = temporalRestrictionValue;
    }

    @XmlTransient
    public List<Condition> getConditionList() {
        return conditionList;
    }

    public void setConditionList(List<Condition> conditionList) {
        this.conditionList = conditionList;
    }

    public TemporalOperator getTemporalOperatorID() {
        return temporalOperatorID;
    }

    public void setTemporalOperatorID(TemporalOperator temporalOperatorID) {
        this.temporalOperatorID = temporalOperatorID;
    }

    public TemporalUnit getTemporalUnitID() {
        return temporalUnitID;
    }

    public void setTemporalUnitID(TemporalUnit temporalUnitID) {
        this.temporalUnitID = temporalUnitID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (temporalRestrictionID != null ? temporalRestrictionID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TemporalRestriction)) {
            return false;
        }
        TemporalRestriction other = (TemporalRestriction) object;
        if ((this.temporalRestrictionID == null && other.temporalRestrictionID != null) || (this.temporalRestrictionID != null && !this.temporalRestrictionID.equals(other.temporalRestrictionID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.compguide.web.Persistence.Entities.TemporalRestriction[ temporalRestrictionID=" + temporalRestrictionID + " ]";
    }

}
