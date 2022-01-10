package com.epam.druzhinin.controllers;

import com.epam.druzhinin.document.ProductDocument;
import com.epam.druzhinin.dto.AddItemDto;
import com.epam.druzhinin.dto.BasketItemsDto;
import com.epam.druzhinin.dto.ItemDto;
import com.epam.druzhinin.dto.MessageDto;
import com.epam.druzhinin.entity.BasketEntity;
import com.epam.druzhinin.entity.ItemEntity;
import com.epam.druzhinin.entity.UserEntity;
import com.epam.druzhinin.repositories.BasketRepository;
import com.epam.druzhinin.repositories.ItemRepository;
import com.epam.druzhinin.repositories.ProductRepository;
import com.epam.druzhinin.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.epam.druzhinin.util.PreparationUtil.prepareProductDocument;
import static com.epam.druzhinin.util.PreparationUtil.prepareUserEntity;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BasketControllerTest {

    private static final String BASKET_END_POINT = "/basket";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void clearDb() {
        itemRepository.deleteAll();
        basketRepository.deleteAll();
        userRepository.deleteAll();
    }


    @Test
    void getItemsByUserId_shouldReturn200OkAndBasketItemsDto() throws Exception {
        //given
        UserEntity expectedUser = userRepository.save(prepareUserEntity());
        BasketEntity expectedBasket = basketRepository.save(new BasketEntity().setUser(expectedUser));
        //when
        String result = mockMvc.perform(get(BASKET_END_POINT + "/{id}", expectedUser.getId()))
                //then
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        BasketItemsDto actualEntity = objectMapper.readerFor(BasketItemsDto.class).readValue(result);

        assertThat(result).isNotBlank();
        assertThat(actualEntity.getUserId()).isEqualTo(expectedUser.getId());
        assertThat(actualEntity.getBasketId()).isEqualTo(expectedBasket.getId());
    }

    @Test
    void getItemsByInvalidUserId_shouldReturn404AndMessageDto() throws Exception {
        //given
        Long invalidUserId = 1L;
        //when
        String result = mockMvc.perform(get(BASKET_END_POINT + "/{id}", invalidUserId))
                //then
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        MessageDto actualDto = objectMapper.readerFor(MessageDto.class).readValue(result);

        assertThat(result).isNotBlank();
        assertThat(actualDto.getMessage()).isNotBlank();
    }

    @Test
    void addItemToBasket_shouldReturn200OkAndItemDto() throws Exception {
        //given
        ProductDocument savedProduct = productRepository.save(prepareProductDocument().setId(String.valueOf(1L)));
        UserEntity expectedUser = userRepository.save(prepareUserEntity());
        BasketEntity expectedBasket = basketRepository.save(new BasketEntity().setUser(expectedUser));
        AddItemDto addItemDto = new AddItemDto().setProductId(Long.valueOf(savedProduct.getId())).setAmount(2);
        //when
        String result = mockMvc.perform(post(BASKET_END_POINT + "/{id}", expectedUser.getId())
                        .content(objectMapper.writeValueAsString(addItemDto))
                        .contentType(MediaType.APPLICATION_JSON))
                //then
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        ItemDto actualDto = objectMapper.readerFor(ItemDto.class).readValue(result);

        assertThat(result).isNotBlank();
        assertThat(actualDto.getProductId()).isEqualTo(Long.valueOf(savedProduct.getId()));
        assertThat(actualDto.getAmount()).isEqualTo(addItemDto.getAmount());
    }

    @Test
    void addItemToBasketWithInvalidId_shouldReturn404AndMessageDto() throws Exception {
        //given
        Long invalidUserId = 1L;
        ProductDocument savedProduct = productRepository.save(prepareProductDocument().setId(String.valueOf(1L)));
        AddItemDto addItemDto = new AddItemDto().setProductId(Long.valueOf(savedProduct.getId())).setAmount(2);
        //when
        String result = mockMvc.perform(post(BASKET_END_POINT + "/{id}", invalidUserId)
                        .content(objectMapper.writeValueAsString(addItemDto))
                        .contentType(MediaType.APPLICATION_JSON))
                //then
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        MessageDto actualDto = objectMapper.readerFor(MessageDto.class).readValue(result);

        assertThat(result).isNotBlank();
        assertThat(actualDto.getMessage()).isNotBlank();
    }

    @Test
    void deleteItemFromBasket_shouldReturn200OkAndMessageDto() throws Exception {
        //given
        ProductDocument savedProduct = productRepository.save(prepareProductDocument().setId(String.valueOf(1L)));
        UserEntity expectedUser = userRepository.save(prepareUserEntity());
        BasketEntity expectedBasket = basketRepository.save(new BasketEntity().setUser(expectedUser));
        ItemEntity savedItem = itemRepository.save(new ItemEntity()
                .setBasket(expectedBasket)
                .setAmount(2)
                .setProductId(Long.valueOf(savedProduct.getId()))
        );
        //when
        String result = mockMvc.perform(delete(BASKET_END_POINT + "/{id}", savedItem.getId()))
                //then
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        MessageDto actualDto = objectMapper.readerFor(MessageDto.class).readValue(result);

        assertThat(result).isNotBlank();
        assertThat(actualDto.getMessage()).isNotBlank();
    }

    @Test
    void deleteItemFromBasketWithInvalidItemId__shouldReturn404AndMessageDto() throws Exception {
        //given
        Long invalidItemId = 1L;
        //when
        String result = mockMvc.perform(delete(BASKET_END_POINT + "/{id}", invalidItemId))
                //then
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        MessageDto actualDto = objectMapper.readerFor(MessageDto.class).readValue(result);

        assertThat(result).isNotBlank();
        assertThat(actualDto.getMessage()).isNotBlank();
    }
}