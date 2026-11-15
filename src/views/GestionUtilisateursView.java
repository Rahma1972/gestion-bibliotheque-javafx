package views;

import controle.GestionUtilisateursController;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import models.users.Personne;
import models.users.Lecteur;
import models.users.Bibliothecaire;
import models.users.AdminBibliotheque;
import models.users.UtilisateurNonTrouveException;

public class GestionUtilisateursView {
    private final boolean peutSupprimer;
    private final GestionUtilisateursController controller;

    public GestionUtilisateursView(GestionUtilisateursController controller, boolean peutSupprimer) {
        this.controller = controller;
        this.peutSupprimer = peutSupprimer;
    }

    public void show(Stage stage) {
        Label titleLabel = new Label("Gestion des Utilisateurs");
        titleLabel.getStyleClass().add("main-title");
        HBox statsBox = createStatsBox();

        TableView<Personne> tableView = new TableView<>();
        tableView.setPrefSize(850, 300);

        TableColumn<Personne, String> nomCol = new TableColumn<>(" Nom");
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        nomCol.setPrefWidth(200);

        TableColumn<Personne, String> emailCol = new TableColumn<>(" Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailCol.setPrefWidth(250);

        TableColumn<Personne, String> cinCol = new TableColumn<>(" CIN");
        cinCol.setCellValueFactory(new PropertyValueFactory<>("cin"));
        cinCol.setPrefWidth(150);
        cinCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Personne, String> roleCol = new TableColumn<>("Rôle");
        roleCol.setCellValueFactory(param -> {
            Personne p = param.getValue();
            String role;
            if (p instanceof Lecteur) {
                role = " Lecteur";
            } else if (p instanceof Bibliothecaire) {
                role = "👨 Bibliothécaire";
            } else if (p instanceof AdminBibliotheque) {
                role = " Administrateur";
            } else {
                role = " Employé";
            }
            return new javafx.beans.property.SimpleStringProperty(role);
        });
        roleCol.setPrefWidth(200);
        roleCol.setStyle("-fx-alignment: CENTER;");

        tableView.getColumns().addAll(nomCol, emailCol, cinCol, roleCol);
        loadUsers(tableView);
        HBox filterBox = createFilterButtons(tableView);
        VBox root = new VBox(20,
                titleLabel,
                statsBox,
                new Label("Liste complète des utilisateurs :"),
                tableView,
                filterBox
        );

        if (peutSupprimer) {
            root.getChildren().add(new Separator());
            VBox deleteSection = createDeleteSection(tableView, statsBox);
            root.getChildren().add(deleteSection);
        }

        root.getStyleClass().add("v-box");

        Scene scene = new Scene(root, 900, peutSupprimer ? 720 : 580);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        stage.setTitle("Gestion Utilisateurs - Bibliothèque");
        stage.setScene(scene);
        stage.show();
    }

    private HBox createStatsBox() {
        var utilisateurs = controller.getUtilisateurs();
        long nbLecteurs = utilisateurs.stream().filter(p -> p instanceof Lecteur).count();
        long nbBibliothecaires = utilisateurs.stream().filter(p -> p instanceof Bibliothecaire).count();
        long nbAdmins = utilisateurs.stream().filter(p -> p instanceof AdminBibliotheque).count();
        int total = utilisateurs.size();

        VBox lecteursBox = createStatCard("", "Lecteurs", String.valueOf(nbLecteurs));
        VBox bibliothBox = createStatCard("", "Bibliothécaires", String.valueOf(nbBibliothecaires));
        VBox adminsBox = createStatCard("", "Admins", String.valueOf(nbAdmins));
        VBox totalBox = createStatCard("", "Total", String.valueOf(total));

        HBox statsBox = new HBox(15, lecteursBox, bibliothBox, adminsBox, totalBox);
        statsBox.setAlignment(Pos.CENTER);
        statsBox.setStyle("-fx-padding: 10 0;");

        return statsBox;
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
                        "-fx-padding: 15; " +
                        "-fx-min-width: 150; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);"
        );

        return card;
    }

    private HBox createFilterButtons(TableView<Personne> tableView) {
        Button btnFilterLecteurs = new Button(" Lecteurs");
        btnFilterLecteurs.getStyleClass().add("filter-button");
        btnFilterLecteurs.setOnAction(e -> {
            tableView.getItems().clear();
            controller.getUtilisateurs().stream()
                    .filter(p -> p instanceof Lecteur)
                    .forEach(tableView.getItems()::add);
        });

        Button btnFilterBibliothecaires = new Button("Bibliothécaires");
        btnFilterBibliothecaires.getStyleClass().add("filter-button");
        btnFilterBibliothecaires.setOnAction(e -> {
            tableView.getItems().clear();
            controller.getUtilisateurs().stream()
                    .filter(p -> p instanceof Bibliothecaire)
                    .forEach(tableView.getItems()::add);
        });

        Button btnFilterAdmins = new Button("⚙️ Admins");
        btnFilterAdmins.getStyleClass().add("filter-button");
        btnFilterAdmins.setOnAction(e -> {
            tableView.getItems().clear();
            controller.getUtilisateurs().stream()
                    .filter(p -> p instanceof AdminBibliotheque)
                    .forEach(tableView.getItems()::add);
        });

        Button btnShowAll = new Button("Tous");
        btnShowAll.getStyleClass().add("filter-button-primary");
        btnShowAll.setOnAction(e -> loadUsers(tableView));

        Button btnRefresh = new Button("Actualiser");
        btnRefresh.setOnAction(e -> loadUsers(tableView));

        HBox filterBox = new HBox(10,
                btnFilterLecteurs,
                btnFilterBibliothecaires,
                btnFilterAdmins,
                btnShowAll,
                btnRefresh
        );
        filterBox.setAlignment(Pos.CENTER);
        filterBox.setStyle("-fx-padding: 10 0;");

        return filterBox;
    }


    private VBox createDeleteSection(TableView<Personne> tableView, HBox statsBox) {
        Label deleteTitle = new Label(" Supprimer un utilisateur");
        deleteTitle.getStyleClass().add("section-title");

        Label infoLabel = new Label("Entrez le CIN de l'utilisateur à supprimer :");
        infoLabel.setStyle("-fx-text-fill: #4a5568;");

        TextField cinField = new TextField();
        cinField.setPromptText("Exemple : 12345678");
        cinField.setPrefWidth(300);

        Button btnSupprimer = new Button(" Supprimer");
        btnSupprimer.getStyleClass().add("delete-button");
        btnSupprimer.setPrefWidth(150);

        btnSupprimer.setOnAction(e -> {
            String cin = cinField.getText().trim();
            if (!cin.isEmpty()) {
                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setTitle("Confirmation");
                confirmAlert.setHeaderText("Supprimer l'utilisateur ?");
                confirmAlert.setContentText("Êtes-vous sûr de vouloir supprimer l'utilisateur avec le CIN : " + cin + " ?");

                confirmAlert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        try {
                            controller.supprimerUtilisateur(cin);
                            new Alert(Alert.AlertType.INFORMATION, " Utilisateur supprimé avec succès !").showAndWait();
                            loadUsers(tableView);
                            cinField.clear();

                            statsBox.getChildren().clear();
                            statsBox.getChildren().addAll(createStatsBox().getChildren());

                        } catch (UtilisateurNonTrouveException ex) {
                            new Alert(Alert.AlertType.ERROR, " Erreur : " + ex.getMessage()).showAndWait();
                        }
                    }
                });
            } else {
                new Alert(Alert.AlertType.WARNING, " Veuillez entrer un CIN.").showAndWait();
            }
        });

        HBox deleteInputBox = new HBox(10, cinField, btnSupprimer);
        deleteInputBox.setAlignment(Pos.CENTER_LEFT);

        VBox deleteSection = new VBox(10, deleteTitle, infoLabel, deleteInputBox);
        deleteSection.setStyle(
                "-fx-background-color: #fff5f5; " +
                        "-fx-border-color: #fc8181; " +
                        "-fx-border-width: 2; " +
                        "-fx-border-radius: 10; " +
                        "-fx-background-radius: 10; " +
                        "-fx-padding: 20;"
        );

        return deleteSection;
    }

    private void loadUsers(TableView<Personne> tableView) {
        tableView.getItems().clear();
        tableView.getItems().addAll(controller.getUtilisateurs());
    }
}