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
public class Pretplata {
    private Integer id;
    // Ovde je rec o Korisnik klasi pravljenoj za podsistem1 koja ima dodatna polja sem id, ali ona ce samo dobiti vrednost null.
    private Korisnik idKor;
    private Paket idPak;
    private Date datumvreme;
    private int cena;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    
    @Override
    public String toString() {
        return this.id + "\t" + this.idKor.getId() + "\t" + this.idPak.getId() + "\t" + this.datumvreme + "\t" + this.cena;
    }
}
