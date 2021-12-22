package com.epam.druzhinin.services;

import com.epam.druzhinin.document.ProductDocument;
import com.epam.druzhinin.dto.AddItemDto;
import com.epam.druzhinin.dto.BasketItemsDto;
import com.epam.druzhinin.dto.ItemDto;
import com.epam.druzhinin.entity.BasketEntity;
import com.epam.druzhinin.entity.ItemEntity;
import com.epam.druzhinin.entity.UserEntity;
import com.epam.druzhinin.exception.NotFoundException;
import com.epam.druzhinin.repositories.BasketRepository;
import com.epam.druzhinin.repositories.ItemRepository;
import com.epam.druzhinin.repositories.ProductRepository;
import com.epam.druzhinin.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BasketService {

    private final BasketRepository basketRepository;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final ItemRepository itemRepository;

    private final ModelMapper modelMapper;

    public BasketService(BasketRepository basketRepository, UserRepository userRepository, ProductRepository productRepository, ItemRepository itemRepository, ModelMapper modelMapper) {
        this.basketRepository = basketRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.itemRepository = itemRepository;
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

    public ItemDto addItem(AddItemDto addItemDto, Long userId) {
        log.info("Staring to add item of basket for user [id={}]", userId);
        UserEntity user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User isn't found with id=" + userId)
        );
        BasketEntity basket = basketRepository.findByUser(user).orElseThrow(
                () -> new NotFoundException("Basket isn't found for user id=" + user.getId())
        );
        log.info("Basket received [basketId={}]", basket.getId());
        ProductDocument productDocument = productRepository.findById(addItemDto.getProductId().toString()).orElseThrow(
                () -> new NotFoundException("Product isn't found id=" + addItemDto.getProductId())
        );
        log.info("ProductDocument is existing [id={}]", productDocument.getId());
        ItemEntity itemEntity = modelMapper.map(addItemDto, ItemEntity.class);
        itemEntity.setBasket(basket);
        ItemEntity savedEntity = itemRepository.save(itemEntity);
        log.info("Item was added for user [userId={},basketId={}]", userId, basket.getId());
        return modelMapper.map(savedEntity, ItemDto.class);
    }

    public void deleteItem(Long itemId) {
        log.info("Starting to delete the item [id={}]", itemId);
        ItemEntity itemEntity = itemRepository.findById(itemId).orElseThrow(
                () -> new NotFoundException("Item is not found id=" + itemId)
        );
        itemRepository.deleteById(itemId);
        log.info("Item is deleted [itemId={}, basketId={}]", itemEntity.getId(), itemEntity.getBasket().getId());
    }
}
