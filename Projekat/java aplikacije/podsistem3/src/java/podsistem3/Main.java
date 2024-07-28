/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package podsistem3;

import jms.Gledanja;
import entities.Gledanje;
import entities.Korisnik;
import entities.Ocena;
import entities.OcenaPK;
import jms.Ocene;
import entities.Paket;
import jms.Paketi;
import entities.Pretplata;
import jms.Pretplate;
import entities.Video;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
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

    @Resource(lookup="queue13")
    private static Queue queue3;

    
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("podsistem3PU");
        EntityManager em = emf.createEntityManager();
        
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(queue3);
        
        System.out.println("Podsistem 3 zapocinje rad");
        
        
        while (true) {
            try {
                Message message = consumer.receive();
                Destination replyTo = message.getJMSReplyTo();
                
                ObjectMessage objMessage = (ObjectMessage)message;
                Zahtev zahtev = (Zahtev)objMessage.getObject();
                System.out.println("Obrada zahteva " + zahtev.getZahtevId());
                
                switch (zahtev.getZahtevId()) {
                    // Zahtevi od podsistema 1:
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
                        System.out.println("Podistem3 salje odgovor.");
                        producer.send(replyTo, odgovor);
                        System.out.println("Podistem3 poslao odgovor.");
                        break;
                    }
                    
                    // Zahtevi od podsistema 2:
                    case KREIRANJE_VIDEO: {
                        TextMessage odgovor = context.createTextMessage();
                        odgovor.setJMSCorrelationID(message.getJMSCorrelationID());
                        
                        int idVid = zahtev.getIntegerParams().get(0);
                        
                        Video video = new Video();
                        video.setId(idVid);
                        
                        em.getTransaction().begin();
                        em.persist(video);
                        em.getTransaction().commit();
                        em.clear();
                        
                        System.out.println("Uspesno kreiran video");
                        odgovor.setText("Uspesno kreiran video sa id " + idVid);
                        System.out.println("Podistem3 salje odgovor.");
                        producer.send(replyTo, odgovor);
                        System.out.println("Podistem3 poslao odgovor.");
                        break;
                    }                   
                    
                    case BRISANJE_VIDEO: {
                        TextMessage odgovor = context.createTextMessage();
                        odgovor.setJMSCorrelationID(message.getJMSCorrelationID());
                
                        int idVid = zahtev.getIntegerParams().get(0);
                        Video video = em.find(Video.class, idVid);
                        
                        em.getTransaction().begin();
                                
                        // Obrisi gledanja za video (i kod korisnika) i ocene za video (i kod korisnika)
                        List<Gledanje> gledanjaVidea = video.getGledanjeList();
                        for (Gledanje g : gledanjaVidea) {
                            List<Gledanje> gledanjaKorisnika = g.getIdKor().getGledanjeList();
                            gledanjaKorisnika.remove(g);
                            g.getIdKor().setGledanjeList(gledanjaKorisnika);

                            em.remove(g);
                        }
                        video.setGledanjeList(null);

                        List<Ocena> oceneVidea = video.getOcenaList();
                        for (Ocena o : oceneVidea) {
                            List<Ocena> oceneKorisnika = o.getKorisnik().getOcenaList();
                            oceneKorisnika.remove(o);
                            o.getKorisnik().setOcenaList(oceneKorisnika);

                            em.remove(o);
                        }
                        video.setOcenaList(null);

                        em.remove(video);

                        em.getTransaction().commit();
                        em.clear();
                        odgovor.setText("Uspesno obrisan video.");
                        
                        
                        producer.send(replyTo, odgovor);
                        System.out.println("Podistem3 poslao odgovor.");
                        break;
                    }   
                    
                    // Zahtevi od centralnog servera:
                    case KREIRANJE_PAKET: {
                        TextMessage odgovor = context.createTextMessage();
                        odgovor.setJMSCorrelationID(message.getJMSCorrelationID());
                        
                        Paket paket = new Paket();
                        Integer lastId = em.createQuery("SELECT MAX(p.id) FROM Paket p", Integer.class).getSingleResult();
                        lastId = lastId == null ? 0 : lastId;
                        paket.setId(lastId + 1);
                        paket.setCena(zahtev.getIntegerParams().get(0));
                        
                        em.getTransaction().begin();
                        em.persist(paket);
                        em.getTransaction().commit();
                        em.clear();
                        
                        System.out.println("Uspesno kreiran paket");
                        odgovor.setText("Uspesno kreiran paket sa cenom '" + zahtev.getIntegerParams().get(0) + "'");
                        System.out.println("Podistem3 salje odgovor.");
                        producer.send(replyTo, odgovor);
                        System.out.println("Podistem3 poslao odgovor.");
                        break;
                    }
                    
                    case PROMENA_CENA_PAKET: {
                        TextMessage odgovor = context.createTextMessage();
                        odgovor.setJMSCorrelationID(message.getJMSCorrelationID());
                
                        int idPak = zahtev.getIntegerParams().get(0);
                        int cena = zahtev.getIntegerParams().get(1);
                        
                        Paket paket = em.find(Paket.class, idPak);
                        if (paket == null) {
                            odgovor.setText("Nepostojeci idPak: " + idPak);
                        }
                        else {
                            if (paket.getCena() != cena) {
                                em.getTransaction().begin();
                                paket.setCena(cena);
                                em.getTransaction().commit();
                                em.clear();
                                
                                odgovor.setText("Uspesno promenjena cena na " + zahtev.getIntegerParams().get(1));
                            }
                            else
                                odgovor.setText("Nova cena se ne razlikuje od stare");
                        }
                        producer.send(replyTo, odgovor);
                        break;
                    }
                    
                    case KREIRANJE_PRETPLATA: {
                        TextMessage odgovor = context.createTextMessage();
                        odgovor.setJMSCorrelationID(message.getJMSCorrelationID());
                        
                        int idKor = zahtev.getIntegerParams().get(0);
                        int idPak = zahtev.getIntegerParams().get(1);
                        
                        Korisnik korisnik = em.find(Korisnik.class, idKor);
                        Paket paket = em.find(Paket.class, idPak);
                        if (korisnik == null) {
                            odgovor.setText("Nepostojeci idKor: " + idKor);
                        }
                        else if (paket == null) {
                            odgovor.setText("Nepostojeci idPak: " + idPak);
                        }
                        else {
                            Date sada = new Date();
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(sada);
                            calendar.add(Calendar.MONTH, -1);
                            Date preMesecDana = calendar.getTime();

                            // Provera da li korisnik ima jos uvek aktivnu pretplatu:
                            boolean imaAktivnuPretplatu = false;
                            List<Pretplata> pretplate = korisnik.getPretplataList();
                            for (Pretplata p : pretplate) {
                                if (p.getDatumvreme().after(preMesecDana)) {
                                    System.out.println("Korisnik ima i dalje aktivnu pretplatu.");
                                    odgovor.setText("Korisnik ima i dalje aktivnu pretplatu.");
                                    imaAktivnuPretplatu = true;
                                }
                            }
                            if (!imaAktivnuPretplatu) {
                                Pretplata pretplata = new Pretplata();
                                Integer lastId = em.createQuery("SELECT MAX(p.id) FROM Pretplata p", Integer.class).getSingleResult();
                                lastId = lastId == null ? 0 : lastId;
                                pretplata.setId(lastId + 1);
                                pretplata.setIdKor(korisnik);
                                pretplata.setIdPak(paket);
                                pretplata.setDatumvreme(sada);
                                pretplata.setCena(paket.getCena());

                                System.out.println("Uspesno kreirana pretplata.");
                                odgovor.setText("Uspesno kreirana pretplata.");

                                em.getTransaction().begin();

                                pretplate = pretplate == null ? new ArrayList<>() : pretplate;
                                pretplate.add(pretplata);
                                korisnik.setPretplataList(pretplate);

                                List<Pretplata> pretplatePaket = paket.getPretplataList();
                                pretplatePaket = pretplatePaket == null ? new ArrayList<>() : pretplatePaket;
                                pretplatePaket.add(pretplata);
                                paket.setPretplataList(pretplatePaket);

                                em.persist(pretplata);
    //                            em.persist(korisnik);
    //                            em.persist(paket);
                                em.getTransaction().commit();
                                em.clear();

                                System.out.println("Gotova transakcija.");
                            }
                        }
                        
                        System.out.println("Podistem3 salje odgovor.");
                        producer.send(replyTo, odgovor);
                        System.out.println("Podistem3 poslao odgovor.");
                        break;
                    }
                    
                    case KREIRANJE_GLEDANJE: {
                        TextMessage odgovor = context.createTextMessage();
                        odgovor.setJMSCorrelationID(message.getJMSCorrelationID());
                        
                        int idKor = zahtev.getIntegerParams().get(0);
                        int idVid = zahtev.getIntegerParams().get(1);
                        String datumvremeString = zahtev.getStringParams().get(0);
                        int pocetak = zahtev.getIntegerParams().get(2);
                        int trajanje = zahtev.getIntegerParams().get(3);
                        
                        Korisnik korisnik = em.find(Korisnik.class, idKor);
                        Video video = em.find(Video.class, idVid);
                        if (korisnik == null) {
                            odgovor.setText("Nepostojeci idKor: " + idKor);
                        }
                        else if (video == null) {
                            odgovor.setText("Nepostojeci idVid: " + idVid);
                        }
                        else {
                            Instant timestamp = Instant.parse(datumvremeString);
                            Date datumvreme = Date.from(timestamp);

                            Gledanje gledanje = new Gledanje();
                            Integer lastId = em.createQuery("SELECT MAX(g.id) FROM Gledanje g", Integer.class).getSingleResult();
                            lastId = lastId == null ? 0 : lastId;
                            gledanje.setId(lastId + 1);
                            gledanje.setIdKor(korisnik);
                            gledanje.setIdVid(video);
                            gledanje.setDatumvreme(datumvreme);
                            gledanje.setPocetak(pocetak);
                            gledanje.setTrajanje(trajanje);

                            em.getTransaction().begin();
                            em.persist(gledanje);
                            em.getTransaction().commit();
                            em.clear();
                            System.out.println("Uspesno kreirano gledanje.");
                            odgovor.setText("Uspesno kreirano gledanje.");
                        }
                        
                        System.out.println("Podistem3 salje odgovor.");
                        producer.send(replyTo, odgovor);
                        System.out.println("Podistem3 poslao odgovor.");
                        break;
                    }
                    
                    case KREIRANJE_OCENA: {
                        TextMessage odgovor = context.createTextMessage();
                        odgovor.setJMSCorrelationID(message.getJMSCorrelationID());
                        
                        int idKor = zahtev.getIntegerParams().get(0);
                        int idVid = zahtev.getIntegerParams().get(1);
                        int vrednost = zahtev.getIntegerParams().get(2);
                        
                        Korisnik korisnik = em.find(Korisnik.class, idKor);
                        Video video = em.find(Video.class, idVid);
                        if (korisnik == null) {
                            odgovor.setText("Nepostojeci idKor: " + idKor);
                        }
                        else if (video == null) {
                            odgovor.setText("Nepostojeci idVid: " + idVid);
                        }
                        else if (vrednost < 1 || vrednost > 5) {
                            odgovor.setText("Ocena mora biti izmedju 1 i 5");
                        }
                        else {
                            // Provera je li korisnik vec ocenio video:
                            boolean vecOcenio = false;
                            List<Ocena> oceneVidea = video.getOcenaList();
                            for (Ocena o : oceneVidea) {
                                if (o.getKorisnik().getId().intValue() == idKor) {
                                    odgovor.setText("Korisnik je vec ocenio ovaj video.");
                                    vecOcenio = true;
                                    break;
                                }
                            }
                            if (!vecOcenio) {
                                Date sada = new Date();

                                Ocena ocena = new Ocena(new OcenaPK(korisnik.getId(), video.getId()));
                                ocena.setKorisnik(korisnik);
                                ocena.setVideo(video);
                                ocena.setOcena(vrednost);
                                ocena.setDatumvreme(sada);

                                em.getTransaction().begin();

                                List<Ocena> oceneKorisnika = korisnik.getOcenaList();
                                oceneKorisnika = oceneKorisnika == null ? new ArrayList<>() : oceneKorisnika;
                                oceneKorisnika.add(ocena);
                                korisnik.setOcenaList(oceneKorisnika);

                                oceneVidea = oceneVidea == null ? new ArrayList<>() : oceneVidea;
                                oceneVidea.add(ocena);
                                video.setOcenaList(oceneVidea);

                                em.persist(ocena);
                                em.getTransaction().commit();
                                em.clear();
                                System.out.println("Uspesno kreirana ocena.");
                                odgovor.setText("Uspesno kreirana ocena.");
                            }
                        }
                        
                        System.out.println("Podistem3 salje odgovor.");
                        producer.send(replyTo, odgovor);
                        System.out.println("Podistem3 poslao odgovor.");
                        break;
                    }
                    
                    case PROMENA_OCENA: {
                        TextMessage odgovor = context.createTextMessage();
                        odgovor.setJMSCorrelationID(message.getJMSCorrelationID());
                
                        int idKor = zahtev.getIntegerParams().get(0);
                        int idVid = zahtev.getIntegerParams().get(1);
                        int vrednost = zahtev.getIntegerParams().get(2);
                        
                        Korisnik korisnik = em.find(Korisnik.class, idKor);
                        Video video = em.find(Video.class, idVid);
                        if (korisnik == null) {
                            odgovor.setText("Nepostojeci idKor: " + idKor);
                        }
                        else if (video == null) {
                            odgovor.setText("Nepostojeci idVid: " + idVid);
                        }
                        else if (vrednost < 1 || vrednost > 5) {
                            odgovor.setText("Ocena mora biti izmedju 1 i 5");
                        }
                        else {
                            // Provera da li je korsnik prethodno ocenio ovaj video ili ne.
                            List<Ocena> ocene = 
                                    em.createQuery("SELECT o FROM Ocena o WHERE o.ocenaPK.idKor = :idKor AND o.ocenaPK.idVid = :idVid", Ocena.class)
                                            .setParameter("idKor", idKor)
                                            .setParameter("idVid", idVid)
                                            .getResultList();
                            if (ocene == null || ocene.isEmpty()) {
                                odgovor.setText("Korisnik nije prethodno ocenio ovaj video");
                            }
                            else {
                                em.getTransaction().begin();
                                
                                Ocena ocena = ocene.get(0);
                                
                                // Da bi se videla promena u tekucoj sesiji:
                                List<Ocena> oceneKorisnik = korisnik.getOcenaList();
                                List<Ocena> oceneKorisnikNovo = new ArrayList<>();
                                for (Ocena o : oceneKorisnik) {
                                    if (o.getKorisnik().getId() == korisnik.getId().intValue() && o.getVideo().getId() == video.getId().intValue()) {
                                        o.setOcena(vrednost);
                                    }
                                    oceneKorisnikNovo.add(o);
                                }
                                korisnik.setOcenaList(oceneKorisnikNovo);
                                
                                List<Ocena> oceneVideo = korisnik.getOcenaList();
                                List<Ocena> oceneVideoNovo = new ArrayList<>();
                                for (Ocena o : oceneVideo) {
                                    if (o.getKorisnik().getId() == korisnik.getId().intValue() && o.getVideo().getId() == video.getId().intValue()) {
                                        o.setOcena(vrednost);
                                    }
                                    oceneVideoNovo.add(o);
                                }
                                video.setOcenaList(oceneVideoNovo);
                                
                                ocena.setOcena(vrednost);
                                
                                em.persist(ocena);
                                em.getTransaction().commit();
                                em.clear();
                                odgovor.setText("Uspesno promenjena ocena.");
                            }
                                
                        }
                        
                        System.out.println("Podistem3 salje odgovor.");
                        producer.send(replyTo, odgovor);
                        System.out.println("Podistem3 poslao odgovor.");
                        break;
                    }
                    
                    case BRISANJE_OCENA: {
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
                        else {
                            List<Ocena> ocene = 
                                    em.createQuery("SELECT o FROM Ocena o WHERE o.ocenaPK.idKor = :idKor AND o.ocenaPK.idVid = :idVid", Ocena.class)
                                            .setParameter("idKor", idKor)
                                            .setParameter("idVid", idVid)
                                            .getResultList();
                            if (ocene == null || ocene.isEmpty()) {
                                odgovor.setText("Korisnik nije prethodno ocenio ovaj video.");
                            }
                            else {
                                em.getTransaction().begin();
                                
                                Ocena ocena = ocene.get(0);
                                video.getOcenaList().remove(ocena);
                                korisnik.getOcenaList().remove(ocena);
                                em.remove(ocena);
                                
                                em.getTransaction().commit();
                                em.clear();
                                odgovor.setText("Uspesno obrisana ocena.");
                            }
                        }
                        
                        producer.send(replyTo, odgovor);
                        System.out.println("Podistem3 poslao odgovor.");
                        break;
                    }                
                    
                    case DOHVATANJE_SVE_PAKET: {
                        List<Paket> listaPaketa = em.createNamedQuery("Paket.findAll", Paket.class).getResultList();
                        
                        Paketi paketi = new Paketi();
                        paketi.setListaPaketa(listaPaketa);
                        ObjectMessage odgovorObjMsg = context.createObjectMessage(paketi);
                        odgovorObjMsg.setJMSCorrelationID(message.getJMSCorrelationID());
                        
                        // Debugging:
                        for (Paket p : listaPaketa) {
                            System.out.println(p.getId());
                        }
                        
                        producer.send(replyTo, odgovorObjMsg);
                        
                        System.out.println("Podistem3 poslao listu paketa centralnom serveru.");
                        break;
                    }
                    
                    case DOHVATANJE_PRETPLATA_KORISNIK: {
                        int idKor = zahtev.getIntegerParams().get(0);
                        
                        Korisnik korisnik = em.find(Korisnik.class, idKor);
                        if (korisnik != null) {
                            List<Pretplata> svePretplate = em.createNamedQuery("Pretplata.findAll", Pretplata.class).getResultList();
                            List<Pretplata> listaPretplata = new ArrayList<>();
                            for (Pretplata p : svePretplate) {
                                if (p.getIdKor().getId() == idKor) listaPretplata.add(p);
                            }

                            Pretplate pretplate = new Pretplate();
                            pretplate.setListaPretplata(listaPretplata);
                            ObjectMessage odgovorObjMsg = context.createObjectMessage(pretplate);
                            odgovorObjMsg.setJMSCorrelationID(message.getJMSCorrelationID());
                        
                            producer.send(replyTo, odgovorObjMsg);
                            System.out.println("Podistem3 poslao listu pretplata korisnika centralnom serveru.");
                        }
                        else {
                            System.out.println("Nepostojeci idKor.");
                            TextMessage odgovor = context.createTextMessage();
                            odgovor.setJMSCorrelationID(message.getJMSCorrelationID());
                            odgovor.setText("Nepostojeci idKor: " + idKor);
                            producer.send(replyTo, odgovor);
                        }
                        
                        break;
                    }
                    
                    case DOHVATANJE_GLEDANJE_VIDEO: {
                        int idVid = zahtev.getIntegerParams().get(0);
                        
                        Video video = em.find(Video.class, idVid);
                        if (video != null) {
                            Gledanja gledanja = new Gledanja();
                            List<Gledanje> svaGledanja = em.createNamedQuery("Gledanje.findAll", Gledanje.class).getResultList();
                            List<Gledanje> listaGledanja = new ArrayList<>();
                            for (Gledanje g : svaGledanja) {
                                if (g.getIdVid().getId() == idVid) listaGledanja.add(g);
                            }
                            
                            gledanja.setListaGledanja(listaGledanja);
                            ObjectMessage odgovorObjMsg = context.createObjectMessage(gledanja);
                            odgovorObjMsg.setJMSCorrelationID(message.getJMSCorrelationID());
                            producer.send(replyTo, odgovorObjMsg);
                            System.out.println("Podistem3 poslao listu gledanja videa centralnom serveru.");
                        }
                        else {
                            System.out.println("Nepostojeci idVid.");
                            TextMessage odgovor = context.createTextMessage();
                            odgovor.setJMSCorrelationID(message.getJMSCorrelationID());
                            odgovor.setText("Nepostojeci idVid: " + idVid);
                            producer.send(replyTo, odgovor);
                        }
                        
                        break;
                    }
                    
                    case DOHVATANJE_OCENA_VIDEO: {
                        int idVid = zahtev.getIntegerParams().get(0);
                        
                        Video video = em.find(Video.class, idVid);
                        if (video != null) {
                            List<Ocena> sveOcene = em.createNamedQuery("Ocena.findAll", Ocena.class).getResultList();
                            List<Ocena> listaOcena = new ArrayList<>();
                            for (Ocena o : sveOcene) {
                                if (o.getOcenaPK().getIdVid() == idVid) listaOcena.add(o);
                            }

                            Ocene ocene = new Ocene();
                            ocene.setListaOcena(listaOcena);
                            ObjectMessage odgovorObjMsg = context.createObjectMessage(ocene);
                            odgovorObjMsg.setJMSCorrelationID(message.getJMSCorrelationID());
                            
                            producer.send(replyTo, odgovorObjMsg);
                            System.out.println("Podistem3 poslao listu gledanja videa centralnom serveru.");
                        }
                        else {
                            System.out.println("Nepostojeci idVid.");
                            TextMessage odgovor = context.createTextMessage();
                            odgovor.setJMSCorrelationID(message.getJMSCorrelationID());
                            odgovor.setText("Nepostojeci idVid: " + idVid);
                            producer.send(replyTo, odgovor);
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
