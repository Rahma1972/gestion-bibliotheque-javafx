package views;

import controle.ConnexionController;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import models.users.Lecteur;
import models.media.Media;
import models.media.Livre;
import models.media.Magazine;
import models.media.Dictionnaire;
import models.users.GestionUtilisateursServiceImpl;
import models.media.StockServiceImpl;
import java.util.List;

public class HistoriqueLecteurView {

    private final Lecteur lecteur;
    private final Stage stage;
    private final GestionUtilisateursServiceImpl utilisateurService;
    private final StockServiceImpl stockService;

    public HistoriqueLecteurView(
            Lecteur lecteur,
            Stage stage,
            GestionUtilisateursServiceImpl utilisateurService,
            StockServiceImpl stockService) {
        this.lecteur = lecteur;
        this.stage = stage;
        this.utilisateurService = utilisateurService;
        this.stockService = stockService;
    }

    public void show() {
        Label titleLabel = new Label(" Mon Historique d'Emprunts");
        titleLabel.getStyleClass().add("main-title");
        VBox lecteurInfoBox = createLecteurInfoBox();

        HBox statsBox = createStatsBox();
        ListView<String> listView = new ListView<>();
        listView.setPrefHeight(300);
        listView.setPlaceholder(new Label(" Aucun emprunt dans votre historique"));
        updateListView(listView);


        Button btnRefresh = new Button("Actualiser l'historique");
        btnRefresh.setPrefWidth(250);
        btnRefresh.getStyleClass().add("filter-button-primary");
        btnRefresh.setOnAction(e -> {
            updateListView(listView);
            updateStatsBox(statsBox);
        });


        Button btnLogout = new Button(" Se déconnecter");
        btnLogout.setPrefWidth(250);
        btnLogout.getStyleClass().add("logout-button");
        btnLogout.setOnAction(e -> {
            stage.close();
            ConnexionController loginCtrl = new ConnexionController(utilisateurService, stockService);
            ConnexionView loginView = new ConnexionView(loginCtrl);
            loginView.show(new Stage());
        });

        VBox infoBox = createInfoBox();

        HBox btnBox = new HBox(15, btnRefresh, btnLogout);
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setStyle("-fx-padding: 10 0;");

        VBox root = new VBox(20,
                titleLabel,
                lecteurInfoBox,
                statsBox,
                new Label(" Historique détaillé :"),
                listView,
                btnBox,
                infoBox
        );
        root.getStyleClass().add("v-box");

        Scene scene = new Scene(root, 700, 750);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setTitle("Mon Historique - Bibliothèque");
        stage.setScene(scene);
        stage.show();
    }


    private VBox createLecteurInfoBox() {
        Label nameLabel = new Label(" " + lecteur.getNom());
        nameLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2d3748;");

        Label emailLabel = new Label("" + lecteur.getEmail());
        emailLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #718096;");

        Label cinLabel = new Label("CIN: " + lecteur.getCin());
        cinLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #718096;");

        VBox infoBox = new VBox(8, nameLabel, emailLabel, cinLabel);
        infoBox.setAlignment(Pos.CENTER_LEFT);
        infoBox.setStyle(
                "-fx-background-color: #ebf4ff; " +
                        "-fx-border-color: #667eea; " +
                        "-fx-border-width: 2; " +
                        "-fx-border-radius: 12; " +
                        "-fx-background-radius: 12; " +
                        "-fx-padding: 20;"
        );

        return infoBox;
    }

    private HBox createStatsBox() {
        List<Media> historique = lecteur.getHistorique();
        int total = historique != null ? historique.size() : 0;
        long enCours = historique != null ? historique.stream().filter(m -> !m.isDisponible()).count() : 0;
        long retournes = total - enCours;

        VBox totalBox = createStatCard("", "Total emprunts", String.valueOf(total));
        VBox enCoursBox = createStatCard("", "En cours", String.valueOf(enCours));
        VBox retournesBox = createStatCard("", "Retournés", String.valueOf(retournes));

        HBox statsBox = new HBox(15, totalBox, enCoursBox, retournesBox);
        statsBox.setAlignment(Pos.CENTER);
        statsBox.setStyle("-fx-padding: 10 0;");

        return statsBox;
    }

    private void updateStatsBox(HBox statsBox) {
        statsBox.getChildren().clear();

        List<Media> historique = lecteur.getHistorique();
        int total = historique != null ? historique.size() : 0;
        long enCours = historique != null ? historique.stream().filter(m -> !m.isDisponible()).count() : 0;
        long retournes = total - enCours;

        VBox totalBox = createStatCard("", "Total emprunts", String.valueOf(total));
        VBox enCoursBox = createStatCard("", "En cours", String.valueOf(enCours));
        VBox retournesBox = createStatCard("", "Retournés", String.valueOf(retournes));

        statsBox.getChildren().addAll(totalBox, enCoursBox, retournesBox);
    }

    private VBox createStatCard(String icon, String label, String value) {
        Label iconLabel = new Label(icon);
        iconLabel.setStyle("-fx-font-size: 32px;");

        Label valueLabel = new Label(value);
        valueLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #667eea;");

        Label labelText = new Label(label);
        labelText.setStyle("-fx-font-size: 12px; -fx-text-fill: #718096;");

        VBox card = new VBox(5, iconLabel, valueLabel, labelText);
        card.setAlignment(Pos.CENTER);
        card.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #e2e8f0; " +
                        "-fx-border-width: 2; " +
                        "-fx-border-radius: 10; " +
                        "-fx-background-radius: 10; " +
                        "-fx-padding: 20; " +
                        "-fx-min-width: 180; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);"
        );

        return card;
    }

    private VBox createInfoBox() {
        Label infoTitle = new Label(" Informations");
        infoTitle.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #4a5568;");

        Label info1 = new Label("• Les médias marqués ont été retournés");
        info1.setStyle("-fx-font-size: 12px; -fx-text-fill: #718096;");

        Label info2 = new Label("• Les médias marqués  sont actuellement empruntés");
        info2.setStyle("-fx-font-size: 12px; -fx-text-fill: #718096;");

        Label info3 = new Label("• Cliquez sur 'Actualiser' pour mettre à jour l'historique");
        info3.setStyle("-fx-font-size: 12px; -fx-text-fill: #718096;");

        VBox infoBox = new VBox(8, infoTitle, info1, info2, info3);
        infoBox.setStyle(
                "-fx-background-color: #f7fafc; " +
                        "-fx-border-color: #cbd5e0; " +
                        "-fx-border-width: 2; " +
                        "-fx-border-radius: 10; " +
                        "-fx-background-radius: 10; " +
                        "-fx-padding: 15;"
        );

        return infoBox;
    }

    private void updateListView(ListView<String> listView) {
        System.out.println("Affichage historique : " + lecteur.getNom());
        System.out.println("Taille historique = " + lecteur.getHistorique().size());

        listView.getItems().clear();
        List<Media> historique = lecteur.getHistorique();

        if (historique == null || historique.isEmpty()) {
            listView.getItems().add(" Aucun emprunt pour le moment.");
        } else {
            for (int i = 0; i < historique.size(); i++) {
                Media m = historique.get(i);
                if (m == null) {
                    listView.getItems().add((i + 1) + ".  [Média inconnu]");
                    continue;
                }

                String icon;
                String type;
                if (m instanceof Livre) {
                    icon = "";
                    type = "Livre";
                } else if (m instanceof Magazine) {
                    icon = "";
                    type = "Magazine";
                } else if (m instanceof Dictionnaire) {
                    icon = "";
                    type = "Dictionnaire";
                } else {
                    icon = "";
                    type = "Média";
                }

                String titre = m.getTitre() != null ? m.getTitre() : "[Sans titre]";
                String status = m.isDisponible() ? " Retourné" : " En cours d'emprunt";

                listView.getItems().add(
                        String.format("%d. %s %s (%s) — %s",
                                i + 1, icon, titre, type, status)
                );
            }
        }

        listView.setCellFactory(param -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    if (item.contains("✅")) {
                        setStyle("-fx-text-fill: #48bb78; -fx-font-weight: 600;");
                    } else if (item.contains("❌")) {
                        setStyle("-fx-text-fill: #f56565; -fx-font-weight: 600;");
                    } else {
                        setStyle("-fx-text-fill: #4a5568;");
                    }
                }
            }
        });
    }
}