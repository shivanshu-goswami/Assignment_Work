package com.shivanshu.Assignment.service;

import com.shivanshu.Assignment.model.ShelfPosition;

import java.util.List;

public interface ShelfPositionService {
    ShelfPosition getById(String id);
    List<ShelfPosition> getByDeviceId(String deviceId);
    List<ShelfPosition> getAll();
    void delete(String id);
}
