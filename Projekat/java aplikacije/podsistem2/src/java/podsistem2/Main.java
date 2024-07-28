/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package podsistem2;

import entities.Korisnik;
import entities.Kategorija;
import jms.Kategorije;
import jms.Videi;
import entities.Video;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jms.Zahtev;

/**
 *
 * @author PC
 */
public class Main {
    @Resource(lookup="jms/__defaultConnectionFactory")
    private static ConnectionFactory connectionFactory;

    @Resource(lookup="queue12")
    private static Queue queue2;
    @Resource(lookup="queue13")
    private static Queue queue3;
    
    
    // Kada podsistem2 treba da posalje poruku podsistemu3 kako bi i on azurirao svoju tabelu.
    private static void propagirajZahtev(JMSContext context, JMSProducer producer, JMSConsumer consumer, Zahtev zahtev, String correlationID) {
        System.out.println("Podsistem 2 salje zahtev podsistemu " + zahtev.getPodsistemId());
        ObjectMessage objMsg = context.createObjectMessage(zahtev);
        try {
            objMsg.setJMSReplyTo(queue2);
            objMsg.setJMSCorrelationID(correlationID);
            producer.send(queue3, objMsg);
            
            Message responseMsg = consumer.receive();
            
            if (responseMsg instanceof TextMessage) {
                TextMessage responseTxtMsg = (TextMessage)responseMsg;
                String responseStr = responseTxtMsg.getText();

                // Debugging:
                System.out.println("Primljen odgovor: " + responseStr);
            }
            // Ovo se desi kad podsistem nije upaljen, a saljemo mu poruke.
            else {
                System.out.println("GRESKA: Primljen odgovor nije TextMessage ni ObjectMessage");
            }
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("podsistem2PU");
        EntityManager em = emf.createEntityManager();
        
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(queue2);
        
        System.out.println("Podsistem 2 zapocinje rad");
        
        
        while (true) {
            try {
                Message message = consumer.receive();
                Destination replyTo = message.getJMSReplyTo();
                
                ObjectMessage objMessage = (ObjectMessage)message;
                Zahtev zahtev = (Zahtev)objMessage.getObject();
                System.out.println("Obrada zahteva " + zahtev.getZahtevId());
                
                switch (zahtev.getZahtevId()) {
                    // Zahtevi od podsistema1:
                    case KREIRANJE_KORISNIK: {
                        TextMessage odgovor = context.createTextMessage();
                        odgovor.setJMSCorrelationID(message.getJMSCorrelationID());
                        
                        int idKor = zahtev.getIntegerParams().get(0);
                        
                        Korisnik korisnik = new Korisnik();
                        korisnik.setId(idKor);
                        
                        em.getTransaction().begin();
                        em.persist(korisnik);
                        em.getTransaction().commit();
                        em.clear();
                        
                        System.out.println("Uspesno kreiran korisnik");
                        odgovor.setText("Uspesno kreiran korisnik sa id " + idKor);
                        System.out.println("Podistem2 salje odgovor.");
                        producer.send(replyTo, odgovor);
                        System.out.println("Podistem2 poslao odgovor.");
                        break;
                    }
                    
                    // Zahtevi od centralnog servera:
                    case KREIRANJE_KATEGORIJA: {
                        TextMessage odgovor = context.createTextMessage();
                        odgovor.setJMSCorrelationID(message.getJMSCorrelationID());
                        
                        Kategorija kategorija = new Kategorija();
                        Integer lastId = em.createQuery("SELECT MAX(k.id) FROM Kategorija k", Integer.class).getSingleResult();
                        lastId = lastId == null ? 0 : lastId;
                        kategorija.setId(lastId + 1);
                        kategorija.setNaziv(zahtev.getStringParams().get(0));
                        kategorija.setVideoList(null);
                        
                        em.getTransaction().begin();
                        em.persist(kategorija);
                        em.getTransaction().commit();
                        em.clear();
                        
                        System.out.println("Uspesno kreirana kategorija");
                        odgovor.setText("Uspesno kreirana kategorija sa nazivom '" + zahtev.getStringParams().get(0) + "'");
                        System.out.println("Podistem2 salje odgovor.");
                        producer.send(replyTo, odgovor);
                        System.out.println("Podistem2 poslao odgovor.");
                        break;
                    }
                    
                    case KREIRANJE_VIDEO: {
                        TextMessage odgovor = context.createTextMessage();
                        odgovor.setJMSCorrelationID(message.getJMSCorrelationID());
                        
                        Korisnik korisnik = em.find(Korisnik.class, zahtev.getIntegerParams().get(1));
                        if (korisnik == null) {
                            System.out.println("Nepostojeci idKor.");
                            odgovor.setText("Nepostojeci idKor: " + zahtev.getIntegerParams().get(1));
                        }
                        else {
                            Date datum = new Date();
                            
                            Video video = new Video();
                            Integer lastId = em.createQuery("SELECT MAX(v.id) FROM Video v", Integer.class).getSingleResult();
                            lastId = lastId == null ? 0 : lastId;
                            video.setId(lastId + 1);
                            video.setNaziv(zahtev.getStringParams().get(0));
                            video.setTrajanje(zahtev.getIntegerParams().get(0));
                            video.setIdKor(korisnik);
                            video.setDatumvreme(datum);

                            em.getTransaction().begin();
                            em.persist(video);
                            em.getTransaction().commit();
                            em.clear();

                            System.out.println("Uspesno kreiran video");
                            odgovor.setText("Uspesno kreirana video sa nazivom '" + zahtev.getStringParams().get(0) + "'");
                            
                            // Propagiranje idVid do podsistema3:
                            Zahtev zahtevKreirajVideo = new Zahtev();
                            zahtevKreirajVideo.setZahtevId(Zahtev.ZahtevId.KREIRANJE_VIDEO);
                            List<Integer> parametriInt = new ArrayList<>();
                            parametriInt.add(video.getId());
                            zahtevKreirajVideo.setIntegerParams(parametriInt);
                            
                            zahtevKreirajVideo.setPodsistemId(3);
                            propagirajZahtev(context, producer, consumer, zahtevKreirajVideo, message.getJMSCorrelationID());
                        }
                        System.out.println("Podistem2 salje odgovor.");
                        producer.send(replyTo, odgovor);
                        System.out.println("Podistem2 poslao odgovor.");
                        
                        break;
                    }
                    
                    case PROMENA_NAZIV_VIDEO: {
                        TextMessage odgovor = context.createTextMessage();
                        odgovor.setJMSCorrelationID(message.getJMSCorrelationID());
                
                        int idVid = zahtev.getIntegerParams().get(0);
                        String naziv = zahtev.getStringParams().get(0);
                        
                        Video video = em.find(Video.class, idVid);
                        if (video == null) {
                            odgovor.setText("Nepostojeci idVid: " + idVid);
                        }
                        else {
                            if (!video.getNaziv().equals(naziv)) {
                                em.getTransaction().begin();
                                video.setNaziv(naziv);
                                em.getTransaction().commit();
                                em.clear();
                                
                                odgovor.setText("Uspesno promenjen naziv na " + zahtev.getStringParams().get(0));
                            }
                            else
                                odgovor.setText("Novi naziv se ne razlikuje od starog");
                        }
                        producer.send(replyTo, odgovor);
                        break;
                    }
                    
                    case DODAVANJE_KATEGORIJA_VIDEO: {
                        TextMessage odgovor = context.createTextMessage();
                        odgovor.setJMSCorrelationID(message.getJMSCorrelationID());
                
                        int idVid = zahtev.getIntegerParams().get(0);
                        int idKat = zahtev.getIntegerParams().get(1);
                        
                        Video video = em.find(Video.class, idVid);
                        Kategorija kategorija = em.find(Kategorija.class, idKat);
                        if (video == null) {
                            odgovor.setText("Nepostojeci idVid: " + idVid);
                        }
                        else if (kategorija == null) {
                            odgovor.setText("Nepostojeci idKat: " + idKat);
                        }
                        else {
                            boolean vecDodeljena = false;
                            List<Kategorija> kategorije = video.getKategorijaList();
                            if (kategorije == null) kategorije = new ArrayList<>();
                            for (Kategorija k : kategorije) {
                                if (k.getId() == idKat) {
                                    vecDodeljena = true;
                                    break;
                                }
                            }
                            if (!vecDodeljena) {
                                em.getTransaction().begin();
                                
                                List<Video> videi = kategorija.getVideoList();
                                if (videi == null) videi = new ArrayList<>();
                                videi.add(video);
                                kategorija.setVideoList(videi);
                                
                                kategorije.add(kategorija);
                                video.setKategorijaList(kategorije);
                                
                                em.getTransaction().commit();
                                em.clear();
                                
                                odgovor.setText("Uspesno dodata kategorija " + idKat + " snimku " + idVid);
                            }
                            else
                                odgovor.setText("Kategorija je vec dodeljena snimku");
                        }
                        producer.send(replyTo, odgovor);
                        break;
                    }
                    
                    case BRISANJE_VIDEO: {
                        TextMessage odgovor = context.createTextMessage();
                        odgovor.setJMSCorrelationID(message.getJMSCorrelationID());
                
                        int idKor = zahtev.getIntegerParams().get(0);
                        int idVid = zahtev.getIntegerParams().get(1);
                        
                        Korisnik korisnik = em.find(Korisnik.class, idKor);
                        Video video = em.find(Video.class, idVid);
                        if (korisnik == null) {
                            odgovor.setText("Nepostojeci idKor: " + idKor);
                        }
                        else if (video == null) {
                            odgovor.setText("Nepostojeci idVid: " + idVid);
                        }
                        else if (video.getIdKor().getId() != idKor) {
                            odgovor.setText("Korisnik nije vlasnik videa.");
                        }
                        else {
                            // Kontaktiraj podsistem3 da odradi brisanje u svojoj bazi
                            Zahtev zahtevObrisiVideo = new Zahtev();
                            zahtevObrisiVideo.setZahtevId(Zahtev.ZahtevId.BRISANJE_VIDEO);
                            List<Integer> parametriInt = new ArrayList<>();
                            parametriInt.add(idVid);
                            zahtevObrisiVideo.setIntegerParams(parametriInt);
                            
                            zahtevObrisiVideo.setPodsistemId(3);
                            propagirajZahtev(context, producer, consumer, zahtevObrisiVideo, message.getJMSCorrelationID());
                            
                            
                            em.getTransaction().begin();
                            
                            // Obrisi kod korisnika da je vlasnik videa i u kategoriji da joj video pripada.
                            List<Video> videiKorisnika = video.getIdKor().getVideoList();
                            videiKorisnika.remove(video);
                            video.getIdKor().setVideoList(videiKorisnika);
                            
                            List<Kategorija> kategorijeVidea = video.getKategorijaList();
                            for (Kategorija k : kategorijeVidea) {
                                List<Video> videiKategorije = k.getVideoList();
                                videiKategorije.remove(video);
                                k.setVideoList(videiKategorije);
                            }
                            
                            em.remove(video);

                            em.getTransaction().commit();
                            em.clear();
                            odgovor.setText("Uspesno obrisan video.");
                        }
                        
                        System.out.println("Podistem2 salje odgovor.");
                        producer.send(replyTo, odgovor);
                        System.out.println("Podistem2 poslao odgovor.");
                        break;
                    }
                    
                    case DOHVATANJE_SVE_KATEGORIJA: {
                        List<Kategorija> listaKategorija = em.createNamedQuery("Kategorija.findAll", Kategorija.class).getResultList();
                        
                        Kategorije kategorije = new Kategorije();
                        kategorije.setListaKategorija(listaKategorija);
                        ObjectMessage odgovorObjMsg = context.createObjectMessage(kategorije);
                        odgovorObjMsg.setJMSCorrelationID(message.getJMSCorrelationID());
                        
                        // Debugging:
                        for (Kategorija k : listaKategorija) {
                            System.out.println(k.getNaziv());
                        }
                        
                        producer.send(replyTo, odgovorObjMsg);
                        
                        System.out.println("Podistem2 poslao listu kategorija centralnom serveru.");
                        break;
                    }
                    
                    case DOHVATANJE_SVE_VIDEO: {
                        List<Video> listaVidea = em.createNamedQuery("Video.findAll", Video.class).getResultList();
                        
                        Videi videi = new Videi();
                        videi.setListaVidea(listaVidea);
                        ObjectMessage odgovorObjMsg = context.createObjectMessage(videi);
                        odgovorObjMsg.setJMSCorrelationID(message.getJMSCorrelationID());
                        
                        // Debugging:
                        for (Video v : listaVidea) {
                            System.out.println(v.getNaziv());
                        }
                        
                        producer.send(replyTo, odgovorObjMsg);
                        
                        System.out.println("Podistem2 poslao listu videa centralnom serveru.");
                        break;
                    }
                    
                    case DOHVATANJE_KATEGORIJA_VIDEO: {
                        int idVid = zahtev.getIntegerParams().get(0);
                        
                        Video video = em.find(Video.class, idVid);
                        if (video == null) {
                            System.out.println("Nepostojeci idVid.");
                            TextMessage odgovor = context.createTextMessage();
                            odgovor.setJMSCorrelationID(message.getJMSCorrelationID());
                            odgovor.setText("Nepostojeci idVid: " + idVid);
                            producer.send(replyTo, odgovor);
                        }
                        else {
                            List<Kategorija> sveKategorije = em.createNamedQuery("Kategorija.findAll", Kategorija.class).getResultList();
                            List<Kategorija> listaKategorija = new ArrayList<>();
                            for (Kategorija k : sveKategorije) {
                                List<Video> videi = k.getVideoList();
                                for (Video v : videi) {
                                    if (v.getId() == idVid) {
                                        listaKategorija.add(k);
                                        break;
                                    }
                                }
                            }

                            Kategorije kategorije = new Kategorije();
                            kategorije.setListaKategorija(listaKategorija);
                            ObjectMessage odgovorObjMsg = context.createObjectMessage(kategorije);
                            odgovorObjMsg.setJMSCorrelationID(message.getJMSCorrelationID());

                            // Debugging:
                            for (Kategorija k : listaKategorija) {
                                System.out.println(k.getNaziv());
                            }

                            producer.send(replyTo, odgovorObjMsg);
                            System.out.println("Podistem2 poslao listu kategorija centralnom serveru.");
                        }
                        break;
                    }
                    
                    default:
                        System.out.println("Neprepoznatljiv zahtev.");
                        break;
                }
                
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}