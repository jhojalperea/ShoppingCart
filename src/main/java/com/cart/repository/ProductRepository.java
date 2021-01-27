package com.cart.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cart.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {


	Page<Product> findByNameContainingIgnoreCaseOrderByNameDesc( String name, Pageable pageable );
	
	Page<Product> findByIdBrandOrderByNameDesc( Integer idBrand, Pageable pageable );
	
	Page<Product> findByPriceBetween( Integer from, Integer to, Pageable pageable );

}
