<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>📚 Create New Quiz</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-5">
    <h2>📚 Create New Quiz</h2>

    <form action="/quiz/create" method="post">
        <!-- Quiz Title -->
        <div class="mb-3">
            <label for="title" class="form-label">Quiz Title:</label>
            <input type="text" class="form-control" id="title" name="title" required>
        </div>

        <!-- Category Selection -->
        <div class="mb-3">
            <label for="category" class="form-label">Select Category:</label>
            <select class="form-select" id="category" name="categoryId" required>
                <option value="">-- Select a Category --</option>
                <c:forEach var="category" items="${categories}">
                    <option value="${category.categoryId}">${category.name}</option>
                </c:forEach>
            </select>
        </div>

        <h3>Questions</h3>

        <div class="mb-3">
            <label for="questionText" class="form-label">Question:</label>
            <input type="text" class="form-control" id="questionText" name="questions[0].questionText" required>
        </div>

        <div class="mb-3">
            <label for="optionA" class="form-label">Option A:</label>
            <input type="text" class="form-control" id="optionA" name="questions[0].optionA" required>
        </div>

        <div class="mb-3">
            <label for="optionB" class="form-label">Option B:</label>
            <input type="text" class="form-control" id="optionB" name="questions[0].optionB" required>
        </div>

        <div class="mb-3">
            <label for="optionC" class="form-label">Option C:</label>
            <input type="text" class="form-control" id="optionC" name="questions[0].optionC" required>
        </div>

        <div class="mb-3">
            <label for="optionD" class="form-label">Option D:</label>
            <input type="text" class="form-control" id="optionD" name="questions[0].optionD" required>
        </div>

        <div class="mb-3">
            <label for="correctOption" class="form-label">Correct Answer:</label>
            <select class="form-select" id="correctOption" name="questions[0].correctOption" required>
                <option value="A">A</option>
                <option value="B">B</option>
                <option value="C">C</option>
                <option value="D">D</option>
            </select>
        </div>

        <button type="submit" class="btn btn-primary">Create Quiz</button>
    </form>
</div>

</body>
</html>
