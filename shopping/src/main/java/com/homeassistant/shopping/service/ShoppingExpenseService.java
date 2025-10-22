package com.homeassistant.shopping.service;

import com.homeassistant.shopping.dto.ShoppingExpenseResponse;
import com.homeassistant.shopping.entity.ShoppingExpense;
import com.homeassistant.shopping.repository.ShoppingExpenseRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ShoppingExpenseService {

    private ShoppingExpenseRepository repository;
    private ModelMapper modelMapper;

    public ShoppingExpense save(ShoppingExpense expense) {
        return repository.save(expense);
    }

    public List<ShoppingExpense> findAll() {
        return Optional.of(repository.findAll()).orElse(Collections.emptyList());
    }

    public ShoppingExpense saveExpenseWithFile(LocalDate date, BigDecimal amount, String category, String place, String description, MultipartFile file) {
        try {
            ShoppingExpense expense = new ShoppingExpense();
            expense.setDate(date);
            expense.setAmount(amount);
            expense.setCategory(category);
            expense.setPlace(place);
            expense.setDescription(description);
            expense.setImage(file.getBytes());

            return repository.save(expense);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read image file", e);
        }
    }

    public ShoppingExpense getExpense(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
    }

    public ShoppingExpenseResponse getExpenseDto(Long id) {
        return modelMapper.map(getExpense(id), ShoppingExpenseResponse.class);
    }
}