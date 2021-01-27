package com.cart.controller;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cart.entity.Product;
import com.cart.exception.ProductException;
import com.cart.model.PaginationResponse;
import com.cart.service.BrandService;
import com.cart.service.ProductService;

@RestController
@RequestMapping("/product")
@Validated
public class ProductController extends HandlerValidationController {
	
	
	private final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private BrandService brandService;
	
	@GetMapping(value = { "/filterByName/{name}/{page}/{size}", 
			"/filterByName/{name}/{page}", 
			"/filterByName/{name}" } )
    public ResponseEntity<PaginationResponse> filterProductByName( 
    	@PathVariable( name = "name" ) @NotBlank(message = "no puede ser NULL o vacio") String name, 
    	@PathVariable( name = "page", required = false ) @PositiveOrZero( message = "debe ser >= 0" ) Integer page, 
		@PathVariable( name = "size", required = false ) @Positive( message = "debe ser > 0" ) Integer size ){
        
		Pageable pagination = PageRequest.of(
			page != null? page: 0, 
			size != null? size: 3 );
		Page<Product> productPage = productService.getProductsByContainsName(name, pagination);
		
		return buildPaginationResponse( productPage );
	}
    
	
	@GetMapping(value = { "/filterByPrice/{priceFrom}/{priceTo}/{page}/{size}", 
			"/filterByPrice/{priceFrom}/{priceTo}/{page}", 
			"/filterByPrice/{priceFrom}/{priceTo}" })
    public ResponseEntity<PaginationResponse> filterProductByPrice( 
    	@PathVariable( name="priceFrom" ) @PositiveOrZero( message = "debe ser >= 0" ) int priceFrom, 
    	@PathVariable( name="priceTo" ) @PositiveOrZero( message = "debe ser >= 0" ) int priceTo, 
    	@PathVariable( name = "page", required = false ) @PositiveOrZero( message = "debe ser >= 0" ) Integer page, 
		@PathVariable( name = "size", required = false ) @Positive( message = "debe ser > 0" ) Integer size ){
        
		Pageable pagination = PageRequest.of(
				page != null? page: 0, 
				size != null? size: 3 );
		Page<Product> productPage = productService.getProductsByPricesBetween(priceFrom, priceTo, pagination);
		
		return buildPaginationResponse( productPage );
	}
	
	@GetMapping(value = { "/filterByBrand/{brand}/{page}/{size}",
			"/filterByBrand/{brand}/{page}",
			"/filterByBrand/{brand}" })
    public ResponseEntity<PaginationResponse> filterProductByBrand( 
    	@PathVariable( name = "brand" ) @NotBlank(message = "no puede ser NULL o vacio") String brand, 
    	@PathVariable( name = "page", required = false ) @PositiveOrZero( message = "debe ser >= 0" ) Integer page, 
		@PathVariable( name = "size", required = false ) @Positive( message = "debe ser > 0" ) Integer size ){
        
		Pageable pagination = PageRequest.of(
				page != null? page: 0, 
				size != null? size: 3 );
		try {			
			Page<Product> productPage = productService.getProductsByBrand(brand, pagination);
			return buildPaginationResponse( productPage );
			
		} catch (ProductException e) {
			
			return new ResponseEntity<PaginationResponse>(
				new PaginationResponse( pagination.getPageNumber(), pagination.getPageSize() ), 
				HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	private ResponseEntity<PaginationResponse> buildPaginationResponse( Page<Product> page ) {
		
		List<Product> products = page.getContent();
		for( Product p : products ) {
			p.setBrand( brandService.getBrandNameById( p.getIdBrand() ) );
		}
		PaginationResponse response = new PaginationResponse( page.getNumber(), page.getSize() );
		response.setResponse( products );
		response.setTotalElements( page.getTotalElements() );
		response.setNumberPages( page.getTotalPages() );
		return ResponseEntity.ok().body( response );
	}
}
