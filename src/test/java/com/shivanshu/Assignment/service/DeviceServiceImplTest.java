package com.shivanshu.Assignment.service;

import com.shivanshu.Assignment.exception.DeviceNotFoundException;
import com.shivanshu.Assignment.model.Device;
import com.shivanshu.Assignment.repository.DeviceRepository;
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
class DeviceServiceImplTest {

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private DeviceServiceImpl deviceService;

    //creating device

    @Test
    void createDevice_ShouldCreateSuccessfully() {

        Device device = new Device();
        device.setDeviceName("Router");

        when(deviceRepository.createDevice(any(Device.class))).thenReturn(device);

        Device saved = deviceService.createDevice(device);

        assertNotNull(saved);
        assertFalse(saved.isDeleted());
        verify(deviceRepository, times(1)).createDevice(any(Device.class));
    }

    //fetching device by id
    @Test
    void getDeviceById_ShouldReturnDevice_WhenExists() {

        Device device = new Device();
        device.setId("123");

        when(deviceRepository.findById("123")).thenReturn(Optional.of(device));

        Device result = deviceService.getDeviceById("123");

        assertEquals("123", result.getId());
        verify(deviceRepository).findById("123");
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
        Device d2 = new Device();

        when(deviceRepository.findAll()).thenReturn(Arrays.asList(d1, d2));

        List<Device> devices = deviceService.getAllDevices();

        assertEquals(2, devices.size());
        verify(deviceRepository).findAll();
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