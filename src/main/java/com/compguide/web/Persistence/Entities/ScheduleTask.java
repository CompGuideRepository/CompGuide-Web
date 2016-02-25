/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Persistence.Entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author António
 */
@Entity
@Table(name = "scheduletask")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ScheduleTask.findAll", query = "SELECT s FROM ScheduleTask s"),
    @NamedQuery(name = "ScheduleTask.findByScheduleTaskID", query = "SELECT s FROM ScheduleTask s WHERE s.scheduleTaskID = :scheduleTaskID"),
    @NamedQuery(name = "ScheduleTask.findByCompletedDate", query = "SELECT s FROM ScheduleTask s WHERE s.completedDate = :completedDate"),
    @NamedQuery(name = "ScheduleTask.findByGuideExecID", query = "SELECT s FROM ScheduleTask s WHERE s.guideExecID = :guideExecID"),
    @NamedQuery(name = "ScheduleTask.findByTaskType", query = "SELECT s FROM ScheduleTask s WHERE s.taskType = :taskType"),
    @NamedQuery(name = "ScheduleTask.findByTaskFormat", query = "SELECT s FROM ScheduleTask s WHERE s.taskFormat = :taskFormat"),
    @NamedQuery(name = "ScheduleTask.findByTaskIdentifier", query = "SELECT s FROM ScheduleTask s WHERE s.taskIdentifier = :taskIdentifier"),
    @NamedQuery(name = "ScheduleTask.findByTaskPlan", query = "SELECT s FROM ScheduleTask s WHERE s.taskPlan = :taskPlan"),
    @NamedQuery(name = "ScheduleTask.findByCompleted", query = "SELECT s FROM ScheduleTask s WHERE s.completed = :completed"),
    @NamedQuery(name = "ScheduleTask.findByCurrentNumberOfRepetitions", query = "SELECT s FROM ScheduleTask s WHERE s.currentNumberOfRepetitions = :currentNumberOfRepetitions"),
    @NamedQuery(name = "ScheduleTask.findByEndDate", query = "SELECT s FROM ScheduleTask s WHERE s.endDate = :endDate"),
    @NamedQuery(name = "ScheduleTask.findByStartDate", query = "SELECT s FROM ScheduleTask s WHERE s.startDate = :startDate")})
public class ScheduleTask implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ScheduleTaskID")
    private Integer scheduleTaskID;
    @Column(name = "CompletedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date completedDate;
    @Size(max = 45)
    @Column(name = "TaskType")
    private String taskType;
    @Size(max = 45)
    @Column(name = "TaskFormat")
    private String taskFormat;
    @Lob
    @Size(max = 65535)
    @Column(name = "TaskDescription")
    private String taskDescription;
    @Size(max = 45)
    @Column(name = "TaskIdentifier")
    private String taskIdentifier;
    @Size(max = 45)
    @Column(name = "TaskPlan")
    private String taskPlan;
    @Lob
    @Size(max = 65535)
    @Column(name = "NextTask")
    private String nextTask;
    @Column(name = "Completed")
    private Boolean completed;
    @Column(name = "CurrentNumberOfRepetitions")
    private Integer currentNumberOfRepetitions;
    @Column(name = "EndDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @Column(name = "StartDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @OneToMany(mappedBy = "scheduleTaskID")
    private List<TemporalElement> temporalElementList;
    @OneToMany(mappedBy = "parentTaskID")
    private List<ScheduleTask> scheduleTaskList;
    @JoinColumn(name = "ParentTaskID", referencedColumnName = "ScheduleTaskID")
    @ManyToOne
    private ScheduleTask parentTaskID;
    @JoinColumn(name = "GuideExecID", referencedColumnName = "idguideexec")
    @ManyToOne(optional = false)
    private GuideExec guideExecID;
    @OneToMany(mappedBy = "scheduleTaskID")
    private List<Event> eventList;
    @OneToMany(mappedBy = "scheduleTaskID")
    private List<Outcome> outcomeList;

    public ScheduleTask() {
    }

    public ScheduleTask(Integer scheduleTaskID) {
        this.scheduleTaskID = scheduleTaskID;
    }

    public ScheduleTask(String taskType, String taskFormat, String taskDescription, String taskIdentifier, String taskPlan, String nextTask, Boolean completed, Integer currentNumberOfRepetitions, Date endDate, Date startDate, GuideExec guideExecID) {
        this.taskType = taskType;
        this.taskFormat = taskFormat;
        this.taskDescription = taskDescription;
        this.taskIdentifier = taskIdentifier;
        this.taskPlan = taskPlan;
        this.nextTask = nextTask;
        this.completed = completed;
        this.currentNumberOfRepetitions = currentNumberOfRepetitions;
        this.endDate = endDate;
        this.startDate = startDate;
        this.guideExecID = guideExecID;
    }

    public ScheduleTask(Integer scheduleTaskID, Date completedDate, String taskType, String taskFormat, String taskDescription, String taskIdentifier, String taskPlan, String nextTask, Boolean completed, Integer currentNumberOfRepetitions, Date endDate, Date startDate, GuideExec guideExecID) {
        this.scheduleTaskID = scheduleTaskID;
        this.completedDate = completedDate;
        this.taskType = taskType;
        this.taskFormat = taskFormat;
        this.taskDescription = taskDescription;
        this.taskIdentifier = taskIdentifier;
        this.taskPlan = taskPlan;
        this.nextTask = nextTask;
        this.completed = completed;
        this.currentNumberOfRepetitions = currentNumberOfRepetitions;
        this.endDate = endDate;
        this.startDate = startDate;
        this.guideExecID = guideExecID;
    }

    public Integer getScheduleTaskID() {
        return scheduleTaskID;
    }

    public void setScheduleTaskID(Integer scheduleTaskID) {
        this.scheduleTaskID = scheduleTaskID;
    }

    public Date getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTaskFormat() {
        return taskFormat;
    }

    public void setTaskFormat(String taskFormat) {
        this.taskFormat = taskFormat;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskIdentifier() {
        return taskIdentifier;
    }

    public void setTaskIdentifier(String taskIdentifier) {
        this.taskIdentifier = taskIdentifier;
    }

    public String getTaskPlan() {
        return taskPlan;
    }

    public void setTaskPlan(String taskPlan) {
        this.taskPlan = taskPlan;
    }

    public String getNextTask() {
        return nextTask;
    }

    public void setNextTask(String nextTask) {
        this.nextTask = nextTask;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Integer getCurrentNumberOfRepetitions() {
        return currentNumberOfRepetitions;
    }

    public void setCurrentNumberOfRepetitions(Integer currentNumberOfRepetitions) {
        this.currentNumberOfRepetitions = currentNumberOfRepetitions;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @XmlTransient
    public List<TemporalElement> getTemporalElementList() {
        return temporalElementList;
    }

    public void setTemporalElementList(List<TemporalElement> temporalElementList) {
        this.temporalElementList = temporalElementList;
    }

    @XmlTransient
    public List<ScheduleTask> getScheduleTaskList() {
        return scheduleTaskList;
    }

    public void setScheduleTaskList(List<ScheduleTask> scheduleTaskList) {
        this.scheduleTaskList = scheduleTaskList;
    }

    public ScheduleTask getParentTaskID() {
        return parentTaskID;
    }

    public void setParentTaskID(ScheduleTask parentTaskID) {
        this.parentTaskID = parentTaskID;
    }

    public GuideExec getGuideExecID() {
        return guideExecID;
    }

    public void setGuideExecID(GuideExec guideExecID) {
        this.guideExecID = guideExecID;
    }

    @XmlTransient
    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    @XmlTransient
    public List<Outcome> getOutcomeList() {
        return outcomeList;
    }

    public void setOutcomeList(List<Outcome> outcomeList) {
        this.outcomeList = outcomeList;
    }

    public static ScheduleTask clone(ScheduleTask t) {

        ScheduleTask task = new ScheduleTask(t.getScheduleTaskID(),
                t.getCompletedDate(), t.getTaskType(),
                t.getTaskFormat(), t.getTaskDescription(),
                t.getTaskIdentifier(), t.getTaskPlan(),
                t.getNextTask(), t.getCompleted(),
                t.getCurrentNumberOfRepetitions(),
                t.getEndDate(), t.getStartDate(),
                t.getGuideExecID());

        return task;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (scheduleTaskID != null ? scheduleTaskID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ScheduleTask)) {
            return false;
        }
        ScheduleTask other = (ScheduleTask) object;
        if ((this.scheduleTaskID == null && other.scheduleTaskID != null) || (this.scheduleTaskID != null && !this.scheduleTaskID.equals(other.scheduleTaskID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.compguide.web.Persistence.Entities.ScheduleTask[ scheduleTaskID=" + scheduleTaskID + " ]";
    }

}
