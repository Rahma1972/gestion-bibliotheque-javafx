package views;

import controle.GestionMediasController;
import models.media.Media;
import models.media.Livre;
import models.media.Magazine;
import models.media.Dictionnaire;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;

public class GestionMediasView {

    private final GestionMediasController controller;

    public GestionMediasView(GestionMediasController controller) {
        this.controller = controller;
    }

    public void show(Stage stage) {
        Label titleLabel = new Label(" Gestion des Médias");
        titleLabel.getStyleClass().add("main-title");

        HBox statsBox = createStatsBox();
        TableView<Media> tableView = new TableView<>();
        tableView.setPrefSize(850, 350);

        TableColumn<Media, String> titreCol = new TableColumn<>(" Titre");
        titreCol.setCellValueFactory(new PropertyValueFactory<>("titre"));
        titreCol.setPrefWidth(300);

        TableColumn<Media, Integer> anneeCol = new TableColumn<>("Année");
        anneeCol.setCellValueFactory(new PropertyValueFactory<>("annee"));
        anneeCol.setPrefWidth(100);
        anneeCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Media, String> typeCol = new TableColumn<>(" Type");
        typeCol.setCellValueFactory(param -> {
            Media m = param.getValue();
            String type;
            if (m instanceof Livre) {
                type = "Livre";
            } else if (m instanceof Magazine) {
                type = "Magazine";
            } else if (m instanceof Dictionnaire) {
                type = "Dictionnaire";
            } else {
                type = "Inconnu";
            }
            return new javafx.beans.property.SimpleStringProperty(type);
        });
        typeCol.setPrefWidth(150);
        typeCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Media, String> dispoCol = new TableColumn<>(" Disponibilité");
        dispoCol.setCellValueFactory(param -> {
            boolean dispo = param.getValue().isDisponible();
            return new javafx.beans.property.SimpleStringProperty(dispo ? " Disponible" : " Emprunté");
        });
        dispoCol.setPrefWidth(150);
        dispoCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Media, String> detailsCol = new TableColumn<>("Détails");
        detailsCol.setCellValueFactory(param -> {
            Media m = param.getValue();
            String details = "";
            if (m instanceof Livre) {
                Livre livre = (Livre) m;
                details = "Auteur: " + (livre.getAuteur() != null ? livre.getAuteur() : "N/A");
            } else if (m instanceof Magazine) {
                Magazine mag = (Magazine) m;
                details = "N°: " + mag.getNumero();
            } else if (m instanceof Dictionnaire) {
                Dictionnaire dict = (Dictionnaire) m;
                details = "Langue: " + (dict.getLangue() != null ? dict.getLangue() : "N/A");
            }
            return new javafx.beans.property.SimpleStringProperty(details);
        });
        detailsCol.setPrefWidth(200);

        tableView.getColumns().addAll(titreCol, anneeCol, typeCol, dispoCol, detailsCol);
        loadMedias(tableView);
        HBox searchBox = createSearchBox(tableView);
        HBox actionBox = createActionButtons(tableView);

        VBox root = new VBox(20,
                titleLabel,
                statsBox,
                searchBox,
                new Label("Liste complète des médias :"),
                tableView,
                actionBox
        );
        root.getStyleClass().add("v-box");

        Scene scene = new Scene(root, 900, 700);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        stage.setTitle("Gestion Médias - Bibliothèque");
        stage.setScene(scene);
        stage.show();
    }

    private HBox createStatsBox() {
        var medias = controller.getMedias();
        long nbLivres = medias.stream().filter(m -> m instanceof Livre).count();
        long nbMagazines = medias.stream().filter(m -> m instanceof Magazine).count();
        long nbDictionnaires = medias.stream().filter(m -> m instanceof Dictionnaire).count();
        long nbDisponibles = medias.stream().filter(Media::isDisponible).count();

        VBox livresBox = createStatCard("", "Livres", String.valueOf(nbLivres));
        VBox magazinesBox = createStatCard("", "Magazines", String.valueOf(nbMagazines));
        VBox dictionnairesBox = createStatCard("", "Dictionnaires", String.valueOf(nbDictionnaires));
        VBox disponiblesBox = createStatCard("", "Disponibles", String.valueOf(nbDisponibles));

        HBox statsBox = new HBox(15, livresBox, magazinesBox, dictionnairesBox, disponiblesBox);
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

    private HBox createSearchBox(TableView<Media> tableView) {
        TextField searchField = new TextField();
        searchField.setPromptText(" Rechercher un média par titre...");
        searchField.setPrefWidth(400);

        Button searchBtn = new Button("Rechercher");
        searchBtn.getStyleClass().add("filter-button-primary");

        searchBtn.setOnAction(e -> {
            String searchText = searchField.getText().trim().toLowerCase();
            if (searchText.isEmpty()) {
                loadMedias(tableView);
            } else {
                tableView.getItems().clear();
                controller.getMedias().stream()
                        .filter(m -> m.getTitre().toLowerCase().contains(searchText))
                        .forEach(tableView.getItems()::add);
            }
        });

        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            String searchText = newVal.trim().toLowerCase();
            if (searchText.isEmpty()) {
                loadMedias(tableView);
            } else {
                tableView.getItems().clear();
                controller.getMedias().stream()
                        .filter(m -> m.getTitre().toLowerCase().contains(searchText))
                        .forEach(tableView.getItems()::add);
            }
        });

        HBox searchBox = new HBox(10, searchField, searchBtn);
        searchBox.setAlignment(Pos.CENTER_LEFT);

        return searchBox;
    }

    private HBox createActionButtons(TableView<Media> tableView) {
        //Button btnRefresh = new Button("Actualiser");
        //btnRefresh.getStyleClass().add("filter-button");
        //btnRefresh.setOnAction(e -> loadMedias(tableView));

        Button btnFilterLivres = new Button("Livres");
        btnFilterLivres.getStyleClass().add("filter-button");
        btnFilterLivres.setOnAction(e -> {
            tableView.getItems().clear();
            controller.getMedias().stream()
                    .filter(m -> m instanceof Livre)
                    .forEach(tableView.getItems()::add);
        });

        Button btnFilterMagazines = new Button(" Magazines");
        btnFilterMagazines.getStyleClass().add("filter-button");
        btnFilterMagazines.setOnAction(e -> {
            tableView.getItems().clear();
            controller.getMedias().stream()
                    .filter(m -> m instanceof Magazine)
                    .forEach(tableView.getItems()::add);
        });

        Button btnFilterDictionnaires = new Button(" Dictionnaires");
        btnFilterDictionnaires.getStyleClass().add("filter-button");
        btnFilterDictionnaires.setOnAction(e -> {
            tableView.getItems().clear();
            controller.getMedias().stream()
                    .filter(m -> m instanceof Dictionnaire)
                    .forEach(tableView.getItems()::add);
        });

        Button btnFilterDisponibles = new Button(" Disponibles");
        btnFilterDisponibles.getStyleClass().add("filter-button-primary");
        btnFilterDisponibles.setOnAction(e -> {
            tableView.getItems().clear();
            controller.getMedias().stream()
                    .filter(Media::isDisponible)
                    .forEach(tableView.getItems()::add);
        });

        Button btnShowAll = new Button(" Tout afficher");
        btnShowAll.setOnAction(e -> loadMedias(tableView));

        HBox actionBox = new HBox(10,
                //btnRefresh,
                btnFilterLivres,
                btnFilterMagazines,
                btnFilterDictionnaires,
                btnFilterDisponibles,
                btnShowAll
        );
        actionBox.setAlignment(Pos.CENTER);
        actionBox.setStyle("-fx-padding: 10 0 0 0;");

        return actionBox;
    }

    private void loadMedias(TableView<Media> tableView) {
        tableView.getItems().clear();
        tableView.getItems().addAll(controller.getMedias());
    }
}