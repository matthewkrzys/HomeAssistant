package com.homeassistant.shopping.service;

import com.homeassistant.shopping.dto.ShoppingExpenseResponse;
import com.homeassistant.shopping.entity.ShoppingExpense;
import com.homeassistant.shopping.repository.ShoppingExpenseRepository;
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
public class ShoppingExpenseService {

    private final ShoppingExpenseRepository repository;
    private final ModelMapper modelMapper;


    public ShoppingExpenseService(ShoppingExpenseRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public ShoppingExpense save(ShoppingExpense expense) {
        return repository.save(expense);
    }

    public List<ShoppingExpense> findAll() {
        return Optional.of(repository.findAll()).orElse(Collections.emptyList());
    }

    public void saveExpenseWithFile(String category, BigDecimal amount, LocalDate date, String description, MultipartFile file) {
        try {
            ShoppingExpense expense = new ShoppingExpense();
            expense.setCategory(category);
            expense.setAmount(amount);
            expense.setDate(date);
            expense.setDescription(description);
            expense.setImage(file.getBytes()); // zapis zdjęcia w bazie (byte[])

            repository.save(expense);
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