package com.homeassistant.shopping.service;

import com.homeassistant.shopping.entity.ShoppingExpense;
import com.homeassistant.shopping.repository.ShoppingExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingExpenseService {

    private final ShoppingExpenseRepository repository;

    public ShoppingExpenseService(ShoppingExpenseRepository repository) {
        this.repository = repository;
    }

    public ShoppingExpense save(ShoppingExpense expense) {
        return repository.save(expense);
    }

    public List<ShoppingExpense> findAll() {
        return Optional.of(repository.findAll()).orElse(Collections.emptyList());
    }
}