package com.shivanshu.Assignment.service;

import com.shivanshu.Assignment.exception.DeviceNotFoundException;
import com.shivanshu.Assignment.exception.ShelfPositionNotFoundException;
import com.shivanshu.Assignment.model.ShelfPosition;
import com.shivanshu.Assignment.repository.DeviceRepository;
import com.shivanshu.Assignment.repository.ShelfPositionRepository;
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
class ShelfPositionServiceImplTest {

    @Mock
    private ShelfPositionRepository shelfPositionRepository;

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private ShelfPositionServiceImpl shelfPositionService;

    //fetching by id

    @Test
    void getById_ShouldReturnShelfPosition_WhenExists() {

        ShelfPosition sp = new ShelfPosition();
        sp.setId("sp1");

        when(shelfPositionRepository.findById("sp1"))
                .thenReturn(Optional.of(sp));

        ShelfPosition result = shelfPositionService.getById("sp1");

        assertEquals("sp1", result.getId());
        verify(shelfPositionRepository).findById("sp1");
    }

    @Test
    void getById_ShouldThrowException_WhenNotExists() {

        when(shelfPositionRepository.findById("sp1"))
                .thenReturn(Optional.empty());

        assertThrows(ShelfPositionNotFoundException.class,
                () -> shelfPositionService.getById("sp1"));

        verify(shelfPositionRepository).findById("sp1");
    }

    //fetching by deviceId

    @Test
    void getByDeviceId_ShouldReturnList_WhenDeviceExists() {

        when(deviceRepository.findById("device1"))
                .thenReturn(Optional.of(new com.shivanshu.Assignment.model.Device()));

        when(shelfPositionRepository.findByDeviceId("device1"))
                .thenReturn(Arrays.asList(new ShelfPosition(), new ShelfPosition()));

        List<ShelfPosition> result = shelfPositionService.getByDeviceId("device1");

        assertEquals(2, result.size());
        verify(deviceRepository).findById("device1");
        verify(shelfPositionRepository).findByDeviceId("device1");
    }

    @Test
    void getByDeviceId_ShouldThrowException_WhenDeviceNotFound() {

        when(deviceRepository.findById("device1"))
                .thenReturn(Optional.empty());

        assertThrows(DeviceNotFoundException.class,
                () -> shelfPositionService.getByDeviceId("device1"));

        verify(deviceRepository).findById("device1");
        verify(shelfPositionRepository, never()).findByDeviceId(anyString());
    }

   //fetching all shelf position

    @Test
    void getAll_ShouldReturnList() {

        when(shelfPositionRepository.findAll())
                .thenReturn(Arrays.asList(new ShelfPosition(), new ShelfPosition()));

        List<ShelfPosition> result = shelfPositionService.getAll();

        assertEquals(2, result.size());
        verify(shelfPositionRepository).findAll();
    }

   //checking delete on shelf position

    @Test
    void delete_ShouldDeleteSuccessfully_WhenExists() {

        ShelfPosition sp = new ShelfPosition();
        sp.setId("sp1");

        when(shelfPositionRepository.findById("sp1"))
                .thenReturn(Optional.of(sp));

        shelfPositionService.delete("sp1");

        verify(shelfPositionRepository).findById("sp1");
        verify(shelfPositionRepository).softDelete("sp1");
    }

    @Test
    void delete_ShouldThrowException_WhenNotExists() {

        when(shelfPositionRepository.findById("sp1"))
                .thenReturn(Optional.empty());

        assertThrows(ShelfPositionNotFoundException.class,
                () -> shelfPositionService.delete("sp1"));

        verify(shelfPositionRepository).findById("sp1");
        verify(shelfPositionRepository, never()).softDelete(anyString());
    }
}