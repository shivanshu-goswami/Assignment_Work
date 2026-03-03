package com.shivanshu.Assignment.controller;

import com.shivanshu.Assignment.dto.DeviceResponseDto;
import com.shivanshu.Assignment.exception.DeviceNotFoundException;
import com.shivanshu.Assignment.model.Device;
import com.shivanshu.Assignment.service.DeviceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
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
        DeviceResponseDto dto=new DeviceResponseDto(
                "1",
                "Router",
                "R-100",
                "Building",
                "Router",
                new ArrayList<>()

        );

        when(deviceService.createDevice(any(Device.class))).thenReturn(dto);

        DeviceResponseDto result = deviceController.createDevice(device);

        assertNotNull(result);
        //it only assured that controller returned something and it matches with the result
        //but it didn't checked that controller actually called service which is checked
        //by verify() below
        assertEquals(dto, result);
        //verify checks that service was called exactly one time,it checks that controller actually called the service
        //also times(1) is default so you can remove it too and it will work
        verify(deviceService, times(1)).createDevice(device);
    }

    @Test
    void getAllDevices_ShouldReturnList() {
        Device device = new Device();
        device.setId("1");
        DeviceResponseDto dto=new DeviceResponseDto(
                "1",
                "Router A",
                "R-100",
                "Building",
                "Router",
                new ArrayList<>()

        );

        when(deviceService.getAllDevices()).thenReturn(List.of(dto));

        List<DeviceResponseDto> result = deviceController.getAllDevices();

        assertEquals(List.of(dto), result);
        verify(deviceService, times(1)).getAllDevices();
    }

    @Test
    void getDeviceById_ShouldReturnDevice() {
        DeviceResponseDto dto=new DeviceResponseDto(
                "1",
                "Router A",
                "R-100",
                "Building",
                "Router",
                new ArrayList<>()

        );

        when(deviceService.getDeviceById("1")).thenReturn(dto);

        DeviceResponseDto result = deviceController.getDeviceById("1");

        assertEquals("1", result.getId());
        verify(deviceService, times(1)).getDeviceById("1");
    }
    //test case for update device
    @Test
    void updateDevice_ShouldReturnUpdatedDevice() {

        Device input = new Device();
        input.setDeviceName("Updated Router");
        DeviceResponseDto updatedDto=new DeviceResponseDto(
                "1",
                "Updated Router",
                "R-100",
                "Building",
                "Router",
                new ArrayList<>()

        );


        when(deviceService.updateDevice("1", input))
                .thenReturn(updatedDto);

        DeviceResponseDto result = deviceController.updateDevice("1", input);

        assertEquals("1", result.getId());
        assertEquals("Updated Router", result.getName());

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