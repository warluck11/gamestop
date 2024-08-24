package com.itvdeant.gamestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itvdeant.gamestore.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
