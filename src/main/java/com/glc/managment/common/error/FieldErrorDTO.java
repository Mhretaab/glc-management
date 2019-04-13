package com.glc.managment.common.error;

public class FieldErrorDTO {

    private String field;
    private String message;

    public FieldErrorDTO() {
    }

    public FieldErrorDTO(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("Field: %s, Message: %s", this.field, this.message);
    }
}