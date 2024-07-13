package com.example.BookHeaven.Utils;

import java.util.Optional;

public class ResponseMessage<T> {
    private boolean success;
    private String message;
    private Optional<T> data;

    public ResponseMessage(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.data = Optional.empty();
    }
    
    public ResponseMessage(boolean success, String message,T data) {
        this.success = success;
        this.message = message;
        this.data = Optional.of(data);
    }

    // Getters and setters
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

	public Optional<T> getData() {
		return data;
	}

	public void setData(Optional<T> data) {
		this.data = data;
	}
}
