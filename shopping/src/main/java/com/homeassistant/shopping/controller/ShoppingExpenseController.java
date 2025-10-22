package com.homeassistant.shopping.controller;

import com.homeassistant.shopping.dto.ShoppingExpenseResponse;
import com.homeassistant.shopping.entity.ShoppingExpense;
import com.homeassistant.shopping.service.ShoppingExpenseService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/shopping")
public class ShoppingExpenseController {

    private ShoppingExpenseService service;

    public ShoppingExpenseController(ShoppingExpenseService service) {
        this.service = service;
    }

    @PostMapping()
    public ResponseEntity<?> addShoppingExpense(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam("amount") BigDecimal amount,
            @RequestParam("category") String category,
            @RequestParam("place") String place,
            @RequestParam("description") String description,
            @RequestParam("file") MultipartFile file
    ) {
        ShoppingExpense expense = service.saveExpenseWithFile(date, amount, category, place, description, file);
        return ResponseEntity.ok(expense);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShoppingExpenseResponse> getExpense(@PathVariable Long id) {
        return ResponseEntity.ok(service.getExpenseDto(id));
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getExpenseImage(@PathVariable Long id) {
        ShoppingExpense expense = service.getExpense(id);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                .body(expense.getImage());
    }
    @GetMapping
    public List<ShoppingExpense> getAll() {
        return service.findAll();
    }
}