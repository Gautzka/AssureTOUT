/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Salle;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries(
    value={@NamedQuery(name="findAllPlayers", query="select object(p) from Joueur p"),
    @NamedQuery(name="getPlayer", query="select object(p) from Joueur p where p.pseudo= :pseudo and p.password= :password")}
)

public class Joueur implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true)
    private String pseudo;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    public enum StatusJoueur {
        ENJEU, ENLIGNE, HORSLIGNE
    };
    @Enumerated(EnumType.ORDINAL)
    private StatusJoueur status;
    
    @OneToMany(mappedBy = "joueur", cascade = {CascadeType.REMOVE}, fetch= FetchType.EAGER)
    private Set<JeuxJoueur> jeuxAssocies = new HashSet<JeuxJoueur>();

    public Joueur() {
        this.pseudo   = "pseudo";
        this.email    = "email@email.email";
        this.password = "password";
    }

    public Joueur(String pseudo, String email, String password) {
        this.pseudo   = pseudo;
        this.email    = email;
        this.password = password;
    }

    public Long getId() {
        return this.id;
    }

    public String getPseudo() {
        return this.pseudo;
    }

    public String getEmail() {
        return this.email;
    }

    public StatusJoueur getStatus() {
        return this.status;
    }
    
    public void setStatus(StatusJoueur status) {
        this.status = status;
    }
    
    public Set<JeuxJoueur> getJeuxAssocies() {
        return this.jeuxAssocies;
    }

    public void setJeuxAssocies(Set<JeuxJoueur> pg) {
        this.jeuxAssocies = pg;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object o) {
        return o instanceof Joueur && ((Joueur) o).id.equals(this.id);
    }
}
