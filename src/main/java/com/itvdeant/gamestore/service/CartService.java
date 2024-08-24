package com.itvdeant.gamestore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.itvdeant.gamestore.entity.Cart;
import com.itvdeant.gamestore.entity.Product;
import com.itvdeant.gamestore.entity.User;
import com.itvdeant.gamestore.repository.CartRepository;

@Service
public class CartService {
	
	@Autowired
	private CartRepository cartRepository;

	
	public Cart createCart(User user, Product product) {
		
		Cart cart = new Cart();
		
		cart.setUser_id(user);
		cart.setProd_id(product);
		cart.setQunatity(1);
		cart.setTotal_price(product.getPrice());
		
		this.cartRepository.save(cart);
		
		return cart;
	}
	
}
