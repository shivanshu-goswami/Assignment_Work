package com.shivanshu.Assignment.service;

import com.shivanshu.Assignment.dto.DeviceResponseDto;
import com.shivanshu.Assignment.exception.DeviceNotFoundException;
import com.shivanshu.Assignment.model.Device;
import com.shivanshu.Assignment.repository.DeviceRepository;
import com.shivanshu.Assignment.repository.ShelfPositionRepository;
import com.shivanshu.Assignment.repository.ShelfRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeviceServiceImplTest {

    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private ShelfPositionRepository shelfPositionRepository;

    @Mock
    private ShelfRepository shelfRepository;

    @InjectMocks
    private DeviceServiceImpl deviceService;

    //creating device

    @Test
    void createDevice_ShouldCreateSuccessfully() {

        Device savedDevice = new Device();
        savedDevice.setId("generated-id");
        savedDevice.setDeviceName("Router");

        // when saving device
        when(deviceRepository.createDevice(any(Device.class)))
                .thenReturn(savedDevice);

        // IMPORTANT: do NOT hardcode ID
        when(deviceRepository.findById(anyString()))
                .thenReturn(Optional.of(savedDevice));

        // mock shelf positions
        when(shelfPositionRepository.findByDeviceId(anyString()))
                .thenReturn(Collections.emptyList());

        Device input = new Device();
        input.setDeviceName("Router");

        DeviceResponseDto response = deviceService.createDevice(input);

        assertNotNull(response);
        assertEquals("Router", response.getName());
    }
    //fetching device by id
    @Test
    void getDeviceById_ShouldReturnDevice_WhenExists() {

        Device device = new Device();
        device.setId("1");
        device.setDeviceName("Router");
        device.setPartNumber("R-100");
        device.setBuildingName("Building 1");
        device.setDeviceType("Router");

        when(deviceRepository.findById("1"))
                .thenReturn(Optional.of(device));

        when(shelfPositionRepository.findByDeviceId("1"))
                .thenReturn(Collections.emptyList());

        DeviceResponseDto result =
                deviceService.getDeviceById("1");

        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("Router", result.getName());

        verify(deviceRepository).findById("1");
    }


    @Test
    void getDeviceById_ShouldThrowException_WhenNotFound() {

        when(deviceRepository.findById("123")).thenReturn(Optional.empty());

        assertThrows(DeviceNotFoundException.class,
                () -> deviceService.getDeviceById("123"));

        verify(deviceRepository).findById("123");
    }

    //fetching all devices

    @Test
    void getAllDevices_ShouldReturnList() {

        Device d1 = new Device();
        d1.setId("1");
        d1.setDeviceName("Router");

        Device d2 = new Device();
        d2.setId("2");
        d2.setDeviceName("Switch");

        when(deviceRepository.findAll())
                .thenReturn(Arrays.asList(d1, d2));

        when(deviceRepository.findById("1"))
                .thenReturn(Optional.of(d1));
        when(deviceRepository.findById("2"))
                .thenReturn(Optional.of(d2));

        when(shelfPositionRepository.findByDeviceId(anyString()))
                .thenReturn(Collections.emptyList());

        List<DeviceResponseDto> result =
                deviceService.getAllDevices();

        assertEquals(2, result.size());

        verify(deviceRepository).findAll();
    }

    //writing test for update device method

    @Test
    void updateDevice_ShouldReturnUpdatedDevice() {

        Device existing = new Device();
        existing.setId("1");
        existing.setDeviceName("Old Router");

        Device input = new Device();
        input.setDeviceName("Updated Router");

        Device updated = new Device();
        updated.setId("1");
        updated.setDeviceName("Updated Router");

        when(deviceRepository.findById("1"))
                .thenReturn(Optional.of(updated));

        when(deviceRepository.updateDevice("1", input))
                .thenReturn(updated);

        when(shelfPositionRepository.findByDeviceId("1"))
                .thenReturn(Collections.emptyList());

        DeviceResponseDto response = deviceService.updateDevice("1", input);

        assertEquals("1", response.getId());
        assertEquals("Updated Router", response.getName());
    }

    @Test
    void updateDevice_ShouldThrowException_WhenDeviceNotFound() {

        Device input = new Device();

        when(deviceRepository.findById("1"))
                .thenReturn(Optional.empty());

        assertThrows(DeviceNotFoundException.class,
                () -> deviceService.updateDevice("1", input));

        verify(deviceRepository).findById("1");
        verify(deviceRepository, never()).updateDevice(anyString(), any());
    }


    //deleting device

    @Test
    void deleteDevice_ShouldDeleteSuccessfully() {

        Device device = new Device();
        device.setId("123");

        when(deviceRepository.findById("123")).thenReturn(Optional.of(device));

        deviceService.deleteDevice("123");

        verify(deviceRepository).findById("123");
        verify(deviceRepository).softDelete("123");
    }

    @Test
    void deleteDevice_ShouldThrowException_WhenNotFound() {

        when(deviceRepository.findById("123")).thenReturn(Optional.empty());

        assertThrows(DeviceNotFoundException.class,
                () -> deviceService.deleteDevice("123"));

        verify(deviceRepository).findById("123");
        verify(deviceRepository, never()).softDelete(anyString());
    }
}