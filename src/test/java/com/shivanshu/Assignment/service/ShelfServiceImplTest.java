package com.shivanshu.Assignment.service;

import com.shivanshu.Assignment.exception.ShelfNotFoundException;
import com.shivanshu.Assignment.exception.ShelfPositionNotFoundException;
import com.shivanshu.Assignment.exception.ShelfPositionOccupiedException;
import com.shivanshu.Assignment.model.Shelf;
import com.shivanshu.Assignment.repository.ShelfRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShelfServiceImplTest {

    @Mock
    private ShelfRepository shelfRepository;

    @InjectMocks
    private ShelfServiceImpl shelfService;
    //testing create shelf method
    @Test
    void createShelf_ShouldCreateSuccessfully() {

        Shelf shelf = new Shelf();
        shelf.setShelfName("Shelf A");
        shelf.setPartNumber("PN123");

        when(shelfRepository.createShelf(anyString(), any(Shelf.class)))
                .thenReturn(shelf);

        Shelf result = shelfService.createShelf("sp1", shelf);

        assertNotNull(result);
//        verify(shelfRepository).createShelf("sp1", shelf);
        assertEquals(result,shelf);
    }

    @Test
    void createShelf_ShouldThrowShelfPositionNotFound() {

        Shelf shelf = new Shelf();
        shelf.setShelfName("Shelf A");
        shelf.setPartNumber("PN123");

        when(shelfRepository.createShelf(anyString(), any()))
                .thenThrow(new ShelfPositionNotFoundException("Not found"));

        assertThrows(ShelfPositionNotFoundException.class,
                () -> shelfService.createShelf("sp1", shelf));
    }

    @Test
    void createShelf_ShouldThrowShelfPositionOccupied() {

        Shelf shelf = new Shelf();
        shelf.setShelfName("Shelf A");
        shelf.setPartNumber("PN123");

        when(shelfRepository.createShelf(anyString(), any()))
                .thenThrow(new ShelfPositionOccupiedException("Occupied"));

        assertThrows(ShelfPositionOccupiedException.class,
                () -> shelfService.createShelf("sp1", shelf));
    }

    @Test
    void createShelf_ShouldThrowValidation_WhenShelfNameBlank() {

        Shelf shelf = new Shelf();
        shelf.setShelfName("");
        shelf.setPartNumber("PN123");

        assertThrows(IllegalArgumentException.class,
                () -> shelfService.createShelf("sp1", shelf));

        verify(shelfRepository, never()).createShelf(anyString(), any());
    }

    @Test
    void createShelf_ShouldThrowValidation_WhenPartNumberBlank() {

        Shelf shelf = new Shelf();
        shelf.setShelfName("Shelf A");
        shelf.setPartNumber("");

        assertThrows(IllegalArgumentException.class,
                () -> shelfService.createShelf("sp1", shelf));

        verify(shelfRepository, never()).createShelf(anyString(), any());
    }

    //testing get shelf with id

    @Test
    void getShelfById_ShouldReturnShelf_WhenExists() {

        Shelf shelf = new Shelf();
        shelf.setId("1");

        when(shelfRepository.findShelfById("1"))
                .thenReturn(Optional.of(shelf));

        Shelf result = shelfService.getShelfById("1");

        assertEquals("1", result.getId());
    }

    @Test
    void getShelfById_ShouldThrowException_WhenNotFound() {

        when(shelfRepository.findShelfById("1"))
                .thenReturn(Optional.empty());

        assertThrows(ShelfNotFoundException.class,
                () -> shelfService.getShelfById("1"));
    }

    //testing getAll shelf method

    @Test
    void getAllShelves_ShouldReturnList() {

        when(shelfRepository.findAll())
                .thenReturn(Arrays.asList(new Shelf(), new Shelf()));

        List<Shelf> result = shelfService.getAllShelves();

        assertEquals(2, result.size());
    }

    //testing delete any shelf method

    @Test
    void deleteShelf_ShouldDeleteSuccessfully_WhenExists() {

        Shelf shelf = new Shelf();
        shelf.setId("1");

        when(shelfRepository.findShelfById("1"))
                .thenReturn(Optional.of(shelf));

        shelfService.deleteShelf("1");

        verify(shelfRepository).softDelete("1");
    }

    @Test
    void deleteShelf_ShouldThrowException_WhenNotFound() {

        when(shelfRepository.findShelfById("1"))
                .thenReturn(Optional.empty());

        assertThrows(ShelfNotFoundException.class,
                () -> shelfService.deleteShelf("1"));

        verify(shelfRepository, never()).softDelete(anyString());
    }
}