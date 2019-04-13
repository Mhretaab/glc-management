package com.glc.managment.common.error;

public abstract class AbstractServiceException extends RuntimeException {

    private final int errorCode;

    public AbstractServiceException(int errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}