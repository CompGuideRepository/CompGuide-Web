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
@Table(name = "waitingtime")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WaitingTime.findAll", query = "SELECT w FROM WaitingTime w"),
    @NamedQuery(name = "WaitingTime.findByWaitingTimeID", query = "SELECT w FROM WaitingTime w WHERE w.waitingTimeID = :waitingTimeID"),
    @NamedQuery(name = "WaitingTime.findByExactWaitingTime", query = "SELECT w FROM WaitingTime w WHERE w.exactWaitingTime = :exactWaitingTime"),
    @NamedQuery(name = "WaitingTime.findByMinWaitingTime", query = "SELECT w FROM WaitingTime w WHERE w.minWaitingTime = :minWaitingTime"),
    @NamedQuery(name = "WaitingTime.findByMaxWaitingTime", query = "SELECT w FROM WaitingTime w WHERE w.maxWaitingTime = :maxWaitingTime"),
    @NamedQuery(name = "WaitingTime.findByMinMaxWaitingTimeAndTemporalUnitID", query = "SELECT w FROM WaitingTime w WHERE w.minWaitingTime = :minWaitingTime AND w.maxWaitingTime = :maxWaitingTime AND w.temporalUnitID = :temporalUnitID"),
    @NamedQuery(name = "WaitingTime.findByExactWaitingTimeAndTemporalUnitID", query = "SELECT w FROM WaitingTime w WHERE w.exactWaitingTime = :exactWaitingTime AND w.temporalUnitID = :temporalUnitID")})
public class WaitingTime implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "WaitingTimeID")
    private Integer waitingTimeID;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ExactWaitingTime")
    private Double exactWaitingTime;
    @Column(name = "MinWaitingTime")
    private Double minWaitingTime;
    @Column(name = "MaxWaitingTime")
    private Double maxWaitingTime;
    @OneToMany(mappedBy = "waitingTimeID")
    private List<TemporalElement> temporalElementList;
    @JoinColumn(name = "TemporalUnitID", referencedColumnName = "TemporalUnitID")
    @ManyToOne(optional = false)
    private TemporalUnit temporalUnitID;

    public WaitingTime() {
    }

    public WaitingTime(Integer waitingTimeID) {
        this.waitingTimeID = waitingTimeID;
    }

    public WaitingTime(Double minWaitingTime, Double maxWaitingTime) {
        this.minWaitingTime = minWaitingTime;
        this.maxWaitingTime = maxWaitingTime;
    }

    public WaitingTime(Double exactWaitingTime) {
        this.exactWaitingTime = exactWaitingTime;
    }

    public Integer getWaitingTimeID() {
        return waitingTimeID;
    }

    public void setWaitingTimeID(Integer waitingTimeID) {
        this.waitingTimeID = waitingTimeID;
    }

    public Double getExactWaitingTime() {
        return exactWaitingTime;
    }

    public void setExactWaitingTime(Double exactWaitingTime) {
        this.exactWaitingTime = exactWaitingTime;
    }

    public Double getMinWaitingTime() {
        return minWaitingTime;
    }

    public void setMinWaitingTime(Double minWaitingTime) {
        this.minWaitingTime = minWaitingTime;
    }

    public Double getMaxWaitingTime() {
        return maxWaitingTime;
    }

    public void setMaxWaitingTime(Double maxWaitingTime) {
        this.maxWaitingTime = maxWaitingTime;
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

    public boolean asExactValue() {
        if (exactWaitingTime != null) {
            return true;
        }
        return false;
    }

    public boolean asInterval() {
        if (minWaitingTime != null && maxWaitingTime != null) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (waitingTimeID != null ? waitingTimeID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WaitingTime)) {
            return false;
        }
        WaitingTime other = (WaitingTime) object;
        if ((this.waitingTimeID == null && other.waitingTimeID != null) || (this.waitingTimeID != null && !this.waitingTimeID.equals(other.waitingTimeID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.compguide.web.Persistence.Entities.WaitingTime[ waitingTimeID=" + waitingTimeID + " ]";
    }

}
