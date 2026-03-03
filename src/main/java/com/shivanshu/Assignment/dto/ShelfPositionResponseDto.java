package com.shivanshu.Assignment.dto;

public class ShelfPositionResponseDto {

    private String id;
    private int positionNumber;
    private ShelfResponseDto shelf;

    public ShelfPositionResponseDto(String id, int positionNumber,
                                    ShelfResponseDto shelf) {
        this.id = id;
        this.positionNumber = positionNumber;
        this.shelf = shelf;
    }

    public String getId() {
        return id;
    }

    public int getPositionNumber() {
        return positionNumber;
    }

    public ShelfResponseDto getShelf() {
        return shelf;
    }
}