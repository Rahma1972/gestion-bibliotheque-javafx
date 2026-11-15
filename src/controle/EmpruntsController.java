package controle;

import models.users.*;
import models.media.*;
import java.util.List;


import java.util.stream.Collectors;
import models.media.MediaNonDisponibleException;
public class EmpruntsController {
    private final GestionUtilisateursServiceImpl utilisateurService;
    private final StockServiceImpl stockService;
    private final JournalEmpruntsService journal;
    public EmpruntsController(
            GestionUtilisateursServiceImpl utilisateurService,
            StockServiceImpl stockService,
            JournalEmpruntsService journal) {
        this.utilisateurService = utilisateurService;
        this.stockService = stockService;
        this.journal = journal;

    }


    public boolean emprunterMediaAvecResultat(Lecteur lecteur, Media media) {
        if (!media.isDisponible()) {
            return false;
        }
        try {
            lecteur.emprunter(media);
            journal.enregistrerEmprunt(lecteur, media);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public void emprunterMedia(Lecteur lecteur, Media media) throws MediaNonDisponibleException {
        lecteur.emprunter(media);
        journal.enregistrerEmprunt(lecteur, media);
    }

    public List<Lecteur> getLecteurs() {
        return utilisateurService.lister().stream()
                .filter(p -> p instanceof Lecteur)
                .map(p -> (Lecteur) p)
                .collect(Collectors.toList());
    }

    public List<Media> getMediasDisponibles() {
        return stockService.lister().stream()
                .filter(Media::isDisponible)
                .collect(Collectors.toList());
    }
}