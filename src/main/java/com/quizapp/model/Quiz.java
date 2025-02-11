package com.quizapp.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Quiz {
    private int quizId;
    private int id;
    private String title;
    private int createdBy;
    private int categoryId;  // 添加 categoryId
    private List<Question> questions; //  支持多问题存储

    private LocalDateTime last_attempt;

    //  默认构造方法 (No-args constructor)

    public Quiz() {
        this.questions = new ArrayList<>();
    }

    //  全参数构造方法
    public Quiz(String title, int createdBy, int categoryId) {
        this.title = title;
        this.createdBy = createdBy;
        this.categoryId = categoryId;
        this.questions = new ArrayList<>();

    }
    public Quiz(int quizId, String title, int createdBy) {
        this.quizId = quizId;
        this.title = title;
        this.createdBy = createdBy;
    }


    public Quiz(int quizId, String title, int createdBy,LocalDateTime last_attempt) {
        this.quizId = quizId;
        this.title = title;
        this.createdBy = createdBy;
        this.last_attempt = last_attempt;
    }




    //  添加问题方法
    public void addQuestion(Question question) {
        if (this.questions == null) {
            this.questions = new ArrayList<>();
        }
        this.questions.add(question);
    }

    //  Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getQuizId() {
        return quizId;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getCreatedBy() { return createdBy; }
    public void setCreatedBy(int createdBy) { this.createdBy = createdBy; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public List<Question> getQuestions() { return questions; }
    public void setQuestions(List<Question> questions) { this.questions = questions; }

    public LocalDateTime getLast_attempt() { return last_attempt; }
}
