package util;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import Salle.Joueur;
import Salle.SalleDeJeu;

public class SessionListener implements HttpSessionListener {
    
    @Override
    public void sessionCreated(HttpSessionEvent ev) {
    }
    
    @Override
    public void sessionDestroyed(HttpSessionEvent ev) {
        HttpSession session = ev.getSession();
        if (session.getAttribute("player") != null && session.getAttribute("player") instanceof Joueur) {
            
            Joueur _player = (Joueur) session.getAttribute("player");
            SalleDeJeu.getInstance().removeJoueur(_player);
        }
    }
}
