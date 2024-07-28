/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jms;

import entities.Mesto;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author PC
 */
public class Mesta implements Serializable {
    List<Mesto> listaMesta;

    public List<Mesto> getListaMesta() {
        return listaMesta;
    }
    public void setListaMesta(List<Mesto> listaMesta) {
        this.listaMesta = listaMesta;
    }
}
