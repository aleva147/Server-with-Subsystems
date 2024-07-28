/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jms;

import entities.Kategorija;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author PC
 */
public class Kategorije implements Serializable {
    List<Kategorija> listaKategorija;

    public List<Kategorija> getListaKategorija() {
        return listaKategorija;
    }
    
    public void setListaKategorija(List<Kategorija> listaKategorija) {
        this.listaKategorija = listaKategorija;
    }
}
