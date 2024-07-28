/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

/**
 *
 * @author PC
 */
public class Korisnik {
    private Integer id;
    private String ime;
    private String email;
    private int godiste;
    private Character pol;
    private Mesto idMes;

    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGodiste() {
        return godiste;
    }

    public void setGodiste(int godiste) {
        this.godiste = godiste;
    }

    public Character getPol() {
        return pol;
    }

    public void setPol(Character pol) {
        this.pol = pol;
    }

    public Mesto getIdMes() {
        return idMes;
    }

    public void setIdMes(Mesto idMes) {
        this.idMes = idMes;
    }
    
    @Override
    public String toString() {
        return this.id + "\t" + this.ime + "\t" + this.email + "\t" + this.godiste + "\t" + this.pol + "\t" + this.idMes.getId();
    }
}
