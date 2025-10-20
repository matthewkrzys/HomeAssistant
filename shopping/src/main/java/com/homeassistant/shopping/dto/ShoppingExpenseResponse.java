package com.homeassistant.shopping.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ShoppingExpenseResponse {
    private Long id;
    private LocalDate date;
    private BigDecimal amount;
    private String category;
    private String place;
    private String description;
}
