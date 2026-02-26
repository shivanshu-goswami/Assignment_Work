package com.shivanshu.Assignment.controller;

import com.shivanshu.Assignment.model.ShelfPosition;
import com.shivanshu.Assignment.service.ShelfPositionService;
import com.shivanshu.Assignment.exception.DeviceNotFoundException;
import com.shivanshu.Assignment.exception.ShelfPositionNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShelfPositionControllerTest {

    @Mock
    private ShelfPositionService shelfPositionService;

    @InjectMocks
    private ShelfPositionController shelfPositionController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // testing get by id
    @Test
    void getById_ShouldReturnShelfPosition() {
        ShelfPosition sp = new ShelfPosition();
        sp.setId("1");

        when(shelfPositionService.getById("1")).thenReturn(sp);

        ShelfPosition result = shelfPositionController.getById("1");

        assertNotNull(result);
        assertEquals(sp, result);
        verify(shelfPositionService).getById("1");
    }

    // test case when id is not found
    @Test
    void getById_ShouldThrowShelfPositionNotFound() {
        when(shelfPositionService.getById("1"))
                .thenThrow(new ShelfPositionNotFoundException("Not found"));

        assertThrows(ShelfPositionNotFoundException.class,
                () -> shelfPositionController.getById("1"));

        verify(shelfPositionService).getById("1");
    }

    // test case when
    @Test
    void getByDeviceId_ShouldReturnList() {
        ShelfPosition sp = new ShelfPosition();
        sp.setId("1");

        when(shelfPositionService.getByDeviceId("10"))
                .thenReturn(List.of(sp));

        List<ShelfPosition> result =
                shelfPositionController.getByDeviceId("10");

        assertEquals(1, result.size());
        verify(shelfPositionService).getByDeviceId("10");
    }

    // test for device not found
    @Test
    void getByDeviceId_ShouldThrowDeviceNotFound() {
        when(shelfPositionService.getByDeviceId("10"))
                .thenThrow(new DeviceNotFoundException("Device not found"));

        assertThrows(DeviceNotFoundException.class,
                () -> shelfPositionController.getByDeviceId("10"));

        verify(shelfPositionService).getByDeviceId("10");
    }

    // test when get all sp is called
    @Test
    void getAll_ShouldReturnList() {
        ShelfPosition sp = new ShelfPosition();
        sp.setId("1");

        when(shelfPositionService.getAll())
                .thenReturn(List.of(sp));

        List<ShelfPosition> result =
                shelfPositionController.getAll();

        assertEquals(1, result.size());
        verify(shelfPositionService).getAll();
    }

    // test for delete sp
    @Test
    void delete_ShouldCallService() {
        doNothing().when(shelfPositionService).delete("1");

        shelfPositionController.delete("1");

        verify(shelfPositionService).delete("1");
    }

    // test for delete sp (when not found)
    @Test
    void delete_ShouldThrowShelfPositionNotFound() {
        doThrow(new ShelfPositionNotFoundException("Not found"))
                .when(shelfPositionService).delete("1");

        assertThrows(ShelfPositionNotFoundException.class,
                () -> shelfPositionController.delete("1"));

        verify(shelfPositionService).delete("1");
    }
}