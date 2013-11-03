package Session;

import java.io.Serializable;
import javax.ejb.Remote;
import Salle.Jeu_PFC.Choix;
import Salle.Joueur;

@Remote
public interface Jeu_Interface extends Serializable {

    public void nouveauJeu();
    
    public void setJoueur(Joueur joueur);
    
    public void setJeu(String jeu);

    public Enum getChoixJoueur();

    public Enum getChoixOrdi();

    public int getScoreJoueur();

    public int getScoreOrdi();

    public int jouer(Enum choixJoueur, int idJeu);

    public boolean partieTerminee();

    public boolean joueurGagne();
    
    public int getId();
    
    public void setId(int id);
}
