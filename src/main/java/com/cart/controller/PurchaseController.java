package com.cart.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cart.exception.ProductException;
import com.cart.service.PurchaseService;

@RestController
@RequestMapping("/cart")
@Validated
public class PurchaseController extends HandlerValidationController {

	@Autowired
	private PurchaseService purchaseService;
	

	@GetMapping(value = "/buy/{userId}")
    public ResponseEntity<Map<String, Object>> buyProductsByUserId( @PathVariable String userId ){
		
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			response.put("status", HttpStatus.OK.value());
		    response.put("message", HttpStatus.OK.getReasonPhrase());
			
		    purchaseService.buy(userId);			
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
	
}
