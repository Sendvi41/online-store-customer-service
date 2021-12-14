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

    private final UserRepository productRepository;

    public UserService(ModelMapper modelMapper, UserRepository productRepository) {
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
    }

    public UserEntity createNewUser(CreateUserRequestDto userRequest) {
        UserEntity userEntity = modelMapper.map(userRequest, UserEntity.class);
        return productRepository.save(userEntity);
    }
}
