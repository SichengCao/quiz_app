package com.quizapp.controller;

import com.quizapp.model.Category;
import com.quizapp.model.Quiz;
import com.quizapp.model.User;
import com.quizapp.service.UserService;
import com.quizapp.service.QuizService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final QuizService quizService;

    public UserController(UserService userService, QuizService quizService) {
        this.userService = userService;
        this.quizService = quizService;
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String email,
                           @RequestParam String password,
                           @RequestParam String confirmPassword,
                           @RequestParam String firstname,
                           @RequestParam String lastname,
                           Model model) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("errorMessage", "Passwords do not match!");
            return "register";
        }

        User newUser = new User(0, email, password, firstname, lastname, true, false);
        boolean isRegistered = userService.register(newUser);

        if (isRegistered) {
            model.addAttribute("successMessage", "Registration successful! Please login.");
            return "login";
        } else {
            model.addAttribute("errorMessage", "Email already exists!");
            return "register";
        }
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        String token = userService.login(email, password, session);
        if (token != null) {
            model.addAttribute("token", token);
            return "redirect:/user/home";
        } else {
            model.addAttribute("errorMessage", "Invalid email or password.");
            return "login";
        }
    }

    @GetMapping("/home")
    public String showHomePage(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/user/login"; // Redirect if not logged in
        }

        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin != null && isAdmin) {
            return "adminHome"; // 管理员跳转到 `adminHome.jsp`
        }

        List<Category> categories = quizService.getAllCategories();
        List<Quiz> recentQuizzes = quizService.getRecentQuizzes(userId);

        model.addAttribute("categories", categories);
        model.addAttribute("recentQuizzes", recentQuizzes);

        return "home"; // This should match the JSP file name
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // ✅ 清除所有会话数据
        return "redirect:/user/login"; // ✅ 退出后跳转到 `login.jsp`
    }


}
