package app;

import javafx.application.Application;
import javafx.stage.Stage;
import controle.ConnexionController;
import views.ConnexionView;
import models.users.*;
import models.media.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class MainFX extends Application {
    @Override
    public void start(Stage primaryStage) {

        Map<String, Personne> utilisateursMap = new HashMap<>();
        GestionUtilisateursServiceImpl utilisateurService = new GestionUtilisateursServiceImpl(utilisateursMap);
        StockServiceImpl stockService = new StockServiceImpl();


        Lecteur lecteur1 = new Lecteur(" Lecteur1", "lecteur1@example.com", "111", "lecteur123", new ArrayList<>());
        Lecteur lecteur2 = new Lecteur(" Lecteur2", "lecteur2@example.com", "222", "lecteur123", new ArrayList<>());
        Bibliothecaire biblio = new Bibliothecaire("Biblio", "biblio@example.com", "333", "biblio123", "Bibliothecaire", 2019, "ROMANS");
        AdminBibliotheque admin = new AdminBibliotheque("Admin", "admin@example.com", "444", "admin123", "Admin", 2017, 1);

        utilisateurService.ajouter(lecteur1);
        utilisateurService.ajouter(lecteur2);
        utilisateurService.ajouter(biblio);
        utilisateurService.ajouter(admin);


        Livre livre1 = new Livre("L1", "Java Avancé", 2020, "Joshua Bloch");
        Livre livre2 = new Livre("L2", "Clean Code", 2008, "Robert C. Martin");
        Livre livre3 = new Livre("L3", "Design Patterns", 1994, "Gamma, Helm, Johnson, Vlissides");
        Livre livre4 = new Livre("L4", "Effective Java", 2018, "Joshua Bloch");
        Livre livre5 = new Livre("L5", "Le Seigneur des Anneaux", 1954, "J.R.R. Tolkien");


        Magazine mag1 = new Magazine("M1", "Science", 2022, 15);
        Magazine mag2 = new Magazine("M2", "National Geographic", 2023, 204);
        Magazine mag3 = new Magazine("M3", "Wired", 2023, 312);
        Magazine mag4 = new Magazine("M4", "Time", 2024, 1890);


        Dictionnaire dict1 = new Dictionnaire("D1", "Oxford English Dictionary", 2015, "Anglais");
        Dictionnaire dict2 = new Dictionnaire("D2", "Le Robert", 2020, "Français");
        Dictionnaire dict3 = new Dictionnaire("D3", "Duden", 2019, "Allemand");
        Dictionnaire dict4 = new Dictionnaire("D4", "Dictionnaire Espagnol Larousse", 2021, "Espagnol");


        stockService.ajouter(livre1);
        stockService.ajouter(livre2);
        stockService.ajouter(livre3);
        stockService.ajouter(livre4);
        stockService.ajouter(livre5);

        stockService.ajouter(mag1);
        stockService.ajouter(mag2);
        stockService.ajouter(mag3);
        stockService.ajouter(mag4);

        stockService.ajouter(dict1);
        stockService.ajouter(dict2);
        stockService.ajouter(dict3);
        stockService.ajouter(dict4);


        ConnexionController connexioncontroller = new ConnexionController(utilisateurService, stockService);
        ConnexionView  connexionview = new ConnexionView(connexioncontroller);
        connexionview.show(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}