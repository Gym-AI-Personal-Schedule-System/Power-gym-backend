package com.Power_gym.backend.DTO.common;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ResponseMessage {
    private long statusCode;
    private String message;
    private Object data;
    private Date dateTime;
    private Long dataCount;
    private Long pages;

    public ResponseMessage() {
    }

    public ResponseMessage(long statusCode, String message, Object data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public ResponseMessage(long statusCode, String message, Object data, long dataCount) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        this.dataCount = dataCount;
    }
    public ResponseMessage(long statusCode, String message, Object data, long dataCount, long pages) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        this.dataCount = dataCount;
        this.pages = pages;
    }

    public ResponseMessage(long statusCode, String message, Date dateTime, long dataCount) {
        this.statusCode = statusCode;
        this.message = message;
        this.dateTime = dateTime;
        this.dataCount = dataCount;
    }

    public ResponseMessage(long statusCode, String message, Object data, Date dateTime, long dataCount) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        this.dateTime = dateTime;
        this.dataCount = dataCount;
    }

    public ResponseMessage(long statusCode) {
        this.statusCode = statusCode;
    }

    public ResponseMessage(long statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
    public ResponseMessage(String message) {
        this.message = message;
    }

}
