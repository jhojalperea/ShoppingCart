package com.cart.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cart.exception.ProductException;
import com.cart.model.Cart;
import com.cart.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController extends HandlerValidationController {

	
	@Autowired
	private CartService cartService;
	
	@PostMapping(value = "/addProduct")
    public ResponseEntity<Map<String, Object>> addProduct( @Valid @RequestBody @NotNull(message = "no puede ser NULL") Cart cartAdd ){		
		
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			response.put("status", HttpStatus.OK.value());
		    response.put("message", HttpStatus.OK.getReasonPhrase());
			
		    cartService.addProductToCart(cartAdd);			
			return ResponseEntity
					.ok()
					.body( response );
		} catch (ProductException e) {			
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		    response.put("message", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		    response.put("error", e.getMessage());
		    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);	
		}
	}
    
	@GetMapping(value = "/listProducts/{userId}")
    public ResponseEntity<Cart> listProductsByUserId( @PathVariable @NotBlank(message = "no puede ser NULL o vacio") String userId ){
		
		Cart cart = cartService.getCartProductsByUserId(userId);
		if( cart == null ) {
			return ResponseEntity
					.ok()
					.body( new Cart(userId) );
		}
		return ResponseEntity
				.ok()
				.body( cart );
	}
	
	@GetMapping(value = "/clear/{userId}")
    public ResponseEntity<Map<String, Object>> clearProductsByuserId( @PathVariable @NotBlank(message = "no puede ser NULL o vacio") String userId ){
		
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("status", HttpStatus.OK.value());
	    response.put("message", HttpStatus.OK.getReasonPhrase());
	    
		cartService.clearCartByUserId(userId);
		return ResponseEntity
				.ok()
				.body( response );
	}
	
}
