<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Quiz Results Management | Quiz App</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-5">
    <h2>Quiz Results Management</h2>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>User ID</th>
            <th>User Email</th>
            <th>Quiz Title</th>
            <th>Total Questions</th>
            <th>Correct Answers</th>
            <th>Score (%)</th>
            <th>Completed At</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="result" items="${quizResults}">
            <tr>
                <td>${result.userId}</td>
                <td>${result.userEmail}</td>
                <td>${result.quizTitle}</td>
                <td>${result.totalQuestions}</td>
                <td>${result.correctAnswers}</td>
                <td>${result.scorePercentage}</td>
                <td>${result.completedAt}</td>
                <td>
                    <a href="<%= request.getContextPath() %>/admin/delete-quiz-result/${result.quizId}/${result.userId}"
                       class="btn btn-danger btn-sm">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>
