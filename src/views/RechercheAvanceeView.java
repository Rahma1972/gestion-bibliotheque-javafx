package views;

import controle.RechercheAvanceeController;
import models.users.*;
import models.media.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;

public class RechercheAvanceeView {
    private final RechercheAvanceeController controller;

    public RechercheAvanceeView(RechercheAvanceeController controller) {
        this.controller = controller;
    }

    public void show(Stage stage) {
        Label titleLabel = new Label(" Recherche Avancée");
        titleLabel.getStyleClass().add("main-title");

        ListView<String> results = new ListView<>();
        results.setPrefHeight(300);
        results.setPlaceholder(new Label("Sélectionnez un filtre pour afficher les résultats"));

        Label countLabel = new Label("");
        countLabel.getStyleClass().add("result-count");

        Label usersTitle = new Label("Filtrer par utilisateurs");
        usersTitle.getStyleClass().add("section-title");

        Button btnLecteurs = new Button("Lecteurs");
        btnLecteurs.getStyleClass().add("filter-button");
        btnLecteurs.setOnAction(e -> {
            results.getItems().clear();
            var lecteurs = controller.filtrerUtilisateurs(p -> p instanceof Lecteur);
            lecteurs.forEach(p -> results.getItems().add(" " + p.getInfos()));
            countLabel.setText(lecteurs.size() + " lecteur(s) trouvé(s)");
        });

        Button btnBibliothecaires = new Button("Bibliothécaires");
        btnBibliothecaires.getStyleClass().add("filter-button");
        btnBibliothecaires.setOnAction(e -> {
            results.getItems().clear();
            var bibliothecaires = controller.filtrerUtilisateurs(p -> p instanceof Bibliothecaire);
            bibliothecaires.forEach(p -> results.getItems().add("" + p.getInfos()));
            countLabel.setText(bibliothecaires.size() + " bibliothécaire(s) trouvé(s)");
        });

        Button btnAdmins = new Button("Administrateurs");
        btnAdmins.getStyleClass().add("filter-button");
        btnAdmins.setOnAction(e -> {
            results.getItems().clear();
            var admins = controller.filtrerUtilisateurs(p -> p instanceof AdminBibliotheque);
            admins.forEach(p -> results.getItems().add(" " + p.getInfos()));
            countLabel.setText(admins.size() + " administrateur(s) trouvé(s)");
        });

        HBox usersBox = new HBox(10, btnLecteurs, btnBibliothecaires, btnAdmins);
        usersBox.setAlignment(Pos.CENTER_LEFT);


        Label mediasTitle = new Label(" Filtrer par médias");
        mediasTitle.getStyleClass().add("section-title");

        Button btnLivres = new Button("Livres");
        btnLivres.getStyleClass().add("filter-button");
        btnLivres.setOnAction(e -> {
            results.getItems().clear();
            var livres = controller.filtrerMedias(m -> m instanceof Livre);
            livres.forEach(m -> {
                String dispo = m.isDisponible() ? "" : "";
                results.getItems().add(" " + m.getTitre() + " (" + m.getAnnee() + ") " + dispo);
            });
            countLabel.setText(livres.size() + " livre(s) trouvé(s)");
        });

        Button btnMagazines = new Button("Magazines");
        btnMagazines.getStyleClass().add("filter-button");
        btnMagazines.setOnAction(e -> {
            results.getItems().clear();
            var magazines = controller.filtrerMedias(m -> m instanceof Magazine);
            magazines.forEach(m -> {
                String dispo = m.isDisponible() ? "" : "";
                results.getItems().add(" " + m.getTitre() + " (" + m.getAnnee() + ") " + dispo);
            });
            countLabel.setText(magazines.size() + " magazine(s) trouvé(s)");
        });

        Button btnDictionnaires = new Button("Dictionnaires");
        btnDictionnaires.getStyleClass().add("filter-button");
        btnDictionnaires.setOnAction(e -> {
            results.getItems().clear();
            var dictionnaires = controller.filtrerMedias(m -> m instanceof Dictionnaire);
            dictionnaires.forEach(m -> {
                String dispo = m.isDisponible() ? "" : "";
                results.getItems().add(" " + m.getTitre() + " (" + m.getAnnee() + ") " + dispo);
            });
            countLabel.setText(dictionnaires.size() + " dictionnaire(s) trouvé(s)");
        });

        HBox mediasBox = new HBox(10, btnLivres, btnMagazines, btnDictionnaires);
        mediasBox.setAlignment(Pos.CENTER_LEFT);


        Label dispoTitle = new Label(" Filtrer par disponibilité");
        dispoTitle.getStyleClass().add("section-title");

        Button btnDisponibles = new Button("Médias disponibles");
        btnDisponibles.getStyleClass().add("filter-button");
        btnDisponibles.setOnAction(e -> {
            results.getItems().clear();
            var disponibles = controller.filtrerMedias(Media::isDisponible);
            disponibles.forEach(m -> {
                String type = m instanceof Livre ? "" : m instanceof Magazine ? "" : "";
                results.getItems().add(type + " " + m.getTitre() + "  Disponible");
            });
            countLabel.setText(disponibles.size() + " média(s) disponible(s)");
        });

        Button btnEmpruntes = new Button("Médias empruntés");
        btnEmpruntes.getStyleClass().add("filter-button");
        btnEmpruntes.setOnAction(e -> {
            results.getItems().clear();
            var empruntes = controller.filtrerMedias(m -> !m.isDisponible());
            empruntes.forEach(m -> {
                String type = m instanceof Livre ? "" : m instanceof Magazine ? "" : "";
                results.getItems().add(type + " " + m.getTitre() + "  Emprunté");
            });
            countLabel.setText(empruntes.size() + " média(s) emprunté(s)");
        });

        Button btnTousMedias = new Button("Tous les médias");
        btnTousMedias.getStyleClass().add("filter-button-primary");
        btnTousMedias.setOnAction(e -> {
            results.getItems().clear();
            var tous = controller.filtrerMedias(m -> true);
            tous.forEach(m -> {
                String type = m instanceof Livre ? "" : m instanceof Magazine ? "" : "";
                String dispo = m.isDisponible() ? "" : "";
                results.getItems().add(type + " " + m.getTitre() + " " + dispo);
            });
            countLabel.setText(tous.size() + " média(s) au total");
        });

        HBox dispoBox = new HBox(10, btnDisponibles, btnEmpruntes, btnTousMedias);
        dispoBox.setAlignment(Pos.CENTER_LEFT);


        Separator sep1 = new Separator();
        Separator sep2 = new Separator();
        Separator sep3 = new Separator();

        VBox root = new VBox(20,
                titleLabel,
                usersTitle,
                usersBox,
                sep1,
                mediasTitle,
                mediasBox,
                sep2,
                dispoTitle,
                dispoBox,
                sep3,
                countLabel,
                new Label("Résultats :"),
                results
        );
        root.getStyleClass().add("v-box");

        Scene scene = new Scene(root, 750, 700);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setTitle("Recherche Avancée - Bibliothèque");
        stage.setScene(scene);
        stage.show();
    }
}