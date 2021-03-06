package com.epam.druzhinin.repositories;

import com.epam.druzhinin.entity.OrderEntity;
import com.epam.druzhinin.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Page<OrderEntity> findAllByUser(UserEntity userEntity, Pageable pageable);
}
