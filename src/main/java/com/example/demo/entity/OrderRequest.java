package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private List<BookRequest> books;

    public List<BookRequest> getBooks() {
        return books;
    }

    public void setBooks(List<BookRequest> books) {
        this.books = books;
    }


}