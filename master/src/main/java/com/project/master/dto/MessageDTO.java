package com.project.master.dto;

public class MessageDTO {
    boolean success;
    String message;

    public MessageDTO(){

    }

    public MessageDTO(boolean success, String message) {
        this.success = success;
        this.message = message;
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

