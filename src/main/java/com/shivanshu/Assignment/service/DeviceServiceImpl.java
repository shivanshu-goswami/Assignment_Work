package com.shivanshu.Assignment.service;

import com.shivanshu.Assignment.exception.DeviceNotFoundException;
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
        return deviceRepository.findById(id).orElseThrow(()->new DeviceNotFoundException("Device not found with id "+id));
    }

    @Override
    public List<Device> getAllDevices(){
        return deviceRepository.findAll();
    }
    @Override
    public Device updateDevice(String id, Device device){
        //it will throw error if device with this id doesn't exist so exceptio handled here
        getDeviceById(id);
        //now calling this is safe and we don't need to throw anything or return null as it wouldnt' have been called if device with given id doesnt exist
        return deviceRepository.updateDevice(id, device);
    }

    @Override
    public void deleteDevice(String id){
        Device device=deviceRepository.findById(id).orElseThrow(()->new DeviceNotFoundException("Device not found with id "+id));
        deviceRepository.softDelete(device.getId());
    }
}
