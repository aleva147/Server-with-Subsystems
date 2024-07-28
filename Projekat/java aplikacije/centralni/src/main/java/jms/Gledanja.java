/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jms;

import entities.Gledanje;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author PC
 */
public class Gledanja implements Serializable {
    List<Gledanje> listaGledanja;

    public List<Gledanje> getListaGledanja() {
        return listaGledanja;
    }

    public void setListaGledanja(List<Gledanje> listaGledanja) {
        this.listaGledanja = listaGledanja;
    }
}
