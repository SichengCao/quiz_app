<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Home | Quiz App</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>

<!-- Navigation Bar -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">Admin Dashboard</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link btn btn-danger text-white px-3" href="<%= request.getContextPath() %>/user/logout">Logout</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!-- Admin Home Content -->
<div class="container text-center mt-5">
    <h2>Welcome, Admin ${sessionScope.firstname} ${sessionScope.lastname}!</h2>
    <p>Manage users, quizzes, questions, and contact messages.</p>

    <div class="mt-4">
        <a href="<%= request.getContextPath() %>/admin/users" class="btn btn-primary btn-lg">👥 User Management</a>
        <a href="<%= request.getContextPath() %>/admin/quiz-results" class="btn btn-secondary btn-lg">📊 Quiz Result Management</a>
        <a href="<%= request.getContextPath() %>/admin/questions" class="btn btn-success btn-lg">📝 Question Management</a>
        <a href="<%= request.getContextPath() %>/admin/contact" class="btn btn-warning btn-lg">📩 Contact Us Management</a>
    </div>
</div>

</body>
</html>
