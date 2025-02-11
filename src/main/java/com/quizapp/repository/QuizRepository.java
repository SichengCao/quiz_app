package com.quizapp.repository;

import com.quizapp.model.Quiz;
import com.quizapp.model.Question;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class QuizRepository {
    private final JdbcTemplate jdbcTemplate;

    public QuizRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Create a new quiz and return the generated ID
    public int createQuiz(Quiz quiz) {
        String sql = "INSERT INTO quiz (title, created_by) VALUES (?, ?)";
        jdbcTemplate.update(sql, quiz.getTitle(), quiz.getCreatedBy());
        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    }

    // Add a list of questions to a quiz
    public void addQuestions(int quizId, List<Question> questions) {
        String sql = "INSERT INTO quiz_question (quiz_id, question_text, option_a, option_b, option_c, option_d, correct_option) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        for (Question q : questions) {
            String correctOpt = q.getCorrectOption().trim().substring(0, 1).toUpperCase(); // 仅保留第一个字母
            jdbcTemplate.update(sql,
                    quizId,
                    q.getQuestionText(),
                    q.getOptionA(),
                    q.getOptionB(),
                    q.getOptionC(),
                    q.getOptionD(),
                    correctOpt
            );
            System.out.println("📌 Debug: Storing correct answer [" + correctOpt + "] for question: " + q.getQuestionText());
        }
    }


    // Get random questions from a specific category
    public List<Question> getRandomQuestions(int categoryId, int limit) {
        String sql = "SELECT * FROM quiz_question WHERE category_id = ? ORDER BY RAND() LIMIT ?";
        return jdbcTemplate.query(sql, new Object[]{categoryId, limit}, (rs, rowNum) -> new Question(
                rs.getInt("qq_id"),
                rs.getInt("quiz_id"),
                rs.getString("question_text"),
                rs.getString("option_a"),
                rs.getString("option_b"),
                rs.getString("option_c"),
                rs.getString("option_d"),
                rs.getString("correct_option")
        ));
    }

    //  Calculate quiz score
    public int calculateScore(Map<String, String> answers) {
        int score = 0;
        String sql = "SELECT correct_option FROM quiz_question WHERE qq_id = ?";

        for (Map.Entry<String, String> entry : answers.entrySet()) {
            int questionId = Integer.parseInt(entry.getKey());
            String userAnswer = entry.getValue();
            String correctAnswer = jdbcTemplate.queryForObject(sql, new Object[]{questionId}, String.class);

            if (userAnswer.equalsIgnoreCase(correctAnswer)) {
                score++;
            }
        }
        return score * 10; // Each question is worth 10 points
    }
}
