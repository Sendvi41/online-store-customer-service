package com.epam.druzhinin.controllers;

import com.epam.druzhinin.config.TestContainerConfig;
import com.epam.druzhinin.dto.CreateUserRequestDto;
import com.epam.druzhinin.entity.UserEntity;
import com.epam.druzhinin.enums.Gender;
import com.epam.druzhinin.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TestContainerConfig.class)
@AutoConfigureMockMvc
@Testcontainers
public class UserControllerTest {
    private static final String USERS_END_POINT = "/users";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createNewUser_shouldReturn200OkAndUserEntity() throws Exception {
        //given
        CreateUserRequestDto userRequestDto = prepareCreateUserRequestDto();
        //when
        String result = mockMvc.perform(post(USERS_END_POINT)
                        .content(objectMapper.writeValueAsString(userRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                //then
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        UserEntity actualEntity = objectMapper.readerFor(UserEntity.class).readValue(result);

        assertThat(result).isNotBlank();
        assertThat(userRequestDto.getName()).isEqualTo(actualEntity.getName());
        assertThat(userRequestDto.getCity()).isEqualTo(actualEntity.getCity());
        assertThat(userRequestDto.getGender()).isEqualTo(actualEntity.getGender());
        assertThat(userRequestDto.getEmail()).isEqualTo(actualEntity.getEmail());
        assertThat(userRequestDto.getPhoneNumber()).isEqualTo(actualEntity.getPhoneNumber());
    }

    private static CreateUserRequestDto prepareCreateUserRequestDto() {
        return new CreateUserRequestDto()
                .setName("Alex")
                .setLastName("Shmidt")
                .setBirthday(LocalDate.now())
                .setEmail("alex@epam.com")
                .setCity("New-York")
                .setGender(Gender.MALE);
    }
}
