package com.homeassistant.shopping.controller;

import com.homeassistant.shopping.entity.ShoppingExpense;
import com.homeassistant.shopping.service.ShoppingExpenseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shopping")
public class ShoppingExpenseController {

    private final ShoppingExpenseService service;

    public ShoppingExpenseController(ShoppingExpenseService service) {
        this.service = service;
    }

    @PostMapping
    public ShoppingExpense add(@RequestBody ShoppingExpense expense) {
        return service.save(expense);
    }

    @GetMapping
    public List<ShoppingExpense> getAll() {
        return service.findAll();
    }
}