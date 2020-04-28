package com.blaese.error.manager.service.impl;

import com.blaese.error.manager.entity.User;
import com.blaese.error.manager.repository.UserRepository;
import com.blaese.error.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository repository;

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmailEquals(email);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }
}
