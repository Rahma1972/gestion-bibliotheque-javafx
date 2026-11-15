package models.users;

import models.media.*;
import java.util.List;

public final class Lecteur extends Personne {
    private List<Media> historique;

    public Lecteur(String nom, String email, String cin, String motDePasse, List<Media> historique) {
        super(nom, email, cin, motDePasse);
        this.historique = historique;
    }

    public List<Media> getHistorique() {
        return this.historique;
    }


    public void emprunter(Media media) throws MediaNonDisponibleException {
        if (!media.isDisponible()) {
            throw new MediaNonDisponibleException("Le média \"" + media.getTitre() + "\" n'est pas disponible.");
        }
        historique.add(media);
        media.setDisponible(false);
        System.out.println("EMPRUNT DANS : " + this.getNom() + " (hash=" + System.identityHashCode(this) + ")");
    }
    @Override
    public String toString() {
        return getNom();
    }

    @Override
    public String getInfos() {
        return super.getInfos() + " | Historique: " + historique.size() + " média(s)";
    }

}


