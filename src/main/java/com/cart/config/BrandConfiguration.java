package com.cart.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cart.entity.Brand;
import com.cart.repository.BrandRepository;

@Configuration
public class BrandConfiguration {

	
	@Autowired
	private BrandRepository brandRepository;
	
	@Bean(name="brands")
	public List<Brand> loadedBrands() {
		
		List<Brand> brands = new ArrayList<>();
		brands.add(	new Brand("Apple") );
		brands.add(	new Brand("HTC") );
		brands.add(	new Brand("Huawey") );
		brands.add(	new Brand("LG") );
		brands.add(	new Brand("motorola") );
		brands.add(	new Brand("Samsung") );
		
		brands = brandRepository.saveAll(brands);
		return brands;
	}
}
