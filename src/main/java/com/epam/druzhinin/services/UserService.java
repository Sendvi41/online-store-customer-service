package com.epam.druzhinin.services;

import com.epam.druzhinin.dto.CreateUserRequestDto;
import com.epam.druzhinin.entity.UserEntity;
import com.epam.druzhinin.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
    private final ModelMapper modelMapper;

    private final UserRepository productRepository;

    @Autowired
    public UserService(ModelMapper modelMapper, UserRepository productRepository) {
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
    }

    public UserEntity saveUser(CreateUserRequestDto userRequest) {
        log.info("Starting to save the user[productDto={}]", userRequest);
        UserEntity userEntity = modelMapper.map(userRequest, UserEntity.class);
        UserEntity savedEntity = productRepository.save(userEntity);
        log.info("User is saved [id={}]", savedEntity.getId());
        return savedEntity;
    }
}
