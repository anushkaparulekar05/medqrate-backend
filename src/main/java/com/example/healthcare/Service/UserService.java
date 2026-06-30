package com.example.healthcare.Service;

import java.util.Optional;

import com.example.healthcare.Entity.User;

public interface UserService {

    User register(User user);

    Optional<User> login(String email, String password);

    Optional<User> findById(Long id);
}