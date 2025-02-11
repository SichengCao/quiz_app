<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Quiz Result</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-5">
    <h2>📊 Quiz Result</h2>
    <p>Total Questions: <strong>${totalQuestions}</strong></p>
    <p>Correct Answers: <strong>${correctCount}</strong></p>
    <p>Status: <strong class="${isPassed == 'Passed' ? 'text-success' : 'text-danger'}">${isPassed}</strong></p>

    <table class="table table-bordered mt-4">
        <thead class="table-light">
        <tr>
            <th>#</th>
            <th>Question</th>
            <th>Your Answer</th>
            <th>Correct Answer</th>
            <th>Result</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="result" items="${results}" varStatus="status">
            <tr>
                <td>${status.index + 1}</td>
                <td>${result.questionText}</td>
                <td>${result.userAnswer}</td>
                <td>${result.correctAnswer}</td>
                <td>
                    <c:choose>
                        <c:when test="${result.isCorrect}">
                            ✅ Correct
                        </c:when>
                        <c:otherwise>
                            ❌ Incorrect
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
        </tbody>
