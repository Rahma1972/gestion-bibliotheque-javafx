package views;

import controle.TabDeBordController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;

public class TabDeBordView {
    private final TabDeBordController controller;
    private final Stage stage;

    public TabDeBordView(TabDeBordController controller, Stage stage) {
        this.controller = controller;
        this.stage = stage;
    }

    public void show() {
        Label titleLabel = new Label(" Tableau de Bord - Bibliothèque");
        titleLabel.getStyleClass().add("main-title");

        Label subtitleLabel = new Label("Gérez votre bibliothèque efficacement");
        subtitleLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #718096; -fx-padding: 0 0 20 0;");

        Button btnUtilisateurs = createDashboardButton(" Gestion Utilisateurs",
                "Gérer les lecteurs et le personnel");
        Button btnMedias = createDashboardButton("Gestion Médias",
                "Gérer livres, magazines et dictionnaires");
        Button btnEmprunts = createDashboardButton("Emprunts",
                "Valider et suivre les emprunts");
        Button btnStats = createDashboardButton("Statistiques",
                "Consulter les statistiques");
        Button btnRecherche = createDashboardButton("Recherche Avancée",
                "Filtrer et rechercher dans la base");

        Button btnLogout = new Button("Se déconnecter");
        btnLogout.getStyleClass().add("logout-button");
        btnLogout.setPrefWidth(250);
        btnLogout.setPrefHeight(45);

        btnUtilisateurs.setOnAction(e -> controller.openGestionUtilisateurs());
        btnMedias.setOnAction(e -> controller.openGestionMedias());
        btnEmprunts.setOnAction(e -> controller.openEmprunts());
        btnStats.setOnAction(e -> controller.openStatistiques());
        btnRecherche.setOnAction(e -> controller.openRechercheAvancee());
        btnLogout.setOnAction(e -> controller.logout(stage));

        GridPane gridPane = new GridPane();
        gridPane.setHgap(15);
        gridPane.setVgap(15);
        gridPane.setAlignment(Pos.CENTER);

        gridPane.add(btnUtilisateurs, 0, 0);
        gridPane.add(btnMedias, 1, 0);
        gridPane.add(btnEmprunts, 0, 1);
        gridPane.add(btnStats, 1, 1);
        gridPane.add(btnRecherche, 0, 2);

        VBox root = new VBox(25);
        root.setAlignment(Pos.TOP_CENTER);
        root.getChildren().addAll(titleLabel, subtitleLabel, gridPane, btnLogout);
        root.getStyleClass().add("v-box");

        Scene scene = new Scene(root, 600, 650);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setTitle("TableauDeBord - Bibliothèque");
        stage.setScene(scene);
        stage.show();
    }

    private Button createDashboardButton(String title, String description) {
        Button btn = new Button(title);
        btn.setPrefWidth(250);
        btn.setPrefHeight(80);
        btn.setStyle(
                "-fx-font-size: 15px; " +
                        "-fx-alignment: CENTER-LEFT; " +
                        "-fx-content-display: TOP;"
        );
        
        return btn;
    }
}