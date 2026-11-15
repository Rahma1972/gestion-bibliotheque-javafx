package controle;

import models.media.Media;
import models.media.StockServiceImpl;
import java.util.List;
import java.util.stream.Collectors;

public class GestionMediasController {
    private StockServiceImpl stockService;

    public GestionMediasController(StockServiceImpl stockService) {
        this.stockService = stockService;
    }

    public List<Media> getMedias() {
        return stockService.lister();
    }

    public List<String> getMediasInfos() {
        return stockService.lister().stream()
                .map(Media::getTitre)
                .collect(Collectors.toList());
    }

    public void ajouterMedia(Media m) {
        stockService.ajouter(m);
    }

    public void supprimerMedia(String id) {
        stockService.supprimer(id);
    }

    public Media rechercherMedia(String id) {
        return stockService.rechercher(id);
    }
}