package com.example.concert.model.enums;

import com.example.concert.config.exception.AppException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum PurchaseStatus {
    BOOKED("1"),
    PAID("2"),
    EXPIRED("3"),
    CANCELED("4");

    private final String value;

    private static final Map<String, PurchaseStatus> VALUE_MAP = new HashMap<>();

    // Static block to populate the map
    static {
        for (PurchaseStatus status : PurchaseStatus.values()) {
            VALUE_MAP.put(status.getValue(), status);
        }
    }

    public static PurchaseStatus fromValue(String value) {
        PurchaseStatus status = VALUE_MAP.get(value);
        if (status == null) {
            throw new AppException("Unknown status: " + value);
        }
        return status;
    }

    public static boolean isValid(String value) {
        return VALUE_MAP.containsKey(value);
    }
}
