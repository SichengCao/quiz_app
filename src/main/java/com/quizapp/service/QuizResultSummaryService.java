package com.quizapp.service;

import com.quizapp.model.QuizResultSummary;
import com.quizapp.repository.QuizResultSummaryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizResultSummaryService {
    private final QuizResultSummaryRepository quizResultSummaryRepository;

    public QuizResultSummaryService(QuizResultSummaryRepository quizResultSummaryRepository) {
        this.quizResultSummaryRepository = quizResultSummaryRepository;
    }

    public List<QuizResultSummary> getAllQuizResults() {
        return quizResultSummaryRepository.findAll();
    }

    public void deleteQuizResult(int quizId, int userId) {
        quizResultSummaryRepository.deleteQuizResult(quizId, userId);
    }

}
