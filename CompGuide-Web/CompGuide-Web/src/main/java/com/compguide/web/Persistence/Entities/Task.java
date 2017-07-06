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
import javax.persistence.CascadeType;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author António
 */
@Entity
@Table(name = "task")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Task.findAll", query = "SELECT t FROM Task t"),
    @NamedQuery(name = "Task.findByIdtask", query = "SELECT t FROM Task t WHERE t.idtask = :idtask"),
    @NamedQuery(name = "Task.findByIdguideexec", query = "SELECT t FROM Task t WHERE t.idguideexec = :idguideexec"),
    @NamedQuery(name = "Task.findByTime", query = "SELECT t FROM Task t WHERE t.time = :time"),
    @NamedQuery(name = "Task.findByTaskType", query = "SELECT t FROM Task t WHERE t.taskType = :taskType"),
    @NamedQuery(name = "Task.findByTaskFormat", query = "SELECT t FROM Task t WHERE t.taskFormat = :taskFormat"),
    @NamedQuery(name = "Task.findByTaskIdentifier", query = "SELECT t FROM Task t WHERE t.taskIdentifier = :taskIdentifier"),
    @NamedQuery(name = "Task.findByTaskPlan", query = "SELECT t FROM Task t WHERE t.taskPlan = :taskPlan"),
    @NamedQuery(name = "Task.findByCompleted", query = "SELECT t FROM Task t WHERE t.completed = :completed"),
    @NamedQuery(name = "Task.findByRepetitionValue", query = "SELECT t FROM Task t WHERE t.repetitionValue = :repetitionValue")})
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtask")
    private Integer idtask;
    @Basic(optional = false)
    @NotNull
    @Column(name = "time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    @Size(max = 45)
    @Column(name = "taskType")
    private String taskType;
    @Size(max = 45)
    @Column(name = "taskFormat")
    private String taskFormat;
    @Lob
    @Size(max = 65535)
    @Column(name = "taskDescription")
    private String taskDescription;
    @Size(max = 45)
    @Column(name = "taskIdentifier")
    private String taskIdentifier;
    @Size(max = 45)
    @Column(name = "taskPlan")
    private String taskPlan;
    @Lob
    @Size(max = 65535)
    @Column(name = "nextTask")
    private String nextTask;
    @Column(name = "completed")
    private Boolean completed;
    @Column(name = "repetitionValue")
    private Integer repetitionValue;
    @OneToMany(mappedBy = "taskID")
    private List<TemporalElement> temporalElementList;
    @OneToMany(mappedBy = "taskID")
    private List<Event> eventList;
    @OneToMany(mappedBy = "taskID")
    private List<Outcome> outcomeList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idtask")
    private List<NonMedication> nonMedicationList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idtask")
    private List<Observation> observationList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idtask")
    private List<Medication> medicationList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idtask")
    private List<Procedure> procedureList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idtask")
    private List<Exam> examList;
    @JoinColumn(name = "idguideexec", referencedColumnName = "idguideexec")
    @ManyToOne(optional = false)
    private GuideExec idguideexec;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idtask")
    private List<Formula> formulaList;

    public Task() {
    }

    public Task(Integer idtask) {
        this.idtask = idtask;
    }

    public Task(Integer idtask, Date time) {
        this.idtask = idtask;
        this.time = time;
    }

    public Task(Date time, String taskType, String taskFormat, String taskDescription, String taskIdentifier, String taskPlan, String nextTask, Boolean completed, Integer repetitionValue, GuideExec idguideexec) {
        this.time = time;
        this.taskType = taskType;
        this.taskFormat = taskFormat;
        this.taskDescription = taskDescription;
        this.taskIdentifier = taskIdentifier;
        this.taskPlan = taskPlan;
        this.nextTask = nextTask;
        this.completed = completed;
        this.repetitionValue = repetitionValue;
        this.idguideexec = idguideexec;
    }

    public Task(Integer idtask, Date time, String taskType, String taskFormat, String taskDescription, String taskIdentifier, String taskPlan, String nextTask, boolean completed, Integer repetitionValue, GuideExec idguideexec) {
        this.idtask = idtask;
        this.time = time;
        this.taskType = taskType;
        this.taskFormat = taskFormat;
        this.taskDescription = taskDescription;
        this.taskIdentifier = taskIdentifier;
        this.taskPlan = taskPlan;
        this.nextTask = nextTask;
        this.completed = completed;
        this.repetitionValue = repetitionValue;
        this.idguideexec = idguideexec;
    }

    public Integer getIdtask() {
        return idtask;
    }

    public void setIdtask(Integer idtask) {
        this.idtask = idtask;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
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

    public Integer getRepetitionValue() {
        return repetitionValue;
    }

    public void setRepetitionValue(Integer repetitionValue) {
        this.repetitionValue = repetitionValue;
    }

    @XmlTransient
    public List<TemporalElement> getTemporalElementList() {
        return temporalElementList;
    }

    public void setTemporalElementList(List<TemporalElement> temporalElementList) {
        this.temporalElementList = temporalElementList;
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

    @XmlTransient
    public List<NonMedication> getNonMedicationList() {
        return nonMedicationList;
    }

    public void setNonMedicationList(List<NonMedication> nonMedicationList) {
        this.nonMedicationList = nonMedicationList;
    }

    @XmlTransient
    public List<Observation> getObservationList() {
        return observationList;
    }

    public void setObservationList(List<Observation> observationList) {
        this.observationList = observationList;
    }

    @XmlTransient
    public List<Medication> getMedicationList() {
        return medicationList;
    }

    public void setMedicationList(List<Medication> medicationList) {
        this.medicationList = medicationList;
    }

    @XmlTransient
    public List<Procedure> getProcedureList() {
        return procedureList;
    }

    public void setProcedureList(List<Procedure> procedureList) {
        this.procedureList = procedureList;
    }

    @XmlTransient
    public List<Exam> getExamList() {
        return examList;
    }

    public void setExamList(List<Exam> examList) {
        this.examList = examList;
    }

    public GuideExec getIdguideexec() {
        return idguideexec;
    }

    public void setIdguideexec(GuideExec idguideexec) {
        this.idguideexec = idguideexec;
    }

    @XmlTransient
    public List<Formula> getFormulaList() {
        return formulaList;
    }

    public void setFormulaList(List<Formula> formulaList) {
        this.formulaList = formulaList;
    }

    public static Task clone(Task t) {

        Task task = new Task(t.getIdtask(),
                t.getTime(), t.getTaskType(),
                t.getTaskFormat(), t.getTaskDescription(),
                t.getTaskIdentifier(), t.getTaskPlan(),
                t.getNextTask(), t.getCompleted(),
                t.getRepetitionValue(), t.getIdguideexec());

        return task;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtask != null ? idtask.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Task)) {
            return false;
        }
        Task other = (Task) object;
        if ((this.idtask == null && other.idtask != null) || (this.idtask != null && !this.idtask.equals(other.idtask))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.compguide.web.Persistence.Entities.Task[ idtask=" + idtask + " ]";
    }

}
