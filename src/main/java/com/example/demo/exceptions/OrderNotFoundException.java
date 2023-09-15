package com.example.demo.exceptions;

public class OrderNotFoundException extends Throwable {
    public OrderNotFoundException(String s) {
        System.out.println(s);
    }
}
