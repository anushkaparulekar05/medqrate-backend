package com.example.healthcare.Service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.healthcare.Dao.UserDao;
import com.example.healthcare.Entity.User;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User register(User user) {
        return userDao.save(user);
    }

    @Override
    public Optional<User> login(String email, String password) {
        return userDao.findByEmail(email)
                .filter(u -> u.getPassword().equals(password));
    }

    @Override
    public Optional<User> findById(Long id) {
        return userDao.findById(id);
    }
}