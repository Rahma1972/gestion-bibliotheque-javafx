package models.media;

public class Magazine extends Media {
    private int numero;

    public Magazine(String id, String titre, int annee, int numero) {
        super(id, titre, annee);
        this.numero = numero;
    }

    public int getNumero() { return numero; }

    @Override
    public void afficherInfo() {
        super.afficherInfo();
        System.out.println("Numero="+getNumero());
    }
}
