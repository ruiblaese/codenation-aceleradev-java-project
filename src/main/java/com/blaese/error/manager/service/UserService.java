package com.blaese.error.manager.service;

import com.blaese.error.manager.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User save(User user);

    Optional<User> findByEmail(String email);

    List<User> findAll();


}
