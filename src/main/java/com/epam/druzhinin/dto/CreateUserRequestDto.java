package com.epam.druzhinin.dto;

import com.epam.druzhinin.enums.Gender;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Data
@Accessors(chain = true)
public class CreateUserRequestDto {
    @NotBlank
    private String name;
    private String middleName;
    @NotBlank
    private String lastName;

    @DateTimeFormat(pattern = "YYYY-MM-DD")
    private ZonedDateTime birthday;

    @NotNull
    private Gender gender;
    private String city;

    private String phoneNumber;

    @Email
    private String email;
}
