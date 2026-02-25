package com.shivanshu.Assignment.service;

import com.shivanshu.Assignment.exception.DeviceNotFoundException;
import com.shivanshu.Assignment.exception.ShelfPositionNotFoundException;
import com.shivanshu.Assignment.model.ShelfPosition;
import com.shivanshu.Assignment.repository.DeviceRepository;
import com.shivanshu.Assignment.repository.ShelfPositionRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ShelfPositionServiceImpl implements ShelfPositionService {
    private final ShelfPositionRepository shelfPositionRepository;
    //so that we can check exception of getDeviceById where we check first if device exist
    private final DeviceRepository deviceRepository;

    public ShelfPositionServiceImpl(ShelfPositionRepository shelfPositionRepository, DeviceRepository deviceRepository) {
        this.shelfPositionRepository = shelfPositionRepository;
        this.deviceRepository = deviceRepository;
    }

    public ShelfPosition getById(String id) {
        return shelfPositionRepository.findById(id).orElseThrow(()->new ShelfPositionNotFoundException("Shelf Position not found with id "+id));
    }

    public List<ShelfPosition> getByDeviceId(String deviceId) {
        deviceRepository.findById(deviceId).orElseThrow(()->new DeviceNotFoundException("Device not found with id "+deviceId));
        return shelfPositionRepository.findByDeviceId(deviceId);
    }

    public List<ShelfPosition> getAll() {
        return shelfPositionRepository.findAll();
    }
    public void delete(String id){
        //just checking existence so that error get automatically handled
        getById(id);
        shelfPositionRepository.softDelete(id);
    }
}
