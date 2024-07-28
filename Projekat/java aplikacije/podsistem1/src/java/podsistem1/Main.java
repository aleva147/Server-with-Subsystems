/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package podsistem1;

import jms.Korisnici;
import entities.Korisnik;
import jms.Mesta;
import entities.Mesto;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
import jms.Zahtev;

/**
 *
 * @author PC
 */
public class Main {
    @Resource(lookup="jms/__defaultConnectionFactory")
    private static ConnectionFactory connectionFactory;

    @Resource(lookup="queue11")
    private static Queue queue1;
    @Resource(lookup="queue12")
    private static Queue queue2;
    @Resource(lookup="queue13")
    private static Queue queue3;
    
    
    // Kada podsistem1 treba da posalje poruku podsistemu2 ili podsistemu3 kako bi i oni azurirali svoje tabele.
    private static void propagirajZahtev(JMSContext context, JMSProducer producer, JMSConsumer consumer, Zahtev zahtev, String correlationID) {
        System.out.println("Podsistem 1 salje zahtev podsistemu " + zahtev.getPodsistemId());
        ObjectMessage objMessage = context.createObjectMessage(zahtev);
        try {
            objMessage.setJMSReplyTo(queue1);
            objMessage.setJMSCorrelationID(correlationID);
            if (zahtev.getPodsistemId() == 2) 
                producer.send(queue2, objMessage);
            else 
                producer.send(queue3, objMessage);
            
            Message responseMsg = consumer.receive();
            
            if (responseMsg instanceof TextMessage) {
                TextMessage responseTxtMsg = (TextMessage)responseMsg;
                String responseStr = responseTxtMsg.getText();

                // Debugging:
                System.out.println("Primljen odgovor: " + responseStr);
            }
            // Ovo se desi kad odredisni podsistem nije upaljen, a saljemo mu poruke.
            else {
                System.out.println("GRESKA: Primljen odgovor nije TextMessage ni ObjectMessage");
            }
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("podsistem1PU");
        EntityManager em = emf.createEntityManager();
        
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(queue1);
        
        System.out.println("Podsistem 1 zapocinje rad");
        
        
        while (true) {
            try {
                Message message = consumer.receive();
                Destination replyTo = message.getJMSReplyTo();
                
                ObjectMessage objMessage = (ObjectMessage)message;
                Zahtev zahtev = (Zahtev)objMessage.getObject();
                System.out.println("Obrada zahteva " + zahtev.getZahtevId());
                
                switch (zahtev.getZahtevId()) {
                    case KREIRANJE_GRAD: {
                        TextMessage odgovor = context.createTextMessage();
                        odgovor.setJMSCorrelationID(message.getJMSCorrelationID());
                        
                        Mesto mesto = new Mesto();
                        Integer lastId = em.createQuery("SELECT MAX(m.id) FROM Mesto m", Integer.class).getSingleResult();
                        lastId = lastId == null ? 0 : lastId;
                        mesto.setId(lastId + 1);
                        mesto.setNaziv(zahtev.getStringParams().get(0));
                        
                        em.getTransaction().begin();
                        em.persist(mesto);
                        em.getTransaction().commit();
                        em.clear();
                        
                        System.out.println("Uspesno kreirano mesto");
                        odgovor.setText("Uspesno kreirano mesto sa nazivom '" + zahtev.getStringParams().get(0) + "'");
                        System.out.println("Podistem1 salje odgovor.");
                        producer.send(replyTo, odgovor);
                        System.out.println("Podistem1 poslao odgovor.");
                        break;
                    }
                    
                    case KREIRANJE_KORISNIK: {
                        TextMessage odgovor = context.createTextMessage();
                        odgovor.setJMSCorrelationID(message.getJMSCorrelationID());
                
                        Mesto mesto = em.find(Mesto.class, zahtev.getIntegerParams().get(1));
                        if (mesto == null) {
                            System.out.println("Nepostojeci idMes.");
                            odgovor.setText("Nepostojeci idMes: " + zahtev.getIntegerParams().get(1));
                        }
                        else {
                            Korisnik korisnik = new Korisnik();
                            Integer lastId = em.createQuery("SELECT MAX(k.id) FROM Korisnik k", Integer.class).getSingleResult();
                            lastId = lastId == null ? 0 : lastId;
                            korisnik.setId(lastId + 1);
                            korisnik.setIme(zahtev.getStringParams().get(0));
                            korisnik.setEmail(zahtev.getStringParams().get(1));
                            korisnik.setGodiste(zahtev.getIntegerParams().get(0));
                            korisnik.setPol(zahtev.getCharacterParams().get(0));
                            korisnik.setIdMes(mesto);

                            em.getTransaction().begin();

                            // Azuriranje liste svih korisnika koji zive u datom mestu
                            // (kako bi pri dohvatanju ove liste promena koju smo napravili bila vidljiva bez ponovnog pokretanja servera)
                            List<Korisnik> korisnici = mesto.getKorisnikList();
                            korisnici = korisnici == null ? new ArrayList<>() : korisnici;
                            korisnici.add(korisnik);
                            
                            em.persist(korisnik);
                            em.getTransaction().commit();
                            em.clear();

                            odgovor.setText("Uspesno kreiran korisnik");
                            
                            // Prosledi idKor podsistemu2 i podsistemu3
                            Zahtev zahtevKreirajKorisnika = new Zahtev();
                            zahtevKreirajKorisnika.setZahtevId(Zahtev.ZahtevId.KREIRANJE_KORISNIK);
                            List<Integer> parametriInt = new ArrayList<>();
                            parametriInt.add(korisnik.getId());
                            zahtevKreirajKorisnika.setIntegerParams(parametriInt);
                            
                            zahtevKreirajKorisnika.setPodsistemId(2);
                            propagirajZahtev(context, producer, consumer, zahtevKreirajKorisnika, message.getJMSCorrelationID());
                            
                            zahtevKreirajKorisnika.setPodsistemId(3);
                            propagirajZahtev(context, producer, consumer, zahtevKreirajKorisnika, message.getJMSCorrelationID());
                        }      
                        producer.send(replyTo, odgovor);
                        break;
                    }
                    
                    case PROMENA_EMAIL_KORISNIK: {
                        TextMessage odgovor = context.createTextMessage();
                        odgovor.setJMSCorrelationID(message.getJMSCorrelationID());
                
                        Korisnik korisnik = em.find(Korisnik.class, zahtev.getIntegerParams().get(0));
                        if (korisnik == null) {
                            System.out.println("Nepostojeci idKor.");
                            odgovor.setText("Nepostojeci idKor: " + zahtev.getIntegerParams().get(0));
                        }
                        else {
                            em.getTransaction().begin();
                            korisnik.setEmail(zahtev.getStringParams().get(0));
                            em.getTransaction().commit();
                            em.clear();

                            odgovor.setText("Uspesno promenjen email na " + zahtev.getStringParams().get(0));
                        }
                        producer.send(replyTo, odgovor);
                        break;
                    }
                    
                    case PROMENA_MESTO_KORISNIK: {
                        TextMessage odgovor = context.createTextMessage();
                        odgovor.setJMSCorrelationID(message.getJMSCorrelationID());
                
                        int idKor = zahtev.getIntegerParams().get(0), idMes = zahtev.getIntegerParams().get(1);
                        
                        Korisnik korisnik = em.find(Korisnik.class, idKor);
                        Mesto mesto = em.find(Mesto.class, idMes);
                        if (korisnik == null)
                            odgovor.setText("Nepostojeci idKor: " + idKor);
                        else if (mesto == null)
                            odgovor.setText("Nepostojeci idMes: " + idMes);
                        else {
                            if (korisnik.getIdMes().getId() != idMes) {
                                em.getTransaction().begin();
                                korisnik.setIdMes(mesto);
                                em.getTransaction().commit();
                                em.clear();
                                
                                List<Korisnik> korisnici = mesto.getKorisnikList();
                                korisnici = korisnici == null ? new ArrayList<>() : korisnici;
                                korisnici.add(korisnik);
                                
                                odgovor.setText("Uspesno promenjeno mesto na " + zahtev.getIntegerParams().get(1));
                            }
                            else
                                odgovor.setText("Novo mesto se ne razlikuje od starog");
                        }
                        producer.send(replyTo, odgovor);
                        break;
                    }
                    
                    case DOHVATANJE_SVE_MESTO: {
                        List<Mesto> listaMesta = em.createNamedQuery("Mesto.findAll", Mesto.class).getResultList();
                        
                        Mesta mesta = new Mesta();
                        mesta.setListaMesta(listaMesta);
                        ObjectMessage odgovorObjMsg = context.createObjectMessage(mesta);
                        odgovorObjMsg.setJMSCorrelationID(message.getJMSCorrelationID());
                        
                        // Debugging:
                        for (Mesto m : listaMesta) {
                            System.out.println(m.getNaziv());
                        }
                        
                        producer.send(replyTo, odgovorObjMsg);
                        
                        System.out.println("Podistem1 poslao listu mesta centralnom serveru.");
                        break;
                    }
                    
                    case DOHVATANJE_SVE_KORISNIK: {
                        List<Korisnik> listaKorisnika = em.createNamedQuery("Korisnik.findAll", Korisnik.class).getResultList();
                        
                        Korisnici korisnici = new Korisnici();
                        korisnici.setListaKorisnika(listaKorisnika);
                        ObjectMessage odgovorObjMsg = context.createObjectMessage(korisnici);
                        odgovorObjMsg.setJMSCorrelationID(message.getJMSCorrelationID());
                        
                        // Debugging:
                        for (Korisnik k : listaKorisnika) {
                            System.out.println(k.getIme());
                        }
                        
                        producer.send(replyTo, odgovorObjMsg);
                        
                        System.out.println("Podistem1 poslao listu korisnika centralnom serveru.");
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
