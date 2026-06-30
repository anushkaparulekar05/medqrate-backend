package com.example.healthcare.Dao;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.healthcare.Entity.User;
import com.example.healthcare.Repository.UserRepository;

@Repository
public class UserDao {

    private UserRepository userRepository;

    public UserDao(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}