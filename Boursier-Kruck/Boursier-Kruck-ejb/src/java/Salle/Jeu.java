package Salle;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries(
    value={@NamedQuery(name="getGames", query="select object(g) from Jeu g")}
)

public class Jeu implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true)
    private String titre;
    @Lob
    private String description;
    @OneToMany(mappedBy = "jeu", cascade = {CascadeType.REMOVE})
    private Set<JeuxJoueur> jeuxAssocies = new HashSet<JeuxJoueur>();

    public Jeu() {
        this.titre       = "title";
        this.description = "description";
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Long getId() {
        return this.id;
    }

    public String getTitre() {
        return this.titre;
    }

    public String getDescription() {
        return this.description;
    }

    public Set<JeuxJoueur> getJoueurs() {
        return this.jeuxAssocies;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Jeu && ((Jeu) o).getId().equals(this.id);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
    
    
}
