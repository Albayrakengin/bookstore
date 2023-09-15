package com.example.demo.exceptions;

public class BookNotFoundException extends Throwable {
    public BookNotFoundException(String s) {
        System.out.println(s);
    }
}
