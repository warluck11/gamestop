package com.itvdeant.gamestore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


import com.itvdeant.gamestore.entity.Product;
import com.itvdeant.gamestore.entity.User;
import com.itvdeant.gamestore.repository.CartRepository;
import com.itvdeant.gamestore.repository.ProductRepository;
import com.itvdeant.gamestore.service.CartService;
import com.itvdeant.gamestore.service.ProductService;

@Controller
@RequestMapping("/carts")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private ProductService productService;

	@GetMapping("/add/{prod_id}")
	public String addToCart(@AuthenticationPrincipal User user
			,@PathVariable("prod_id") Integer id) {
		
		Product product = this.productService.getProductById(id);
		
		this.cartService.createCart(user, product);
		
		return "redirect:/products/show";
	}
	
}
