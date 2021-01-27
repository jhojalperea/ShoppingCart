package com.cart.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cart.cache.CartCache;
import com.cart.entity.Product;
import com.cart.exception.ProductException;
import com.cart.model.Cart;
import com.cart.model.CartProduct;

@Service
public class CartService {

	
	private final Logger logger = LoggerFactory.getLogger(CartService.class);
	
	@Autowired
	private CartCache cartCacheService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private BrandService brandService;
	
	
	public void addProductToCart( Cart cartAdd ) throws ProductException {
		
		validProductQuantityStock( cartAdd );
			
		CartProduct cartProductAdd = cartAdd.getCartProducts().get(0);			
		Product product = productService.getProductById( cartProductAdd.getIdProduct() );
		if( product == null ) {				
			logger.error("Producto asociado a [id={}] no existe", cartProductAdd.getIdProduct());
			throw new ProductException(
				new StringBuilder("Producto asociado a id=")
					.append(cartProductAdd.getIdProduct())
					.append(" no existe")
					.toString()
			);
		}
		cartProductAdd.setBrand( brandService.getBrandNameById( product.getIdBrand() ) );
		cartProductAdd.setDiscountPercentage( product.getDiscountPercentage() );
		cartProductAdd.setName( product.getName() );
		cartProductAdd.setPrice( product.getPrice() );
		cartProductAdd.setStatus( product.getStatus() );

		Cart cartCache = cartCacheService.getCartByUserId( cartAdd.getUserId() );
		if( cartCache == null ) {				
			cartCacheService.putCart(cartAdd);
		}else {
			
			CartProduct cartProductCache = getCartProductIfExistsInCart( cartCache, cartProductAdd.getIdProduct() );
			if( cartProductCache != null ) {
				cartProductCache.setQuantity( cartProductAdd.getQuantity() );
			}else {
				cartCache.getCartProducts().add(cartProductAdd);
			}	
			cartCacheService.putCart(cartCache);
		}
		
	}
	
	public Cart getCartProductsByUserId( String userId ) {		
		return cartCacheService.getCartByUserId(userId);
	}
	
	public void clearCartByUserId( String userId ) {		
		cartCacheService.relaseCartByUserId(userId);
	}
	
	private void validProductQuantityStock( Cart cart ) throws ProductException {
		
		CartProduct cartProduct =  cart.getCartProducts().get(0);
		Product product = productService.getProductById( cartProduct.getIdProduct() );
		if( product == null ) {				
			logger.error("Producto asociado a [id={} no existe", cartProduct.getIdProduct());
			throw new ProductException(
				new StringBuilder("Producto asociado a id=")
					.append(cartProduct.getIdProduct())
					.append(" no existe")
					.toString()
			);
		}
		if( product.getStockQuantity() < cartProduct.getQuantity() ) {
			logger.error("[Stock={}] es menor a [Quantity={}] para [ProductId={}]", product.getStockQuantity(), cartProduct.getQuantity(), cartProduct.getIdProduct());
			throw new ProductException(
				new StringBuilder("Stock=")
					.append(product.getStockQuantity())
					.append(" es menor a Quantity=")
					.append(cartProduct.getQuantity())
					.append(" para ProductId=")
					.append(cartProduct.getIdProduct())
					.toString()
			);
		}		
	}
	
	private CartProduct getCartProductIfExistsInCart( Cart cart, Long idProduct ) {
		
		for( CartProduct product : cart.getCartProducts() ) {
			if( product.getIdProduct().compareTo(idProduct) == 0 ) {
				return product;
			}
		}
		return null;
	}
}