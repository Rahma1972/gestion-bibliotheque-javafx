package models.users;

public class AdminBibliotheque extends Employe{
    private int niveauAutorisation;
    public AdminBibliotheque(String nom,  String email, String cin , String motDePasse, String poste, int anneeRecrutement, int niveauAutorisation) {
        super(nom, email, cin, motDePasse, poste, anneeRecrutement);
        this.niveauAutorisation = niveauAutorisation;
    }
    public int getNiveauAutorisation() {
        return niveauAutorisation;
    }
    public void setNiveauAutorisation(int niveauAutorisation) {
        this.niveauAutorisation = niveauAutorisation;
    }
    public void gererSysteme() {
        System.out.println("Système géré par " + this.getNom());
    }
    public void reinitialiserMotsDePasse(String cin) {
        System.out.println("Mot de passe réinitialisé pour l'utilisateur CIN: " + cin);
    }
    public void debloquerUtilisateur(String cin) {
        System.out.println("Utilisateur CIN: " + cin + " a été débloqué.");
    }
    @Override public void afficherInfos(){
        super.afficherInfos();
        System.out.println("Niveau autorisataion = " + this.getNiveauAutorisation());

    }






}
