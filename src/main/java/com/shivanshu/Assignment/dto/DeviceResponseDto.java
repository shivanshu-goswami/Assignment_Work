package com.shivanshu.Assignment.dto;

import java.util.List;

public class DeviceResponseDto {

    private String id;
    private String name;
    private String partNumber;
    private String building;
    private String type;

    private List<ShelfPositionResponseDto> shelfPositions;

    public DeviceResponseDto(String id, String name, String partNumber,
                             String building, String type,
                             List<ShelfPositionResponseDto> shelfPositions) {
        this.id = id;
        this.name = name;
        this.partNumber = partNumber;
        this.building = building;
        this.type = type;
        this.shelfPositions = shelfPositions;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public String getBuilding() {
        return building;
    }

    public String getType() {
        return type;
    }

    public List<ShelfPositionResponseDto> getShelfPositions() {
        return shelfPositions;
    }
}