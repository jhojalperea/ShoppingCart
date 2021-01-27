package com.cart.model;

import javax.validation.constraints.Positive;

public class CartProduct {

	@Positive( message = "debe ser > 0" )
	private Long idProduct;

	@Positive( message = "debe ser > 0" )
	private Integer quantity;
	private String name;
	private String brand;
	private String status;
	private Integer price;
	private Integer discountPercentage;	

	
	public Long getIdProduct() {
		return idProduct;
	}
	
	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getBrand() {
		return brand;
	}
	
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Integer getQuantity() {
		return quantity;
	}
	
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	public Integer getPrice() {
		return price;
	}
	
	public void setPrice(Integer price) {
		this.price = price;
	}
	
	public Integer getDiscountPercentage() {
		return discountPercentage;
	}
	
	public void setDiscountPercentage(Integer discountPercentage) {
		this.discountPercentage = discountPercentage;
	}
	
	
	public Integer getSalePrice() {
		return (int)((1 - discountPercentage / 100.0) * price);
	}
	
	public Integer getSubtotal() {
		return quantity * getSalePrice();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CartProduct [idProduct=");
		builder.append(idProduct);
		builder.append("][quantity=");
		builder.append(quantity);
		builder.append("][name=");
		builder.append(name);
		builder.append("][brand=");
		builder.append(brand);
		builder.append("][status=");
		builder.append(status);
		builder.append("][price=");
		builder.append(price);
		builder.append("][discountPercentage=");
		builder.append(discountPercentage);
		builder.append("]");
		return builder.toString();
	}	
}
