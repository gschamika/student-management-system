package com.example.sms.excepton;

public class SmsProjectException extends RuntimeException{
    public SmsProjectException(String message, Throwable cause) {
        super(message, cause);
    }
}
