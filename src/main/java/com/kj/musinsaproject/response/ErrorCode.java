package com.kj.musinsaproject.response;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNEXPECTED_ATTRIBUTE(10),
    INVALID_ARGUMENT(11),
    DELETE_FAILED(12),
    DATA_NOT_FOUND(13),
    BRAND_NAME_IS_DUPLICATED(100),
    ;

    private final int value;

    ErrorCode(int i) {
        this.value = i;
    }
}
