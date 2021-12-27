package com.epam.druzhinin.util;

import com.epam.druzhinin.document.ProductDocument;
import com.epam.druzhinin.entity.UserEntity;
import com.epam.druzhinin.enums.Gender;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@UtilityClass
public class PreparationUtil {
    public static UserEntity prepareUserEntity() {
        return new UserEntity()
                .setName("Alex")
                .setMiddleName("Andreevich")
                .setLastName("Shmidt")
                .setBirthday(LocalDate.now())
                .setEmail("alex@epam.com")
                .setCity("New-York")
                .setGender(Gender.MALE);
    }

    public static ProductDocument prepareProductDocument() {
        return new ProductDocument()
                .setName("sofa")
                .setCategory("furniture")
                .setAmount(2)
                .setPrice(BigDecimal.valueOf(1000))
                .setDate(ZonedDateTime.now());
    }
}
