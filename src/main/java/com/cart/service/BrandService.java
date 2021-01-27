package com.cart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cart.entity.Brand;

@Service
public class BrandService {

	@Autowired
	public List<Brand> brands;
	
	public String getBrandNameById( Integer idBrand ) {
		for( Brand brand : brands ) {
			if( brand.getId().compareTo(idBrand) == 0 ) {
				return brand.getBrand();
			}
		}
		return null;
	}
	
	public Integer getBrandIdByBrandName( String brandName ) {
		for( Brand brand : brands ) {
			if( brand.getBrand().equalsIgnoreCase( brandName ) ) {
				return brand.getId();
			}
		}
		return null;
	}
	
}
