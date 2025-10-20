package com.homeassistant.shopping.service;

import com.homeassistant.shopping.dto.ShoppingExpenseResponse;
import com.homeassistant.shopping.entity.ShoppingExpense;
import com.homeassistant.shopping.repository.ShoppingExpenseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShoppingExpenseServiceTest {

    @Mock
    ShoppingExpenseRepository repository;

    @InjectMocks
    ShoppingExpenseService service;

    @Mock
    private ModelMapper modelMapper;

    @Test
    void shouldSaveExpenseWithFile() {
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.jpg", "image/jpeg", "fakeimage".getBytes()
        );
        service.saveExpenseWithFile("Food", BigDecimal.valueOf(100), LocalDate.now(), "Test", file);
        verify(repository, times(1)).save(any(ShoppingExpense.class));
    }

    @Test
    void shouldReturnAllExpense() {
        ShoppingExpense shoppingExpenseMock = Mockito.mock(ShoppingExpense.class);
        ShoppingExpense shoppingExpenseAnotherMock = Mockito.mock(ShoppingExpense.class);

        when(repository.findAll()).thenReturn(List.of(shoppingExpenseMock, shoppingExpenseAnotherMock));
        assertEquals(service.findAll().size(), 2);
    }

    @Test
    void shouldSaveExpense() {
        ShoppingExpense shoppingExpenseMock = Mockito.mock(ShoppingExpense.class);
        service.save(shoppingExpenseMock);
        verify(repository,times(1)).save(any(ShoppingExpense.class));
    }

    static Stream<Arguments> expenseProvider() {
        ShoppingExpense shoppingExpenseMock = Mockito.mock(ShoppingExpense.class);
        when(shoppingExpenseMock.getId()).thenReturn(1L);
        ShoppingExpense shoppingExpenseAnotherMock = Mockito.mock(ShoppingExpense.class);
        when(shoppingExpenseAnotherMock.getId()).thenReturn(2L);
        return Stream.of(
                Arguments.of(shoppingExpenseMock, 1L),
                Arguments.of(shoppingExpenseAnotherMock, 2L)
        );
    }

    @ParameterizedTest
    @MethodSource("expenseProvider")
    void shouldReturnExpenseById(ShoppingExpense shoppingExpenseMock, Long expectedId) {
        when(repository.findById(shoppingExpenseMock.getId())).thenReturn(Optional.of(shoppingExpenseMock));
        assertEquals(service.getExpense(expectedId).getId(), expectedId);
    }

    @Test
    void shouldReturnByIdDto() {
        Long expectedID = 1L;
        ShoppingExpense shoppingExpenseMock = Mockito.mock(ShoppingExpense.class, RETURNS_DEEP_STUBS);
        ShoppingExpenseResponse shoppingExpenseResponse = Mockito.mock(ShoppingExpenseResponse.class);
        when(repository.findById(expectedID)).thenReturn(Optional.of(shoppingExpenseMock));
        when(modelMapper.map(shoppingExpenseMock,ShoppingExpenseResponse.class)).thenReturn(shoppingExpenseResponse);
        assertNotNull(service.getExpenseDto(expectedID));
    }
}
