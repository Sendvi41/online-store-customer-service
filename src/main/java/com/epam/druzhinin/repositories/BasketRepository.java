package com.epam.druzhinin.repositories;

import com.epam.druzhinin.entity.BasketEntity;
import com.epam.druzhinin.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BasketRepository extends JpaRepository<BasketEntity, Long> {
    Optional<BasketEntity> findByUser(UserEntity userEntity);
}
