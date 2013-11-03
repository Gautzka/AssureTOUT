/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Session;

import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import Salle.Jeu;
import Salle.Joueur;
import Salle.SalleDeJeu;

@Stateless
public class Salle_Session implements Salle_Interface {

    @javax.persistence.PersistenceContext(unitName = "persistence")
    private EntityManager em;
    private SalleDeJeu sdj;
    private InitialContext ic = null;
    
    private ConnectionFactory connectionFactory;
    
    private Topic topic;
    private Connection connection = null;
    private Session jmsSession = null;
    private MessageProducer producer = null;
    //private Jeu j = null;

    public Salle_Session() {
        try {
            this.sdj = SalleDeJeu.getInstance();
            this.ic = new InitialContext();
            this.connectionFactory = (ConnectionFactory) ic.lookup("jms/ConnexionFactoryMiniProjet");
            this.topic = (Topic) ic.lookup("jms/TopicMiniProjet");
        } catch (Exception ne) {
            ne.printStackTrace();
        }
    }

    @Override
    public void envoyerMessage() {
        try {
            this.connection = this.connectionFactory.createConnection();
            this.jmsSession = this.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            this.producer = this.jmsSession.createProducer(this.topic);
            this.connection.start();
            TextMessage msg = this.jmsSession.createTextMessage();
            this.producer.send(msg);
            this.connection.close();
        } catch (Exception ne) {
            ne.printStackTrace();
        }
    }
    
    @Override
    public List<Jeu> getJeux() {
        return em.createNamedQuery("getGames").getResultList();
    }

    @Override
    public Set<Joueur> getJoueurs() {
        return this.sdj.getJoueurs();
    }

    @Override
    public void addJoueur(Joueur j) {
        this.sdj.addJoueur(j);
    }
    
    @Override
    public void removeJoueur(Joueur j) {
        this.sdj.removeJoueur(j);
    }
}
