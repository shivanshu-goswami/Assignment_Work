package com.shivanshu.Assignment.service;

import com.shivanshu.Assignment.exception.DeviceNoTFoundException;
import com.shivanshu.Assignment.model.Device;
import com.shivanshu.Assignment.repository.DeviceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DeviceServiceImpl implements DeviceService{
    private final DeviceRepository deviceRepository;
    public DeviceServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }
    @Override
    public Device createDevice(Device device){
        device.setId(UUID.randomUUID().toString());
        device.setDeleted(false);
        return deviceRepository.createDevice(device);
    }

    @Override
    public Device getDeviceById(String id){
        return deviceRepository.findById(id).orElseThrow(()->new DeviceNoTFoundException("Device not found with id "+id));
    }

    @Override
    public List<Device> getAllDevices(){
        return deviceRepository.findAll();
    }

    @Override
    public void deleteDevice(String id){
        Device device=deviceRepository.findById(id).orElseThrow(()->new DeviceNoTFoundException("Device not found with id "+id));
        deviceRepository.softDelete(device.getId());
    }
}
