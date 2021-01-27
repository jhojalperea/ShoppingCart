package com.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cart.entity.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {

	
	Brand findByBrandIgnoreCase( String brand );

}
