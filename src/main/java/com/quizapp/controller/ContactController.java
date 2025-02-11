package com.quizapp.controller;

import com.quizapp.model.ContactMessage;
import com.quizapp.service.ContactService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/contact")
public class ContactController {
    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    // ✅ 显示 Contact 页面
    @GetMapping("")
    public String showContactPage() {
        return "contact";
    }

    @PostMapping("/sendMessage")
    public String sendMessage(@RequestParam String subject,
                              @RequestParam String email,
                              @RequestParam String message,
                              HttpSession session) {
        ContactMessage contactMessage = new ContactMessage(subject, email, message);
        contactService.saveMessage(contactMessage); // ✅ 确保数据存入 `contact` 表

        session.setAttribute("contactSuccess", "Your message has been sent successfully!");
        return "redirect:/user/home";  // ✅ 成功后跳转到 `user/home`
    }
}
