/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PC
 */
@Entity
@Table(name = "gledanje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gledanje.findAll", query = "SELECT g FROM Gledanje g"),
    @NamedQuery(name = "Gledanje.findById", query = "SELECT g FROM Gledanje g WHERE g.id = :id"),
    @NamedQuery(name = "Gledanje.findByDatumvreme", query = "SELECT g FROM Gledanje g WHERE g.datumvreme = :datumvreme"),
    @NamedQuery(name = "Gledanje.findByPocetak", query = "SELECT g FROM Gledanje g WHERE g.pocetak = :pocetak"),
    @NamedQuery(name = "Gledanje.findByTrajanje", query = "SELECT g FROM Gledanje g WHERE g.trajanje = :trajanje")})
public class Gledanje implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datumvreme")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumvreme;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pocetak")
    private int pocetak;
    @Basic(optional = false)
    @NotNull
    @Column(name = "trajanje")
    private int trajanje;
    @JoinColumn(name = "idKor", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Korisnik idKor;
    @JoinColumn(name = "idVid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Video idVid;

    public Gledanje() {
    }

    public Gledanje(Integer id) {
        this.id = id;
    }

    public Gledanje(Integer id, Date datumvreme, int pocetak, int trajanje) {
        this.id = id;
        this.datumvreme = datumvreme;
        this.pocetak = pocetak;
        this.trajanje = trajanje;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDatumvreme() {
        return datumvreme;
    }

    public void setDatumvreme(Date datumvreme) {
        this.datumvreme = datumvreme;
    }

    public int getPocetak() {
        return pocetak;
    }

    public void setPocetak(int pocetak) {
        this.pocetak = pocetak;
    }

    public int getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(int trajanje) {
        this.trajanje = trajanje;
    }

    public Korisnik getIdKor() {
        return idKor;
    }

    public void setIdKor(Korisnik idKor) {
        this.idKor = idKor;
    }

    public Video getIdVid() {
        return idVid;
    }

    public void setIdVid(Video idVid) {
        this.idVid = idVid;
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
        if (!(object instanceof Gledanje)) {
            return false;
        }
        Gledanje other = (Gledanje) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "podsistem3.Gledanje[ id=" + id + " ]";
    }
    
}
