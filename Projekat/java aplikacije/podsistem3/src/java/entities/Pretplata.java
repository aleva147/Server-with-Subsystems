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
@Table(name = "pretplata")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pretplata.findAll", query = "SELECT p FROM Pretplata p"),
    @NamedQuery(name = "Pretplata.findById", query = "SELECT p FROM Pretplata p WHERE p.id = :id"),
    @NamedQuery(name = "Pretplata.findByDatumvreme", query = "SELECT p FROM Pretplata p WHERE p.datumvreme = :datumvreme"),
    @NamedQuery(name = "Pretplata.findByCena", query = "SELECT p FROM Pretplata p WHERE p.cena = :cena")})
public class Pretplata implements Serializable {

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
    @Column(name = "cena")
    private int cena;
    @JoinColumn(name = "idKor", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Korisnik idKor;
    @JoinColumn(name = "idPak", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Paket idPak;

    public Pretplata() {
    }

    public Pretplata(Integer id) {
        this.id = id;
    }

    public Pretplata(Integer id, Date datumvreme, int cena) {
        this.id = id;
        this.datumvreme = datumvreme;
        this.cena = cena;
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

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    public Korisnik getIdKor() {
        return idKor;
    }

    public void setIdKor(Korisnik idKor) {
        this.idKor = idKor;
    }

    public Paket getIdPak() {
        return idPak;
    }

    public void setIdPak(Paket idPak) {
        this.idPak = idPak;
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
        if (!(object instanceof Pretplata)) {
            return false;
        }
        Pretplata other = (Pretplata) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "podsistem3.Pretplata[ id=" + id + " ]";
    }
    
}
