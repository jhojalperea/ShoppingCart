package com.cart.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table
public class Product implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@JsonIgnore
	private Integer idBrand;
	private String name;
	private Integer price;
	private Integer stockQuantity;
	private String status;
	private Integer discountPercentage;
	
	@Transient
	private String brand;
		
	@CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Bogota")
	private Date creationDate;
    
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Bogota")
	private Date updateDate;
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Integer getIdBrand() {
		return idBrand;
	}
	
	public void setIdBrand(Integer idBrand) {
		this.idBrand = idBrand;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getPrice() {
		return price;
	}
	
	public void setPrice(Integer price) {
		this.price = price;
	}
	
	public Integer getStockQuantity() {
		return stockQuantity;
	}
	
	public void setStockQuantity(Integer stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
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
	
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Product [id=");
		builder.append(id);
		builder.append("][idBrand=");
		builder.append(idBrand);
		builder.append("][name=");
		builder.append(name);
		builder.append("][price=");
		builder.append(price);
		builder.append("][stockQuantity=");
		builder.append(stockQuantity);
		builder.append("][status=");
		builder.append(status);
		builder.append("][discountPercentage=");
		builder.append(discountPercentage);
		builder.append("][brand=");
		builder.append(brand);
		builder.append("][creationDate=");
		builder.append(creationDate);
		builder.append("][updateDate=");
		builder.append(updateDate);
		builder.append("]");
		return builder.toString();
	}	
}
