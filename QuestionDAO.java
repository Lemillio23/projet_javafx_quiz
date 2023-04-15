package com.example.authentification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {
    private Connection connection;
    private PreparedStatement stmt;

    public QuestionDAO() {
        connection = ConnectionUtil.getConnection();
    }

    public boolean addQuestion(Question question) {
        try {
            stmt = connection.prepareStatement("INSERT INTO questions (question, option1, option2, option3, option4, answer) VALUES (?, ?, ?, ?, ?, ?)");

            stmt.setString(1, question.getQuestion());
            stmt.setString(2, question.getOptions()[0]);
            stmt.setString(3, question.getOptions()[1]);
            stmt.setString(4, question.getOptions()[2]);
            stmt.setString(5, question.getOptions()[3]);
            stmt.setInt(6, question.getAnswer());

            int result = stmt.executeUpdate();

            if (result == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.close(stmt);
        }
        return false;
    }

    public boolean updateQuestion(Question question) {
        try {
            stmt = connection.prepareStatement("UPDATE questions SET question=?, option1=?, option2=?, option3=?, option4=?, answer=? WHERE id=?");

            stmt.setString(1, question.getQuestion());
            stmt.setString(2, question.getOptions()[0]);
            stmt.setString(3, question.getOptions()[1]);
            stmt.setString(4, question.getOptions()[2]);
            stmt.setString(5, question.getOptions()[3]);
            stmt.setInt(6, question.getAnswer());
            stmt.setInt(7, question.getId());

            int result = stmt.executeUpdate();

            if (result == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.closeConnection(stmt);
        }
        return false;
    }

    public boolean deleteQuestion(int id) {
        try {
            stmt = connection.prepareStatement("DELETE FROM questions WHERE id=?");

            stmt.setInt(1, id);

            int result = stmt.executeUpdate();

            if (result == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.close(stmt);
        }
        return false;
    }

    public Question getQuestionById(int id) {
        Question question = null;

        try {
            stmt = connection.prepareStatement("SELECT * FROM questions WHERE id=?");

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                question = getQuestionFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.close(stmt);
        }

        return question;
    }

    public List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<>();

        try {
            stmt = connection.prepareStatement("SELECT * FROM questions");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Question question = getQuestionFromResultSet(rs);
                questions.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.close(stmt);
        }

        return questions;
    }

    private Question getQuestionFromResultSet(ResultSet rs) throws SQLException {
        String questionText = rs.getString("question");
        String[] options = new String[4];
        options[0] = rs.getString("option1");
        options[1] = rs.getString("option2");
        options[2] = rs.getString("option3");
        options[3] = rs.getString("option4");
        int answer = rs.getInt("answer");

        Question question = new Question(questionText, options, answer);
        question.setId(rs.getInt("id"));

        return question;
    }
}