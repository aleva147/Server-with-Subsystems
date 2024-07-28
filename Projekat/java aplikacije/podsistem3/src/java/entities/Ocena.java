/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "ocena")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ocena.findAll", query = "SELECT o FROM Ocena o"),
    @NamedQuery(name = "Ocena.findByIdKor", query = "SELECT o FROM Ocena o WHERE o.ocenaPK.idKor = :idKor"),
    @NamedQuery(name = "Ocena.findByIdVid", query = "SELECT o FROM Ocena o WHERE o.ocenaPK.idVid = :idVid"),
    @NamedQuery(name = "Ocena.findByOcena", query = "SELECT o FROM Ocena o WHERE o.ocena = :ocena"),
    @NamedQuery(name = "Ocena.findByDatumvreme", query = "SELECT o FROM Ocena o WHERE o.datumvreme = :datumvreme")})
public class Ocena implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected OcenaPK ocenaPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ocena")
    private int ocena;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datumvreme")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumvreme;
    @JoinColumn(name = "idKor", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Korisnik korisnik;
    @JoinColumn(name = "idVid", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Video video;

    public Ocena() {
    }

    public Ocena(OcenaPK ocenaPK) {
        this.ocenaPK = ocenaPK;
    }

    public Ocena(OcenaPK ocenaPK, int ocena, Date datumvreme) {
        this.ocenaPK = ocenaPK;
        this.ocena = ocena;
        this.datumvreme = datumvreme;
    }

    public Ocena(int idKor, int idVid) {
        this.ocenaPK = new OcenaPK(idKor, idVid);
    }

    public OcenaPK getOcenaPK() {
        return ocenaPK;
    }

    public void setOcenaPK(OcenaPK ocenaPK) {
        this.ocenaPK = ocenaPK;
    }

    public int getOcena() {
        return ocena;
    }

    public void setOcena(int ocena) {
        this.ocena = ocena;
    }

    public Date getDatumvreme() {
        return datumvreme;
    }

    public void setDatumvreme(Date datumvreme) {
        this.datumvreme = datumvreme;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ocenaPK != null ? ocenaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ocena)) {
            return false;
        }
        Ocena other = (Ocena) object;
        if ((this.ocenaPK == null && other.ocenaPK != null) || (this.ocenaPK != null && !this.ocenaPK.equals(other.ocenaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "podsistem3.Ocena[ ocenaPK=" + ocenaPK + " ]";
    }
    
}
