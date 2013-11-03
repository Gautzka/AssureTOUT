/*
 * CustomerSession.java
 *
 * Created on January 16, 2006, 6:38 PM
 *
 * Copyright 2004-2005 Sun Microsystems, Inc.  All rights reserved.
 * Use is subject to license terms.
 */
package Session;

import Exception.UtilisateurInconnu;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import javax.ejb.*;
import javax.naming.InitialContext;
import Salle.Jeu;
import Salle.Joueur;
import Salle.JeuxJoueur;

@Stateless
public class Joueur_Session implements Joueur_Interface {

    @javax.persistence.PersistenceContext(unitName = "persistence")
    private EntityManager em;

    public Joueur_Session() {
    }

    @Override
    public void initApp() {
        try {
            Jeu jeu1 = new Jeu();
            jeu1.setTitre("Rock Paper Scissors");
            jeu1.setDescription("Both players have to choose one of the three options "
                    + "and show their choice simultaneously."
                    + "\nRock beats scissors, scissors beat paper  "
                    + "and paper beats rock. It is considered even if they both pick the same.");
            em.persist(jeu1);
            Jeu jeu2 = new Jeu();
            jeu2.setTitre("Heads or Tails?");
            jeu2.setDescription("The computer tosses a coin, and you have to "
                    + "guess if it is heads or tails!");
            em.persist(jeu2);
        } catch (Exception e) {
        }
    }

    @Override
    public Joueur chercherJoueurs(Long id) {
        Joueur j = (Joueur) em.find(Joueur.class, id);
        return j;
    }

    @Override
    public Joueur identification(String pseudo, String password) throws UtilisateurInconnu {
        List<Joueur> joueurs = em.createNamedQuery("getPlayer").setParameter("pseudo", pseudo).setParameter("password", password).getResultList();
        if (joueurs.isEmpty()) {
            throw new UtilisateurInconnu();
        } else {
            Joueur j = joueurs.get(0);
            j.setStatus(Joueur.StatusJoueur.ENLIGNE);
            em.merge(j);
            try {
                InitialContext ic = new InitialContext();
                Salle_Interface irs = (Salle_Interface) ic.lookup("java:global/Boursier-Kruck/Boursier-Kruck-ejb/Salle_Session");
                irs.addJoueur(j);
                return j;
            } catch (Exception e) {
                throw new EJBException(e);
            }
        }
    }

    @Override
    public Joueur enregistrement(String pseudo, String password, String mail) {
        Joueur nouveauJoueur = new Joueur(pseudo, mail, password);
        List<Jeu> jeux = em.createNamedQuery("getGames").getResultList();
        Set<JeuxJoueur> jeuxAssocies = new HashSet<JeuxJoueur>();
        for (Jeu jeu : jeux) {
            JeuxJoueur JeuJoueur = new JeuxJoueur();
            JeuJoueur.setJeu(jeu);
            JeuJoueur.setJoueur(nouveauJoueur);
            jeuxAssocies.add(JeuJoueur);
        }
        nouveauJoueur.setJeuxAssocies(jeuxAssocies);
        em.persist(nouveauJoueur);
        for (JeuxJoueur pg : nouveauJoueur.getJeuxAssocies()) {
            em.persist(pg);
        }

        return nouveauJoueur;
    }

    @Override
    public void persist(Object obj) {
        em.persist(obj);
    }

    @Override    
    public List trouverJoueurs() {
        List joueurs = em.createNamedQuery("findAllPlayers").getResultList();
        return joueurs;
    }
}
