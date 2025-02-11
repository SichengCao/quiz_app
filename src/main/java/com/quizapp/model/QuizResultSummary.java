package com.quizapp.model;

import java.time.LocalDateTime;

public class QuizResultSummary {
    private int userId;
    private String userEmail;
    private int quizId;
    private String quizTitle;
    private int totalQuestions;
    private int correctAnswers;
    private double scorePercentage;
    private LocalDateTime completedAt;

    public QuizResultSummary(int userId, String userEmail, int quizId, String quizTitle,
                             int totalQuestions, int correctAnswers, double scorePercentage,
                             LocalDateTime completedAt) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.quizId = quizId;
        this.quizTitle = quizTitle;
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
        this.scorePercentage = scorePercentage;
        this.completedAt = completedAt;
    }

    public int getUserId() { return userId; }
    public String getUserEmail() { return userEmail; }
    public int getQuizId() { return quizId; }
    public String getQuizTitle() { return quizTitle; }
    public int getTotalQuestions() { return totalQuestions; }
    public int getCorrectAnswers() { return correctAnswers; }
    public double getScorePercentage() { return scorePercentage; }
    public LocalDateTime getCompletedAt() { return completedAt; }
}
