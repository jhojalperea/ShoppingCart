package com.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cart.entity.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

}
