package Session;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.ejb.Remote;
import Salle.Jeu;
import Salle.Joueur;

@Remote
public interface Salle_Interface extends Serializable {

    public Set<Joueur> getJoueurs();

    public void envoyerMessage();

    public void addJoueur(Joueur p);

    public void removeJoueur(Joueur p);

    public List<Jeu> getJeux();
    
    /*public Jeu creerJeu();*/
}
