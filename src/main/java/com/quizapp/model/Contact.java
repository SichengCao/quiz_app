package com.quizapp.model;

import java.sql.Timestamp;

public class Contact {
    private int contactId;
    private String subject;
    private String email;
    private Timestamp time;
    private String message;

    public Contact(int contactId, String subject, String email, Timestamp time, String message) {
        this.contactId = contactId;
        this.subject = subject;
        this.email = email;
        this.time = time;
        this.message = message;
    }

    // Getters and Setters
    public int getContactId() { return contactId; }
    public void setContactId(int contactId) { this.contactId = contactId; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Timestamp getTime() { return time; }
    public void setTime(Timestamp time) { this.time = time; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
