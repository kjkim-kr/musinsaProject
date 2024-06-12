package com.kj.musinsaproject.response;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNEXPECTED_ATTRIBUTE(10),
    INVALID_ARGUMENT(11),
    DELETE_FAILED(12),
    DATA_NOT_FOUND(13),

    FOREIGN_KEY_VIOLATION(100),
    UNIQUE_KEY_VIOLATION(101),

    INTERNAL_SERVER_ERROR(500),
    ;

    private final int value;

    ErrorCode(int i) {
        this.value = i;
    }
}
