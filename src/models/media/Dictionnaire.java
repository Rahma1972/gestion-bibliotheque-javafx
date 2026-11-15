package models.media;

public final class Dictionnaire extends Media {
    private String langue;

    public Dictionnaire(String id, String titre, int annee, String langue) {
        super(id, titre, annee);
        this.langue = langue;
    }

    public String getLangue() { return langue; }

    @Override
    public void afficherInfo() {
         super.afficherInfo();
         System.out.println("Langue= " + getLangue());
    }
}
