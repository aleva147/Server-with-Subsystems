/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.centralni.resources;

import jms.Gledanja;
import entities.Gledanje;
import entities.Kategorija;
import jms.Kategorije;
import jms.Korisnici;
import entities.Korisnik;
import jms.Mesta;
import entities.Mesto;
import entities.Ocena;
import jms.Ocene;
import entities.Paket;
import jms.Paketi;
import entities.Pretplata;
import jms.Pretplate;
import jms.Videi;
import entities.Video;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import jms.PodsistemiJMS;
import jms.Zahtev;

/**
 *
 * @author PC
 */
@Path("")
public class Resources {
    @Inject
    PodsistemiJMS podsistemiJMS;
    
    // 0) TEST
    @GET
    @Path("/test")
    public String test() {
        return "Uspostavljena veza Korisnik-CentralniServer.";
    }
    
    // 1) KREIRANJE GRADA
    @POST
    @Path("/kreiranje_grad/{naziv}")
    public String kreirajMesto(@PathParam("naziv") String naziv){
        Zahtev zahtev = new Zahtev();
        zahtev.setZahtevId(Zahtev.ZahtevId.KREIRANJE_GRAD);
        zahtev.setPodsistemId(1);
        List<String> parametri = new ArrayList<>();
        parametri.add(naziv);
        zahtev.setStringParams(parametri);
        System.out.println("Centralni server salje zahtev podsistemu 1");
        
        String odgovor = (String)podsistemiJMS.delegiraj(zahtev);
        System.out.println("Centralni server primio odgovor: " + odgovor);
        return odgovor;
    }
    
    // 2) KREIRANJE KORISNIKA
    @POST
    @Path("/kreiranje_korisnik/")
    public String kreirajKorisnika(
            @QueryParam("ime") String ime, 
            @QueryParam("email") String email, 
            @QueryParam("godiste") Integer godiste, 
            @QueryParam("pol") Character pol,
            @QueryParam("idMes") Integer idMes
    ){
        Zahtev zahtev = new Zahtev();
        zahtev.setZahtevId(Zahtev.ZahtevId.KREIRANJE_KORISNIK);
        zahtev.setPodsistemId(1);
        List<String> stringParametri = new ArrayList<>(Arrays.asList(new String[]{ime, email}));
        List<Integer> integerParametri = new ArrayList<>(Arrays.asList(new Integer[]{godiste, idMes}));
        List<Character> characterParametri = new ArrayList<>(); characterParametri.add(pol);
        zahtev.setStringParams(stringParametri);
        zahtev.setIntegerParams(integerParametri);
        zahtev.setCharacterParams(characterParametri);
        System.out.println("Centralni server salje zahtev podsistemu 1");
        
        String odgovor = (String)podsistemiJMS.delegiraj(zahtev);
        System.out.println("Centralni server primio odgovor: " + odgovor);
        return odgovor;
    }
    
    // 3) PROMENA EMAIL KORISNIKA
    @PUT
    @Path("/promena_email")
    public String promeniEmail(
            @QueryParam("idKor") Integer idKor, 
            @QueryParam("email") String email
    ){
        Zahtev zahtev = new Zahtev();
        zahtev.setZahtevId(Zahtev.ZahtevId.PROMENA_EMAIL_KORISNIK);
        zahtev.setPodsistemId(1);
        List<Integer> integerParametri = new ArrayList<>(); integerParametri.add(idKor);
        List<String> stringParametri = new ArrayList<>(); stringParametri.add(email);
        zahtev.setIntegerParams(integerParametri);
        zahtev.setStringParams(stringParametri);
        System.out.println("Centralni server salje zahtev podsistemu 1");
        
        String odgovor = (String)podsistemiJMS.delegiraj(zahtev);
        System.out.println("Centralni server primio odgovor: " + odgovor);
        return odgovor;
    }
    
    // 4) PROMENA MESTA KORISNIKA
    @PUT
    @Path("/promena_mesto")
    public String promeniMesto(
            @QueryParam("idKor") Integer idKor, 
            @QueryParam("idMes") Integer idMes
    ){
        Zahtev zahtev = new Zahtev();
        zahtev.setZahtevId(Zahtev.ZahtevId.PROMENA_MESTO_KORISNIK);
        zahtev.setPodsistemId(1);
        List<Integer> integerParametri = new ArrayList<>(Arrays.asList(new Integer[]{idKor, idMes}));
        zahtev.setIntegerParams(integerParametri);
        System.out.println("Centralni server salje zahtev podsistemu 1");
        
        String odgovor = (String)podsistemiJMS.delegiraj(zahtev);
        System.out.println("Centralni server primio odgovor: " + odgovor);
        return odgovor;
    }
    
    // 17) DOHVATANJE SVIH MESTA
    @GET
    @Path("/dohvatanje_mesta")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Mesto> dohvatiMesta(){
        Zahtev zahtev = new Zahtev();
        zahtev.setZahtevId(Zahtev.ZahtevId.DOHVATANJE_SVE_MESTO);
        zahtev.setPodsistemId(1);
        System.out.println("Centralni server salje zahtev podsistemu 1");
        
        Mesta odgovor = (Mesta)podsistemiJMS.delegiraj(zahtev);
        // Neophodno zbog Lazy errora:
        for (Mesto m : odgovor.getListaMesta()) {
            m.setKorisnikList(null);
        }
        System.out.println("Centralni server primio odgovor: " + odgovor);
        return odgovor.getListaMesta();
    }
    
    // 18) DOHVATANJE SVIH KORISNIKA
    @GET
    @Path("/dohvatanje_korisnici")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Korisnik> dohvatiKorisnici(){
        Zahtev zahtev = new Zahtev();
        zahtev.setZahtevId(Zahtev.ZahtevId.DOHVATANJE_SVE_KORISNIK);
        zahtev.setPodsistemId(1);
        System.out.println("Centralni server salje zahtev podsistemu 1");
        
        Korisnici odgovor = (Korisnici)podsistemiJMS.delegiraj(zahtev);
        for (Korisnik k : odgovor.getListaKorisnika()) {
            k.setGledanjeList(null);
            k.setOcenaList(null);
            k.setPretplataList(null);
            k.setVideoList(null);
            k.getIdMes().setKorisnikList(null);
        }
        System.out.println("Centralni server primio odgovor: " + odgovor);
        return odgovor.getListaKorisnika();
    }
    
    
    
    // 5) KREIRANJE KATEGORIJE
    @POST
    @Path("/kreiranje_kategorija/{naziv}")
    public String kreirajKategoriju(@PathParam("naziv") String naziv){
        Zahtev zahtev = new Zahtev();
        zahtev.setZahtevId(Zahtev.ZahtevId.KREIRANJE_KATEGORIJA);
        zahtev.setPodsistemId(2);
        List<String> parametri = new ArrayList<>();
        parametri.add(naziv);
        zahtev.setStringParams(parametri);
        System.out.println("Centralni server salje zahtev podsistemu 2");
        
        String odgovor = (String)podsistemiJMS.delegiraj(zahtev);
        System.out.println("Centralni server primio odgovor: " + odgovor);
        return odgovor;
    }
    
    // 6) KREIRANJE VIDEO
    @POST
    @Path("/kreiranje_video")
    public String kreirajVideo(
            @QueryParam("naziv") String naziv,
            @QueryParam("trajanje") Integer trajanje,
            @QueryParam("idKor") Integer idKor
    ){
        Zahtev zahtev = new Zahtev();
        zahtev.setZahtevId(Zahtev.ZahtevId.KREIRANJE_VIDEO);
        zahtev.setPodsistemId(2);
        List<String> parametriStr = new ArrayList<>(); parametriStr.add(naziv);
        List<Integer> parametriInt = new ArrayList<>(Arrays.asList(new Integer[]{trajanje, idKor}));
        zahtev.setStringParams(parametriStr);
        zahtev.setIntegerParams(parametriInt);
        System.out.println("Centralni server salje zahtev podsistemu 2");
        
        String odgovor = (String)podsistemiJMS.delegiraj(zahtev);
        System.out.println("Centralni server primio odgovor: " + odgovor);
        return odgovor;
    }
    
    // 7) PROMENA NAZIVA VIDEA
    @PUT
    @Path("/promena_naziv_video")
    public String promeniNazivVideo(
            @QueryParam("idVid") Integer idVid, 
            @QueryParam("naziv") String naziv
    ){
        Zahtev zahtev = new Zahtev();
        zahtev.setZahtevId(Zahtev.ZahtevId.PROMENA_NAZIV_VIDEO);
        zahtev.setPodsistemId(2);
        List<Integer> parametriInt = new ArrayList<>(); parametriInt.add(idVid);
        List<String> parametriStr = new ArrayList<>(); parametriStr.add(naziv);
        zahtev.setIntegerParams(parametriInt);
        zahtev.setStringParams(parametriStr);
        System.out.println("Centralni server salje zahtev podsistemu 2");
        
        String odgovor = (String)podsistemiJMS.delegiraj(zahtev);
        System.out.println("Centralni server primio odgovor: " + odgovor);
        return odgovor;
    }
    
    // 8) DODELA KATEGORIJE SNIMKU
    @PUT
    @Path("/dodela_kategorija")
    public String dodeliKategoriju(
            @QueryParam("idVid") Integer idVid, 
            @QueryParam("idKat") Integer idKat
    ){
        Zahtev zahtev = new Zahtev();
        zahtev.setZahtevId(Zahtev.ZahtevId.DODAVANJE_KATEGORIJA_VIDEO);
        zahtev.setPodsistemId(2);
        List<Integer> parametriInt = new ArrayList<>(Arrays.asList(new Integer[]{idVid, idKat}));
        zahtev.setIntegerParams(parametriInt);
        System.out.println("Centralni server salje zahtev podsistemu 2");
        
        String odgovor = (String)podsistemiJMS.delegiraj(zahtev);
        System.out.println("Centralni server primio odgovor: " + odgovor);
        return odgovor;
    }
    
    // 16) BRISANJE VIDEA
    @DELETE
    @Path("/brisanje_video")
    public String obrisiVideo(
            @QueryParam("idKor") Integer idKor, 
            @QueryParam("idVid") Integer idVid
    ){
        Zahtev zahtev = new Zahtev();
        zahtev.setZahtevId(Zahtev.ZahtevId.BRISANJE_VIDEO);
        zahtev.setPodsistemId(2);
        List<Integer> parametriInt = new ArrayList<>(Arrays.asList(new Integer[]{idKor, idVid}));
        zahtev.setIntegerParams(parametriInt);
        System.out.println("Centralni server salje zahtev podsistemu 2");
        
        String odgovor = (String)podsistemiJMS.delegiraj(zahtev);
        System.out.println("Centralni server primio odgovor: " + odgovor);
        return odgovor;
    }
    
    // 19) DOHVATANJE SVIH KATEGORIJA
    @GET
    @Path("/dohvatanje_kategorija")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Kategorija> dohvatiKategorije(){
        Zahtev zahtev = new Zahtev();
        zahtev.setZahtevId(Zahtev.ZahtevId.DOHVATANJE_SVE_KATEGORIJA);
        zahtev.setPodsistemId(2);
        System.out.println("Centralni server salje zahtev podsistemu 2");
        
        Kategorije odgovor = (Kategorije)podsistemiJMS.delegiraj(zahtev);
        // Neophodno zbog Lazy errora:
        for (Kategorija k : odgovor.getListaKategorija()) {
            k.setVideoList(null);
        }
        System.out.println("Centralni server primio odgovor: " + odgovor);
        return odgovor.getListaKategorija();
    }
    
    // 20) DOHVATANJE SVIH VIDEA
    @GET
    @Path("/dohvatanje_videa")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Video> dohvatiVidee(){
        Zahtev zahtev = new Zahtev();
        zahtev.setZahtevId(Zahtev.ZahtevId.DOHVATANJE_SVE_VIDEO);
        zahtev.setPodsistemId(2);
        System.out.println("Centralni server salje zahtev podsistemu 2");
        
        Videi odgovor = (Videi)podsistemiJMS.delegiraj(zahtev);
        // Neophodno zbog Lazy errora:
        for (Video v : odgovor.getListaVidea()) {
            v.setGledanjeList(null);
            v.setKategorijaList(null);
            v.getIdKor().setGledanjeList(null);
            v.getIdKor().setOcenaList(null);
            v.getIdKor().setPretplataList(null);
            v.getIdKor().setVideoList(null);
        }
        System.out.println("Centralni server primio odgovor: " + odgovor);
        return odgovor.getListaVidea();
    }
    
    // 21) DOHVATANJE KATEGORIJA ZA VIDEO
    @POST
    @Path("/dohvatanje_kategorija_video/{idVid}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Kategorija> dohvatiKategorijeVideo(@PathParam("idVid") Integer idVid){
        Zahtev zahtev = new Zahtev();
        zahtev.setZahtevId(Zahtev.ZahtevId.DOHVATANJE_KATEGORIJA_VIDEO);
        zahtev.setPodsistemId(2);
        List<Integer> parametriInt = new ArrayList<>(); parametriInt.add(idVid);
        zahtev.setIntegerParams(parametriInt);
        System.out.println("Centralni server salje zahtev podsistemu 2");
        
        Object obj = podsistemiJMS.delegiraj(zahtev);
        if (obj instanceof String) return null; // Ako je bio zadat nepostojeci idVid.
        
        Kategorije odgovor = (Kategorije)obj;
        // Neophodno zbog Lazy errora:
        for (Kategorija k : odgovor.getListaKategorija()) {
            k.setVideoList(null);
        }
        System.out.println("Centralni server primio odgovor: " + odgovor);
        return odgovor.getListaKategorija();
    }
    
    
    // 9) KREIRANJE PAKETA
    @POST
    @Path("/kreiranje_paket/{cena}")
    public String kreirajPaket(@PathParam("cena") Integer cena){
        Zahtev zahtev = new Zahtev();
        zahtev.setZahtevId(Zahtev.ZahtevId.KREIRANJE_PAKET);
        zahtev.setPodsistemId(3);
        List<Integer> parametri = new ArrayList<>(); parametri.add(cena);
        zahtev.setIntegerParams(parametri);
        System.out.println("Centralni server salje zahtev podsistemu 3");
        
        String odgovor = (String)podsistemiJMS.delegiraj(zahtev);
        System.out.println("Centralni server primio odgovor: " + odgovor);
        return odgovor;
    }
    
    // 10) PROMENA CENA PAKETA
    @PUT
    @Path("/promena_cena_paket")
    public String promeniCenuPaketa(
            @QueryParam("idPak") Integer idPak, 
            @QueryParam("cena") Integer cena
    ){
        Zahtev zahtev = new Zahtev();
        zahtev.setZahtevId(Zahtev.ZahtevId.PROMENA_CENA_PAKET);
        zahtev.setPodsistemId(3);
        List<Integer> parametriInt = new ArrayList<>(Arrays.asList(new Integer[]{idPak, cena}));
        zahtev.setIntegerParams(parametriInt);
        System.out.println("Centralni server salje zahtev podsistemu 3");
        
        String odgovor = (String)podsistemiJMS.delegiraj(zahtev);
        System.out.println("Centralni server primio odgovor: " + odgovor);
        return odgovor;
    }
    
    // 11) KREIRANJE PRETPLATE
    @POST
    @Path("/kreiranje_pretplata")
    public String kreirajPretplatu(
            @QueryParam("idKor") Integer idKor, 
            @QueryParam("idPak") Integer idPak
    ){
        Zahtev zahtev = new Zahtev();
        zahtev.setZahtevId(Zahtev.ZahtevId.KREIRANJE_PRETPLATA);
        zahtev.setPodsistemId(3);
        List<Integer> parametri = new ArrayList<>(Arrays.asList(new Integer[]{idKor, idPak}));
        zahtev.setIntegerParams(parametri);
        System.out.println("Centralni server salje zahtev podsistemu 3");
        
        String odgovor = (String)podsistemiJMS.delegiraj(zahtev);
        System.out.println("Centralni server primio odgovor: " + odgovor);
        return odgovor;
    }
    
    // 12) KREIRANJE GLEDANJE (primer za datumvremeString: 2024-06-18T12:00:21Z)
    @POST
    @Path("/kreiranje_gledanje")
    public String kreirajGledanje(
            @QueryParam("idKor") Integer idKor, 
            @QueryParam("idVid") Integer idVid, 
            @QueryParam("datumvreme") String datumvremeString, 
            @QueryParam("pocetak") Integer pocetak, 
            @QueryParam("trajanje") Integer trajanje
    ){
        Zahtev zahtev = new Zahtev();
        zahtev.setZahtevId(Zahtev.ZahtevId.KREIRANJE_GLEDANJE);
        zahtev.setPodsistemId(3);
        List<Integer> parametriInt = new ArrayList<>(Arrays.asList(new Integer[]{idKor, idVid, pocetak, trajanje}));
        List<String> parametriStr = new ArrayList<>(); parametriStr.add(datumvremeString);
        zahtev.setIntegerParams(parametriInt);
        zahtev.setStringParams(parametriStr);
        System.out.println("Centralni server salje zahtev podsistemu 3");
        
        String odgovor = (String)podsistemiJMS.delegiraj(zahtev);
        System.out.println("Centralni server primio odgovor: " + odgovor);
        return odgovor;
    }
    
    // 13) KREIRANJE OCENA
    @POST
    @Path("/kreiranje_ocena")
    public String kreirajOcenu(
            @QueryParam("idKor") Integer idKor, 
            @QueryParam("idVid") Integer idVid,
            @QueryParam("ocena") Integer vrednost
    ){
        Zahtev zahtev = new Zahtev();
        zahtev.setZahtevId(Zahtev.ZahtevId.KREIRANJE_OCENA);
        zahtev.setPodsistemId(3);
        List<Integer> parametriInt = new ArrayList<>(Arrays.asList(new Integer[]{idKor, idVid, vrednost}));
        zahtev.setIntegerParams(parametriInt);
        System.out.println("Centralni server salje zahtev podsistemu 3");
        
        String odgovor = (String)podsistemiJMS.delegiraj(zahtev);
        System.out.println("Centralni server primio odgovor: " + odgovor);
        return odgovor;
    }
    
    // 14) PROMENA OCENA
    @PUT
    @Path("/promena_ocena")
    public String promeniOcenu(
            @QueryParam("idKor") Integer idKor, 
            @QueryParam("idVid") Integer idVid,
            @QueryParam("ocena") Integer vrednost
    ){
        Zahtev zahtev = new Zahtev();
        zahtev.setZahtevId(Zahtev.ZahtevId.PROMENA_OCENA);
        zahtev.setPodsistemId(3);
        List<Integer> parametriInt = new ArrayList<>(Arrays.asList(new Integer[]{idKor, idVid, vrednost}));
        zahtev.setIntegerParams(parametriInt);
        System.out.println("Centralni server salje zahtev podsistemu 3");
        
        String odgovor = (String)podsistemiJMS.delegiraj(zahtev);
        System.out.println("Centralni server primio odgovor: " + odgovor);
        return odgovor;
    }
    
    // 15) BRISANJE OCENE
    @DELETE
    @Path("/brisanje_ocena")
    public String obrisiOcenu(
            @QueryParam("idKor") Integer idKor, 
            @QueryParam("idVid") Integer idVid
    ){
        Zahtev zahtev = new Zahtev();
        zahtev.setZahtevId(Zahtev.ZahtevId.BRISANJE_OCENA);
        zahtev.setPodsistemId(3);
        List<Integer> parametriInt = new ArrayList<>(Arrays.asList(new Integer[]{idKor, idVid}));
        zahtev.setIntegerParams(parametriInt);
        System.out.println("Centralni server salje zahtev podsistemu 3");
        
        String odgovor = (String)podsistemiJMS.delegiraj(zahtev);
        System.out.println("Centralni server primio odgovor: " + odgovor);
        return odgovor;
    }
    
    // 22) DOHVATANJE SVIH PAKETA
    @GET
    @Path("/dohvatanje_paketa")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Paket> dohvatiPakete(){
        Zahtev zahtev = new Zahtev();
        zahtev.setZahtevId(Zahtev.ZahtevId.DOHVATANJE_SVE_PAKET);
        zahtev.setPodsistemId(3);
        System.out.println("Centralni server salje zahtev podsistemu 3");
        
        Paketi odgovor = (Paketi)podsistemiJMS.delegiraj(zahtev);
        // Neophodno zbog Lazy errora:
        for (Paket p : odgovor.getListaPaketa()) {
            p.setPretplataList(null);
        }
        System.out.println("Centralni server primio odgovor: " + odgovor);
        return odgovor.getListaPaketa();
    }
    
    // 23) DOHVATANJE SVIH PRETPLATA KORISNIKA
    @GET
    @Path("/dohvatanje_pretplata_korisnik/{idKor}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Pretplata> dohvatiPretplateKorisnika(@PathParam("idKor") Integer idKor){
        Zahtev zahtev = new Zahtev();
        zahtev.setZahtevId(Zahtev.ZahtevId.DOHVATANJE_PRETPLATA_KORISNIK);
        zahtev.setPodsistemId(3);
        System.out.println("Centralni server salje zahtev podsistemu 3");
        List<Integer> parametriInt = new ArrayList<>(); parametriInt.add(idKor);
        zahtev.setIntegerParams(parametriInt);
        
        Object obj = podsistemiJMS.delegiraj(zahtev);
        if (obj == null) return null; // Ako je bio zadat nepostojeci idKor.
        
        Pretplate odgovor = (Pretplate)obj;
        // Neophodno zbog Lazy errora:
        for (Pretplata p : odgovor.getListaPretplata()) {
            p.getIdKor().setGledanjeList(null);
            p.getIdKor().setOcenaList(null);
            p.getIdKor().setPretplataList(null);
            p.getIdKor().setVideoList(null);
            p.getIdPak().setPretplataList(null);
        }
        System.out.println("Centralni server primio odgovor: " + odgovor);
        return odgovor.getListaPretplata();
    }
    
    // 24) DOHVATANJE SVIH GLEDANJA VIDEA
    @GET
    @Path("/dohvatanje_gledanja_video/{idVid}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Gledanje> dohvatiGledanjaVidea(@PathParam("idVid") Integer idVid){
        Zahtev zahtev = new Zahtev();
        zahtev.setZahtevId(Zahtev.ZahtevId.DOHVATANJE_GLEDANJE_VIDEO);
        zahtev.setPodsistemId(3);
        System.out.println("Centralni server salje zahtev podsistemu 3");
        List<Integer> parametriInt = new ArrayList<>(); parametriInt.add(idVid);
        zahtev.setIntegerParams(parametriInt);
        
        Object obj = podsistemiJMS.delegiraj(zahtev);
        if (obj instanceof String) return null; // Ako je bio zadat nepostojeci idVid.
        
        Gledanja odgovor = (Gledanja)obj;
        // Neophodno zbog Lazy errora:
        for (Gledanje g : odgovor.getListaGledanja()) {
            g.getIdKor().setGledanjeList(null);
            g.getIdKor().setOcenaList(null);
            g.getIdKor().setPretplataList(null);
            g.getIdKor().setVideoList(null);
            g.getIdVid().setGledanjeList(null);
            g.getIdVid().setKategorijaList(null);
            g.getIdVid().setOcenaList(null);
        }
        System.out.println("Centralni server primio odgovor: " + odgovor);
        return odgovor.getListaGledanja();
    }
    
    // 25) DOHVATANJE SVIH OCENA VIDEA
    @GET
    @Path("/dohvatanje_ocena_video/{idVid}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Ocena> dohvatiOceneVidea(@PathParam("idVid") Integer idVid){
        Zahtev zahtev = new Zahtev();
        zahtev.setZahtevId(Zahtev.ZahtevId.DOHVATANJE_OCENA_VIDEO);
        zahtev.setPodsistemId(3);
        System.out.println("Centralni server salje zahtev podsistemu 3");
        List<Integer> parametriInt = new ArrayList<>(); parametriInt.add(idVid);
        zahtev.setIntegerParams(parametriInt);
        
        Object obj = podsistemiJMS.delegiraj(zahtev);
        if (obj == null) return null; // Ako je bio zadat nepostojeci idVid.
        
        Ocene odgovor = (Ocene)obj;
        // Neophodno zbog Lazy errora:
        for (Ocena o : odgovor.getListaOcena()) {
            o.getKorisnik().setGledanjeList(null);
            o.getKorisnik().setOcenaList(null);
            o.getKorisnik().setPretplataList(null);
            o.getKorisnik().setVideoList(null);
            o.getVideo().setGledanjeList(null);
            o.getVideo().setKategorijaList(null);
            o.getVideo().setOcenaList(null);
        }
        System.out.println("Centralni server primio odgovor: " + odgovor);
        return odgovor.getListaOcena();
    }
}
