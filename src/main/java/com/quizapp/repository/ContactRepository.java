package com.quizapp.repository;

import com.quizapp.model.ContactMessage;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ContactRepository {
    private final JdbcTemplate jdbcTemplate;

    public ContactRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // ✅ 存储消息
    public void saveMessage(ContactMessage message) {
        String sql = "INSERT INTO contact (subject, email, message, time) VALUES (?, ?, ?, NOW())";
        jdbcTemplate.update(sql, message.getSubject(), message.getEmail(), message.getMessage());
    }
}
