package com.example.demo.exceptions;

public class InsufficientStockException extends Throwable {
    public InsufficientStockException(String s) {
        System.out.println(s);
    }
}
