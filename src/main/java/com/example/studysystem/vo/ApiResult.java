package com.example.studysystem.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResult<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(1, "操作成功", data);
    }

    public static <T> ApiResult<T> error(String message) {
        return new ApiResult<>(0, message, null);
    }
}
