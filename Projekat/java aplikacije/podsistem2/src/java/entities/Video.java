/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author PC
 */
@Entity
@Table(name = "video")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Video.findAll", query = "SELECT v FROM Video v"),
    @NamedQuery(name = "Video.findById", query = "SELECT v FROM Video v WHERE v.id = :id"),
    @NamedQuery(name = "Video.findByNaziv", query = "SELECT v FROM Video v WHERE v.naziv = :naziv"),
    @NamedQuery(name = "Video.findByTrajanje", query = "SELECT v FROM Video v WHERE v.trajanje = :trajanje"),
    @NamedQuery(name = "Video.findByDatumvreme", query = "SELECT v FROM Video v WHERE v.datumvreme = :datumvreme")})
public class Video implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "naziv")
    private String naziv;
    @Basic(optional = false)
    @NotNull
    @Column(name = "trajanje")
    private int trajanje;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datumvreme")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumvreme;
    @ManyToMany(mappedBy = "videoList")
    private List<Kategorija> kategorijaList;
    @JoinColumn(name = "idKor", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Korisnik idKor;

    public Video() {
    }

    public Video(Integer id) {
        this.id = id;
    }

    public Video(Integer id, String naziv, int trajanje, Date datumvreme) {
        this.id = id;
        this.naziv = naziv;
        this.trajanje = trajanje;
        this.datumvreme = datumvreme;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(int trajanje) {
        this.trajanje = trajanje;
    }

    public Date getDatumvreme() {
        return datumvreme;
    }

    public void setDatumvreme(Date datumvreme) {
        this.datumvreme = datumvreme;
    }

    @XmlTransient
    public List<Kategorija> getKategorijaList() {
        return kategorijaList;
    }

    public void setKategorijaList(List<Kategorija> kategorijaList) {
        this.kategorijaList = kategorijaList;
    }

    public Korisnik getIdKor() {
        return idKor;
    }

    public void setIdKor(Korisnik idKor) {
        this.idKor = idKor;
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
        if (!(object instanceof Video)) {
            return false;
        }
        Video other = (Video) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "podsistem2.Video[ id=" + id + " ]";
    }
    
}
