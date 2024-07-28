/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jms;

import entities.Korisnik;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author PC
 */
public class Korisnici implements Serializable {
    List<Korisnik> listaKorisnika;

    public List<Korisnik> getListaKorisnika() {
        return listaKorisnika;
    }
    public void setListaKorisnika(List<Korisnik> listaKorisnika) {
        this.listaKorisnika = listaKorisnika;
    }
}
