package com.example.authentification;

import java.util.List;

public class Question {
    private int id;
    private String question;
    private String[] options;
    private int answer;

    public Question(int id, String question, List<String> options, int answer) {
        this.id = id;
        this.question = question;
        this.options = options.toArray(new String[0]);
        this.answer = answer;
    }

    public Question(String question, String[] options, int answer) {
        this.question = question;
        this.options = options;
        this.answer = answer;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}