package com.quizapp.repository;

import com.quizapp.model.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Save user
    public void save(User user) {
        System.out.println("Saving user to DB: " + user);

        String sql = "INSERT INTO user (email, password, firstname, lastname, is_active, is_admin) VALUES (?, ?, ?, ?, ?, ?)";
        int rowsAffected = jdbcTemplate.update(sql,
                user.getEmail(), user.getPassword(), user.getFirstname(), user.getLastname(), user.isActive(), user.isAdmin());

        System.out.println(" Rows affected: " + rowsAffected);
    }

    // Find user by email
    public User findByEmail(String email) {
        String sql = "SELECT * FROM user WHERE email = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{email}, (rs, rowNum) ->
                    new User(
                            rs.getInt("user_id"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("firstname"),
                            rs.getString("lastname"),
                            rs.getBoolean("is_active"),
                            rs.getBoolean("is_admin")
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return null; // If no user is found, return null
        }
    }


    public List<User> findAll() {
        String sql = "SELECT * FROM user";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new User(
                        rs.getInt("user_id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getBoolean("is_active"),
                        rs.getBoolean("is_admin")
                )
        );
    }

    public void deleteById(int userId) {
        String sql = "DELETE FROM user WHERE user_id = ?";
        jdbcTemplate.update(sql, userId);
    }

    public User findById(int userId) {
        String sql = "SELECT * FROM user WHERE user_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{userId}, (rs, rowNum) ->
                new User(
                        rs.getInt("user_id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getBoolean("is_active"),
                        rs.getBoolean("is_admin")
                )
        );
    }

    public void updateUser(User user) {
        String sql = "UPDATE user SET email = ?, firstname = ?, lastname = ?, is_active = ?, is_admin = ? WHERE user_id = ?";
        jdbcTemplate.update(sql, user.getEmail(), user.getFirstname(), user.getLastname(), user.isActive(), user.isAdmin(), user.getUserId());
    }

}
