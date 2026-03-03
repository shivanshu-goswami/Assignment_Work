package com.shivanshu.Assignment.dto;

public class ShelfSummaryResponseDto {

    private String id;
    private String shelfName;
    private String partNumber;

    private Integer positionNumber;
    private String deviceId;
    private String deviceName;

    public ShelfSummaryResponseDto(String id,
                                   String shelfName,
                                   String partNumber,
                                   Integer positionNumber,
                                   String deviceId,
                                   String deviceName) {
        this.id = id;
        this.shelfName = shelfName;
        this.partNumber = partNumber;
        this.positionNumber = positionNumber;
        this.deviceId = deviceId;
        this.deviceName = deviceName;
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

    public Integer getPositionNumber() {
        return positionNumber;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }
}


