package views;

import controle.EmpruntsController;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import models.users.Lecteur;
import models.media.Media;
import models.media.Livre;
import models.media.Magazine;
import models.media.MediaNonDisponibleException;
import java.util.List;

public class EmpruntsView {

    private final EmpruntsController controller;

    public EmpruntsView(EmpruntsController controller) {
        this.controller = controller;
    }

    public void show(Stage stage) {

        Label titleLabel = new Label(" Gestion des Emprunts");
        titleLabel.getStyleClass().add("main-title");


        Label lecteurLabel = new Label(" Lecteur :");
        lecteurLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #4a5568;");

        ComboBox<Lecteur> lecteursBox = new ComboBox<>();
        lecteursBox.setPromptText("-- Sélectionner un lecteur --");
        lecteursBox.setPrefWidth(500);
        lecteursBox.setPrefHeight(45);

        List<Lecteur> lecteurs = controller.getLecteurs();
        lecteursBox.getItems().addAll(lecteurs);
        if (!lecteurs.isEmpty()) lecteursBox.setValue(lecteurs.get(0));


        Label mediaLabel = new Label(" Média :");
        mediaLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #4a5568;");

        ComboBox<Media> mediasBox = new ComboBox<>();
        mediasBox.setPromptText("-- Sélectionner un média --");
        mediasBox.setPrefWidth(500);
        mediasBox.setPrefHeight(45);


        mediasBox.setCellFactory(param -> new ListCell<Media>() {
            @Override
            protected void updateItem(Media media, boolean empty) {
                super.updateItem(media, empty);
                if (empty || media == null) {
                    setText(null);
                } else {
                    String icon = media instanceof Livre ? "" :
                            media instanceof Magazine ? "" : "";
                    setText(icon + " " + media.getTitre() + " (" + media.getAnnee() + ")");
                }
            }
        });

        mediasBox.setButtonCell(new ListCell<Media>() {
            @Override
            protected void updateItem(Media media, boolean empty) {
                super.updateItem(media, empty);
                if (empty || media == null) {
                    setText(null);
                } else {
                    String icon = media instanceof Livre ? "" :
                            media instanceof Magazine ? "" : "";
                    setText(icon + " " + media.getTitre() + " (" + media.getAnnee() + ")");
                }
            }
        });

        List<Media> medias = controller.getMediasDisponibles();
        mediasBox.getItems().addAll(medias);
        if (!medias.isEmpty()) mediasBox.setValue(medias.get(0));


        Button emprunterBtn = new Button(" Valider l'emprunt");
        emprunterBtn.setPrefWidth(300);
        emprunterBtn.setPrefHeight(50);
        emprunterBtn.setStyle("-fx-font-size: 16px;");
        emprunterBtn.setOnAction(e -> {
            Lecteur lecteur = lecteursBox.getValue();
            Media media = mediasBox.getValue();
            if (lecteur != null && media != null) {
                try {
                    controller.emprunterMedia(lecteur, media);

                    mediasBox.getItems().clear();
                    mediasBox.getItems().addAll(controller.getMediasDisponibles());

                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Succès");
                    successAlert.setHeaderText(" Emprunt validé !");
                    successAlert.setContentText(
                            "Le média \"" + media.getTitre() + "\" a été emprunté par " + lecteur.getNom()
                    );
                    successAlert.showAndWait();

                } catch (MediaNonDisponibleException ex) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Erreur");
                    errorAlert.setHeaderText(" Emprunt impossible");
                    errorAlert.setContentText(ex.getMessage());
                    errorAlert.showAndWait();
                }
            } else {
                Alert warningAlert = new Alert(Alert.AlertType.WARNING);
                warningAlert.setTitle("Attention");
                warningAlert.setHeaderText(" Sélection incomplète");
                warningAlert.setContentText("Veuillez sélectionner un lecteur ET un média.");
                warningAlert.showAndWait();
            }
        });


        VBox root = new VBox(20,
                titleLabel,
                lecteurLabel,
                lecteursBox,
                mediaLabel,
                mediasBox,
                emprunterBtn
        );
        root.getStyleClass().add("v-box");
        root.setAlignment(Pos.TOP_CENTER);

        Scene scene = new Scene(root, 600, 450);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setTitle("Gestion des Emprunts - Bibliothèque");
        stage.setScene(scene);
        stage.show();
    }
}