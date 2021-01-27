package com.cart.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cart.enumerator.StatusEnum;
import com.cart.exception.ProductCsvException;
import com.cart.model.ProductCsv;

@Service
public class ProductCsvValidatorService {
	
	
	private final Logger logger = LoggerFactory.getLogger(ProductCsvValidatorService.class);
	
	@Autowired
	private BrandService brandService;
	
	
	public void validateMounts( ProductCsv productCsv, int lineNumber ) throws ProductCsvException{
		
		if( productCsv.getDiscountPercentage() < 0 ) {
			logger.error("Error registro:{} - [Porcentaje={}] negativo no valido", lineNumber, productCsv.getDiscountPercentage());
			throw new ProductCsvException("Porcentaje negativo no valido");
		}
		if( productCsv.getPrice() < 0 ) {
			logger.error("Error registro:{} - [Precio={}] negativo no valido", lineNumber, productCsv.getPrice());
			throw new ProductCsvException("Precio negativo de producto no valido");
		}
		if( productCsv.getStockQuantity() < 0 ) {
			logger.error("Error registro:{} - [Cantidad en stock={}] negativo no valido", lineNumber, productCsv.getStockQuantity());
			throw new ProductCsvException("Cantidad en stock negativo de producto no valido");
		}		
	}
	
	public void validateProductStatus( ProductCsv productCsv, int lineNumber ) throws ProductCsvException {
		
		if( StatusEnum.getEnumByStatus(productCsv.getStatus()) == null ) {
			logger.error("Error registro:{} - [Status={}] no valido", lineNumber, productCsv.getStatus());
			throw new ProductCsvException( "Status no valido" );
		}
	}
	
	public void validateBrand( ProductCsv productCsv, int lineNumber ) throws ProductCsvException {
	
		if( brandService.getBrandIdByBrandName( productCsv.getBrand() ) == null ) {
			logger.error("Error registro:{} - [Brand={}] no valido (no existe en la tabla Brand)", lineNumber, productCsv.getBrand());
			throw new ProductCsvException("Brand no valido (no existe en la tabla Brand)");
		}
	}
	
	public void validateDuplicated( List<ProductCsv> productsCsv, ProductCsv newProductCsv, int lineNumber ) throws ProductCsvException {
		if( ProductCsv.search(productsCsv, newProductCsv) ) {
			logger.error("Error registro:{} - Duplicado (registro se ignorará)", lineNumber);
			throw new ProductCsvException("Registro duplicado");
		}
	}
	
	
	public void validateCompleteInformation( ProductCsv productCsv, int lineNumber ) throws ProductCsvException{
		
		if( productCsv.getBrand() == null ||
			productCsv.getDiscountPercentage() == null ||
			productCsv.getName() == null ||
			productCsv.getPrice() == null ||
			productCsv.getStatus() == null ||
			productCsv.getStockQuantity() == null ) {
			
			logger.error("Error registro:{} - Parte de la información esta incompleta [{}]", lineNumber, productCsv);
			throw new ProductCsvException(
				new StringBuilder("Parte de la información esta incompleta ")
					.append(productCsv)
					.toString()
			);
		}
	}

}