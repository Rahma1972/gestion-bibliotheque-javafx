package controle;

import models.media.StockService;
import models.media.StatistiquesMedia;
import models.media.Media;
import models.media.Livre;
import models.media.Magazine;
import models.media.Dictionnaire;
import java.util.List;

public class StatistiquesController {
    private final StockService stockService;

    public StatistiquesController(StockService stockService) {
        this.stockService = stockService;
    }

    public StatistiquesMedia calculerStatistiques() {
        List<Media> medias = stockService.lister();
        long nbLivres = medias.stream().filter(m -> m instanceof Livre).count();
        long nbMagazines = medias.stream().filter(m -> m instanceof Magazine).count();
        long nbDictionnaires = medias.stream().filter(m -> m instanceof Dictionnaire).count();
        return new StatistiquesMedia(
                (int) nbLivres,
                (int) nbMagazines,
                (int) nbDictionnaires
        );
    }
}
