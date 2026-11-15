package models.media;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StockServiceImpl implements StockService {
    private List<Media> stock = new ArrayList<>();

    @Override
    public void ajouter(Media m) {
        stock.add(m);
    }

    @Override
    public void supprimer(String id) {
        stock.removeIf(m -> m.getId().equals(id));
    }

    @Override
    public Media rechercher(String id) {
        return stock.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Media> lister() {
        return stock;
    }

    @Override
    public List<Media> filtrer(FiltreMedia f) {
        return stock.stream()
                .filter(f::tester)
                .collect(Collectors.toList());
    }
}