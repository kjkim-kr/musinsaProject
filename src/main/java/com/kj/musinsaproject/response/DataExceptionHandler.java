package com.kj.musinsaproject.response;

import org.hibernate.exception.ConstraintViolationException;

public class DataExceptionHandler {
    private DataExceptionHandler() {}

    public static String handleDataException(Throwable cause) {
        // 연결된 FK에서 오류가 있거나 Product PK 제약에 걸렸을 때
        if (cause instanceof ConstraintViolationException constraintViolationException) {
            int errorCode = constraintViolationException.getErrorCode();
            SQLErrorCode sqlErrorCode = SQLErrorCode.of(errorCode);

            return switch (sqlErrorCode) {
                case MYSQL_PK_UNIQUE_VIOLATION,
                     POSTGRESQL_H2_PK_UNIQUE_VIOLATION,
                     SQLSERVER_PK_UNIQUE_VIOLATION,
                     ORACLE_PK_UNIQUE_VIOLATION
                        -> JsonGenerator.getErrorJsonResponse(ErrorCode.UNIQUE_KEY_VIOLATION);

                case MYSQL_FOREIGN_KEY_VIOLATION,
                     POSTGRESQL_FOREIGN_KEY_VIOLATION,
                     H2_FOREIGN_KEY_VIOLATION,
                     SQLSERVER_FOREIGN_KEY_VIOLATION,
                     ORACLE_FOREIGN_KEY_VIOLATION
                        -> JsonGenerator.getErrorJsonResponse(ErrorCode.FOREIGN_KEY_VIOLATION);

                default -> JsonGenerator.getErrorJsonResponse(ErrorCode.INTERNAL_SERVER_ERROR);
            };
        }

        return JsonGenerator.getErrorJsonResponse(ErrorCode.INTERNAL_SERVER_ERROR);
    }
}
