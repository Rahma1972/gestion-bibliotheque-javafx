package models.users;

import models.media.*;

public class Bibliothecaire extends Employe {
    private String rayonAttribue;
    private static int nbOperations = 0;

    public Bibliothecaire(String nom, String email, String cin, String motDePasse, String poste, int anneeRecrutement, String rayonAttribue) {
        super(nom, email, cin, motDePasse, poste, anneeRecrutement);
        this.rayonAttribue = rayonAttribue;
    }

    public void setRayonAttribue(String rayonAttribue) {
        this.rayonAttribue = rayonAttribue;
    }

    public String getRayonAttribue() {
        return this.rayonAttribue;
    }

    public void validerEmprunt(Lecteur l, Media m) throws MediaNonDisponibleException {
        l.emprunter(m);
        nbOperations++;
        System.out.println("Emprunt validé pour le média " + m.getTitre() + " par : " + l.getNom());
    }

    public void enregistrerRetour(Media m) {
        if (m.isDisponible()) {
            throw new IllegalStateException("Le média " + m.getTitre() + " n'a pas été emprunté.");
        }
        m.setDisponible(true);
        nbOperations++;
    }

    @Override
    public void afficherInfos() {
        super.afficherInfos();
        System.out.println("Rayon= " + this.getRayonAttribue() + " | Nombre d'opérations = " + nbOperations);
    }
}