/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jms;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.inject.Singleton;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.TextMessage;

/**
 *
 * @author PC
 */
@Singleton
public class PodsistemiJMS {
    @Resource(lookup="jms/__defaultConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup="queueServer")
    private Queue queueServer;
    @Resource(lookup="queue11")
    private Queue queue11;
    @Resource(lookup="queue12")
    private Queue queue12;
    @Resource(lookup="queue13")
    private Queue queue13;
       
    
    public Object delegiraj(Zahtev zahtev) {
        try (JMSContext context = connectionFactory.createContext()) {
            ObjectMessage objMsg = context.createObjectMessage(zahtev);
            String correlationID = UUID.randomUUID().toString();
            
            try (JMSConsumer consumer = context.createConsumer(queueServer, "JMSCorrelationID = '" + correlationID + "'")) {
                objMsg.setJMSReplyTo(queueServer);
                objMsg.setJMSCorrelationID(correlationID);

                // Debugging:
                System.out.println("Saljem zahtev " + correlationID + " podsistemu " + zahtev.getPodsistemId());

                JMSProducer producer = context.createProducer();
                switch (zahtev.getPodsistemId()) {
                    case 1:
                        producer.send(queue11, objMsg); break;
                    case 2:
                        producer.send(queue12, objMsg); break;
                    case 3:
                        producer.send(queue13, objMsg); break;
                }

                // Debugging:
                System.out.println("Poslat zahtev " + correlationID + " podsistemu " + zahtev.getPodsistemId());

                Message responseMsg = consumer.receive();
                
                if (responseMsg instanceof TextMessage) {
                    TextMessage responseTxtMsg = (TextMessage)responseMsg;
                    String responseStr = responseTxtMsg.getText();

                    // Debugging:
                    System.out.println("Primljen odgovor: " + responseStr);
                    return responseStr;
                }
                else if (responseMsg instanceof ObjectMessage) {
                    ObjectMessage responseObjMsg = (ObjectMessage)responseMsg;
                    
                    // Debugging:
                    System.out.println("Primljen odgovor tipa ObjectMessage od podsistema");
                    return responseObjMsg.getObject();
                }
                // Ovo se desi kad podsistem nije upaljen, a saljemo mu poruke.
                else {
                    System.out.println("GRSKA: Primljen odgovor nije TextMessage ni ObjectMessage");
                    return "GRESKA: Primljen odgovor nije TextMessage ni ObjectMessage";
                }
            } catch (JMSException ex) {
                Logger.getLogger(PodsistemiJMS.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("GRESKA: JMSException");
                return "GRESKA: JMSException";
            }
        }
        catch (Exception e) {
            System.out.println("GRESKA: Greska pri delegiranju");
            return "GRESKA: Greska pri delegiranju";
        }
    }
}
