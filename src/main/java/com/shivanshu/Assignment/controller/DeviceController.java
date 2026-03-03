package com.shivanshu.Assignment.controller;

import com.shivanshu.Assignment.dto.DeviceResponseDto;
import com.shivanshu.Assignment.model.Device;
import com.shivanshu.Assignment.service.DeviceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devices")
public class DeviceController {
    private final DeviceService deviceService;
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }
    //creating device
    @PostMapping
    public DeviceResponseDto createDevice(@Valid @RequestBody Device device) {
        return deviceService.createDevice(device);
    }
    //get all the devices
    @GetMapping
    public List<DeviceResponseDto> getAllDevices() {
        return deviceService.getAllDevices();
    }
    //getting a device using id
    @GetMapping("/{id}")
    public DeviceResponseDto getDeviceById(@PathVariable String id) {
        return deviceService.getDeviceById(id);
    }
    //update Device
    @PutMapping("/{id}")
    public DeviceResponseDto updateDevice(@PathVariable String id,@Valid @RequestBody Device device) {
        return deviceService.updateDevice(id, device);
    }
    //Deleting Device
    @DeleteMapping("/{id}")
    public String deleteDevice(@PathVariable String id) {
        deviceService.deleteDevice(id);
        return "Device Deleted Successfully";
    }
}
