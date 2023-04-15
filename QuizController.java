package com.example.authentification;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuizController {

    @FXML
    private Label questionLabel;

    @FXML
    private RadioButton option1;

    @FXML
    private RadioButton option2;

    @FXML
    private RadioButton option3;

    @FXML
    private RadioButton option4;

    @FXML
    private Button nextButton;

    private List<Question> questions;
    private int currentQuestionIndex;
    private ToggleGroup toggleGroup;

    public void initialize() {
        toggleGroup = new ToggleGroup();
        option1.setToggleGroup(toggleGroup);
        option2.setToggleGroup(toggleGroup);
        option3.setToggleGroup(toggleGroup);
        option4.setToggleGroup(toggleGroup);
        loadQuestionsFromDatabase();
        showCurrentQuestion();
    }

    private void loadQuestionsFromDatabase() {
        questions = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quiz", "root", "password");
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM questions");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                List<String> options = new ArrayList<>();
                options.add(rs.getString("option1"));
                options.add(rs.getString("option2"));
                options.add(rs.getString("option3"));
                options.add(rs.getString("option4"));
                Question question = new Question(
                        rs.getInt("id"),
                        rs.getString("question"),
                        options,
                        rs.getInt("answer")
                );
                questions.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showCurrentQuestion() {
        Question currentQuestion = questions.get(currentQuestionIndex);
        questionLabel.setText(currentQuestion.getQuestion());
        List<String> options = Arrays.asList(currentQuestion.getOptions());
        option1.setText(options.get(0));
        option2.setText(options.get(1));
        option3.setText(options.get(2));
        option4.setText(options.get(3));
        toggleGroup.selectToggle(null);
    }
    @FXML
    public void nextQuestion() {
        RadioButton selectedOption = (RadioButton) toggleGroup.getSelectedToggle();
        if (selectedOption == null) {
            return;
        }
        int selectedAnswer = Integer.parseInt(selectedOption.getUserData().toString());
        Question currentQuestion = questions.get(currentQuestionIndex);
        if (selectedAnswer == currentQuestion.getAnswer()) {
            // TODO: Ajouter des points pour la bonne réponse
        }
        currentQuestionIndex++;
        if (currentQuestionIndex >= questions.size()) {
            checkAnswer();
            return;
        }
        showCurrentQuestion();
    }

    private void checkAnswer() {
        int score = 0;
        for (Question question : questions) {
            if (question.getAnswer() == getSelectedAnswer(question)) {
                score++;
            }
        }
        ResultController resultController = new ResultController();
        resultController.setScore(score); // Passer le score à ResultController
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("result.fxml"));
            loader.setController(resultController);
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Résultat");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private int getSelectedAnswer(Question question) {
        RadioButton selectedOption = (RadioButton) toggleGroup.getSelectedToggle();
        if (selectedOption == option1) {
            return 1;
        } else if (selectedOption == option2) {
            return 2;
        } else if (selectedOption == option3) {
            return 3;
        } else if (selectedOption == option4) {
            return 4;
        } else {
            return -1;
        }
    }
}
