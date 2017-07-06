/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Persistence.Entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author António
 */
@Entity
@Table(name = "autenticacao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Autentication.findAll", query = "SELECT a FROM Autentication a"),
    @NamedQuery(name = "Autentication.findById", query = "SELECT a FROM Autentication a WHERE a.id = :id"),
    @NamedQuery(name = "Autentication.findByDuracao", query = "SELECT a FROM Autentication a WHERE a.duracao = :duracao"),
    @NamedQuery(name = "Autentication.findByAuth", query = "SELECT a FROM Autentication a WHERE a.auth = :auth")})
public class Autentication implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "duracao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date duracao;
    @Size(max = 36)
    @Column(name = "auth")
    private String auth;
    @JoinColumn(name = "utilizador_id", referencedColumnName = "iduser")
    @ManyToOne(optional = false)
    private User utilizadorId;

    public Autentication() {
    }

    public Autentication(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDuracao() {
        return duracao;
    }

    public void setDuracao(Date duracao) {
        this.duracao = duracao;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public User getUtilizadorId() {
        return utilizadorId;
    }

    public void setUtilizadorId(User utilizadorId) {
        this.utilizadorId = utilizadorId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Autentication)) {
            return false;
        }
        Autentication other = (Autentication) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.compguide.web.Persistence.Entities.Autentication[ id=" + id + " ]";
    }

}
