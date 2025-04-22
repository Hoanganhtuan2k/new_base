package com.example.thanhan.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> {

    private int status;
    private String message;
    private T data;

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(200, "Success", data);
    }

    public static <T> BaseResponse<T> error(int status, String message) {
        return new BaseResponse<>(status, message, null);
    }
}