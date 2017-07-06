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
import javax.persistence.Lob;
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
@Table(name = "guideline")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Guideline.findAll", query = "SELECT g FROM Guideline g"),
    @NamedQuery(name = "Guideline.findByIdguideline", query = "SELECT g FROM Guideline g WHERE g.idguideline = :idguideline"),
    @NamedQuery(name = "Guideline.findByDateOfCreation", query = "SELECT g FROM Guideline g WHERE g.dateOfCreation = :dateOfCreation"),
    @NamedQuery(name = "Guideline.findByDateOfUpdate", query = "SELECT g FROM Guideline g WHERE g.dateOfUpdate = :dateOfUpdate"),
    @NamedQuery(name = "Guideline.findByVersionNumber", query = "SELECT g FROM Guideline g WHERE g.versionNumber = :versionNumber"),
    @NamedQuery(name = "Guideline.findByGuidelineName", query = "SELECT g FROM Guideline g WHERE g.guidelineName = :guidelineName"),
    @NamedQuery(name = "Guideline.findByAuthorship", query = "SELECT g FROM Guideline g WHERE g.authorship = :authorship"),
    @NamedQuery(name = "Guideline.findByIdentifier", query = "SELECT g FROM Guideline g WHERE g.identifier = :identifier")})
public class Guideline implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idguideline")
    private Integer idguideline;
    @Column(name = "dateOfCreation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfCreation;
    @Column(name = "dateOfUpdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfUpdate;
    @Size(max = 45)
    @Column(name = "versionNumber")
    private String versionNumber;
    @Size(max = 128)
    @Column(name = "guidelineName")
    private String guidelineName;
    @Size(max = 128)
    @Column(name = "authorship")
    private String authorship;
    @Size(max = 255)
    @Column(name = "identifier")
    private String identifier;
    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idguideline")
    private List<GuideExec> guideExecList;

    public Guideline() {
    }

    public Guideline(Integer idguideline) {
        this.idguideline = idguideline;
    }

    public Integer getIdguideline() {
        return idguideline;
    }

    public void setIdguideline(Integer idguideline) {
        this.idguideline = idguideline;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public Date getDateOfUpdate() {
        return dateOfUpdate;
    }

    public void setDateOfUpdate(Date dateOfUpdate) {
        this.dateOfUpdate = dateOfUpdate;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getGuidelineName() {
        return guidelineName;
    }

    public void setGuidelineName(String guidelineName) {
        this.guidelineName = guidelineName;
    }

    public String getAuthorship() {
        return authorship;
    }

    public void setAuthorship(String authorship) {
        this.authorship = authorship;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public List<GuideExec> getGuideExecList() {
        return guideExecList;
    }

    public void setGuideExecList(List<GuideExec> guideExecList) {
        this.guideExecList = guideExecList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idguideline != null ? idguideline.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Guideline)) {
            return false;
        }
        Guideline other = (Guideline) object;
        if ((this.idguideline == null && other.idguideline != null) || (this.idguideline != null && !this.idguideline.equals(other.idguideline))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.compguide.web.Persistence.Entities.Guideline[ idguideline=" + idguideline + " ]";
    }

}
