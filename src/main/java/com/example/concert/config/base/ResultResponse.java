package com.example.concert.config.base;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultResponse<T> {
    private String status;
    private T data;
    private MetaResponse meta;
}
