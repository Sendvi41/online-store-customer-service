package com.epam.druzhinin.controllers;

import com.epam.druzhinin.document.ProductDocument;
import com.epam.druzhinin.dto.MessageDto;
import com.epam.druzhinin.dto.OrderDto;
import com.epam.druzhinin.entity.*;
import com.epam.druzhinin.enums.OrderStatus;
import com.epam.druzhinin.repositories.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static com.epam.druzhinin.util.PreparationUtil.prepareProductDocument;
import static com.epam.druzhinin.util.PreparationUtil.prepareUserEntity;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    private static final String ORDER_END_POINT = "/order";

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
    private OrderRepository orderRepository;

    @Autowired
    private OrderedProductRepository orderedProductRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeTestMethod
    void clearDb() {
        itemRepository.deleteAll();
        basketRepository.deleteAll();
        orderedProductRepository.deleteAll();
        orderRepository.deleteAll();
        userRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    void createOrder_shouldReturn200OkAndOrderDto() throws Exception {
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
        String result = mockMvc.perform(post(ORDER_END_POINT)
                        .param("userId", expectedUser.getId().toString()))
                //then
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        OrderDto actualDto = objectMapper.readerFor(OrderDto.class).readValue(result);

        assertThat(result).isNotBlank();
        assertThat(actualDto.getUserId()).isEqualTo(expectedUser.getId());
        assertThat(actualDto.getOrderedProducts()).isNotNull();
        assertThat(actualDto.getOrderedProducts().get(0).getAmount()).isEqualTo(savedItem.getAmount());
    }

    @Test
    void createOrderWithInvalidUserId_shouldReturn404AndMessageDto() throws Exception {
        //given
        Long invalidUserId = 1L;
        //when
        String result = mockMvc.perform(post(ORDER_END_POINT)
                        .param("userId", invalidUserId.toString()))
                //then
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        MessageDto actualDto = objectMapper.readerFor(MessageDto.class).readValue(result);

        assertThat(result).isNotBlank();
        assertThat(actualDto.getMessage()).isNotBlank();
    }

    @Test
    void getOrdersByUserId_shouldReturn200OkAndListOrderDto() throws Exception {
        //given
        ProductDocument savedProduct = productRepository.save(prepareProductDocument().setId(String.valueOf(1L)));
        UserEntity expectedUser = userRepository.save(prepareUserEntity());
        BasketEntity expectedBasket = basketRepository.save(new BasketEntity().setUser(expectedUser));
        OrderEntity savedOrder = orderRepository.save(new OrderEntity()
                .setUser(expectedUser)
                .setDate(ZonedDateTime.now())
                .setStatus(OrderStatus.PENDING)
        );
        orderedProductRepository.save(new OrderedProductEntity()
                .setProductId(Long.valueOf(savedProduct.getId()))
                .setAmount(2)
                .setPrice(BigDecimal.valueOf(2000))
                .setOrder(savedOrder)
        );
        //when
        String result = mockMvc.perform(get(ORDER_END_POINT)
                        .param("userId", expectedUser.getId().toString()))
                //then
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        assertThat(result).isNotBlank();
    }

    @Test
    void getOrdersByInvalidUserId_shouldReturn404AndMessageDto() throws Exception {
        //given
        Long invalidUserId = 1L;
        //when
        String result = mockMvc.perform(get(ORDER_END_POINT)
                        .param("userId", invalidUserId.toString()))
                //then
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        MessageDto actualDto = objectMapper.readerFor(MessageDto.class).readValue(result);

        assertThat(result).isNotBlank();
        assertThat(actualDto.getMessage()).isNotBlank();
    }
}