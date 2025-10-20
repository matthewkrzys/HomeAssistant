package com.homeassistant.shopping.entity;

import com.homeassistant.core.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
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

    private LocalDate date;
    private BigDecimal amount;
    private String category;
    private String place;
    private String description;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] image;
}