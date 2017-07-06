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
@Table(name = "outcome")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Outcome.findAll", query = "SELECT o FROM Outcome o"),
    @NamedQuery(name = "Outcome.findByScheduleTaskID", query = "SELECT o FROM Outcome o WHERE o.scheduleTaskID = :scheduleTaskID"),
    @NamedQuery(name = "Outcome.findByOutcomeID", query = "SELECT o FROM Outcome o WHERE o.outcomeID = :outcomeID"),
    @NamedQuery(name = "Outcome.findByCanAsk", query = "SELECT o FROM Outcome o WHERE o.canAsk = :canAsk")})
public class Outcome implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "OutcomeID")
    private Integer outcomeID;
    @Column(name = "CanAsk")
    private Boolean canAsk;
    @JoinColumn(name = "ScheduleTaskID", referencedColumnName = "ScheduleTaskID")
    @ManyToOne
    private ScheduleTask scheduleTaskID;
    @JoinColumn(name = "TaskID", referencedColumnName = "idtask")
    @ManyToOne
    private Task taskID;
    @JoinColumn(name = "ConditionSetID", referencedColumnName = "idconditionset")
    @ManyToOne
    private ConditionSet conditionSetID;

    public Outcome() {
    }

    public Outcome(Integer outcomeID) {
        this.outcomeID = outcomeID;
    }

    public Outcome(Boolean canAsk, ScheduleTask scheduleTaskID) {
        this.canAsk = canAsk;
        this.scheduleTaskID = scheduleTaskID;
    }

    public Integer getOutcomeID() {
        return outcomeID;
    }

    public void setOutcomeID(Integer outcomeID) {
        this.outcomeID = outcomeID;
    }

    public Boolean getCanAsk() {
        return canAsk;
    }

    public void setCanAsk(Boolean canAsk) {
        this.canAsk = canAsk;
    }

    public ScheduleTask getScheduleTaskID() {
        return scheduleTaskID;
    }

    public void setScheduleTaskID(ScheduleTask scheduleTaskID) {
        this.scheduleTaskID = scheduleTaskID;
    }

    public Task getTaskID() {
        return taskID;
    }

    public void setTaskID(Task taskID) {
        this.taskID = taskID;
    }

    public ConditionSet getConditionSetID() {
        return conditionSetID;
    }

    public void setConditionSetID(ConditionSet conditionSetID) {
        this.conditionSetID = conditionSetID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (outcomeID != null ? outcomeID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Outcome)) {
            return false;
        }
        Outcome other = (Outcome) object;
        if ((this.outcomeID == null && other.outcomeID != null) || (this.outcomeID != null && !this.outcomeID.equals(other.outcomeID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.compguide.web.Persistence.Entities.Outcome[ outcomeID=" + outcomeID + " ]";
    }

}
