package models.media;

public class Livre extends Media {
    private String auteur;

    public Livre(String id, String titre, int annee, String auteur) {
        super(id, titre, annee);
        this.auteur = auteur;
    }

    public String getAuteur() { return auteur; }

    @Override
    public void afficherInfo() {
        super.afficherInfo();
        System.out.println("Auteur="+getAuteur());
    }
}
