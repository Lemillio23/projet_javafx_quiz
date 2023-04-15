package com.example.authentification;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static java.sql.DriverManager.*;

public class Controller implements Initializable {

    private double x = 0, y = 0;

    @FXML
    private JFXButton loginButton;

    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;

    @FXML
    private AnchorPane sideBar;

    private Scene stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sideBar.setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
        });

        sideBar.setOnMouseDragged(mouseEvent -> {
            stage.getX();
            stage.getY();
        });
    }

    public void setStage(Scene stage) {
        this.stage = stage;
    }

    public void closeProgram() {
        System.exit(0);
    }

    @FXML
    void login(ActionEvent event) {
        String username = this.username.getText();
        String password = this.password.getText();
        try {
            // Vérification de l'utilisateur dans la table "utilisateurs"
            PreparedStatement stmt;
            Connection connection = getConnection("jdbc:mysql://localhost:3306/nom_de_la_base_de_donnees", "nom_d_utilisateur", "mot_de_passe");
            stmt = connection.prepareStatement("SELECT * FROM utilisateurs WHERE nom_utilisateur = ? AND mot_de_passe = ?");
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // L'utilisateur est connecté en tant qu'utilisateur
                System.out.println("Connexion réussie en tant qu'utilisateur");
                // Ajoutez ici le code pour rediriger l'utilisateur vers la page d'accueil des utilisateurs
            } else {
                // Vérification de l'administrateur dans la table "administrateurs"
                PreparedStatement stmt2 = connection.prepareStatement("SELECT * FROM administrateurs WHERE nom_utilisateur = ? AND mot_de_passe = ?");
                stmt2.setString(1, username);
                stmt2.setString(2, password);
                ResultSet rs2 = stmt2.executeQuery();
                if (rs2.next()) {
                    // L'utilisateur est connecté en tant qu'administrateur
                    System.out.println("Connexion réussie en tant qu'administrateur");
                    // Ajoutez ici le code pour rediriger l'utilisateur vers la page d'accueil des administrateurs
                } else {
                    // L'utilisateur n'est ni un utilisateur ni un administrateur valide
                    System.out.println("Nom d'utilisateur ou mot de passe incorrect");
                    // Afficher un message d'erreur à l'utilisateur
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Afficher un message d'erreur à l'utilisateur
        }
    }
}