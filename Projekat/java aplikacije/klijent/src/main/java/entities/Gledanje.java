/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.util.Date;

/**
 *
 * @author PC
 */
public class Gledanje {
    private Integer id;
    private Korisnik idKor;
    private Video idVid;
    private Date datumvreme;
    private int pocetak;
    private int trajanje;

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
    public String toString() {
        return id + "\t" + idKor.getId() + "\t" + idVid.getId() + "\t" + datumvreme + "\t" + pocetak + "\t" + trajanje;
    }
}
