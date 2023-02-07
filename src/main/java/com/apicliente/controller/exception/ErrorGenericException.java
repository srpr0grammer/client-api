package com.apicliente.controller.exception;

public class ErrorGenericException extends RuntimeException {
    public ErrorGenericException(String msg){
        super(msg);
    }
}
