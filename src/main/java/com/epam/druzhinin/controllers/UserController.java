package com.epam.druzhinin.controllers;


import com.epam.druzhinin.dto.CreateUserRequestDto;
import com.epam.druzhinin.entity.UserEntity;
import com.epam.druzhinin.services.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserEntity createNewUser(@RequestBody @Valid CreateUserRequestDto userRequest) {
        return userService.saveUser(userRequest);
    }
}
