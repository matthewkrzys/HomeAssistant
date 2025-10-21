package com.homeassistant.shopping.controller;

import com.homeassistant.shopping.entity.ShoppingExpense;
import com.homeassistant.shopping.service.ShoppingExpenseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ShoppingExpenseController.class)
public class ShoppingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShoppingExpenseService service;

    @Test
    void shouldGetShoppingExpense() throws Exception {
        ShoppingExpense response = mock(ShoppingExpense.class);
        when(response.getId()).thenReturn(1L);
        when(response.getDate()).thenReturn(LocalDate.of(2025, 10, 21));
        when(response.getCategory()).thenReturn("Food");
        when(response.getAmount()).thenReturn(BigDecimal.valueOf(100L));
        when(response.getPlace()).thenReturn("Lidl");
        when(response.getDescription()).thenReturn("Description");

        when(service.findAll()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/shopping"))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
