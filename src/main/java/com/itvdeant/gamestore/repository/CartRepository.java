package com.itvdeant.gamestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itvdeant.gamestore.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

}
