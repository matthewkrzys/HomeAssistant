package com.homeassistant.shopping.repository;

import com.homeassistant.shopping.entity.ShoppingExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingExpenseRepository extends JpaRepository<ShoppingExpense, Long> {
}