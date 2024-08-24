package com.itvdeant.gamestore.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.itvdeant.gamestore.FileStorageProperties;
import com.itvdeant.gamestore.dao.AddProductDao;
import com.itvdeant.gamestore.dao.UpdateProductDao;
import com.itvdeant.gamestore.entity.Product;
import com.itvdeant.gamestore.exceptions.StorageException;
import com.itvdeant.gamestore.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	private final Path rootLocation;
	
	public ProductService(FileStorageProperties properties) {
		this.rootLocation = Paths.get(properties.getUploadDir());
		
			try {
				Files.createDirectories(rootLocation);
			} catch (IOException e) {
				throw new StorageException("Could not Find File Directory");
			}
		
	}
	
	
	public Product createProduct(AddProductDao addProductDao) {
		
		Product product = new Product();
		
		product.setProductName(addProductDao.getProductName());
		product.setDescription(addProductDao.getDescription());
		product.setManufacturer(addProductDao.getManufacturer());
		product.setPrice(addProductDao.getPrice());
		product.setCategory(addProductDao.getCategory());
		
		this.productRepository.save(product);
		
		
		return product;
		
	}
	
	
	public List<Product> getProducts(){
		List<Product> products = this.productRepository.findAll();
		
		return products;
	}
	
	
	public Product getProductById(Integer id) {
		Product product = this.productRepository.findById(id).orElse(null);
		return product;
	}
	
	
	public void DeleteProduct(Integer id) {
		Product product = this.getProductById(id);
		
		this.productRepository.delete(product);
	}
	
	public Product updateProduct(Integer id, UpdateProductDao updateProductDao) {
		
		Product product = this.getProductById(id);
		
		if(updateProductDao.getProductName() != null) {
			product.setProductName(updateProductDao.getProductName());
		}
		
		if(updateProductDao.getDescription() != null) {
			product.setDescription(updateProductDao.getDescription());
		}
		
		if(product.getCategory() != null) {
			product.setCategory(updateProductDao.getCategory());
		}
		
		if(product.getManufacturer() != null) {
			product.setManufacturer(updateProductDao.getManufacturer());
		}
		
		if(product.getPrice() != null) {
			product.setPrice(updateProductDao.getPrice());
		}
		
		this.productRepository.save(product);
		
		return product;
	}
	
	
	public String storeFile(MultipartFile file, Integer id) {
		
		try {
			
			if(file.isEmpty()) {
				throw new StorageException("File is empty");
			}
			
			Path destinationFile = this.rootLocation.resolve(Paths.get(file.getOriginalFilename()));
			
			try(InputStream inputStream = file.getInputStream()){
				Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
			} 
			
			Product product = this.productRepository.findById(id).orElseThrow( () -> {
				throw new StorageException("Product with id " + id + " is not found");
			});
			
			String fileUploadDir = ServletUriComponentsBuilder.fromCurrentContextPath()
									.path("/products/download/")
									.path(file.getOriginalFilename())
									.toUriString();
			
			product.setImageUrl(fileUploadDir);
			
			this.productRepository.save(product);
			
			return "File Uploaded Succesfully";
			
		} catch (Exception e) {
			throw new StorageException("Could Not Storage File");
		}
		
	}
	
	
	
	public Resource loadAsResource(String fileName) {
		Path file = rootLocation.resolve(fileName);
		
		try {
			
			Resource resource = new UrlResource(file.toUri());
			
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}else {
				throw new StorageException("Could not read a File");
			}
			
		} catch (Exception e) {
			throw new StorageException("Could not read a file");
		}
		
	}
	

}
