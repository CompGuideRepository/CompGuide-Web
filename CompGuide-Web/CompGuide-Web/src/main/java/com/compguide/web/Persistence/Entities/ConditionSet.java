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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author António
 */
@Entity
@Table(name = "conditionset")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConditionSet.findAll", query = "SELECT c FROM ConditionSet c"),
    @NamedQuery(name = "ConditionSet.findByIdconditionset", query = "SELECT c FROM ConditionSet c WHERE c.idconditionset = :idconditionset"),
    @NamedQuery(name = "ConditionSet.findByIdentifier", query = "SELECT c FROM ConditionSet c WHERE c.identifier = :identifier"),
    @NamedQuery(name = "ConditionSet.findByConditionSetCounter", query = "SELECT c FROM ConditionSet c WHERE c.conditionSetCounter = :conditionSetCounter")})
public class ConditionSet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idconditionset")
    private Integer idconditionset;
    @Size(max = 45)
    @Column(name = "identifier")
    private String identifier;
    @Column(name = "ConditionSetCounter")
    private Integer conditionSetCounter;
    @OneToMany(mappedBy = "conditionSetID")
    private List<Outcome> outcomeList;
    @OneToMany(mappedBy = "conditionSetID")
    private List<Condition> conditionList;

    public ConditionSet() {
    }

    public ConditionSet(Integer idconditionset) {
        this.idconditionset = idconditionset;
    }

    public Integer getIdconditionset() {
        return idconditionset;
    }

    public void setIdconditionset(Integer idconditionset) {
        this.idconditionset = idconditionset;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Integer getConditionSetCounter() {
        return conditionSetCounter;
    }

    public void setConditionSetCounter(Integer conditionSetCounter) {
        this.conditionSetCounter = conditionSetCounter;
    }

    @XmlTransient
    public List<Outcome> getOutcomeList() {
        return outcomeList;
    }

    public void setOutcomeList(List<Outcome> outcomeList) {
        this.outcomeList = outcomeList;
    }

    @XmlTransient
    public List<Condition> getConditionList() {
        return conditionList;
    }

    public void setConditionList(List<Condition> conditionList) {
        this.conditionList = conditionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idconditionset != null ? idconditionset.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConditionSet)) {
            return false;
        }
        ConditionSet other = (ConditionSet) object;
        if ((this.idconditionset == null && other.idconditionset != null) || (this.idconditionset != null && !this.idconditionset.equals(other.idconditionset))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.compguide.web.Persistence.Entities.ConditionSet[ idconditionset=" + idconditionset + " ]";
    }

}
