package com.example.concert.config.base;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MetaResponse {
    private int code;
    private String message;
    private String debugInfo;

    public MetaResponse(int code, String message) {
        this.code = code;
        this.message = message;
        this.debugInfo = null;
    }
}
