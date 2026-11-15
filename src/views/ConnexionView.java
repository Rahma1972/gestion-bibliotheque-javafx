package views;

import controle.ConnexionController;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;

public class ConnexionView {

    private final ConnexionController connexioncontroller;

    public ConnexionView(ConnexionController connexioncontroller) {
        this.connexioncontroller = connexioncontroller;
    }

    public void show(Stage stage) {
        Label titleLabel = new Label(" Système de Gestion");
        titleLabel.getStyleClass().add("main-title");
        titleLabel.setAlignment(Pos.CENTER);

        Label subtitleLabel = new Label("Bibliothèque Numérique");
        subtitleLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #718096; -fx-padding: 0 0 30 0;");
        subtitleLabel.setAlignment(Pos.CENTER);


        Label emailLabel = new Label(" Email");
        emailLabel.setStyle("-fx-font-weight: 600; -fx-text-fill: #4a5568;");
        TextField emailField = new TextField();
        emailField.setPromptText("exemple@email.com");
        emailField.setPrefHeight(45);


        Label passwordLabel = new Label(" Mot de passe");
        passwordLabel.setStyle("-fx-font-weight: 600; -fx-text-fill: #4a5568;");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("••••••••");
        passwordField.setPrefHeight(45);


        Button loginBtn = new Button("Se connecter");
        loginBtn.setPrefWidth(300);
        loginBtn.setPrefHeight(50);
        loginBtn.setStyle("-fx-font-size: 16px;");

        loginBtn.setOnAction(e -> {
            connexioncontroller.login(emailField.getText(), passwordField.getText(), stage);
        });


        passwordField.setOnAction(e -> loginBtn.fire());


        Label helpLabel = new Label("Besoin d'aide ? Contactez l'administrateur");
        helpLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #a0aec0; -fx-padding: 15 0 0 0;");
        helpLabel.setAlignment(Pos.CENTER);


        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(
                titleLabel,
                subtitleLabel,
                emailLabel,
                emailField,
                passwordLabel,
                passwordField,
                loginBtn,
                helpLabel
        );
        root.getStyleClass().add("v-box");
        root.setStyle("-fx-padding: 40;");

        Scene scene = new Scene(root, 450, 520);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setTitle("Connexion - Bibliothèque");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

        emailField.requestFocus();
    }
}