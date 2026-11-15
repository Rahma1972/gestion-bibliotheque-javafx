package models.media;

import java.util.List;

public interface StockService {
    void ajouter(Media m);
    void supprimer(String id);
    Media rechercher(String id);
    List<Media> lister();
    List<Media> filtrer(FiltreMedia filtre);
}