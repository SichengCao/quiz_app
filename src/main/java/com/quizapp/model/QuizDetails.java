package com.quizapp.model;

public class QuizDetails {
    private int quizId;
    private String title;
    private String startTime;
    private String endTime;
    private String firstname;
    private String lastname;

    public QuizDetails(int quizId, String title, String startTime, String endTime, String firstname, String lastname) {
        this.quizId = quizId;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    // Getters and Setters
    public int getQuizId() { return quizId; }
    public void setQuizId(int quizId) { this.quizId = quizId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }

    public String getFirstname() { return firstname; }
    public void setFirstname(String firstname) { this.firstname = firstname; }

    public String getLastname() { return lastname; }
    public void setLastname(String lastname) { this.lastname = lastname; }
}
