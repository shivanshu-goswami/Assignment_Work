package com.shivanshu.Assignment.repository;

import com.shivanshu.Assignment.dto.ShelfSummaryResponseDto;
import com.shivanshu.Assignment.model.Shelf;

import java.util.List;
import java.util.Optional;

public interface ShelfRepository {
    Shelf createShelf(String ShelfPositionId ,Shelf shelf);
    Optional<Shelf> findShelfById(String id);
    Optional<Shelf> findShelfByShelfPositionId(String shelfPositionId);
    List<Shelf> findAll();
    Shelf updateShelf(String id,Shelf shelf);
    void softDelete(String id);
    Optional<ShelfSummaryResponseDto> getShelfSummary(String shelfId);
}
