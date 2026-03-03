package com.shivanshu.Assignment.service;

import com.shivanshu.Assignment.dto.DeviceResponseDto;
import com.shivanshu.Assignment.dto.ShelfPositionResponseDto;
import com.shivanshu.Assignment.dto.ShelfResponseDto;
import com.shivanshu.Assignment.exception.DeviceNotFoundException;
import com.shivanshu.Assignment.model.Device;
import com.shivanshu.Assignment.model.Shelf;
import com.shivanshu.Assignment.model.ShelfPosition;
import com.shivanshu.Assignment.repository.DeviceRepository;
import com.shivanshu.Assignment.repository.ShelfPositionRepository;
import com.shivanshu.Assignment.repository.ShelfRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DeviceServiceImpl implements DeviceService{
    private final DeviceRepository deviceRepository;
    private final ShelfPositionRepository shelfPositionRepository;
    private final ShelfRepository shelfRepository;
    public DeviceServiceImpl(DeviceRepository deviceRepository, ShelfPositionRepository shelfPositionRepository, ShelfRepository shelfRepository) {
        this.deviceRepository = deviceRepository;
        this.shelfPositionRepository = shelfPositionRepository;
        this.shelfRepository = shelfRepository;
    }
    @Override
    public DeviceResponseDto createDevice(Device device){
        device.setId(UUID.randomUUID().toString());
        device.setDeleted(false);
        Device newDevice= deviceRepository.createDevice(device);
        return getDeviceById(newDevice.getId());
    }

//    @Override
//    public DeviceResponseDto getDeviceById(String id){
//        return deviceRepository.findById(id).orElseThrow(()->new DeviceNotFoundException("Device not found with id "+id));
//    }
@Override
public DeviceResponseDto getDeviceById(String id) {

    //  Get device entity
    Device device = deviceRepository.findById(id)
            .orElseThrow(() -> new DeviceNotFoundException("Device not found with id: " + id));

    //  Get shelf positions of this device
    List<ShelfPosition> shelfPositions =
            shelfPositionRepository.findByDeviceId(id);

    List<ShelfPositionResponseDto> shelfPositionDtos = new ArrayList<>();

    for (ShelfPosition sp : shelfPositions) {

        //  Get shelf related to this position (if exists)
        Optional<Shelf> shelfOptional =
                shelfRepository.findShelfByShelfPositionId(sp.getId());

        ShelfResponseDto shelfDto = null;

        if (shelfOptional.isPresent()) {
            Shelf shelf = shelfOptional.get();

            shelfDto = new ShelfResponseDto(
                    shelf.getId(),
                    shelf.getShelfName(),
                    shelf.getPartNumber()
            );
        }

        ShelfPositionResponseDto spDto =
                new ShelfPositionResponseDto(
                        sp.getId(),
                        sp.getPositionNumber(),
                        shelfDto
                );

        shelfPositionDtos.add(spDto);
    }

    //  Return final device DTO
    return new DeviceResponseDto(
            device.getId(),
            device.getDeviceName(),
            device.getPartNumber(),
            device.getBuildingName(),
            device.getDeviceType(),
            shelfPositionDtos
    );
}

    @Override
    public List<DeviceResponseDto> getAllDevices(){
       List<Device> devices = deviceRepository.findAll();
       List<DeviceResponseDto> dtos = new ArrayList<>();
       for (Device device : devices) {
           dtos.add(getDeviceById(device.getId()));
       }
       return dtos;
    }
    @Override
    public DeviceResponseDto updateDevice(String id, Device device){
        //it will throw error if device with this id doesn't exist so exceptio handled here
        getDeviceById(id);
        //now calling this is safe and we don't need to throw anything or return null as it wouldnt' have been called if device with given id doesnt exist
        Device updatedDevice= deviceRepository.updateDevice(id, device);
        return getDeviceById(updatedDevice.getId());
    }

    @Override
    public void deleteDevice(String id){
        Device device=deviceRepository.findById(id).orElseThrow(()->new DeviceNotFoundException("Device not found with id "+id));
        deviceRepository.softDelete(device.getId());
    }
}
