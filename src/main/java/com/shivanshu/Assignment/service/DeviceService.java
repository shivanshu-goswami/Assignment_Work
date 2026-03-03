package com.shivanshu.Assignment.service;

import com.shivanshu.Assignment.dto.DeviceResponseDto;
import com.shivanshu.Assignment.model.Device;

import java.util.List;

public interface DeviceService {
    DeviceResponseDto createDevice(Device device);
    DeviceResponseDto getDeviceById(String id);
    List<DeviceResponseDto> getAllDevices();
    DeviceResponseDto updateDevice(String id, Device device);
    void deleteDevice(String id);
}
