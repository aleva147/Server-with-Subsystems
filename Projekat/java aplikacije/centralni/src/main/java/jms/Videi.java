/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jms;

import entities.Video;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author PC
 */
public class Videi implements Serializable {
    List<Video> listaVidea;

    public List<Video> getListaVidea() {
        return listaVidea;
    }

    public void setListaVidea(List<Video> listaVidea) {
        this.listaVidea = listaVidea;
    }
}
