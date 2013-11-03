/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import Salle.Jeu;
import Salle.Jeu_PFC;
import Salle.Jeu_PFC.Choix;
import Salle.Jeu_PouF;
import Salle.Jeu_PouF.ChoixPouF;
import Salle.Joueur;
import Salle.JeuxJoueur;

@Stateful
public class Jeu_Session implements Jeu_Interface, Serializable {

    @javax.persistence.PersistenceContext(unitName = "persistence")
    private EntityManager em;
    private static final int SCORE_MAX = 3;
    private int scoreJoueur;
    private int scoreOrdi;
    private List<Enum> derniersChoixJoueur = new ArrayList<Enum>();
    private List<Enum> derniersChoixOrdi = new ArrayList<Enum>();
    private Joueur joueur;
    private Jeu jeu;
    private int id = 0;

    public Jeu_Session() {
    }

    @Override
    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    @Override
    public void setJeu(String jeu) {
        this.jeu = em.find(Jeu.class, Long.parseLong(jeu));
    }

    @Override
    public void nouveauJeu() {
        this.scoreJoueur = 0;
        this.scoreOrdi = 0;
        this.derniersChoixJoueur.clear();
        this.derniersChoixOrdi.clear();

        for (JeuxJoueur pg : this.joueur.getJeuxAssocies()) {
            if (pg.getJeu().equals(this.jeu)) {
                em.createNamedQuery("incGamesPlayed").setParameter("jeu", this.jeu).setParameter("joueur", this.joueur).executeUpdate();
            }
        }
    }

    @Override
    public Enum getChoixJoueur() {
        return this.derniersChoixJoueur.isEmpty() ? null : this.derniersChoixJoueur.get(this.derniersChoixJoueur.size() - 1);
    }

    @Override
    public Enum getChoixOrdi() {
        return this.derniersChoixOrdi.isEmpty() ? null : this.derniersChoixOrdi.get(this.derniersChoixOrdi.size() - 1);
    }

    @Override
    public int getScoreJoueur() {
        return this.scoreJoueur;
    }

    @Override
    public int getScoreOrdi() {
        return this.scoreOrdi;
    }

    @Override
    public int jouer(Enum choixJoueur, int idJeu) {
        int gagnant;
        if(idJeu == 1){
            Choix choixOrdi = Jeu_PFC.getChoixAleatoire();
            gagnant = Jeu_PFC.getGagnant((Choix)choixJoueur, choixOrdi);
            this.derniersChoixJoueur.add(choixJoueur);
            this.derniersChoixOrdi.add(choixOrdi);
        }else{
            ChoixPouF choixOrdi = Jeu_PouF.getChoixAleatoire();
            gagnant = Jeu_PouF.getGagnant((ChoixPouF)choixJoueur, choixOrdi);
            this.derniersChoixJoueur.add(choixJoueur);
            this.derniersChoixOrdi.add(choixOrdi);
        }
        
        if      (gagnant == 1) this.scoreJoueur++;
        else if (gagnant == 2) this.scoreOrdi++;
        if (partieTerminee() && joueurGagne()) {
            em.createNamedQuery("incGamesWon").setParameter("jeu", this.jeu).setParameter("joueur", this.joueur).executeUpdate();
        }
        return gagnant;
    }

    @Override
    public boolean partieTerminee() {
        return (this.scoreJoueur + this.scoreOrdi) >= SCORE_MAX;
    }

    @Override
    public boolean joueurGagne() {
        return this.scoreJoueur > this.scoreOrdi;
    }
    
    @Override
    public int getId() {
        return this.id;
    }
    
    @Override
    public void setId(int id) {
        this.id = id;
    }
}
