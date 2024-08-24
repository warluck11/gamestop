package com.itvdeant.gamestore.dao;

public class AddProductDao {

	private String productName;
	private String description;
	private String manufacturer;
	private Integer price;
	private String category;
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	@Override
	public String toString() {
		return "AddProductDao [productName=" + productName + ", description=" + description + ", manufacturer="
				+ manufacturer + ", price=" + price + ", category=" + category + "]";
	}
	
	
}
