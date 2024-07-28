/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.klijent;

//import custom_classes.Korisnik;
import entities.Gledanje;
import entities.Kategorija;
import entities.Korisnik;
import entities.Mesto;
import entities.Ocena;
import entities.Paket;
import entities.Pretplata;
import entities.Video;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 *
 * @author PC
 */
public interface KlijentResource {
    @Headers({ "Accept: text/html" })
    @GET("resources/test")
    Call<String> test();
    
    @Headers({ "Accept: text/html" })
    @POST("resources/kreiranje_grad/{naziv}")
    Call<String> kreirajMesto(@Path("naziv") String naziv);
    
    @Headers({ "Accept: text/html" })
    @POST("resources/kreiranje_korisnik/")
    Call<String> kreirajKorisnika(
            @Query("ime") String ime, 
            @Query("email") String email, 
            @Query("godiste") Integer godiste, 
            @Query("pol") Character pol, 
            @Query("idMes") Integer idMes
    );
    
    @Headers({ "Accept: text/html" })
    @PUT("resources/promena_email")
    Call<String> promeniEmail(
            @Query("idKor") Integer idKor, 
            @Query("email") String email
    );
    
    @Headers({ "Accept: text/html" })
    @PUT("resources/promena_mesto")
    Call<String> promeniMesto(
            @Query("idKor") Integer idKor, 
            @Query("idMes") Integer idMes
    );
    
    @Headers({ "Accept: application/json" })
    @GET("resources/dohvatanje_mesta")
    Call<List<Mesto>> dohvatiMesta();
    
    @Headers({ "Accept: application/json" })
    @GET("resources/dohvatanje_korisnici")
    Call<List<Korisnik>> dohvatiKorisnici();
    
    @Headers({ "Accept: text/html" })
    @POST("resources/kreiranje_kategorija/{naziv}")
    Call<String> kreirajKategoriju(@Path("naziv") String naziv);
    
    @Headers({ "Accept: text/html" })
    @POST("resources/kreiranje_video")
    Call<String> kreirajVideo(
            @Query("naziv") String naziv,
            @Query("trajanje") Integer trajanje,
            @Query("idKor") Integer idKor
    );
    
    @Headers({ "Accept: text/html" })
    @PUT("resources/promena_naziv_video")
    Call<String> promeniNazivVideo(
            @Query("idVid") Integer idVid, 
            @Query("naziv") String naziv
    );
    
    @Headers({ "Accept: text/html" })
    @PUT("resources/dodela_kategorija")
    Call<String> dodeliKategoriju(
            @Query("idVid") Integer idVid, 
            @Query("idKat") Integer idKat
    );
    
    @Headers({ "Accept: text/html" })
    @DELETE("resources/brisanje_video")
    Call<String> obrisiVideo(
            @Query("idKor") Integer idKor, 
            @Query("idVid") Integer idVid
    );
    
    @Headers({ "Accept: application/json" })
    @GET("resources/dohvatanje_kategorija")
    Call<List<Kategorija>> dohvatiKategorije();
    
    @Headers({ "Accept: application/json" })
    @GET("resources/dohvatanje_videa")
    Call<List<Video>> dohvatiVidee();
    
    @Headers({ "Accept: application/json" })
    @POST("resources/dohvatanje_kategorija_video/{idVid}")
    Call<List<Kategorija>> dohvatiKategorijeVideo(@Path("idVid") Integer idVid);
    
    @Headers({ "Accept: text/html" })
    @POST("resources/kreiranje_paket/{cena}")
    Call<String> kreirajPaket(@Path("cena") Integer cena);
    
    @Headers({ "Accept: text/html" })
    @PUT("resources/promena_cena_paket")
    Call<String> promeniCenuPaketa(
            @Query("idPak") Integer idPak, 
            @Query("cena") Integer cena
    );
    
    @Headers({ "Accept: text/html" })
    @POST("resources/kreiranje_pretplata")
    Call<String> kreirajPretplatu(
            @Query("idKor") Integer idKor, 
            @Query("idPak") Integer idPak
    );
    
    @Headers({ "Accept: text/html" })
    @POST("resources/kreiranje_gledanje")
    Call<String> kreirajGledanje(
            @Query("idKor") Integer idKor, 
            @Query("idVid") Integer idVid, 
            @Query("datumvreme") String datumvremeString, 
            @Query("pocetak") Integer pocetak, 
            @Query("trajanje") Integer trajanje
    );
    
    @Headers({ "Accept: text/html" })
    @POST("resources/kreiranje_ocena")
    Call<String> kreirajOcenu(
            @Query("idKor") Integer idKor, 
            @Query("idVid") Integer idVid, 
            @Query("ocena") Integer vrednost 
    );
    
    @Headers({ "Accept: text/html" })
    @PUT("resources/promena_ocena")
    Call<String> promeniOcenu(
            @Query("idKor") Integer idKor, 
            @Query("idVid") Integer idVid,
            @Query("ocena") Integer vrednost
    );
    
    @Headers({ "Accept: text/html" })
    @DELETE("resources/brisanje_ocena")
    Call<String> obrisiOcenu(
            @Query("idKor") Integer idKor, 
            @Query("idVid") Integer idVid
    );
    
    @Headers({ "Accept: application/json" })
    @GET("resources/dohvatanje_paketa")
    Call<List<Paket>> dohvatiPakete();
    
    @Headers({ "Accept: application/json" })
    @GET("resources/dohvatanje_pretplata_korisnik/{idKor}")
    Call<List<Pretplata>> dohvatiPretplateKorisnika(@Path("idKor") Integer idKor);
    
    @Headers({ "Accept: application/json" })
    @GET("resources/dohvatanje_gledanja_video/{idVid}")
    Call<List<Gledanje>> dohvatiGledanjaVidea(@Path("idVid") Integer idVid);
    
    @Headers({ "Accept: application/json" })
    @GET("resources/dohvatanje_ocena_video/{idVid}")
    Call<List<Ocena>> dohvatiOceneVidea(@Path("idVid") Integer idVid);
}
