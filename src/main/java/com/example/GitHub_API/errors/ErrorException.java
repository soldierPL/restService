package com.example.GitHub_API.errors;

public class ErrorException extends RuntimeException {

    public ErrorException(String message){
        super(message);
    }
}
