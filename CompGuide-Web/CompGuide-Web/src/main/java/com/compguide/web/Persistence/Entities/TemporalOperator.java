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
@Table(name = "temporaloperator")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TemporalOperator.findAll", query = "SELECT t FROM TemporalOperator t"),
    @NamedQuery(name = "TemporalOperator.findByTemporalOperatorID", query = "SELECT t FROM TemporalOperator t WHERE t.temporalOperatorID = :temporalOperatorID"),
    @NamedQuery(name = "TemporalOperator.findByValue", query = "SELECT t FROM TemporalOperator t WHERE t.value = :value")})
public class TemporalOperator implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TemporalOperatorID")
    private Integer temporalOperatorID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "Value")
    private String value;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "temporalOperatorID")
    private List<TemporalRestriction> temporalRestrictionList;

    public TemporalOperator() {
    }

    public TemporalOperator(Integer temporalOperatorID) {
        this.temporalOperatorID = temporalOperatorID;
    }

    public TemporalOperator(Integer temporalOperatorID, String value) {
        this.temporalOperatorID = temporalOperatorID;
        this.value = value;
    }

    public Integer getTemporalOperatorID() {
        return temporalOperatorID;
    }

    public void setTemporalOperatorID(Integer temporalOperatorID) {
        this.temporalOperatorID = temporalOperatorID;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @XmlTransient
    public List<TemporalRestriction> getTemporalRestrictionList() {
        return temporalRestrictionList;
    }

    public void setTemporalRestrictionList(List<TemporalRestriction> temporalRestrictionList) {
        this.temporalRestrictionList = temporalRestrictionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (temporalOperatorID != null ? temporalOperatorID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TemporalOperator)) {
            return false;
        }
        TemporalOperator other = (TemporalOperator) object;
        if ((this.temporalOperatorID == null && other.temporalOperatorID != null) || (this.temporalOperatorID != null && !this.temporalOperatorID.equals(other.temporalOperatorID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.compguide.web.Persistence.Entities.TemporalOperator[ temporalOperatorID=" + temporalOperatorID + " ]";
    }

}
