package models.users;

import models.media.Media;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JournalEmpruntsService {
    private final List<DemandeEmprunt> historique = new ArrayList<>();

    public void enregistrerEmprunt(Lecteur lecteur, Media media) {
        DemandeEmprunt demande = new DemandeEmprunt(lecteur, media, LocalDate.now());
        historique.add(demande);
        System.out.println("Emprunt enregistré : " + demande);
    }

    public List<DemandeEmprunt> getHistorique() {
        return new ArrayList<>(historique);
    }
}