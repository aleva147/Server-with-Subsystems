/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jms;

import entities.Pretplata;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author PC
 */
public class Pretplate implements Serializable {
    List<Pretplata> listaPretplata;

    public List<Pretplata> getListaPretplata() {
        return listaPretplata;
    }

    public void setListaPretplata(List<Pretplata> listaPretplata) {
        this.listaPretplata = listaPretplata;
    }
}
