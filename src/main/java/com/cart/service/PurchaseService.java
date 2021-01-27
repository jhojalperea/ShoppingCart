package com.cart.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cart.cache.CartCache;
import com.cart.entity.Product;
import com.cart.entity.Purchase;
import com.cart.exception.ProductException;
import com.cart.model.Cart;
import com.cart.model.CartProduct;
import com.cart.repository.PurchaseRepository;

@Service
public class PurchaseService {
	
	
	private final Logger logger = LoggerFactory.getLogger(PurchaseService.class);

	@Autowired
	private PurchaseRepository purchaseRepository;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private ProductService productService;
	
	public List<Purchase> saveProduct( List<Purchase> purchases ){
		return purchaseRepository.saveAll(purchases);
	}
	
	public void buy( String userId ) throws ProductException {
		
		List<Purchase> purchases = new ArrayList<>();
		List<Product> products = new ArrayList<>();
		
		Cart cart = cartService.getCartProductsByUserId( userId );
		if( cart == null ) {
			logger.error("Carrito del usuario se encuentra vacio");
			throw new ProductException("Carrito del usuario se encuentra vacio");
		}
		String orderId = UUID.randomUUID().toString();
		
		for( CartProduct cartProduct : cart.getCartProducts() ) {
			
			Purchase purchase = new Purchase();
			purchase.setIdProduct( cartProduct.getIdProduct() );
			purchase.setOrderId( orderId );
			purchase.setPrice( cartProduct.getPrice() );
			purchase.setQuantity( cartProduct.getQuantity() );
			purchase.setSalePrice( cartProduct.getSalePrice() );
			purchase.setUserId( userId );
			purchases.add( purchase );
			
			Product product = productService.getProductById( cartProduct.getIdProduct() );
			product.setStockQuantity( product.getStockQuantity() - cartProduct.getQuantity() );
			products.add(product);		
		}
		
		saveProduct(purchases);
		productService.saveProduct(products);
		
		cartService.clearCartByUserId(userId);		
	}
}
