package models.users;

public non-sealed class Employe extends Personne{
    private String poste ;
    private int anneeRecrutement;
    public Employe(String nom , String email ,  String cin ,String motDePasse,  String poste , int anneeRecrutement){
        super(nom , email ,cin, motDePasse);
        this.poste=poste;
        this.anneeRecrutement=anneeRecrutement;
    }
    public void SetPoste(String poste){
        this.poste=poste;
    }
    public String getPoste(){
        return this.poste;
    }
    public void SetAnneeRecrutement(int anneRecrutement){
        this.anneeRecrutement=anneeRecrutement;
    }
    public int getAnneeRecrutement(){
        return this.anneeRecrutement;
    }
    public void traiterDemande(){
        System.out.println("Demande traitée par : "+ this.getNom());
    }
    @Override public void afficherInfos(){
        super.afficherInfos();
        System.out.print("Poste "+this.getPoste()+" | Année de recrutement= " +this.getAnneeRecrutement());
    }
}
