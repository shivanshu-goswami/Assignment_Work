package com.shivanshu.Assignment.repository;

import com.shivanshu.Assignment.model.Device;

import java.util.List;
import java.util.Optional;

public interface DeviceRepository {
    Device createDevice(Device device);
    Optional<Device> findById(String id);
    List<Device> findAll();
    Device updateDevice(String id,Device device);
    void softDelete(String id);
}
