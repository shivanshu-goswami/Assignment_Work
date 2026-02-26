package com.shivanshu.Assignment.controller;

import com.shivanshu.Assignment.model.Shelf;
import com.shivanshu.Assignment.service.ShelfService;
import com.shivanshu.Assignment.exception.ShelfNotFoundException;
import com.shivanshu.Assignment.exception.ShelfPositionNotFoundException;
import com.shivanshu.Assignment.exception.ShelfPositionOccupiedException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShelfControllerTest {

    @Mock
    private ShelfService shelfService;

    @InjectMocks
    private ShelfController shelfController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // create shelf testing
    @Test
    void createShelf_ShouldReturnShelf() {
        Shelf shelf = new Shelf();
        shelf.setId("1");

        when(shelfService.createShelf(anyString(), any(Shelf.class)))
                .thenReturn(shelf);

        Shelf result = shelfController.createShelf("123", shelf);

        assertNotNull(result);
        assertEquals(shelf, result);
        verify(shelfService).createShelf("123", shelf);
    }

    // test when create shelf doesnt find shelfPosition
    @Test
    void createShelf_ShouldThrowShelfPositionNotFound() {
        Shelf shelf = new Shelf();

        when(shelfService.createShelf(anyString(), any(Shelf.class)))
                .thenThrow(new ShelfPositionNotFoundException("Not found"));

        assertThrows(ShelfPositionNotFoundException.class,
                () -> shelfController.createShelf("123", shelf));

        verify(shelfService).createShelf("123", shelf);
    }

    // test to tackle occupied shelf position case
    @Test
    void createShelf_ShouldThrowShelfPositionOccupied() {
        Shelf shelf = new Shelf();

        when(shelfService.createShelf(anyString(), any(Shelf.class)))
                .thenThrow(new ShelfPositionOccupiedException("Occupied"));

        assertThrows(ShelfPositionOccupiedException.class,
                () -> shelfController.createShelf("123", shelf));

        verify(shelfService).createShelf("123", shelf);
    }

    // testing read shelf by id
    @Test
    void getShelfById_ShouldReturnShelf() {
        Shelf shelf = new Shelf();
        shelf.setId("1");

        when(shelfService.getShelfById("1")).thenReturn(shelf);

        Shelf result = shelfController.getShelfById("1");

        assertEquals(shelf, result);
        verify(shelfService).getShelfById("1");
    }

    // test when shelf can be found with id
    @Test
    void getShelfById_ShouldThrowShelfNotFound() {
        when(shelfService.getShelfById("1"))
                .thenThrow(new ShelfNotFoundException("Not found"));

        assertThrows(ShelfNotFoundException.class,
                () -> shelfController.getShelfById("1"));

        verify(shelfService).getShelfById("1");
    }

    // testing read all shelf
    @Test
    void getAllShelves_ShouldReturnList() {
        Shelf shelf = new Shelf();
        shelf.setId("1");

        when(shelfService.getAllShelves())
                .thenReturn(List.of(shelf));

        List<Shelf> result = shelfController.getAllShelves();

        assertEquals(1, result.size());
        verify(shelfService).getAllShelves();
    }
    //updating shelf test
    @Test
    void updateShelf_ShouldReturnUpdatedShelf() {

        Shelf input = new Shelf();
        input.setShelfName("Updated Shelf");
        input.setPartNumber("P100");

        Shelf updated = new Shelf();
        updated.setId("1");
        updated.setShelfName("Updated Shelf");
        updated.setPartNumber("P100");

        when(shelfService.updateShelf("1", input))
                .thenReturn(updated);

        Shelf result = shelfController.updateShelfById("1", input);
        //'result' must match with 'updated'
        assertEquals("1", result.getId());
        assertEquals("Updated Shelf", result.getShelfName());

        verify(shelfService).updateShelf("1", input);
    }
    //exception testing in update test method
    @Test
    void updateShelf_ShouldThrowException_WhenShelfNotFound() {

        Shelf input = new Shelf();
        input.setShelfName("Updated Shelf");

        when(shelfService.updateShelf("1", input))
                .thenThrow(new ShelfNotFoundException("Shelf not found"));

        assertThrows(ShelfNotFoundException.class,
                () -> shelfController.updateShelfById("1", input));

        verify(shelfService).updateShelf("1", input);
    }

    // deleting shelf test
    @Test
    void deleteShelf_ShouldCallService() {
        doNothing().when(shelfService).deleteShelf("1");

        String response = shelfController.deleteShelf("1");

        assertEquals("Shelf deleted successfully", response);
        verify(shelfService).deleteShelf("1");
    }
}