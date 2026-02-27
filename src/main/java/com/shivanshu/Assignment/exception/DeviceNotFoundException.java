package com.shivanshu.Assignment.exception;

public class DeviceNotFoundException extends RuntimeException{
    public DeviceNotFoundException(String message){
        super(message);
    }
}