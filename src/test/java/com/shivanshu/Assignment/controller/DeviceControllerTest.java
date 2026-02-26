package com.shivanshu.Assignment.controller;

import com.shivanshu.Assignment.exception.DeviceNotFoundException;
import com.shivanshu.Assignment.model.Device;
import com.shivanshu.Assignment.service.DeviceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeviceControllerTest {

    @Mock
    private DeviceService deviceService;

    @InjectMocks
    private DeviceController deviceController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createDevice_ShouldReturnDevice() {
        Device device = new Device();
        device.setId("1");
        device.setDeviceName("Router");

        when(deviceService.createDevice(any(Device.class))).thenReturn(device);

        Device result = deviceController.createDevice(device);

        assertNotNull(result);
        //it only assured that controller returned something and it matches with the result
        //but it didn't checked that controller actually called service which is checked
        //by verify() below
        assertEquals(device, result);
        //verify checks that service was called exactly one time,it checks that controller actually called the service
        //also times(1) is default so you can remove it too and it will work
        verify(deviceService, times(1)).createDevice(device);
    }

    @Test
    void getAllDevices_ShouldReturnList() {
        Device device = new Device();
        device.setId("1");

        when(deviceService.getAllDevices()).thenReturn(List.of(device));

        List<Device> result = deviceController.getAllDevices();

        assertEquals(List.of(device), result);
        verify(deviceService, times(1)).getAllDevices();
    }

    @Test
    void getDeviceById_ShouldReturnDevice() {
        Device device = new Device();
        device.setId("1");

        when(deviceService.getDeviceById("1")).thenReturn(device);

        Device result = deviceController.getDeviceById("1");

        assertEquals("1", result.getId());
        verify(deviceService, times(1)).getDeviceById("1");
    }
    //test case for update device
    @Test
    void updateDevice_ShouldReturnUpdatedDevice() {

        Device input = new Device();
        input.setDeviceName("Updated Router");

        Device updated = new Device();
        updated.setId("1");
        updated.setDeviceName("Updated Router");

        when(deviceService.updateDevice("1", input))
                .thenReturn(updated);

        Device result = deviceController.updateDevice("1", input);

        assertEquals("1", result.getId());
        assertEquals("Updated Router", result.getDeviceName());

        verify(deviceService).updateDevice("1", input);
    }
    //exception for update device
    @Test
    void updateDevice_ShouldThrowException_WhenNotFound() {

        Device input = new Device();
        input.setDeviceName("Updated Router");

        when(deviceService.updateDevice("1", input))
                .thenThrow(new DeviceNotFoundException("Device not found"));

        assertThrows(DeviceNotFoundException.class,
                () -> deviceController.updateDevice("1", input));

        verify(deviceService).updateDevice("1", input);
    }

    @Test
    void deleteDevice_ShouldReturnSuccessMessage() {
        doNothing().when(deviceService).deleteDevice("1");

        String result = deviceController.deleteDevice("1");

        assertEquals("Device Deleted Successfully", result);
        verify(deviceService, times(1)).deleteDevice("1");
    }
}