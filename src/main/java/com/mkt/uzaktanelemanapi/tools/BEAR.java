package com.mkt.uzaktanelemanapi.tools;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public class BEAR {
    public static final String version = "v1";

    private static HttpHeaders getHeader(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type","application/json; charset=UTF-8");
        return httpHeaders;
    }

    public static ResponseEntity notAcceptableErrorResponse(String errorMessage) {
        return bearResponse(errorMessage, HttpStatus.NOT_ACCEPTABLE, "errorMessage");
    }

    public static ResponseEntity serverErrorResponse(String errorMessage) {
        return bearResponse(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR, "errorMessage");
    }

    public static ResponseEntity successResponse(String successMessage) {
        return bearResponse(successMessage, HttpStatus.OK, "message");
    }

    public static ResponseEntity bearJSON(HashMap<String, Object> resultMap) {
        return ResponseEntity.status(HttpStatus.OK)
                .headers(BEAR.getHeader())
                .body(resultMap);
    }

    private static ResponseEntity bearResponse(String message, HttpStatus httpStatus, String prefix) {
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put(prefix, message);
        return ResponseEntity.status(httpStatus)
                .headers(BEAR.getHeader())
                .body(resultMap);
    }

}
