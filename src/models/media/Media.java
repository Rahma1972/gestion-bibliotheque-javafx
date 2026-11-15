package models.media;

public abstract class Media {
    protected String id;
    protected String titre;
    protected int annee;
    protected boolean disponible;

    public Media(String id, String titre, int annee) {
        this.id = id;
        this.titre = titre;
        this.annee = annee;
        this.disponible = true;
    }

    public String getId() { return id; }
    public String getTitre() { return titre; }
    public int getAnnee() { return annee; }
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean b) { this.disponible = b; }


    @Override
    public String toString() {
        return getTitre();
    }

    public void afficherInfo() {
        System.out.println("Id=" + getId() + " | Titre= " + getTitre() + " | Annee=" + getAnnee());
    }
}