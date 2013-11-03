package Salle;

import java.util.HashSet;
import java.util.Set;

public class SalleDeJeu {

    private Set<Joueur> joueurs = new HashSet<Joueur>();
    private static SalleDeJeu instance;
    
    public static SalleDeJeu getInstance() {
        if (instance == null) {
            instance = new SalleDeJeu();
        }
        return instance;
    }
    
    private SalleDeJeu() {
    }

    public Set<Joueur> getJoueurs() {
        return this.joueurs;
    }

    public void addJoueur(Joueur p) {
        this.joueurs.add(p);
    }
    
    public void removeJoueur(Joueur p) {
        this.joueurs.remove(p);
    }
}
