package com.example.authentification;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;

public class ResultController {

    @FXML
    public Label remark, marks, markstext, correcttext, wrongtext;

    @FXML
    public ProgressIndicator correct_progress, wrong_progress;

    private int score;

    public void setScore(int score) {
        this.score = score;
    }

    @FXML
    private void initialize() {
        int correct = score;
        int wrong = 10 - score;

        correcttext.setText("Correct Answers : " + correct);
        wrongtext.setText("Incorrect Answers : " + wrong);

        marks.setText(score + "/10");
        float correctf = (float) score/10;
        correct_progress.setProgress(correctf);

        float wrongf = (float) wrong/10;
        wrong_progress.setProgress(wrongf);


        markstext.setText(score + " Marks Scored");

        if (score < 2) {
            remark.setText("Oh no..! You have failed the quiz. It seems that you need to improve your general knowledge. Practice daily! Check your results here.");
        } else if (score >= 2 && score < 5) {
            remark.setText("Oops..! You have scored less marks. It seems like you need to improve your general knowledge. Check your results here.");
        } else if (score >= 5 && score <= 7) {
            remark.setText("Good. A bit more improvement might help you to get better results. Practice is the key to success. Check your results here.");
        } else if (score == 8 || score == 9) {
            remark.setText("Congratulations! Its your hardwork and determination which helped you to score good marks. Check you results here.");
        } else if (score == 10) {
            remark.setText("Congratulations! You have passed the quiz with full marks because of your hardwork and dedication towards studies. Keep it up! Check your results here.");
        }
    }
}