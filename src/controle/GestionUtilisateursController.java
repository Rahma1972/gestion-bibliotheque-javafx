package controle;
import models.users.Personne;
import models.users.GestionUtilisateursServiceImpl;
import models.users.UtilisateurNonTrouveException;

import java.util.List;
import java.util.stream.Collectors;

public class GestionUtilisateursController {
    private GestionUtilisateursServiceImpl utilisateurService;

    public GestionUtilisateursController(GestionUtilisateursServiceImpl utilisateurService ) {
        this.utilisateurService = utilisateurService;

    }

    public List<Personne> getUtilisateurs() {
        return utilisateurService.lister();
    }

    public List<String> getUtilisateursInfos() {
        return utilisateurService.lister().stream()
                .map(Personne::getInfos)
                .collect(Collectors.toList());
    }

    public void ajouterUtilisateur(Personne p) {
        utilisateurService.ajouter(p);
    }


    public void supprimerUtilisateur(String cin) {
        try {
            utilisateurService.supprimer(cin);
        } catch (UtilisateurNonTrouveException e) {
            throw e;
        }
    }


    public Personne rechercherUtilisateur(String cin) {
        return utilisateurService.rechercher(cin);
    }
}