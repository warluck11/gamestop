package com.itvdeant.gamestore.controller;

import java.net.http.HttpHeaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.service.annotation.DeleteExchange;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.itvdeant.gamestore.dao.AddProductDao;
import com.itvdeant.gamestore.dao.UpdateProductDao;
import com.itvdeant.gamestore.entity.Product;
import com.itvdeant.gamestore.service.ProductService;



//@RestController
@Controller
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	
	/*
	
	@PostMapping("")
	public ResponseEntity<?> create(@RequestBody AddProductDao addProductDao){
		return ResponseEntity.ok(this.productService.createProduct(addProductDao));
	}
	
	@GetMapping("")
	public ResponseEntity<?> getAll(){
		return ResponseEntity.ok(this.productService.getProducts());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable Integer id){
		return ResponseEntity.ok(this.productService.getProductById(id));
	}
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable Integer id){
		this.productService.DeleteProduct(id);
		
		return "Product deleted";
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody UpdateProductDao updateProductDao, @PathVariable Integer id){
		return ResponseEntity.ok(this.productService.updateProduct(id, updateProductDao));
	}
	
	
	@PostMapping("/{id}/upload")
	public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file,@PathVariable Integer id){
		return ResponseEntity.ok(this.productService.storeFile(file, id));
	}
	
	@GetMapping("/download/{filename}")
	public ResponseEntity<?> download(@PathVariable("filename") String fileName){
		Resource resource = this.productService.loadAsResource(fileName);
		
		return ResponseEntity.ok().header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION,
												"attachment; filename=\"" + fileName + "\"").body(resource);
	}
	
	*/
	
	@GetMapping("")
	public String getAll(Model model) {
		model.addAttribute("products", this.productService.getProducts());
		return "viewproducts.html";
	}
	
	@GetMapping("/add")
	public String addProduct(Model model) {
		model.addAttribute("product", new Product());
		return "addproduct.html";
	}
	
	@PostMapping("/add")
	public String addProducts(Model model, @ModelAttribute("product") AddProductDao addProductDao) {
		model.addAttribute("product", addProductDao);
		this.productService.createProduct(addProductDao);
		return "index.html";
	}
	
	@GetMapping("/edit/{id}")
	public String editProduct(Model model,@PathVariable("id") Integer id) {
		model.addAttribute("product", this.productService.getProductById(id));
		return "editproduct.html";
	}
	
	
	@PostMapping("/edit/{id}")
	public String editproducts(Model model,@ModelAttribute("product") UpdateProductDao updateProductDao,
			@PathVariable("id") Integer id) {
		Product product = this.productService.getProductById(id);
		model.addAttribute("product", product);
		
		this.productService.updateProduct(id, updateProductDao);
		return "viewproducts";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteProduct(@PathVariable("id") Integer id) {
		this.productService.DeleteProduct(id);
		return "viewproducts";
	}
	
}
