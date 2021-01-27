package com.cart.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import com.cart.model.Cart;

@Service
@CacheConfig(cacheNames = {"Carts"})
public class CartCache {

	
	@Autowired
	private CacheManager cacheManager;
	
	
	public Cart getCartByUserId( String userId ) {
		
		if( existsCacheByUserId(userId) ) {
			return (Cart)cacheManager.getCache("Carts").get( userId ).get();
		}
		return null;
	}
	
	private boolean existsCacheByUserId( String userId ){		
		return cacheManager.getCache("Carts").get( userId ) == null? false : true;
	}
	
	@CachePut( key="#cart.userId" )
	public Cart putCart( Cart cart ) {		
		return cart;
	}
		
	@CacheEvict( key="#userId" )
	public void relaseCartByUserId( String userId ) {		
	}
	
}
