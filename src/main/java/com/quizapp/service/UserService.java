package com.quizapp.service;

import com.quizapp.model.User;
import com.quizapp.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder(); //  Inject BCryptPasswordEncoder
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean register(User user) {
        // Check if email already exists
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return false;
        }

        // Hash password before saving to database
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        // Save user
        userRepository.save(user);
        return true;
    }

    // Authenticate user login and return JWT token
    public String login(String email, String password, HttpSession session) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            return null; //
        }

        //  Compare hashed password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return null; //
        }

        //  Store user ID in session
        session.setAttribute("userId", user.getUserId());
        session.setAttribute("firstname", user.getFirstname());
        session.setAttribute("lastname", user.getLastname());
        session.setAttribute("isAdmin", user.isAdmin());


        return "token-placeholder"; // Replace with JWT token logic if needed
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUserById(int userId) {
        userRepository.deleteById(userId);
    }

    public User getUserById(int userId) {
        return userRepository.findById(userId);
    }

    public void updateUser(User user) {
        userRepository.updateUser(user);
    }
}
