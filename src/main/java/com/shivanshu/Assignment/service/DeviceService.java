package com.shivanshu.Assignment.service;

import com.shivanshu.Assignment.model.Device;

import java.util.List;

public interface DeviceService {
    Device createDevice(Device device);
    Device getDeviceById(String id);
    List<Device> getAllDevices();
    Device updateDevice(String id, Device device);
    void deleteDevice(String id);
}
