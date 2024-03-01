package co.com.nequi.model.config;

import lombok.Getter;

@Getter
public enum ErrorCode {
    S204000("S204-000", 204, "No data found"),
    B400000("B400-000", 400, "Bad Request"),
    B400001("B400-001", 400, "Bad Request-fields required"),
    B401000("B401-000", 401, "Unauthorized"),
    B400002("B400-002", 400, "Bad Request-fields bad format"),
    B404000("B404-000", 404, "Not Found - element not found"),
    B409000("B409-000", 409, "Conflict"),
    B409001("B409-001", 409, "Conflict: User already exists"),
    B409002("B409-002", 409, "Conflict: You can't filter by id and value at the same time"),
    E500000("E500-000", 500, "Internal Server Error");


    private final String code;
    private final int status;
    private final String log;

    ErrorCode(String code, int status, String log) {
        this.code = code;
        this.status = status;
        this.log = log;
    }
}
