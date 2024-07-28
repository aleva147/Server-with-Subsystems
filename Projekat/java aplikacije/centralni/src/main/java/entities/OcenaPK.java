/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author PC
 */
@Embeddable
public class OcenaPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idKor")
    private int idKor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idVid")
    private int idVid;

    public OcenaPK() {
    }

    public OcenaPK(int idKor, int idVid) {
        this.idKor = idKor;
        this.idVid = idVid;
    }

    public int getIdKor() {
        return idKor;
    }

    public void setIdKor(int idKor) {
        this.idKor = idKor;
    }

    public int getIdVid() {
        return idVid;
    }

    public void setIdVid(int idVid) {
        this.idVid = idVid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idKor;
        hash += (int) idVid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OcenaPK)) {
            return false;
        }
        OcenaPK other = (OcenaPK) object;
        if (this.idKor != other.idKor) {
            return false;
        }
        if (this.idVid != other.idVid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "podsistem1.OcenaPK[ idKor=" + idKor + ", idVid=" + idVid + " ]";
    }
    
}
