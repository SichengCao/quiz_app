package com.quizapp.service;

import com.quizapp.model.*;
import com.quizapp.repository.QuizRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class QuizService {
    private final QuizRepository quizRepository;
    private final JdbcTemplate jdbcTemplate;

    public QuizService(QuizRepository quizRepository,JdbcTemplate jdbcTemplate) {
        this.quizRepository = quizRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public int createQuiz(int userId, int categoryId, String title) {
        String sql = "INSERT INTO quiz (user_id, category_id, title, name, created_by, time_start) VALUES (?, ?, ?, 'DEBUG', ?, NOW())";
        jdbcTemplate.update(sql, userId, categoryId, title, userId);
        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    }
    public String getCategoryName(int categoryId) {
        String sql = "SELECT name FROM category WHERE category_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{categoryId}, String.class);
    }


    public Integer findExistingQuiz(int userId, int categoryId) {
        String sql = "SELECT quiz_id FROM quiz WHERE user_id = ? AND category_id = ? ORDER BY time_start DESC LIMIT 1";
        try {
            Integer quizId = jdbcTemplate.queryForObject(sql, Integer.class, userId, categoryId);
            System.out.println(" Debug: Fetched existing quizId = " + quizId);
            return quizId;
        } catch (EmptyResultDataAccessException e) {
            System.out.println(" No existing quiz found for userId: " + userId + ", categoryId: " + categoryId);
            return null;
        }
    }





    public void addQuestionToQuiz(int quizId, Question question) {
        String sql = "INSERT INTO quiz_question (quiz_id, question_text, option_a, option_b, option_c, option_d, correct_option) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, quizId, question.getQuestionText(), question.getOptionA(), question.getOptionB(), question.getOptionC(), question.getOptionD(), question.getCorrectOption());
        System.out.println(" Debug: Inserting question for quizId = " + quizId);
        System.out.println(" Debug: Question details -> " + question);
    }

    public List<Category> getAllCategories() {
        String sql = "SELECT * FROM category";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Category(rs.getInt("category_id"), rs.getString("name")));
    }
    public List<Quiz> getRecentQuizzes(int userId) {
        String sql = "SELECT DISTINCT q.quiz_id, q.title, q.created_by, MAX(ua.submitted_at) as last_attempt " +
                "FROM user_answers ua " +
                "JOIN quiz_question qq ON ua.question_id = qq.question_id " +
                "JOIN quiz q ON qq.quiz_id = q.quiz_id " +
                "WHERE ua.user_id = ? " +
                "GROUP BY q.quiz_id, q.title, q.created_by " +
                "ORDER BY last_attempt DESC LIMIT 5";

        return jdbcTemplate.query(sql, new Object[]{userId}, (rs, rowNum) ->
                new Quiz(
                        rs.getInt("quiz_id"),
                        rs.getString("title"),
                        rs.getInt("created_by"),
                        rs.getTimestamp("last_attempt").toLocalDateTime()
                )
        );
    }



    public String getCorrectAnswer(int questionId) {
        System.out.println(" Debug: Fetching correct answer for questionId = " + questionId);

        String sqlCheck = "SELECT COUNT(*) FROM quiz_question WHERE qq_id = ?";
        int count = jdbcTemplate.queryForObject(sqlCheck, new Object[]{questionId}, Integer.class);

        if (count == 0) {
            System.out.println("Warning: No question found for questionId = " + questionId);
            return null; // 防止查询失败
        }

        String sql = "SELECT correct_option FROM quiz_question WHERE qq_id = ?";
        try {
            String correctAnswer = jdbcTemplate.queryForObject(sql, new Object[]{questionId}, String.class);
            System.out.println(" Debug: Correct answer for questionId " + questionId + " = " + correctAnswer);
            return correctAnswer;
        } catch (EmptyResultDataAccessException e) {
            System.out.println(" Warning: No correct answer found for questionId = " + questionId);
            return null;
        }
    }

    public void storeUserAnswer(int userId, int questionId, String userAnswer, boolean isCorrect) {
        String sql = "INSERT INTO user_answers (user_id, question_id, user_choice, is_correct) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, userId, questionId, userAnswer, isCorrect ? 1 : 0);
        System.out.println(" Debug: Stored user answer -> userId: " + userId + ", questionId: " + questionId + ", userAnswer: " + userAnswer + ", isCorrect: " + isCorrect);
    }

//    public List<Map<String, Object>> getQuizResults(int userId, int quizId) {
//        String sql = "SELECT q.question_text, ua.user_choice, q.correct_option, " +
//                "(CASE WHEN ua.user_choice = q.correct_option THEN 1 ELSE 0 END) as is_correct " +
//                "FROM user_answers ua " +
//                "JOIN quiz_question qq ON ua.question_id = qq.question_id " +
//                "JOIN question q ON qq.question_id = q.question_id " +
//                "WHERE ua.user_id = ? AND qq.quiz_id = ?";
//
//        return jdbcTemplate.query(sql, new Object[]{userId, quizId}, (rs, rowNum) -> {
//            Map<String, Object> result = new HashMap<>();
//            result.put("questionText", rs.getString("question_text"));
//            result.put("userAnswer", rs.getString("user_choice"));
//            result.put("correctAnswer", rs.getString("correct_option"));
//            result.put("isCorrect", rs.getInt("is_correct") == 1);
//            return result;
//        });
//    }

    public List<QuizResult> getQuizResults(int userId, int quizId) {
        String sql = """
            SELECT 
                q.description AS question_text,
                ua.user_choice COLLATE utf8mb4_unicode_ci AS user_answer,
                qq.correct_option COLLATE utf8mb4_unicode_ci AS correct_answer,
                (CASE 
                    WHEN ua.user_choice COLLATE utf8mb4_unicode_ci = qq.correct_option COLLATE utf8mb4_unicode_ci 
                    THEN 1 ELSE 0 
                 END) AS is_correct
            FROM user_answers ua
            JOIN quiz_question qq ON ua.question_id = qq.question_id
            JOIN question q ON ua.question_id = q.question_id
            WHERE ua.user_id = ? AND qq.quiz_id = ?;
        """;

        System.out.println("Debug: Fetching results for userId = " + userId + ", quizId = " + quizId );

        return jdbcTemplate.query(sql, new Object[]{userId, quizId}, (rs, rowNum) -> new QuizResult(
                rs.getString("question_text"),
                rs.getString("user_answer"),
                rs.getString("correct_answer"),
                rs.getBoolean("is_correct")
        ));
    }
    public String getQuizTitle(int quizId) {
        String sql = "SELECT title FROM quiz WHERE quiz_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{quizId}, String.class);
    }





    public List<Question> getQuizQuestions(int quizId) {
        String sql = "SELECT question_id, quiz_id, question_text, option_a, option_b, option_c, option_d, correct_option FROM quiz_question WHERE quiz_id = ?";

        return jdbcTemplate.query(sql, new Object[]{quizId}, (rs, rowNum) -> new Question(
                rs.getInt("question_id"),  //  Ensure this matches your column name in the database
                rs.getInt("quiz_id"),
                rs.getString("question_text"),
                rs.getString("option_a"),
                rs.getString("option_b"),
                rs.getString("option_c"),
                rs.getString("option_d"),
                rs.getString("correct_option")
        ));
    }

    public QuizDetails getQuizDetails(int userId) {
        String sql = "SELECT q.quiz_id, q.title, q.time_start, q.time_end, u.firstname, u.lastname " +
                "FROM quiz q " +
                "JOIN user u ON q.user_id = u.user_id " +
                "WHERE q.user_id = ? " +
                "ORDER BY q.time_start DESC LIMIT 1";

        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{userId}, (rs, rowNum) ->
                    new QuizDetails(
                            rs.getInt("quiz_id"),
                            rs.getString("title"),
                            rs.getString("time_start"),
                            rs.getString("time_end"),
                            rs.getString("firstname"),
                            rs.getString("lastname")
                    ));
        } catch (EmptyResultDataAccessException e) {
            System.out.println(" No quiz details found for userId: " + userId);
            return null; // Return null if no quiz found
        }
    }


    public String getQuestionText(int questionId) {
        String sql = "SELECT question_text FROM quiz_question WHERE qq_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{questionId}, String.class);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("No question found for questionId: " + questionId);
            return "Question not found";
        }
    }



    public Quiz getQuizById(int quizId) {
        String sql = "SELECT * FROM quiz WHERE quiz_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{quizId}, (rs, rowNum) ->
                new Quiz(
                        rs.getInt("quiz_id"),
                        rs.getString("title"),
                        rs.getInt("created_by")
                )
        );
    }


}
