package com.glc.managment.common.error;

import java.util.List;

public class ErrorDTO {

    private int code;

    private String message;

    private List<FieldErrorDTO> fieldErrors;

    public ErrorDTO() {
    }

    public ErrorDTO(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorDTO(int code, String message, List<FieldErrorDTO> fieldErrors) {
        this.code = code;
        this.message = message;
        this.fieldErrors = fieldErrors;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<FieldErrorDTO> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(List<FieldErrorDTO> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    @Override
    public String toString() {
        return String.format("Code: %d, Message: %s Field Errors: %s", this.code, this.message, this.fieldErrors.toString());
    }
}