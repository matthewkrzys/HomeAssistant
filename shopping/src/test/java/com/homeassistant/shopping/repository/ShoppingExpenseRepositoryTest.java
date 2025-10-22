package com.homeassistant.shopping.repository;

import com.homeassistant.shopping.entity.ShoppingExpense;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
@ContextConfiguration(classes = ShoppingExpenseRepositoryTest.Config.class)
class ShoppingExpenseRepositoryTest {

    @Autowired
    private ShoppingExpenseRepository repository;

    @Test
    void shouldSaveAndReadExpense() {
        ShoppingExpense expense = new ShoppingExpense();
        expense.setDate(LocalDate.of(2025,1,1));
        expense.setAmount(BigDecimal.valueOf(120.50));
        expense.setCategory("Food");
        expense.setPlace("Lidl");
        expense.setDescription("Description");
        expense.setImage("fake-image".getBytes());
        repository.save(expense);
        assertEquals(repository.findAll().size(), 1);
    }
    
    @Configuration
    @EnableJpaRepositories(basePackages = "com.homeassistant.shopping.repository")
    @EntityScan(basePackages = "com.homeassistant.shopping.entity")
    static class Config {}
}
