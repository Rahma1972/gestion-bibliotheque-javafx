package controle;

import models.users.*;
import models.media.*;
import java.util.List;

public class RechercheAvanceeController {
    private final GestionUtilisateursService utilisateurService;
    private final StockService stockService;

    public RechercheAvanceeController(GestionUtilisateursService utilisateurService, StockService stockService) {
        this.utilisateurService = utilisateurService;
        this.stockService = stockService;
    }

    public List<Personne> filtrerUtilisateurs(FiltrePersonne filtre) {
        return utilisateurService.filtrer(filtre);
    }

    public List<Media> filtrerMedias(FiltreMedia filtre) {
        return stockService.filtrer(filtre);
    }
}