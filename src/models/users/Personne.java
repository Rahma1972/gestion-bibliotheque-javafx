package models.users;

public sealed abstract class Personne permits Lecteur, Employe {
    String nom, email, cin , motDePasse;

    public Personne(String nom, String email, String cin , String motDePasse) {
        this.nom = nom;
        this.email = email;
        this.cin = cin;
        this.motDePasse=motDePasse;

    }

    public void setNom(String nom) { this.nom = nom; }
    public String getNom() { return this.nom; }
    public void setEmail(String email) { this.email = email; }
    public String getEmail() { return this.email; }
    public void setCin(String cin) { this.cin = cin; }
    public String getCin() { return this.cin; }
    public void setMotDePasse(String motDePasse) { this.motDePasse=motDePasse; }
    public String getMotDePasse() { return this.motDePasse; }

    public void afficherInfos() {
        System.out.print("Nom= " + getNom() + " | Email= " + getEmail() + " | Cin= " + getCin());
    }


    public String getInfos() {
        return "Nom= " + getNom() + " | Email= " + getEmail() + " | Cin= " + getCin();
    }
}