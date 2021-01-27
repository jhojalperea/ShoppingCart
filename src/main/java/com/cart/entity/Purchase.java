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

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table
public class Purchase implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String orderId;
	private String userId;
	private Long idProduct;
	private Integer quantity;
	private Integer price;
	private Integer salePrice;
	
	@CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Bogota")
	private Date purchaseDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
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

	public Integer getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Integer salePrice) {
		this.salePrice = salePrice;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Purchase [id=");
		builder.append(id);
		builder.append("][orderId=");
		builder.append(orderId);
		builder.append("][userId=");
		builder.append(userId);
		builder.append("][idProduct=");
		builder.append(idProduct);
		builder.append("][quantity=");
		builder.append(quantity);
		builder.append("][price=");
		builder.append(price);
		builder.append("][salePrice=");
		builder.append(salePrice);
		builder.append("][purchaseDate=");
		builder.append(purchaseDate);
		builder.append("]");
		return builder.toString();
	}	
}
