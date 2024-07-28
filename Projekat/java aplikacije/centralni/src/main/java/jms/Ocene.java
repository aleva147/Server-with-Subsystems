/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jms;

import entities.Ocena;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author PC
 */
public class Ocene implements Serializable {
    List<Ocena> listaOcena;

    public List<Ocena> getListaOcena() {
        return listaOcena;
    }

    public void setListaOcena(List<Ocena> listaOcena) {
        this.listaOcena = listaOcena;
    }
}
