package com.example.dgap.dto;

public record ApiError(boolean success, String message) {
    public static ApiError of(String message) {
        return new ApiError(false, message);
    }
}
