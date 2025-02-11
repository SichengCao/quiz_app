<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Question Management | Quiz App</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-5">
    <h2>Question Management</h2>
    <a href="<%= request.getContextPath() %>/admin/add-question" class="btn btn-success mb-3">Add New Question</a>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Question ID</th>
            <th>Question</th>
            <th>Quiz Title</th>
            <th>Created At</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="question" items="${questions}">
            <tr>
                <td>${question.questionId}</td>
                <td>${question.content}</td>
                <td>${question.quizTitle}</td>
                <td>${question.createdAt}</td>
                <td>
                    <a href="<%= request.getContextPath() %>/admin/edit-question/${question.questionId}" class="btn btn-primary btn-sm">Edit</a>
                    <a href="<%= request.getContextPath() %>/admin/delete-question/${question.questionId}" class="btn btn-danger btn-sm">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>
