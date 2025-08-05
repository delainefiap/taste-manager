package br.com.tastemanager.validator;

import br.com.tastemanager.dto.request.MenuItemRequestDTO;
import br.com.tastemanager.dto.request.MenuRequestDTO;
import br.com.tastemanager.entity.Menu;
import jakarta.validation.ConstraintDefinitionException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MenuValidatorTest {

    private final MenuValidator menuValidator = new MenuValidator();

    @Nested
    @DisplayName("validateMenuRequest")
    class ValidateMenuRequest {

        @Test
        @DisplayName("throws exception when menu items are null")
        void throwsExceptionWhenMenuItemsAreNull() {
            MenuRequestDTO menuRequest = new MenuRequestDTO();
            menuRequest.setItems(null);

            Executable executable = () -> menuValidator.validateMenuRequest(menuRequest);

            assertThrows(IllegalArgumentException.class, executable);
        }

        @Test
        @DisplayName("throws exception when menu items are empty")
        void throwsExceptionWhenMenuItemsAreEmpty() {
            MenuRequestDTO menuRequest = new MenuRequestDTO();
            menuRequest.setItems(Collections.emptyList());

            Executable executable = () -> menuValidator.validateMenuRequest(menuRequest);

            assertThrows(IllegalArgumentException.class, executable);
        }

        @Test
        @DisplayName("throws exception when item name is null")
        void throwsExceptionWhenItemNameIsNull() {
            MenuItemRequestDTO item = new MenuItemRequestDTO();
            item.setName(null);
            item.setPrice(10.0);
            MenuRequestDTO menuRequest = new MenuRequestDTO();
            menuRequest.setItems(List.of(item));

            Executable executable = () -> menuValidator.validateMenuRequest(menuRequest);

            assertThrows(IllegalArgumentException.class, executable);
        }

        @Test
        @DisplayName("throws exception when item price is null")
        void throwsExceptionWhenItemPriceIsNull() {
            MenuItemRequestDTO item = new MenuItemRequestDTO();
            item.setName("Pizza");
            item.setPrice(null);
            MenuRequestDTO menuRequest = new MenuRequestDTO();
            menuRequest.setItems(List.of(item));

            Executable executable = () -> menuValidator.validateMenuRequest(menuRequest);

            assertThrows(ConstraintDefinitionException.class, executable);
        }

        @Test
        @DisplayName("throws exception when item price is less than or equal to zero")
        void throwsExceptionWhenItemPriceIsLessThanOrEqualToZero() {
            MenuItemRequestDTO item = new MenuItemRequestDTO();
            item.setName("Pizza");
            item.setPrice(0.0);
            MenuRequestDTO menuRequest = new MenuRequestDTO();
            menuRequest.setItems(List.of(item));

            Executable executable = () -> menuValidator.validateMenuRequest(menuRequest);

            assertThrows(ConstraintDefinitionException.class, executable);
        }

        @Test
        @DisplayName("does not throw exception for valid menu request")
        void doesNotThrowExceptionForValidMenuRequest() {
            MenuItemRequestDTO item = new MenuItemRequestDTO();
            item.setName("Pizza");
            item.setPrice(10.0);
            MenuRequestDTO menuRequest = new MenuRequestDTO();
            menuRequest.setItems(List.of(item));

            Executable executable = () -> menuValidator.validateMenuRequest(menuRequest);

            assertDoesNotThrow(executable);
        }
    }

    @Nested
    @DisplayName("validateMenuExists")
    class ValidateMenuExists {

        @Test
        @DisplayName("throws exception when menu is null")
        void throwsExceptionWhenMenuIsNull() {
            Executable executable = () -> menuValidator.validateMenuExists(null);

            assertThrows(IllegalArgumentException.class, executable);
        }

        @Test
        @DisplayName("does not throw exception when menu exists")
        void doesNotThrowExceptionWhenMenuExists() {
            Menu menu = new Menu();

            Executable executable = () -> menuValidator.validateMenuExists(menu);

            assertDoesNotThrow(executable);
        }
    }
}
