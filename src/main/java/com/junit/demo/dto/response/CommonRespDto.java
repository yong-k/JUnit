package com.junit.demo.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommonRespDto<T> {
    private Integer code;   // 1(성공), -1(싪)
    private String message; // 성공/에러 메시지
    private T body;

    @Builder
    public CommonRespDto(Integer code, String message, T body) {
        this.code = code;
        this.message = message;
        this.body = body;
    }
}
