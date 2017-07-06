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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author António
 */
@Entity
@Table(name = "generatedtask")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GeneratedTask.findAll", query = "SELECT g FROM GeneratedTask g"),
    @NamedQuery(name = "GeneratedTask.findByIdgeneratedTask", query = "SELECT g FROM GeneratedTask g WHERE g.idgeneratedTask = :idgeneratedTask"),
    @NamedQuery(name = "GeneratedTask.findByIdplan", query = "SELECT g FROM GeneratedTask g WHERE g.idplan = :idplan"),
    @NamedQuery(name = "GeneratedTask.findByIdsync", query = "SELECT g FROM GeneratedTask g WHERE g.idsync = :idsync"),
    @NamedQuery(name = "GeneratedTask.findByIdentifier", query = "SELECT g FROM GeneratedTask g WHERE g.identifier = :identifier")})
public class GeneratedTask implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idgeneratedTask")
    private Integer idgeneratedTask;
    @Column(name = "idplan")
    private Integer idplan;
    @Size(max = 45)
    @Column(name = "idsync")
    private String idsync;
    @Size(max = 45)
    @Column(name = "identifier")
    private String identifier;
    @JoinColumn(name = "idguideexec", referencedColumnName = "idguideexec")
    @ManyToOne(optional = false)
    private GuideExec idguideexec;

    public GeneratedTask() {
    }

    public GeneratedTask(Integer idgeneratedTask) {
        this.idgeneratedTask = idgeneratedTask;
    }

    public Integer getIdgeneratedTask() {
        return idgeneratedTask;
    }

    public void setIdgeneratedTask(Integer idgeneratedTask) {
        this.idgeneratedTask = idgeneratedTask;
    }

    public Integer getIdplan() {
        return idplan;
    }

    public void setIdplan(Integer idplan) {
        this.idplan = idplan;
    }

    public String getIdsync() {
        return idsync;
    }

    public void setIdsync(String idsync) {
        this.idsync = idsync;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public GuideExec getIdguideexec() {
        return idguideexec;
    }

    public void setIdguideexec(GuideExec idguideexec) {
        this.idguideexec = idguideexec;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idgeneratedTask != null ? idgeneratedTask.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GeneratedTask)) {
            return false;
        }
        GeneratedTask other = (GeneratedTask) object;
        if ((this.idgeneratedTask == null && other.idgeneratedTask != null) || (this.idgeneratedTask != null && !this.idgeneratedTask.equals(other.idgeneratedTask))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.compguide.web.Persistence.Entities.GeneratedTask[ idgeneratedTask=" + idgeneratedTask + " ]";
    }

}
