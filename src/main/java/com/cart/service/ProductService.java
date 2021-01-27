package com.cart.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cart.entity.Brand;
import com.cart.entity.Product;
import com.cart.exception.ProductException;
import com.cart.repository.BrandRepository;
import com.cart.repository.ProductRepository;

@Service
public class ProductService {

	
	private final Logger logger = LoggerFactory.getLogger(ProductService.class);
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private BrandRepository brandRepository;
	
	
	public Product getProductById( Long idProduct ) {
		
		Optional<Product> product = productRepository.findById(idProduct);
		return product.isPresent()? 
				product.get() : null;
	}
	
	public Page<Product> getProductsByContainsName( String name, Pageable pagination ){
		
		return productRepository.findByNameContainingIgnoreCaseOrderByNameDesc( name, pagination );
	}
	
	public Page<Product> getProductsByPricesBetween( Integer from, Integer to, Pageable pagination ){
		
		return productRepository.findByPriceBetween( from, to, pagination);
	}
	
	public Page<Product> getProductsByBrand( String brandName, Pageable pagination ) throws ProductException{
		
		Brand brand = brandRepository.findByBrandIgnoreCase( brandName );
		if( brand == null ) {
			logger.error("[Brand={}] no existe", brandName);
			throw new ProductException(
				new StringBuilder("Brand=")
					.append(brandName)
					.append(" no existe")
					.toString()
			);
		}
		return productRepository.findByIdBrandOrderByNameDesc( brand.getId(), pagination );
	}
	
	public List<Product> saveProduct( List<Product> products ){
		return productRepository.saveAll(products);
	}
}
