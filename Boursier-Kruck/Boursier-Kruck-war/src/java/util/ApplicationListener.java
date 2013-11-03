package util;

import javax.naming.InitialContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import Session.Joueur_Interface;

public class ApplicationListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        final String I_PLAYER_SESSION = "java:global/Boursier-Kruck/Boursier-Kruck-ejb/Joueur_Session";
        try {
            InitialContext ic = new InitialContext();
            Joueur_Interface ip = (Joueur_Interface) ic.lookup(I_PLAYER_SESSION);
            ip.initApp();
        } catch (Exception e) {
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // shut down logic?
    }
}