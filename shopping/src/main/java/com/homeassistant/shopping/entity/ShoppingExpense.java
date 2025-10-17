package com.homeassistant.shopping.entity;

import com.homeassistant.core.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "shopping_expenses")
@Getter
@Setter
public class ShoppingExpense extends BaseEntity {

    private String category;
    private BigDecimal amount;
    private LocalDate date;
    private String description;
}