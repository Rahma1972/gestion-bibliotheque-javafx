package models.users;

import java.util.List;

public interface GestionUtilisateursService {
    public void ajouter(Personne p);
    public void supprimer(String s);
    public Personne rechercher(String s);
    public List<Personne> lister();
    List<Personne> filtrer(FiltrePersonne filtre);
}
