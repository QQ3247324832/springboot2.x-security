package com.it.controller;

import com.it.domain.User;
import com.it.service.UserService;
import com.it.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userName}")
    public User getUserByName(@PathVariable String userName) {
        log.info("user {}", SecurityUtils.getUser());
        return userService.findByUsername(userName);
    }
}
