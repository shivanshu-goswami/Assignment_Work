package com.shivanshu.Assignment.dto;

public class ShelfResponseDto {

    private String id;
    private String shelfName;
    private String partNumber;

    public ShelfResponseDto(String id, String shelfName, String partNumber) {
        this.id = id;
        this.shelfName = shelfName;
        this.partNumber = partNumber;
    }

    public String getId() {
        return id;
    }

    public String getShelfName() {
        return shelfName;
    }

    public String getPartNumber() {
        return partNumber;
    }
}