package com.homeassistant.shopping.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homeassistant.shopping.dto.ShoppingExpenseResponse;
import com.homeassistant.shopping.entity.ShoppingExpense;
import com.homeassistant.shopping.service.ShoppingExpenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ShoppingExpenseController.class)
public class ShoppingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShoppingExpenseService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private ShoppingExpense shoppingExpense;

    @Mock
    private ShoppingExpenseResponse shoppingExpenseResponse;

    @BeforeEach
    void setup() {
        when(shoppingExpense.getId()).thenReturn(1L);
        when(shoppingExpense.getCategory()).thenReturn("Food");
        when(shoppingExpense.getAmount()).thenReturn(BigDecimal.valueOf(123.45));
        when(shoppingExpense.getDate()).thenReturn(LocalDate.of(2025, 1, 1));
        when(shoppingExpense.getPlace()).thenReturn("Lidl");
        when(shoppingExpense.getDescription()).thenReturn("Paragon z Lidla");
        when(shoppingExpense.getImage()).thenReturn("fake-image".getBytes());

        when(shoppingExpenseResponse.getId()).thenReturn(1L);
        when(shoppingExpenseResponse.getCategory()).thenReturn("Food");
        when(shoppingExpenseResponse.getAmount()).thenReturn(BigDecimal.valueOf(123.45));
        when(shoppingExpenseResponse.getDate()).thenReturn(LocalDate.of(2025, 1, 1));
        when(shoppingExpenseResponse.getPlace()).thenReturn("Lidl");
        when(shoppingExpenseResponse.getDescription()).thenReturn("Paragon z Lidla");
    }

    @Test
    void shouldAddShoppingExpense() throws Exception {
        MockMultipartFile imagePart = new MockMultipartFile(
                "file",
                "paragon.jpg",
                "image/jpeg",
                "fake-image".getBytes()
        );
        when(service.saveExpenseWithFile(any(), any(), any(), any(), any(), any())).thenReturn(shoppingExpense);
        mockMvc.perform(
                        multipart("/api/shopping")
                                .file(imagePart)
                                .param("date", "2025-01-01")
                                .param("amount", "123.45")
                                .param("category", "Food")
                                .param("place", "Lidl")
                                .param("description", "Paragon z Lidla")
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.date").value("2025-01-01"))
                .andExpect(jsonPath("$.amount").value(123.45))
                .andExpect(jsonPath("$.category").value("Food"))
                .andExpect(jsonPath("$.place").value("Lidl"))
                .andExpect(jsonPath("$.description").value("Paragon z Lidla"));
    }

    @Test
    void shouldGetShoppingExpenseResponse() throws Exception {
        when(service.getExpenseDto(1L)).thenReturn(shoppingExpenseResponse);

        mockMvc.perform(get("/api/shopping/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.date").value("2025-01-01"))
                .andExpect(jsonPath("$.amount").value(123.45))
                .andExpect(jsonPath("$.category").value("Food"))
                .andExpect(jsonPath("$.place").value("Lidl"))
                .andExpect(jsonPath("$.description").value("Paragon z Lidla"));
    }

    @Test
    void shouldGetImage() throws Exception {
        when(service.getExpense(1L)).thenReturn(shoppingExpense);

        mockMvc.perform(get("/api/shopping/1/image")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG))
                .andExpect(content().bytes("fake-image".getBytes()));

    }
}
