package com.shivanshu.Assignment.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class Device {
    private String id;

    @NotBlank(message="Device name is required")
    private String deviceName;

    @NotBlank(message="Part number is required")
    private String partNumber;

    @NotBlank(message="Building name is required")
    private String buildingName;

    @NotBlank(message = "Device Type is required")
    private String deviceType;

    @Min(value=1,message="Number of shelf positions must be at least 1")
    private Integer numberOfShelfPositions;

    private Boolean isDeleted;

    public Device() {
    }

    public Device(String id, String deviceName, String partNumber, String buildingName, String deviceType, Integer numberOfShelfPositions, Boolean isDeleted) {
        this.id = id;
        this.deviceName = deviceName;
        this.partNumber = partNumber;
        this.buildingName = buildingName;
        this.deviceType = deviceType;
        this.numberOfShelfPositions = numberOfShelfPositions;
        this.isDeleted = isDeleted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public Integer getNumberOfShelfPositions() {
        return numberOfShelfPositions;
    }

    public void setNumberOfShelfPositions(int numberOfShelfPositions) {
        this.numberOfShelfPositions = numberOfShelfPositions;
    }

    public Boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
