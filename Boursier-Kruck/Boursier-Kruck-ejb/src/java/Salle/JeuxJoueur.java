package Salle;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


@Entity
@NamedQueries(
    value={@NamedQuery(name="incGamesPlayed", query="UPDATE JeuxJoueur p SET p.partiesJouees = p.partiesJouees + 1 WHERE p.jeu = :jeu AND p.joueur = :joueur"),
    @NamedQuery(name="incGamesWon", query="UPDATE JeuxJoueur p SET p.partiesGagnees = p.partiesGagnees + 1 WHERE p.jeu = :jeu AND p.joueur = :joueur")}
)
public class JeuxJoueur implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn
    private Joueur joueur;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn
    private Jeu jeu;
    private int partiesJouees;
    private int partiesGagnees;

    public JeuxJoueur() {
    }

    public Jeu getJeu() {
        return this.jeu;
    }

    public void incPartiesJouees() {
        this.partiesJouees++;
    }

    public void incPartiesGagnees() {
        this.partiesGagnees++;
    }

    public int getPartiesJouees() {
        return this.partiesJouees;
    }

    public int getPartiesGagnees() {
        return this.partiesGagnees;
    }

    public void setJeu(Jeu j) {
        this.jeu = j;
    }

    public void setJoueur(Joueur j) {
        this.joueur = j;
    }
}
