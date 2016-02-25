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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author António
 */
@Entity
@Table(name = "guideexec")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GuideExec.findAll", query = "SELECT g FROM GuideExec g"),
    @NamedQuery(name = "GuideExec.findByIdguideexec", query = "SELECT g FROM GuideExec g WHERE g.idguideexec = :idguideexec"),
    @NamedQuery(name = "GuideExec.findByUserCompletedGuidelines", query = "SELECT g FROM GuideExec g WHERE g.iduser = :iduser AND g.completed = :completed"),
    @NamedQuery(name = "GuideExec.findByStart", query = "SELECT g FROM GuideExec g WHERE g.start = :start"),
    @NamedQuery(name = "GuideExec.findByTime", query = "SELECT g FROM GuideExec g WHERE g.time = :time"),
    @NamedQuery(name = "GuideExec.findByCompleted", query = "SELECT g FROM GuideExec g WHERE g.completed = :completed")})
public class GuideExec implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idguideexec")
    private Integer idguideexec;
    @Column(name = "start")
    @Temporal(TemporalType.TIMESTAMP)
    private Date start;
    @Column(name = "time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    private String description;
    @Column(name = "completed")
    private Short completed;
    @Lob
    @Size(max = 65535)
    @Column(name = "nextTasks")
    private String nextTasks;
    @Lob
    @Size(max = 65535)
    @Column(name = "pendingTasks")
    private String pendingTasks;
    @JoinColumn(name = "idguideline", referencedColumnName = "idguideline")
    @ManyToOne(optional = false)
    private Guideline idguideline;
    @JoinColumn(name = "idpatient", referencedColumnName = "idpatient")
    @ManyToOne(optional = false)
    private Patient idpatient;
    @JoinColumn(name = "iduser", referencedColumnName = "iduser")
    @ManyToOne(optional = false)
    private User iduser;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "guideExecID")
    private List<ScheduleTask> scheduleTaskList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idguideexec")
    private List<GeneratedTask> generatedTaskList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idguideexec")
    private List<Task> taskList;

    public GuideExec() {
    }

    public GuideExec(Integer idguideexec) {
        this.idguideexec = idguideexec;
    }

    public Integer getIdguideexec() {
        return idguideexec;
    }

    public void setIdguideexec(Integer idguideexec) {
        this.idguideexec = idguideexec;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Short getCompleted() {
        return completed;
    }

    public void setCompleted(Short completed) {
        this.completed = completed;
    }

    public String getNextTasks() {
        return nextTasks;
    }

    public void setNextTasks(String nextTasks) {
        this.nextTasks = nextTasks;
    }

    public String getPendingTasks() {
        return pendingTasks;
    }

    public void setPendingTasks(String pendingTasks) {
        this.pendingTasks = pendingTasks;
    }

    public Guideline getIdguideline() {
        return idguideline;
    }

    public void setIdguideline(Guideline idguideline) {
        this.idguideline = idguideline;
    }

    public Patient getIdpatient() {
        return idpatient;
    }

    public void setIdpatient(Patient idpatient) {
        this.idpatient = idpatient;
    }

    public User getIduser() {
        return iduser;
    }

    public void setIduser(User iduser) {
        this.iduser = iduser;
    }

    @XmlTransient
    public List<ScheduleTask> getScheduleTaskList() {
        return scheduleTaskList;
    }

    public void setScheduleTaskList(List<ScheduleTask> scheduleTaskList) {
        this.scheduleTaskList = scheduleTaskList;
    }

    @XmlTransient
    public List<GeneratedTask> getGeneratedTaskList() {
        return generatedTaskList;
    }

    public void setGeneratedTaskList(List<GeneratedTask> generatedTaskList) {
        this.generatedTaskList = generatedTaskList;
    }

    @XmlTransient
    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idguideexec != null ? idguideexec.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GuideExec)) {
            return false;
        }
        GuideExec other = (GuideExec) object;
        if ((this.idguideexec == null && other.idguideexec != null) || (this.idguideexec != null && !this.idguideexec.equals(other.idguideexec))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.compguide.web.Persistence.Entities.GuideExec[ idguideexec=" + idguideexec + " ]";
    }

}
