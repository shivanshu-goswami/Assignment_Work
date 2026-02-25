package com.shivanshu.Assignment.controller;

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
    public Device createDevice(@Valid @RequestBody Device device) {
        return deviceService.createDevice(device);
    }
    //get all the devices
    @GetMapping
    public List<Device> getAllDevices() {
        return deviceService.getAllDevices();
    }
    //getting a device using id
    @GetMapping("/{id}")
    public Device getDeviceById(@PathVariable String id) {
        return deviceService.getDeviceById(id);
    }
    //Deleting Device
    @DeleteMapping("/{id}")
    public String deleteDevice(@PathVariable String id) {
        deviceService.deleteDevice(id);
        return "Device Deleted Successfully";
    }
}
