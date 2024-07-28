/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PC
 */
public class Zahtev implements Serializable {
    public enum ZahtevId {
        KREIRANJE_GRAD,
        KREIRANJE_KORISNIK,
        PROMENA_EMAIL_KORISNIK,
        PROMENA_MESTO_KORISNIK,
        KREIRANJE_KATEGORIJA,
        KREIRANJE_VIDEO,
        PROMENA_NAZIV_VIDEO,
        DODAVANJE_KATEGORIJA_VIDEO,
        KREIRANJE_PAKET,
        PROMENA_CENA_PAKET,
        KREIRANJE_PRETPLATA,
        KREIRANJE_GLEDANJE,
        KREIRANJE_OCENA,
        PROMENA_OCENA,
        BRISANJE_OCENA,
        BRISANJE_VIDEO,
        DOHVATANJE_SVE_MESTO,
        DOHVATANJE_SVE_KORISNIK,
        DOHVATANJE_SVE_KATEGORIJA,
        DOHVATANJE_SVE_VIDEO,
        DOHVATANJE_KATEGORIJA_VIDEO,
        DOHVATANJE_SVE_PAKET,
        DOHVATANJE_PRETPLATA_KORISNIK,
        DOHVATANJE_GLEDANJE_VIDEO,
        DOHVATANJE_OCENA_VIDEO,
    };
    
    ZahtevId zahtevId;
    int podsistemId;
    
    List<String> stringParams = new ArrayList<>();
    List<Integer> integerParams = new ArrayList<>();
    List<Character> characterParams = new ArrayList<>();
    

    public ZahtevId getZahtevId() {
        return zahtevId;
    }
    public void setZahtevId(ZahtevId zahtevId) {
        this.zahtevId = zahtevId;
    }

    public int getPodsistemId() {
        return podsistemId;
    }
    public void setPodsistemId(int podsistemId) {
        this.podsistemId = podsistemId;
    }
    
    public List<String> getStringParams() {
        return stringParams;
    }
    public void setStringParams(List<String> stringParams) {
        this.stringParams = stringParams;
    }

    public List<Integer> getIntegerParams() {
        return integerParams;
    }

    public void setIntegerParams(List<Integer> integerParams) {
        this.integerParams = integerParams;
    }

    public List<Character> getCharacterParams() {
        return characterParams;
    }

    public void setCharacterParams(List<Character> characterParams) {
        this.characterParams = characterParams;
    }
}
