package com.kj.musinsaproject.response;

import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum SQLErrorCode {
    DEFAULT_VALUE(0),

    MYSQL_PK_UNIQUE_VIOLATION(1062),
    POSTGRESQL_H2_PK_UNIQUE_VIOLATION(23505),   // POSTGRE, H2 23505로 값이 같음
    SQLSERVER_PK_UNIQUE_VIOLATION(2627),
    ORACLE_PK_UNIQUE_VIOLATION(1),

    MYSQL_FOREIGN_KEY_VIOLATION(1452),
    POSTGRESQL_FOREIGN_KEY_VIOLATION(23503),
    H2_FOREIGN_KEY_VIOLATION(23506),
    SQLSERVER_FOREIGN_KEY_VIOLATION(547),
    ORACLE_FOREIGN_KEY_VIOLATION(2291),
    ;

    private final int value;
    private static final Map<Integer, String> CODE_MAP =
            Collections.unmodifiableMap(
                    Stream.of(values())
                            .collect(
                                    Collectors.toMap(
                                            SQLErrorCode::getValue,
                                            SQLErrorCode::name
                                    )
                            )
            );

    SQLErrorCode(int i) {
        this.value = i;
    }

    public static SQLErrorCode of(int i) {
        return SQLErrorCode.valueOf(
                CODE_MAP.getOrDefault(i, SQLErrorCode.DEFAULT_VALUE.name())
        );
    }
}
