package com.example.demo.exceptions;

public class InsufficientOrderTotalException extends Throwable {
    public InsufficientOrderTotalException(String s) {
        System.out.println(s);
    }
}
