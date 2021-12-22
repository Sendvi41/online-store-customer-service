package com.epam.druzhinin.services;

import com.epam.druzhinin.dto.CreateUserRequestDto;
import com.epam.druzhinin.entity.UserEntity;
import com.epam.druzhinin.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    private final BasketService basketService;

    public UserService(ModelMapper modelMapper,
                       UserRepository userRepository,
                       BasketService basketService) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.basketService = basketService;
    }

    public UserEntity saveUser(CreateUserRequestDto userRequest) {
        log.info("Starting to save the user[productDto={}]", userRequest);
        UserEntity userEntity = modelMapper.map(userRequest, UserEntity.class);
        UserEntity savedEntity = userRepository.save(userEntity);
        basketService.createBasketForUser(savedEntity);
        log.info("User is saved [id={}]", savedEntity.getId());
        return savedEntity;
    }
}
