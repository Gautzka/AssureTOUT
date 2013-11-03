package Session;

import Exception.UtilisateurInconnu;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Remote;

import Salle.Joueur;

@Remote
public interface Joueur_Interface extends Serializable {

    public Joueur chercherJoueurs(Long id);

    public Joueur identification(String pseudo, String password) throws UtilisateurInconnu;

    public Joueur enregistrement(String pseudo, String password, String mail);

    public void persist(Object obj);

    public List trouverJoueurs();
    
    public void initApp();
}
