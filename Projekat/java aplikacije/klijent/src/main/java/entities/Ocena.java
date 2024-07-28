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
public class Ocena {
    protected OcenaPK ocenaPK;
    private int ocena;
    private Date datumvreme;
    private Korisnik korisnik;
    private Video video;

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
    public String toString() {
        return ocenaPK + "\t" + ocena + "\t" + datumvreme;
    }
}
