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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author António
 */
@Entity
@Table(name = "temporalelement")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TemporalElement.findAll", query = "SELECT t FROM TemporalElement t"),
    @NamedQuery(name = "TemporalElement.findByScheduleTaskID", query = "SELECT t FROM TemporalElement t WHERE t.scheduleTaskID = :scheduleTaskID"),
    @NamedQuery(name = "TemporalElement.findByTaskID", query = "SELECT t FROM TemporalElement t WHERE t.taskID = :taskID"),
    @NamedQuery(name = "TemporalElement.findByDurationID", query = "SELECT t FROM TemporalElement t WHERE t.durationID = :durationID"),
    @NamedQuery(name = "TemporalElement.findByWaitingTimeID", query = "SELECT t FROM TemporalElement t WHERE t.waitingTimeID = :waitingTimeID"),
    @NamedQuery(name = "TemporalElement.findByPeriodicityID", query = "SELECT t FROM TemporalElement t WHERE t.periodicityID = :periodicityID"),
    @NamedQuery(name = "TemporalElement.findByDurationIDAndWaitingTimeID", query = "SELECT t FROM TemporalElement t WHERE t.durationID = :durationID AND t.waitingTimeID = :waitingTimeID"),
    @NamedQuery(name = "TemporalElement.findByPeriodicityIDAndWaitingTimeID", query = "SELECT t FROM TemporalElement t WHERE t.periodicityID = :periodicityID AND t.waitingTimeID = :waitingTimeID"),
    @NamedQuery(name = "TemporalElement.findByTemporalElementID", query = "SELECT t FROM TemporalElement t WHERE t.temporalElementID = :temporalElementID")})
public class TemporalElement implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TemporalElementID")
    private Integer temporalElementID;
    @JoinColumn(name = "DurationID", referencedColumnName = "DurationID")
    @ManyToOne
    private Duration durationID;
    @JoinColumn(name = "WaitingTimeID", referencedColumnName = "WaitingTimeID")
    @ManyToOne
    private WaitingTime waitingTimeID;
    @JoinColumn(name = "TaskID", referencedColumnName = "idtask")
    @ManyToOne
    private Task taskID;
    @JoinColumn(name = "PeriodicityID", referencedColumnName = "PeriodicityID")
    @ManyToOne
    private Periodicity periodicityID;
    @JoinColumn(name = "ScheduleTaskID", referencedColumnName = "ScheduleTaskID")
    @ManyToOne
    private ScheduleTask scheduleTaskID;

    public TemporalElement() {
    }

    public TemporalElement(Integer temporalElementID) {
        this.temporalElementID = temporalElementID;
    }

    public Integer getTemporalElementID() {
        return temporalElementID;
    }

    public void setTemporalElementID(Integer temporalElementID) {
        this.temporalElementID = temporalElementID;
    }

    public Duration getDurationID() {
        return durationID;
    }

    public void setDurationID(Duration durationID) {
        this.durationID = durationID;
    }

    public WaitingTime getWaitingTimeID() {
        return waitingTimeID;
    }

    public void setWaitingTimeID(WaitingTime waitingTimeID) {
        this.waitingTimeID = waitingTimeID;
    }

    public Task getTaskID() {
        return taskID;
    }

    public void setTaskID(Task taskID) {
        this.taskID = taskID;
    }

    public Periodicity getPeriodicityID() {
        return periodicityID;
    }

    public void setPeriodicityID(Periodicity periodicityID) {
        this.periodicityID = periodicityID;
    }

    public ScheduleTask getScheduleTaskID() {
        return scheduleTaskID;
    }

    public void setScheduleTaskID(ScheduleTask scheduleTaskID) {
        this.scheduleTaskID = scheduleTaskID;
    }

    public boolean isDuration() {
        if (durationID != null) {
            return true;
        }

        return false;
    }

    public boolean isWaitingTime() {
        if (waitingTimeID != null) {
            return true;
        }

        return false;
    }

    public boolean isPeriodicity() {
        if (periodicityID != null) {
            return true;
        }

        return false;
    }

    public boolean isEmpty() {
        if (isDuration() || isPeriodicity() || isWaitingTime()) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (temporalElementID != null ? temporalElementID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TemporalElement)) {
            return false;
        }
        TemporalElement other = (TemporalElement) object;
        if ((this.temporalElementID == null && other.temporalElementID != null) || (this.temporalElementID != null && !this.temporalElementID.equals(other.temporalElementID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.compguide.web.Persistence.Entities.TemporalElement[ temporalElementID=" + temporalElementID + " ]";
    }

}
