package com.shivanshu.Assignment.model;

public class ShelfPosition {
    private String id;
    private String positionNumber;
    private String deviceId;
    private boolean isDeleted;

    public ShelfPosition(){
    }

    public ShelfPosition(String id, String positionNumber, String deviceId, boolean isDeleted) {
        this.id = id;
        this.positionNumber = positionNumber;
        this.deviceId = deviceId;
        this.isDeleted = isDeleted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPositionNumber() {
        return positionNumber;
    }

    public void setPositionNumber(String positionNumber) {
        this.positionNumber = positionNumber;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
