package com.quizapp.controller;

import com.quizapp.model.Question;
import com.quizapp.model.Quiz;
import com.quizapp.model.QuizResult;
import com.quizapp.service.QuizService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/quiz")
public class QuizController {
    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    // 显示创建 Quiz 页面
    @GetMapping("/create")
    public String showCreateQuizPage(Model model) {
        model.addAttribute("categories", quizService.getAllCategories()); // 获取所有分类
        return "create_quiz";  // 对应 create_quiz.jsp
    }

    // 处理 Quiz 创建请求
    @PostMapping("/create")
    public String createQuiz(@RequestParam String title,
                             @RequestParam int categoryId,
                             @RequestParam("questions[0].questionText") String questionText,
                             @RequestParam("questions[0].optionA") String optionA,
                             @RequestParam("questions[0].optionB") String optionB,
                             @RequestParam("questions[0].optionC") String optionC,
                             @RequestParam("questions[0].optionD") String optionD,
                             @RequestParam("questions[0].correctOption") String correctOption,
                             HttpSession session, Model model) {

        // 获取当前用户 ID
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/user/login"; // 未登录，跳转至登录页面
        }

        // 确保 categoryId 存在
        if (categoryId <= 0) {
            model.addAttribute("errorMessage", "Please select a valid category.");
            return "create_quiz"; // 返回创建页面，并显示错误信息
        }

        // 创建 Quiz (调用 QuizService 创建 Quiz)
        int quizId = quizService.createQuiz(userId, categoryId, title);
        System.out.println(" Debug: Created quizId = " + quizId + " in category " + categoryId);

        // 创建 Question 并存入数据库
        Question question = new Question();
        question.setQuestionText(questionText);
        question.setOptionA(optionA);
        question.setOptionB(optionB);
        question.setOptionC(optionC);
        question.setOptionD(optionD);
        question.setCorrectOption(correctOption);

        // 存入数据库
        quizService.addQuestionToQuiz(quizId, question);
        System.out.println(" Debug: Added question to quizId = " + quizId);

        model.addAttribute("successMessage", "Quiz created successfully! ID: " + quizId);
        return "redirect:/user/home";
    }



    // 开始 Quiz
    @GetMapping("/start/{categoryId}")
    public String startQuiz(@PathVariable int categoryId, Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/user/login";
        }

        //  先查询是否有现成的 Quiz
        Integer quizId = quizService.findExistingQuiz(userId, categoryId);

        if (quizId == null) {
            System.out.println("⚠ No existing quiz found for userId = " + userId + ", categoryId = " + categoryId);
            model.addAttribute("errorMessage", "No quiz found for this category. Please create one.");
            return "error";  // ⚠ 这里要确保 `error.jsp` 存在
        }

        System.out.println(" Debug: Found existing quizId = " + quizId);

        //  获取该 Quiz 的问题
        List<Question> questions = quizService.getQuizQuestions(quizId);
        if (questions.isEmpty()) {
            System.out.println("⚠ No questions found for quizId = " + quizId);
            model.addAttribute("errorMessage", "This quiz has no questions.");
            return "error";
        }

        model.addAttribute("quizId", quizId);
        model.addAttribute("questions", questions);

        return "quiz"; // 🚀 确保 quiz.jsp 存在
    }




    // 显示特定 Quiz
    @GetMapping("/{quizId:[0-9]+}")
    public String showQuiz(@PathVariable int quizId, Model model) {
        Quiz quiz = quizService.getQuizById(quizId);
        List<Question> questions = quizService.getQuizQuestions(quizId);

        // Debugging
        System.out.println(" Debug: Fetched Quiz -> " + quiz);
        System.out.println(" Debug: Fetched Questions -> " + questions);

        model.addAttribute("quiz", quiz);
        model.addAttribute("questions", questions);

        return "quiz";  // 返回 quiz.jsp
    }

    @PostMapping("/submit")  // 直接映射到 "/quiz/submit"

    public String submitQuiz(@RequestParam Map<String, String> formData, HttpSession session, Model model) {
        System.out.println(" Debug: Received form data = " + formData);

        int correctCount = 0;
        int totalQuestions = 0;
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/user/login";
        }

        List<Map<String, Object>> results = new ArrayList<>();

        for (Map.Entry<String, String> entry : formData.entrySet()) {
            if (entry.getKey().matches("answers\\[\\d+]"))  // 适配 answers[0], answers[1] 这种格式
            {
                try {
                    int index = Integer.parseInt(entry.getKey().replaceAll("\\D+", ""));  // 提取 0,1,2...
                    System.out.println("Here is the index you get: "+ index);
                    String questionIdStr = formData.get("questionId[" + index + "]");
                    if (questionIdStr == null || questionIdStr.isEmpty()) {
                        System.out.println(" Warning: No questionId found for index " + index);
                        continue;  // 避免 NumberFormatException
                    }
                    int questionId = Integer.parseInt(questionIdStr);
                    System.out.println(" Debug: Extracted questionId = " + questionId);



                    String userAnswer = entry.getValue();
                    String correctAnswer = quizService.getCorrectAnswer(questionId);

                    System.out.println(" Debug: Question ID = " + questionId);
                    System.out.println(" Debug: User Answer = " + userAnswer);
                    System.out.println(" Debug: Correct Answer = " + correctAnswer);


                    boolean isCorrect = userAnswer.equalsIgnoreCase(correctAnswer);
                    if (isCorrect) {
                        correctCount++;
                    }

                    quizService.storeUserAnswer(userId, questionId, userAnswer, isCorrect);

                    Map<String, Object> result = new HashMap<>();
                    result.put("questionText", quizService.getQuestionText(questionId));
                    result.put("userAnswer", userAnswer);
                    result.put("correctAnswer", correctAnswer);
                    result.put("isCorrect", isCorrect);
                    results.add(result);

                    totalQuestions++;
                } catch (NumberFormatException e) {
                    System.out.println("Skipping invalid key: " + entry.getKey());
                }
            }
        }

        model.addAttribute("correctCount", correctCount);
        model.addAttribute("totalQuestions", totalQuestions);
        model.addAttribute("results", results);
        model.addAttribute("isPassed", correctCount >= 3 ? "Passed" : "Failed");

        return "quizResult";
    }

    @GetMapping("/submit")
    public String preventDirectAccess() {
        return "redirect:/quiz";  // 让 GET 请求跳转回 Quiz 页面
    }

    @GetMapping("/result/{quizId}")
    public String viewQuizResult(@PathVariable int quizId, HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/user/login"; // ✅ 如果未登录，跳转到登录页面
        }

        System.out.println("Debug: Fetching results for userId = " + userId + ", quizId = " + quizId);



        // ✅ 获取 Quiz 相关的问题和用户的答案
        List<QuizResult> results = quizService.getQuizResults(userId, quizId);



        if (results.isEmpty()) {
            System.out.println("Debug: No results found for quizId = " + quizId);
            model.addAttribute("errorMessage", "No results found for this quiz.");
        } else {
            System.out.println("Debug: 查询到 " + results.size() + " 条结果");
        }

//        model.addAttribute("quizTitle", quizService.getQuizTitle(quizId));  // ✅ 显示 Quiz Title
        model.addAttribute("results", results);
        return "resultpage";  // ✅ 返回 `quizResult.jsp`
    }






}
