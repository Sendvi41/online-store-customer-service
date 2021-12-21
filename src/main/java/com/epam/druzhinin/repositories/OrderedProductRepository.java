package com.epam.druzhinin.repositories;


import com.epam.druzhinin.entity.OrderedProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderedProductRepository extends JpaRepository<OrderedProductEntity, Long> {
}
