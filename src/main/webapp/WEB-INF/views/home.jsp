<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Home | Quiz App</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>

<!--  Navigation Bar -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">Quiz App</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link" href="#">Dashboard</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Settings</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/contact">📩 Contact Us</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link btn btn-danger text-white px-3" href="<%= request.getContextPath() %>/user/logout">Logout</a>
                </li>

            </ul>
        </div>
    </div>
</nav>

<!-- Welcome Message -->
<div class="container text-center mt-5">
    <h2> Welcome, ${sessionScope.firstname} ${sessionScope.lastname}!</h2>
    <p>Select a category to start a new quiz.</p>
</div>

<!-- Choose Quiz Category -->
<div class="container mt-4">
    <h3>📌 Choose a Quiz Category</h3>
    <form id="quizForm">
        <div class="mb-3">
            <select id="categorySelect" class="form-select" required>
                <option value="">-- Select Category --</option>
                <c:forEach var="category" items="${categories}">
                    <option value="${category.categoryId}">${category.name}</option>
                </c:forEach>
            </select>
        </div>
        <button type="button" class="btn btn-primary" onclick="startQuiz()">Start Quiz</button>
    </form>
</div>

<!--  Recent Quizzes -->
<div class="container mt-5">
    <h3>📌 Your Recent Quizzes</h3>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Quiz Name</th>
            <th>Date Completed</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="quiz" items="${recentQuizzes}">
            <tr>
                <td>
                    <!--  点击 Quiz Name 跳转到 Quiz Result -->
                    <a href="<%= request.getContextPath() %>/quiz/result/${quiz.quizId}" class="text-decoration-none">
                            ${quiz.title}
                    </a>
                </td>
                <td>${quiz.last_attempt}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>



<!--  Admin Functionality: Create Quiz -->
<c:if test="${sessionScope.isAdmin}">
    <div class="container mt-4">
        <a href="/quiz/create" class="btn btn-success">➕ Create Quiz</a>
    </div>
</c:if>

<script>
    function startQuiz() {
        let categoryId = document.getElementById("categorySelect").value;
        if (!categoryId) {
            alert("Please select a category first!");
            return;
        }
        window.location.href = "/quiz/start/" + categoryId;  // ✅ Correct path
    }
</script>

</body>
</html>
