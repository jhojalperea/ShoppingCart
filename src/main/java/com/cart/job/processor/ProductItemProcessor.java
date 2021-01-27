package com.cart.job.processor;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.annotation.BeforeJob;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.cart.entity.Product;
import com.cart.enumerator.StatusEnum;
import com.cart.exception.ProductCsvException;
import com.cart.model.ProductCsv;
import com.cart.service.BrandService;
import com.cart.service.ProductCsvValidatorService;


@Service
@Scope("prototype")
public class ProductItemProcessor implements ItemProcessor<ProductCsv, Product> {

	
	private final Logger logger = LoggerFactory.getLogger(ProductItemProcessor.class);
	
	@Autowired
	private BrandService brandService;
	
	@Autowired
	private ProductCsvValidatorService validator;
	
	private int lineNumber;
	private List<ProductCsv> productsCsv = new ArrayList<>();
	
	@BeforeJob
	public void init() {
		lineNumber = 0;
		productsCsv.clear();
	}	
	
	@Override
	public Product process( final ProductCsv newProductCsv ) throws Exception {
		
		Product product = null;
		try {
			lineNumber++;
			validator.validateCompleteInformation(newProductCsv, lineNumber);
			validator.validateMounts(newProductCsv, lineNumber);
			validator.validateProductStatus(newProductCsv, lineNumber);
			validator.validateBrand(newProductCsv, lineNumber);
			validator.validateDuplicated( productsCsv, newProductCsv, lineNumber);
			
			logger.info("Registro OK - {}", newProductCsv);
			productsCsv.add(newProductCsv);
			
			product = new Product();
			product.setIdBrand( brandService.getBrandIdByBrandName( newProductCsv.getBrand().trim() ) );
			product.setName( newProductCsv.getName().toString() );
			product.setPrice( newProductCsv.getPrice() );
			product.setStatus( StatusEnum.getEnumByStatus( newProductCsv.getStatus()  ).getStatus().trim() );
			product.setStockQuantity( newProductCsv.getStockQuantity() );
			product.setDiscountPercentage( newProductCsv.getDiscountPercentage() );
			
		}catch( ProductCsvException e ) {
			return null;
		}
		return product;
	}
}