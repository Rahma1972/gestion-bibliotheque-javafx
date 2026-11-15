package models.users;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class GestionUtilisateursServiceImpl implements GestionUtilisateursService {
    private Map<String, Personne> utilisateurs;

    public GestionUtilisateursServiceImpl(Map<String, Personne> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }

    @Override
    public void ajouter(Personne p) {
        utilisateurs.put(p.getCin(), p);
    }

    @Override
    public void supprimer(String cin) {
        Personne p = utilisateurs.remove(cin);
        if (p == null) {
            throw new UtilisateurNonTrouveException("Aucun utilisateur trouvé avec CIN : " + cin);
        }
        System.out.println("Utilisateur supprimé : " + p.getNom());
    }


    @Override
    public Personne rechercher(String cin) {
        return utilisateurs.get(cin);
    }

    @Override
    public List<Personne> lister() {
        return new ArrayList<>(utilisateurs.values());
    }

    public List<Personne> filtrer(FiltrePersonne f) {
        return utilisateurs.values().stream()
                .filter(f::tester)
                .collect(Collectors.toList());
    }
}