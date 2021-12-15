package com.epam.druzhinin.services;

import com.epam.druzhinin.dto.BasketItemsDto;
import com.epam.druzhinin.entity.BasketEntity;
import com.epam.druzhinin.entity.UserEntity;
import com.epam.druzhinin.exception.NotFoundException;
import com.epam.druzhinin.repositories.BasketRepository;
import com.epam.druzhinin.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BasketService {

    private final BasketRepository basketRepository;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public BasketService(BasketRepository basketRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.basketRepository = basketRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public BasketEntity createBasketForUser(UserEntity userEntity) {
        log.info("Starting to save the basket for user[userId={}]", userEntity.getId());
        BasketEntity basket = new BasketEntity().setUser(userEntity);
        BasketEntity savedBasket = basketRepository.save(basket);
        log.info("Basket is saved for user [basketId={},userId={}]", savedBasket.getId(), userEntity.getId());
        return savedBasket;
    }

    public BasketItemsDto getItemsByUserId(Long userId) {
        log.info("Staring to get items of basket for user [id={}]", userId);
        UserEntity user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User isn't found with id=" + userId)
        );
        BasketEntity basket = basketRepository.findByUser(user).orElseThrow(
                () -> new NotFoundException("Basket isn't found for user id=" + user.getId())
        );
        log.info("Basket received [basketId={}]", basket.getId());
        log.debug("Starting to map basketEntity to BasketItemsDto[basketId={}]", basket.getId());
        BasketItemsDto basketItems = modelMapper.map(basket, BasketItemsDto.class);
        log.debug("Basket is mapped successfully [basketId={}]", basketItems.getBasketId());
        return basketItems;
    }
}
