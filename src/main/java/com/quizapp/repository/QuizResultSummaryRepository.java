package com.quizapp.repository;

import com.quizapp.model.QuizResultSummary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuizResultSummaryRepository {
    private final JdbcTemplate jdbcTemplate;

    public QuizResultSummaryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<QuizResultSummary> findAll() {
        String sql = """
            SELECT 
                ua.user_id, 
                u.email AS user_email,
                q.quiz_id,
                q.title AS quiz_title,
                COUNT(*) AS total_questions,
                SUM(ua.is_correct) AS correct_answers,
                CASE 
                    WHEN COUNT(*) = 0 THEN 0 
                    ELSE (SUM(ua.is_correct) / COUNT(*)) * 100 
                END AS score_percentage,
                MAX(ua.submitted_at) AS completed_at
            FROM user_answers ua
            JOIN user u ON ua.user_id = u.user_id
            JOIN quiz_question qq ON ua.question_id = qq.question_id
            JOIN quiz q ON qq.quiz_id = q.quiz_id
            GROUP BY ua.user_id, q.quiz_id
            ORDER BY completed_at DESC
        """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new QuizResultSummary(
                rs.getInt("user_id"),
                rs.getString("user_email"),
                rs.getInt("quiz_id"),
                rs.getString("quiz_title"),
                rs.getInt("total_questions"),
                rs.getInt("correct_answers"),
                rs.getDouble("score_percentage"),
                rs.getTimestamp("completed_at").toLocalDateTime()
        ));
    }
    public void deleteQuizResult(int quizId, int userId) {
        String sql = "DELETE FROM user_answers WHERE user_id = ? AND question_id IN (SELECT question_id FROM quiz_question WHERE quiz_id = ?)";
        jdbcTemplate.update(sql, userId, quizId);
    }

}
