/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jms;

import entities.Paket;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author PC
 */
public class Paketi implements Serializable {
    List<Paket> listaPaketa;

    public List<Paket> getListaPaketa() {
        return listaPaketa;
    }

    public void setListaPaketa(List<Paket> listaPaketa) {
        this.listaPaketa = listaPaketa;
    }
}
