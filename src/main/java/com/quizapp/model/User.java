package com.quizapp.model;

public class User {
    private int userId;
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private boolean isActive;
    private boolean isAdmin;

    // ✅ No-args constructor required for JPA and Spring
    public User() {}

    // ✅ Full constructor
    public User(int userId, String email, String password, String firstname, String lastname, boolean isActive, boolean isAdmin) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.isActive = isActive;
        this.isAdmin = isAdmin;
    }

    // ✅ Getters and Setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFirstname() { return firstname; }
    public void setFirstname(String firstname) { this.firstname = firstname; }

    public String getLastname() { return lastname; }
    public void setLastname(String lastname) { this.lastname = lastname; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public boolean isAdmin() { return isAdmin; }

    public void setAdmin(boolean admin) { isAdmin = admin; }

    public boolean getIsAdmin() {
        return isAdmin;
    }
    public boolean getIsActive() {
        return isActive;
    }

}
