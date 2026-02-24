package com.shivanshu.Assignment.repository;

import com.shivanshu.Assignment.model.ShelfPosition;

import java.util.List;
import java.util.Optional;

public interface ShelfPositionRepository {
    Optional<ShelfPosition> findById(String id);
    List<ShelfPosition> findByDeviceId(String deviceId);
    List<ShelfPosition> findAll();
    void softDelete(String id);
}
