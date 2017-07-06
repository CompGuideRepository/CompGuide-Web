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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author António
 */
@Entity
@Table(name = "event")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Event.findAll", query = "SELECT e FROM Event e"),
    @NamedQuery(name = "Event.findByEventID", query = "SELECT e FROM Event e WHERE e.eventID = :eventID"),
    @NamedQuery(name = "Event.findByScheduleTaskID", query = "SELECT t FROM Event t WHERE t.scheduleTaskID = :scheduleTaskID"),
    @NamedQuery(name = "Event.findByTaskID", query = "SELECT t FROM Event t WHERE t.taskID = :taskID"),
    @NamedQuery(name = "Event.findByStartDate", query = "SELECT e FROM Event e WHERE e.startDate = :startDate"),
    @NamedQuery(name = "Event.findByEndDate", query = "SELECT e FROM Event e WHERE e.endDate = :endDate"),
    @NamedQuery(name = "Event.findByChecked", query = "SELECT e FROM Event e WHERE e.checked = :checked"),
    @NamedQuery(name = "Event.findByCanCheck", query = "SELECT e FROM Event e WHERE e.canCheck = :canCheck"),
    @NamedQuery(name = "Event.findByNumberOfRepetitions", query = "SELECT e FROM Event e WHERE e.numberOfRepetitions = :numberOfRepetitions"),
    @NamedQuery(name = "Event.findByRepetitionNumber", query = "SELECT e FROM Event e WHERE e.repetitionNumber = :repetitionNumber"),
    @NamedQuery(name = "Event.findByPostPonedDate", query = "SELECT e FROM Event e WHERE e.postPonedDate = :postPonedDate")})
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "EventID")
    private Integer eventID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "StartDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EndDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Checked")
    private boolean checked;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CanCheck")
    private boolean canCheck;
    @Column(name = "NumberOfRepetitions")
    private Integer numberOfRepetitions;
    @Column(name = "RepetitionNumber")
    private Integer repetitionNumber;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PostPonedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date postPonedDate;
    @OneToMany(mappedBy = "eventID")
    private List<Notification> notificationList;
    @JoinColumn(name = "TaskID", referencedColumnName = "idtask")
    @ManyToOne
    private Task taskID;
    @JoinColumn(name = "ScheduleTaskID", referencedColumnName = "ScheduleTaskID")
    @ManyToOne
    private ScheduleTask scheduleTaskID;
    @OneToMany(mappedBy = "currentEventID")
    private List<StopConditionSet> stopConditionSetList;

    public Event() {
    }

    public Event(Integer eventID) {
        this.eventID = eventID;
    }

    public Event(Integer eventID, Date startDate, Date endDate, boolean checked, boolean canCheck, Date postPonedDate) {
        this.eventID = eventID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.checked = checked;
        this.canCheck = canCheck;
        this.postPonedDate = postPonedDate;
    }

    public Event(Integer eventID, Date startDate, Date endDate, Date postPonedDate, boolean checked, boolean canCheck, Integer numberOfRepetitions, Integer repetitionNumber) {
        this.eventID = eventID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.postPonedDate = postPonedDate;
        this.checked = checked;
        this.canCheck = canCheck;
        this.numberOfRepetitions = numberOfRepetitions;
        this.repetitionNumber = repetitionNumber;
    }

    public Event(Integer eventID, Date startDate, Date endDate, boolean checked, boolean canCheck) {
        this.eventID = eventID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.checked = checked;
        this.canCheck = canCheck;
    }

    public Integer getEventID() {
        return eventID;
    }

    public void setEventID(Integer eventID) {
        this.eventID = eventID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean getChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean getCanCheck() {
        return canCheck;
    }

    public void setCanCheck(boolean canCheck) {
        this.canCheck = canCheck;
    }

    public Integer getNumberOfRepetitions() {
        return numberOfRepetitions;
    }

    public void setNumberOfRepetitions(Integer numberOfRepetitions) {
        this.numberOfRepetitions = numberOfRepetitions;
    }

    public Integer getRepetitionNumber() {
        return repetitionNumber;
    }

    public void setRepetitionNumber(Integer repetitionNumber) {
        this.repetitionNumber = repetitionNumber;
    }

    public Date getPostPonedDate() {
        return postPonedDate;
    }

    public void setPostPonedDate(Date postPonedDate) {
        this.postPonedDate = postPonedDate;
    }

    @XmlTransient
    public List<Notification> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }

    public Task getTaskID() {
        return taskID;
    }

    public void setTaskID(Task taskID) {
        this.taskID = taskID;
    }

    public ScheduleTask getScheduleTaskID() {
        return scheduleTaskID;
    }

    public void setScheduleTaskID(ScheduleTask scheduleTaskID) {
        this.scheduleTaskID = scheduleTaskID;
    }

    @XmlTransient
    public List<StopConditionSet> getStopConditionSetList() {
        return stopConditionSetList;
    }

    public void setStopConditionSetList(List<StopConditionSet> stopConditionSetList) {
        this.stopConditionSetList = stopConditionSetList;
    }

    public static boolean canCheckTask(Date endDate) {
        Date date = new Date();

        if (date.after(endDate)) {
            return true;
        }
        return false;
    }

    public boolean getIsCanCheck() {
        if (canCheck == true) {
            return false;
        }
        return true;
    }

    public static Event clone(Event e) {

        Event event = new Event(e.getEventID(), e.getStartDate(), e.getEndDate(),
                e.getPostPonedDate(), e.getChecked(), e.getCanCheck(),
                e.getNumberOfRepetitions(), e.getRepetitionNumber());

        ScheduleTask task = new ScheduleTask(e.getScheduleTaskID().getScheduleTaskID(),
                e.getScheduleTaskID().getCompletedDate(), e.getScheduleTaskID().getTaskType(),
                e.getScheduleTaskID().getTaskFormat(), e.getScheduleTaskID().getTaskDescription(),
                e.getScheduleTaskID().getTaskIdentifier(), e.getScheduleTaskID().getTaskPlan(),
                e.getScheduleTaskID().getNextTask(), e.getScheduleTaskID().getCompleted(),
                e.getScheduleTaskID().getCurrentNumberOfRepetitions(),
                e.getScheduleTaskID().getEndDate(), e.getScheduleTaskID().getStartDate(),
                e.getScheduleTaskID().getGuideExecID());

        event.setScheduleTaskID(task);

        return event;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eventID != null ? eventID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Event)) {
            return false;
        }
        Event other = (Event) object;
        if ((this.eventID == null && other.eventID != null) || (this.eventID != null && !this.eventID.equals(other.eventID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.compguide.web.Persistence.Entities.Event[ eventID=" + eventID + " ]";
    }

}
