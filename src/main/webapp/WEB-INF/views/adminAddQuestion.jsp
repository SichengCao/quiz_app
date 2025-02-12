<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Add New Question</title>
</head>
<body>
<h2>Add New Question</h2>

<form action="${pageContext.request.contextPath}/admin/add-question" method="post">
    <label>Quiz ID:</label>
    <input type="number" name="quizId" required><br>

    <label>Question:</label>
    <input type="text" name="questionText" required><br>

    <label>Option A:</label>
    <input type="text" name="optionA" required><br>

    <label>Option B:</label>
    <input type="text" name="optionB" required><br>

    <label>Option C:</label>
    <input type="text" name="optionC" required><br>

    <label>Option D:</label>
    <input type="text" name="optionD" required><br>

    <label>Correct Answer:</label>
    <select name="correctOption">
        <option value="A">A</option>
        <option value="B">B</option>
        <option value="C">C</option>
        <option value="D">D</option>
    </select><br>

    <label>Category:</label>
    <select name="categoryId">
        <c:forEach var="category" items="${categories}">
            <option value="${category.id}">${category.name}</option>
        </c:forEach>
    </select><br>

    <input type="submit" value="Add Question">
</form>

<a href="${pageContext.request.contextPath}/admin/questions">Back to Questions</a>
</body>
</html>
