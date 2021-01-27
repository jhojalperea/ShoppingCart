package com.cart.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class Cart {

	@NotBlank(message = "No puede ser NULL o vacio")
	private String userId;
	
	@Valid
	@Size( min = 1, max = 1, message = "lista debe contener un solo elemento")
	private List<CartProduct> cartProducts;
	
	public Cart() {
		super();		
	}	

	public Cart(String userId) {
		super();
		this.userId = userId;
		this.cartProducts = new ArrayList<>();
	}

	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public List<CartProduct> getCartProducts() {
		return cartProducts;
	}
	
	public void setCartProducts(List<CartProduct> cartProducts) {
		this.cartProducts = cartProducts;
	}

	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Cart [userId=");
		builder.append(userId);
		builder.append("][cartProducts=");
		builder.append(cartProducts);
		builder.append("]");
		return builder.toString();
	}	
}

