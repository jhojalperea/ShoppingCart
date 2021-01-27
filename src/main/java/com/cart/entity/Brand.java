package com.cart.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Brand implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String brand;
	
	
	
	public Brand() {
		super();
	}
	
	public Brand(String brand) {
		super();
		this.brand = brand;
	}

	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getBrand() {
		return brand;
	}
	
	public void setBrand(String brand) {
		this.brand = brand;
	}

	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Brand [id=");
		builder.append(id);
		builder.append("][brand=");
		builder.append(brand);
		builder.append("]");
		return builder.toString();
	}	
}
