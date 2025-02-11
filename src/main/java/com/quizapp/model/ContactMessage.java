package com.quizapp.model;

import java.time.LocalDateTime;

public class ContactMessage {
    private int contactId;  // ✅ 需要匹配 `contact_id`
    private String subject;
    private String message;
    private String email;
    private LocalDateTime time;  // ✅ 确保 `time` 字段匹配数据库

    public ContactMessage() {}

    public ContactMessage(String subject, String email, String message) {
        this.subject = subject;
        this.email = email;
        this.message = message;
        this.time = LocalDateTime.now();  // ✅ 默认存储当前时间
    }

    public int getContactId() { return contactId; }
    public void setContactId(int contactId) { this.contactId = contactId; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDateTime getTime() { return time; }
    public void setTime(LocalDateTime time) { this.time = time; }
}
