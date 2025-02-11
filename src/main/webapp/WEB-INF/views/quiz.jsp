<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${quiz.title} | Quiz Page</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <script>
        let timeLeft = 60;
        function updateTimer() {
            document.getElementById("timer").innerText = timeLeft + " seconds left";
            if (timeLeft === 0) {
                document.getElementById("quizForm").submit();
            } else {
                timeLeft--;
                setTimeout(updateTimer, 1000);
            }
        }
        window.onload = updateTimer;
    </script>
</head>
<body>

<div class="container mt-5">
    <h2>📖 Quiz: ${quiz.title}</h2>
    <p id="timer" class="text-danger"></p>

    <c:if test="${empty questions}">
        <p class="text-danger">⚠ No questions available for this quiz.</p>
    </c:if>

    <form action="<%= request.getContextPath() %>/quiz/submit" method="post" id="quizForm">
        <c:forEach var="question" items="${questions}" varStatus="status">
            <div class="mb-4 p-3 border rounded shadow-sm">
                <p class="fw-bold">${status.index + 1}. ${question.questionText}</p>


                <!-- ✅ Store Question ID -->
                <input type="hidden" name="questionId[${status.index}]" value="${question.questionId}">

                <!-- ✅ Ensure each option has a unique ID and name -->
                <div class="form-check">
                    <input class="form-check-input"
                           type="radio"
                           name="answers[${status.index}]"
                           id="option_${status.index}_A"
                           value="A">
                    <label class="form-check-label" for="option_${status.index}_A">
                            ${question.optionA}
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input"
                           type="radio"
                           name="answers[${status.index}]"
                           id="option_${status.index}_B"
                           value="B">
                    <label class="form-check-label" for="option_${status.index}_B">
                            ${question.optionB}
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input"
                           type="radio"
                           name="answers[${status.index}]"
                           id="option_${status.index}_C"
                           value="C">
                    <label class="form-check-label" for="option_${status.index}_C">
                            ${question.optionC}
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input"
                           type="radio"
                           name="answers[${status.index}]"
                           id="option_${status.index}_D"
                           value="D">
                    <label class="form-check-label" for="option_${status.index}_D">
                            ${question.optionD}
                    </label>
                </div>
            </div>
        </c:forEach>

        <button type="submit" class="btn btn-primary">Submit Quiz</button>
    </form>
</div>

</body>
</html>
