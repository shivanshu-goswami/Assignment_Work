package com.shivanshu.Assignment.service;

import com.shivanshu.Assignment.dto.ShelfSummaryResponseDto;
import com.shivanshu.Assignment.model.Shelf;

import java.util.List;
import java.util.Optional;

public interface ShelfService {
    Shelf createShelf(String shelfPositionId,Shelf shelf);
    Shelf getShelfById(String id);
    Optional<Shelf> findShelfByShelfPositionId(String shelfPositionId);
    List<Shelf> getAllShelves();
    Shelf updateShelf(String shelfPositionId,Shelf shelf);
    void deleteShelf(String id);
    ShelfSummaryResponseDto getShelfSummary(String shelfId);
}
