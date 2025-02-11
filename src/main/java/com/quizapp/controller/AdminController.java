package com.quizapp.controller;

import com.quizapp.model.QuizResultSummary;
import com.quizapp.model.User;
import com.quizapp.service.QuizResultSummaryService;
import com.quizapp.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;


import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final QuizResultSummaryService quizResultSummaryService;

    public AdminController(UserService userService,QuizResultSummaryService quizResultSummaryService) {
        this.userService = userService;
        this.quizResultSummaryService = quizResultSummaryService;
    }

    // ✅ 确保管理员访问权限
    private boolean checkAdminAccess(HttpSession session) {
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        return isAdmin != null && isAdmin;
    }

    @GetMapping("/users")
    public String showUserManagementPage(Model model, HttpSession session) {
        if (!checkAdminAccess(session)) return "redirect:/user/login";

        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "adminUsers";
    }



    @GetMapping("/questions")
    public String showQuestionManagementPage(HttpSession session) {
        if (!checkAdminAccess(session)) return "redirect:/user/login";
        return "adminQuestions";
    }



    @GetMapping("/contact")
    public String showContactManagementPage(HttpSession session) {
        if (!checkAdminAccess(session)) return "redirect:/user/login";
        List<User> users = userService.getAllUsers();

        return "adminContact";
    }

    // ✅ 处理删除用户
    @GetMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable("id") int userId, HttpSession session) {
        if (!checkAdminAccess(session)) return "redirect:/user/login";

        userService.deleteUserById(userId);
        return "redirect:/admin/users";  // ✅ 删除后刷新页面
    }

    // ✅ 进入编辑用户页面
    @GetMapping("/edit-user/{id}")
    public String editUserPage(@PathVariable("id") int userId, Model model, HttpSession session) {
        if (!checkAdminAccess(session)) return "redirect:/user/login";

        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
        return "editUser";  // ✅ 显示 `editUser.jsp`
    }

    // ✅ 处理编辑用户的请求
    @PostMapping("/edit-user")
    public String updateUser(@ModelAttribute User user, HttpSession session) {
        if (!checkAdminAccess(session)) return "redirect:/user/login";

        userService.updateUser(user);
        return "redirect:/admin/users";  // ✅ 修改后刷新用户列表
    }

    @GetMapping("/quiz-results")
    public String showQuizResultManagementPage(Model model, HttpSession session) {
        if (!checkAdminAccess(session)) return "redirect:/user/login";

        List<QuizResultSummary> quizResults = quizResultSummaryService.getAllQuizResults();
        model.addAttribute("quizResults", quizResults);
        return "adminQuizResults";
    }

    @GetMapping("/delete-quiz-result/{quizId}/{userId}")
    public String deleteQuizResult(@PathVariable("quizId") int quizId, @PathVariable("userId") int userId, HttpSession session) {
        if (!checkAdminAccess(session)) return "redirect:/user/login";

        quizResultSummaryService.deleteQuizResult(quizId, userId);
        return "redirect:/admin/quiz-results";
    }







}
