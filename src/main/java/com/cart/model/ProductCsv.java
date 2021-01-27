package com.cart.model;

import java.util.List;

public class ProductCsv {

	private String name;
	private String brand;
	private Integer price;
	private Integer stockQuantity;
	private String status;
	private Integer discountPercentage;
	

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
	
	@Override
    public boolean equals(Object obj) {
		
		if (obj == null) return false;
        if (this == obj) return true;
        if (getClass() != obj.getClass()) return false;
        ProductCsv search = (ProductCsv)obj;               
        if( search.getName().trim().equalsIgnoreCase(this.getName().trim()) &&
        	search.getBrand().trim().equalsIgnoreCase(this.getBrand().trim()) &&
        	search.getStatus().trim().equalsIgnoreCase(this.getStatus().trim()) ){
        	return true;
        }
        return false;
    }
	
	public static boolean search( List<ProductCsv> productsCsv, ProductCsv newProductCsv ) {
		for( ProductCsv p : productsCsv ) {
			if( p.equals( newProductCsv ) ) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProductCsv [name=");
		builder.append(name);
		builder.append("][brand=");
		builder.append(brand);
		builder.append("][price=");
		builder.append(price);
		builder.append("][stockQuantity=");
		builder.append(stockQuantity);
		builder.append("][status=");
		builder.append(status);
		builder.append("][discountPercentage=");
		builder.append(discountPercentage);
		builder.append("]");
		return builder.toString();
	}
	
}
