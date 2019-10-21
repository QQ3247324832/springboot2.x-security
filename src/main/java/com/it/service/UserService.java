package com.it.service;

import com.it.domain.User;

public interface UserService {
    User findByUsername(String username);
}
