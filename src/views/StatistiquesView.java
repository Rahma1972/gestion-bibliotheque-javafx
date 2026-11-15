package views;

import controle.StatistiquesController;
import models.media.StatistiquesMedia;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;

public class StatistiquesView {
    private final StatistiquesController controller;

    public StatistiquesView(StatistiquesController controller) {
        this.controller = controller;
    }

    public void show(Stage stage) {
        Label titleLabel = new Label(" Statistiques de la Bibliothèque");
        titleLabel.getStyleClass().add("main-title");
        Label subtitleLabel = new Label("Vue d'ensemble des médias disponibles");
        subtitleLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #718096; -fx-padding: 0 0 30 0;");
        StatistiquesMedia stats = controller.calculerStatistiques();
        VBox livresCard = createLargeStatCard(
                "",
                "Livres",
                String.valueOf(stats.nbLivres()),
                "#667eea"
        );

        VBox magazinesCard = createLargeStatCard(
                "",
                "Magazines",
                String.valueOf(stats.nbMagazines()),
                "#48bb78"
        );

        VBox dictionnairesCard = createLargeStatCard(
                "",
                "Dictionnaires",
                String.valueOf(stats.nbDictionnaires()),
                "#f6ad55"
        );


        int total = stats.nbLivres() + stats.nbMagazines() + stats.nbDictionnaires();
        VBox totalCard = createLargeStatCard(
                "",
                "Total Médias",
                String.valueOf(total),
                "#9f7aea"
        );


        GridPane statsGrid = new GridPane();
        statsGrid.setHgap(20);
        statsGrid.setVgap(20);
        statsGrid.setAlignment(Pos.CENTER);

        statsGrid.add(livresCard, 0, 0);
        statsGrid.add(magazinesCard, 1, 0);
        statsGrid.add(dictionnairesCard, 0, 1);
        statsGrid.add(totalCard, 1, 1);

        VBox detailsBox = createDetailsBox(stats);

        VBox chartBox = createTextChart(stats);

        VBox root = new VBox(30,
                titleLabel,
                subtitleLabel,
                statsGrid,
                detailsBox,
                chartBox
        );
        root.setAlignment(Pos.TOP_CENTER);
        root.getStyleClass().add("v-box");

        Scene scene = new Scene(root, 750, 800);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setTitle("Statistiques - Bibliothèque");
        stage.setScene(scene);
        stage.show();
    }
    private VBox createLargeStatCard(String icon, String label, String value, String color) {
        Label iconLabel = new Label(icon);
        iconLabel.setStyle("-fx-font-size: 48px;");

        Label valueLabel = new Label(value);
        valueLabel.setStyle("-fx-font-size: 42px; -fx-font-weight: bold; -fx-text-fill: " + color + ";");

        Label labelText = new Label(label);
        labelText.setStyle("-fx-font-size: 16px; -fx-text-fill: #4a5568; -fx-font-weight: 600;");

        VBox card = new VBox(12, iconLabel, valueLabel, labelText);
        card.setAlignment(Pos.CENTER);
        card.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: " + color + "; " +
                        "-fx-border-width: 3; " +
                        "-fx-border-radius: 15; " +
                        "-fx-background-radius: 15; " +
                        "-fx-padding: 35; " +
                        "-fx-min-width: 220; " +
                        "-fx-min-height: 200; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 10, 0, 0, 5);"
        );


        card.setOnMouseEntered(e -> {
            card.setStyle(
                    "-fx-background-color: " + color + "; " +
                            "-fx-border-color: " + color + "; " +
                            "-fx-border-width: 3; " +
                            "-fx-border-radius: 15; " +
                            "-fx-background-radius: 15; " +
                            "-fx-padding: 35; " +
                            "-fx-min-width: 220; " +
                            "-fx-min-height: 200; " +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 15, 0, 0, 8); " +
                            "-fx-scale-x: 1.05; " +
                            "-fx-scale-y: 1.05;"
            );
            valueLabel.setStyle("-fx-font-size: 42px; -fx-font-weight: bold; -fx-text-fill: white;");
            labelText.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-font-weight: 600;");
        });

        card.setOnMouseExited(e -> {
            card.setStyle(
                    "-fx-background-color: white; " +
                            "-fx-border-color: " + color + "; " +
                            "-fx-border-width: 3; " +
                            "-fx-border-radius: 15; " +
                            "-fx-background-radius: 15; " +
                            "-fx-padding: 35; " +
                            "-fx-min-width: 220; " +
                            "-fx-min-height: 200; " +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 10, 0, 0, 5);"
            );
            valueLabel.setStyle("-fx-font-size: 42px; -fx-font-weight: bold; -fx-text-fill: " + color + ";");
            labelText.setStyle("-fx-font-size: 16px; -fx-text-fill: #4a5568; -fx-font-weight: 600;");
        });

        return card;
    }


    private VBox createDetailsBox(StatistiquesMedia stats) {
        int total = stats.nbLivres() + stats.nbMagazines() + stats.nbDictionnaires();

        double pctLivres = total > 0 ? (stats.nbLivres() * 100.0 / total) : 0;
        double pctMagazines = total > 0 ? (stats.nbMagazines() * 100.0 / total) : 0;
        double pctDictionnaires = total > 0 ? (stats.nbDictionnaires() * 100.0 / total) : 0;

        Label detailsTitle = new Label(" Répartition détaillée");
        detailsTitle.getStyleClass().add("section-title");

        Label livresDetail = new Label(String.format(" Livres : %d (%.1f%%)", stats.nbLivres(), pctLivres));
        livresDetail.setStyle("-fx-font-size: 15px; -fx-text-fill: #4a5568; -fx-padding: 5;");

        Label magazinesDetail = new Label(String.format("Magazines : %d (%.1f%%)", stats.nbMagazines(), pctMagazines));
        magazinesDetail.setStyle("-fx-font-size: 15px; -fx-text-fill: #4a5568; -fx-padding: 5;");

        Label dictionnairesDetail = new Label(String.format(" Dictionnaires : %d (%.1f%%)", stats.nbDictionnaires(), pctDictionnaires));
        dictionnairesDetail.setStyle("-fx-font-size: 15px; -fx-text-fill: #4a5568; -fx-padding: 5;");

        Label totalDetail = new Label(String.format(" Total : %d médias", total));
        totalDetail.setStyle("-fx-font-size: 16px; -fx-text-fill: #2d3748; -fx-font-weight: bold; -fx-padding: 10 5 5 5;");

        VBox detailsBox = new VBox(8,
                detailsTitle,
                livresDetail,
                magazinesDetail,
                dictionnairesDetail,
                new Label(""),
                totalDetail
        );
        detailsBox.setStyle(
                "-fx-background-color: #f7fafc; " +
                        "-fx-border-color: #cbd5e0; " +
                        "-fx-border-width: 2; " +
                        "-fx-border-radius: 12; " +
                        "-fx-background-radius: 12; " +
                        "-fx-padding: 25; " +
                        "-fx-alignment: CENTER-LEFT;"
        );

        return detailsBox;
    }


    private VBox createTextChart(StatistiquesMedia stats) {
        int total = stats.nbLivres() + stats.nbMagazines() + stats.nbDictionnaires();
        int maxVal = Math.max(stats.nbLivres(), Math.max(stats.nbMagazines(), stats.nbDictionnaires()));

        Label chartTitle = new Label("Graphique de répartition");
        chartTitle.getStyleClass().add("section-title");

        VBox chartContent = new VBox(15);
        chartContent.setStyle("-fx-padding: 20;");

        chartContent.getChildren().add(createBar("Livres", stats.nbLivres(), maxVal, "#667eea"));

        chartContent.getChildren().add(createBar(" Magazines", stats.nbMagazines(), maxVal, "#48bb78"));

        chartContent.getChildren().add(createBar(" Dictionnaires", stats.nbDictionnaires(), maxVal, "#f6ad55"));

        VBox chartBox = new VBox(15, chartTitle, chartContent);
        chartBox.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #e2e8f0; " +
                        "-fx-border-width: 2; " +
                        "-fx-border-radius: 12; " +
                        "-fx-background-radius: 12; " +
                        "-fx-padding: 25;"
        );

        return chartBox;
    }
    private HBox createBar(String label, int value, int maxValue, String color) {
        Label labelText = new Label(label);
        labelText.setStyle("-fx-font-size: 14px; -fx-font-weight: 600; -fx-min-width: 150;");

        double percentage = maxValue > 0 ? (value * 100.0 / maxValue) : 0;
        double barWidth = Math.max(50, percentage * 3); // Minimum 50px, max 300px

        Region bar = new Region();
        bar.setPrefHeight(30);
        bar.setPrefWidth(barWidth);
        bar.setStyle(
                "-fx-background-color: " + color + "; " +
                        "-fx-background-radius: 5;"
        );

        Label valueLabel = new Label(String.valueOf(value));
        valueLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 0 0 0 10;");

        HBox barBox = new HBox(10, labelText, bar, valueLabel);
        barBox.setAlignment(Pos.CENTER_LEFT);

        return barBox;
    }
}