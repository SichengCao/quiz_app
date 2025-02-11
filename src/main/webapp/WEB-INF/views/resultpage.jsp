<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Quiz Results</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-5">
    <h2>📊 Quiz Results</h2>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>Question</th>
            <th>Your Answer</th>
            <th>Correct Answer</th>
<%--            <th>Result</th>--%>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="result" items="${results}">

            <tr>
                <td>${result.questionText}</td>
                <td>${result.userAnswer}</td>
                <td>${result.correctAnswer}</td>
<%--                <td>--%>
<%--                    <c:choose>--%>
<%--                        <c:when test="${result.getIsCorrect()}">--%>
<%--                            ✅ Correct--%>
<%--                        </c:when>--%>
<%--                        <c:otherwise>--%>
<%--                            ❌ Incorrect--%>
<%--                        </c:otherwise>--%>
<%--                    </c:choose>--%>
<%--                </td>--%>

            </tr>
        </c:forEach>
        </tbody>
    </table>

    <a href="/user/home" class="btn btn-secondary">🏠 Back to Home</a>
</div>

</body>
</html>
