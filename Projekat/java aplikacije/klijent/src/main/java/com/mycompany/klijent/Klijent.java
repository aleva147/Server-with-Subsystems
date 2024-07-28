/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.klijent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Gledanje;
import entities.Kategorija;
import entities.Korisnik;
import entities.Mesto;
import entities.Ocena;
import entities.Paket;
import entities.Pretplata;
import entities.Video;
import java.util.List;
import java.util.Scanner;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 *
 * @author PC
 */
public class Klijent {
    
    public static void main(String[] args) throws Exception {
        // Zbog nekog errora bilo neophodno:
        Gson gson = new GsonBuilder()
            .setLenient()
            .create();
        
        // Pravljenje retrofit objekta:
        Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(URI_SERVER)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
        KlijentResource klijentResource = retrofit.create(KlijentResource.class);
        
        // Koriscenje retrofit objekta za slanje zahteva ka serverskoj aplikaciji:
        Klijent klijent = new Klijent();
        
        // Test:
        System.out.println("TEST");
        String test = klijent.test(klijentResource);
        System.out.println(test);
        
        
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("\nUnesite broj zahtev koji zelite da se izvrsi: ");
            int idZahteva = Integer.parseInt(in.nextLine());
            
            switch (idZahteva) {
                // ZAHTEVI ZA PODSISTEM 1:
                case 1:
                    klijent.kreirajMesto(klijentResource, in);
                    break;
                case 2:                
                    klijent.kreirajKorisnika(klijentResource, in);
                    break;
                case 3:
                    klijent.promeniEmail(klijentResource, in);
                    break;
                case 4:
                    klijent.promeniMesto(klijentResource, in);
                    break;
                case 17:
                    klijent.dohvatiMesta(klijentResource);
                    break;
                case 18:
                    klijent.dohvatiKorisnici(klijentResource);
                    break;
                    
                // ZAHTEVI ZA PODSISTEM 2:
                case 5:
                    klijent.kreirajKategoriju(klijentResource, in);
                    break;
                case 6:
                    klijent.kreirajVideo(klijentResource, in);
                    break;
                case 7:
                    klijent.promeniNazivVideo(klijentResource, in);
                    break;
                case 8:
                    klijent.dodeliKategoriju(klijentResource, in);
                    break;
                case 19:
                    klijent.dohvatiKategorije(klijentResource);
                    break;
                case 20:
                    klijent.dohvatiVidee(klijentResource);
                    break;
                case 21:
                    klijent.dohvatiKategorijeVideo(klijentResource, in);
                    break;
                    
                // ZAHTEVI ZA PODSISTEM 3:
                case 9:
                    klijent.kreirajPaket(klijentResource, in);
                    break;
                case 10:
                    klijent.promeniCenuPaketa(klijentResource, in);
                    break;
                case 11:
                    klijent.kreirajPretplatu(klijentResource, in);
                    break;
                case 12:
                    klijent.kreirajGledanje(klijentResource, in);
                    break;
                case 13:
                    klijent.kreirajOcenu(klijentResource, in);
                    break;
                case 14:
                    klijent.promeniOcenu(klijentResource, in);
                    break;
                case 15:
                    klijent.obrisiOcenu(klijentResource, in);
                    break;
                case 16:
                    klijent.obrisiVideo(klijentResource, in);
                    break;
                case 22:
                    klijent.dohvatiPakete(klijentResource);
                    break;
                case 23:
                    klijent.dohvatiPretplateKorisnika(klijentResource, in);
                    break;
                case 24:
                    klijent.dohvatiGledanjaVidea(klijentResource, in);
                    break;
                case 25:
                    klijent.dohvatiOceneVidea(klijentResource, in);
                    break;
                
                default:
                    System.out.println("Uneti zahtev ne postoji.");
            }
            
        }
    }
    
    
    
    private static final String URI_SERVER = "http://localhost:8080/centralni/";
    
    
    private String test(KlijentResource klijentResource) throws Exception {
        Call<String> zahtev = klijentResource.test();
        return zahtev.execute().body();
    }
    
    private void kreirajMesto(KlijentResource klijentResource, Scanner in) throws Exception {
        System.out.println("ZAHTEV 1: Unesite naziv novog mesta");
        String naziv = in.nextLine();
                    
        Call<String> zahtev = klijentResource.kreirajMesto(naziv);
        String odgovorKreirajMesto = zahtev.execute().body();
        System.out.println(odgovorKreirajMesto);
    }
        
    private void kreirajKorisnika(KlijentResource klijentResource, Scanner in) throws Exception {
        System.out.println("ZAHTEV 2: Unesite ime, email, godiste, pol, idMes novog korisnika");
        String ime = in.nextLine(), email = in.nextLine();
        int godiste = Integer.parseInt(in.nextLine());
        char pol = in.nextLine().charAt(0);
        int idMes = Integer.parseInt(in.nextLine());        
        
        Call<String> zahtev = klijentResource.kreirajKorisnika(ime, email, godiste, pol, idMes);
        String odgovorKreirajKorisnika = zahtev.execute().body();
        System.out.println(odgovorKreirajKorisnika);
    }
        
    private void promeniEmail(KlijentResource klijentResource, Scanner in) throws Exception {
        System.out.println("ZAHTEV 3: Unesite idKor i novi email");
        int idKor = Integer.parseInt(in.nextLine());
        String email = in.nextLine();
                    
        Call<String> zahtev = klijentResource.promeniEmail(idKor, email);
        String odgovorPromeniEmail = zahtev.execute().body();
        System.out.println(odgovorPromeniEmail);
    }
    
    private void promeniMesto(KlijentResource klijentResource, Scanner in) throws Exception {
        System.out.println("ZAHTEV 4: Unesite idKor i novi idMes");
        int idKor = Integer.parseInt(in.nextLine()), idMes = Integer.parseInt(in.nextLine());
        
        Call<String> zahtev = klijentResource.promeniMesto(idKor, idMes);
        String odgovorPromeniMesto = zahtev.execute().body();
        System.out.println(odgovorPromeniMesto);
    }
    
    private void dohvatiMesta(KlijentResource klijentResource) throws Exception {
        System.out.println("ZAHTEV 17: Dohvatanje svih mesta");
        
        Call<List<Mesto>> zahtev = klijentResource.dohvatiMesta();
        List<Mesto> mesta = zahtev.execute().body();
        if (mesta != null)
            for (Mesto m : mesta) System.out.println(m);
    }
    
    private void dohvatiKorisnici(KlijentResource klijentResource) throws Exception {
        System.out.println("ZAHTEV 18: Dohvatanje svih korisnika");
                    
        Call<List<Korisnik>> zahtev = klijentResource.dohvatiKorisnici();
        List<Korisnik> korisnici = zahtev.execute().body();
        if (korisnici != null)
            for (Korisnik k : korisnici) System.out.println(k);
    }  
    
    
    
    private void kreirajKategoriju(KlijentResource klijentResource, Scanner in) throws Exception {
        System.out.println("ZAHTEV 5: Unesite naziv nove kategorije");
        String naziv = in.nextLine();
                    
        Call<String> zahtev = klijentResource.kreirajKategoriju(naziv);
        String odgovorKreirajKategoriju = zahtev.execute().body();
        System.out.println(odgovorKreirajKategoriju);
    }
    
    private void kreirajVideo(KlijentResource klijentResource, Scanner in) throws Exception {
        System.out.println("ZAHTEV 6: Unesite naziv, trajanje i idKor novog videa");
        String naziv = in.nextLine();
        int trajanje = Integer.parseInt(in.nextLine()), idKor = Integer.parseInt(in.nextLine());
        
        Call<String> zahtev = klijentResource.kreirajVideo(naziv, trajanje, idKor);
        String odgovorKreirajVideo = zahtev.execute().body();
        System.out.println(odgovorKreirajVideo);
    }
    
    private void promeniNazivVideo(KlijentResource klijentResource, Scanner in) throws Exception {
        System.out.println("ZAHTEV 7: Unesite idVid i novi naziv videa");
        int idVid = Integer.parseInt(in.nextLine());
        String naziv = in.nextLine();
        
        Call<String> zahtev = klijentResource.promeniNazivVideo(idVid, naziv);
        String odgovorPromeniNazivVideo = zahtev.execute().body();
        System.out.println(odgovorPromeniNazivVideo);
    }
    
    private void dodeliKategoriju(KlijentResource klijentResource, Scanner in) throws Exception {
        System.out.println("ZAHTEV 8: Unesite idVid i idKat koja mu se dodeljuje");
        int idVid = Integer.parseInt(in.nextLine());
        int idKat = Integer.parseInt(in.nextLine());
        
        Call<String> zahtev = klijentResource.dodeliKategoriju(idVid, idKat);
        String odgovorDodeliKategoriju = zahtev.execute().body();
        System.out.println(odgovorDodeliKategoriju);
    }
    
    private void obrisiVideo(KlijentResource klijentResource, Scanner in) throws Exception {
        System.out.println("ZAHTEV 16: Unesite svoj idKor i idVid za video koji brisete");
        
        Integer idKor = Integer.parseInt(in.nextLine());
        Integer idVid = Integer.parseInt(in.nextLine());
        Call<String> zahtev = klijentResource.obrisiVideo(idKor, idVid);
        String odgovorObrisiVideo = zahtev.execute().body();
        System.out.println(odgovorObrisiVideo);
    }
    
    private void dohvatiKategorije(KlijentResource klijentResource) throws Exception {
        System.out.println("ZAHTEV 19: Dohvatanje svih kategorija");
        
        Call<List<Kategorija>> zahtev = klijentResource.dohvatiKategorije();
        List<Kategorija> kategorije = zahtev.execute().body();
        if (kategorije != null)
            for (Kategorija k : kategorije) System.out.println(k);
    }
    
    private void dohvatiVidee(KlijentResource klijentResource) throws Exception {
        System.out.println("ZAHTEV 20: Dohvatanje svih videa");
        
        Call<List<Video>> zahtev = klijentResource.dohvatiVidee();
        List<Video> videi = zahtev.execute().body();
        if (videi != null)
            for (Video v : videi) System.out.println(v);
    }
    
    private void dohvatiKategorijeVideo(KlijentResource klijentResource, Scanner in) throws Exception {
        System.out.println("ZAHTEV 21: Unesite idVid cije kategorije dohvatate");
        int idVid = Integer.parseInt(in.nextLine());
        
        Call<List<Kategorija>> zahtev = klijentResource.dohvatiKategorijeVideo(idVid);
        List<Kategorija> kategorije = zahtev.execute().body();
        if (kategorije != null)
            for (Kategorija k : kategorije) System.out.println(k);
        else 
            System.out.println("Nepostojeci idVid: " + idVid);
    }
    
        
    
    private void kreirajPaket(KlijentResource klijentResource, Scanner in) throws Exception {
        System.out.println("ZAHTEV 9: Unesite cenu novog paketa");
        Integer cena = Integer.parseInt(in.nextLine());
                    
        Call<String> zahtev = klijentResource.kreirajPaket(cena);
        String odgovorKreirajPaket = zahtev.execute().body();
        System.out.println(odgovorKreirajPaket);
    }
    
    private void promeniCenuPaketa(KlijentResource klijentResource, Scanner in) throws Exception {
        System.out.println("ZAHTEV 10: Unesite idPak i novu cenu");
        Integer idPak = Integer.parseInt(in.nextLine());
        Integer cena = Integer.parseInt(in.nextLine());
                    
        Call<String> zahtev = klijentResource.promeniCenuPaketa(idPak, cena);
        String odgovorPromeniCenuPaketa = zahtev.execute().body();
        System.out.println(odgovorPromeniCenuPaketa);
    }
    
    private void kreirajPretplatu(KlijentResource klijentResource, Scanner in) throws Exception {
        System.out.println("ZAHTEV 11: Unesite idKor i idPak na koji se pretplacuje");
        Integer idKor = Integer.parseInt(in.nextLine());
        Integer idPak = Integer.parseInt(in.nextLine());
                    
        Call<String> zahtev = klijentResource.kreirajPretplatu(idKor, idPak);
        String odgovorKreirajPretplatu = zahtev.execute().body();
        System.out.println(odgovorKreirajPretplatu);
    }
    
    // Primer za datumvremeString: 2024-06-18T12:00:21Z
    private void kreirajGledanje(KlijentResource klijentResource, Scanner in) throws Exception {
        System.out.println("ZAHTEV 12: Unesite idKor, idVid, datumvreme, pocetak i trajanje gledanja");
        Integer idKor = Integer.parseInt(in.nextLine());
        Integer idVid = Integer.parseInt(in.nextLine());
        String datumvremeString = in.nextLine();
        Integer pocetak = Integer.parseInt(in.nextLine());
        Integer trajanje = Integer.parseInt(in.nextLine());
                    
        Call<String> zahtev = klijentResource.kreirajGledanje(idKor, idVid, datumvremeString, pocetak, trajanje);
        String odgovorKreirajGledanje = zahtev.execute().body();
        System.out.println(odgovorKreirajGledanje);
    }
    
    private void kreirajOcenu(KlijentResource klijentResource, Scanner in) throws Exception {
        System.out.println("ZAHTEV 13: Unesite idKor, idVid i ocenu");
        Integer idKor = Integer.parseInt(in.nextLine());
        Integer idVid = Integer.parseInt(in.nextLine());
        Integer ocena = Integer.parseInt(in.nextLine());
                    
        Call<String> zahtev = klijentResource.kreirajOcenu(idKor, idVid, ocena);
        String odgovorKreirajOcenu = zahtev.execute().body();
        System.out.println(odgovorKreirajOcenu);
    }
    
    private void promeniOcenu(KlijentResource klijentResource, Scanner in) throws Exception {
        System.out.println("ZAHTEV 14: Unesite idKor, idVid i novu ocenu");
        Integer idKor = Integer.parseInt(in.nextLine());
        Integer idVid = Integer.parseInt(in.nextLine());
        Integer ocena = Integer.parseInt(in.nextLine());
                    
        Call<String> zahtev = klijentResource.promeniOcenu(idKor, idVid, ocena);
        String odgovorPromeniOcenu = zahtev.execute().body();
        System.out.println(odgovorPromeniOcenu);
    }
    
    private void obrisiOcenu(KlijentResource klijentResource, Scanner in) throws Exception {
        System.out.println("ZAHTEV 15: Unesite idKor i idVid za ocenu koju brisete");
        
        Integer idKor = Integer.parseInt(in.nextLine());
        Integer idVid = Integer.parseInt(in.nextLine());
        Call<String> zahtev = klijentResource.obrisiOcenu(idKor, idVid);
        String odgovorObrisiOcenu = zahtev.execute().body();
        System.out.println(odgovorObrisiOcenu);
    }
    
    private void dohvatiPakete(KlijentResource klijentResource) throws Exception {
        System.out.println("ZAHTEV 22: Dohvatanje svih paketa");
        
        Call<List<Paket>> zahtev = klijentResource.dohvatiPakete();
        List<Paket> paketi = zahtev.execute().body();
        if (paketi != null)
            for (Paket p : paketi) System.out.println(p);
    }
    
    private void dohvatiPretplateKorisnika(KlijentResource klijentResource, Scanner in) throws Exception {
        System.out.println("ZAHTEV 23: Unesite idKor za koga dohvatate pretplate");
        Integer idKor = Integer.parseInt(in.nextLine());
                    
        Call<List<Pretplata>> zahtev = klijentResource.dohvatiPretplateKorisnika(idKor);
        List<Pretplata> pretplate = zahtev.execute().body();
        if (pretplate != null)
            for (Pretplata p : pretplate) System.out.println(p);
        else 
            System.out.println("Nepostojeci idKor: " + idKor);
    }
    
    private void dohvatiGledanjaVidea(KlijentResource klijentResource, Scanner in) throws Exception {
        System.out.println("ZAHTEV 24: Unesite idVid za koji dohvatate gledanja");
        Integer idVid = Integer.parseInt(in.nextLine());
                    
        Call<List<Gledanje>> zahtev = klijentResource.dohvatiGledanjaVidea(idVid);
        List<Gledanje> gledanja = zahtev.execute().body();
        if (gledanja != null)
            for (Gledanje g : gledanja) System.out.println(g);
        else 
            System.out.println("Nepostojeci idVid: " + idVid);
    }
    
    private void dohvatiOceneVidea(KlijentResource klijentResource, Scanner in) throws Exception {
        System.out.println("ZAHTEV 25: Unesite idVid za koji dohvatate ocene");
        Integer idVid = Integer.parseInt(in.nextLine());
                    
        Call<List<Ocena>> zahtev = klijentResource.dohvatiOceneVidea(idVid);
        List<Ocena> ocene = zahtev.execute().body();
        if (ocene != null)
            for (Ocena o : ocene) System.out.println(o);
        else 
            System.out.println("Nepostojeci idVid: " + idVid);
    }
}
