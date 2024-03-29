package com.example.demo.service;

import com.example.demo.entity.Book;
import com.example.demo.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooks() {
        return bookRepository.findAllByOrderByCreatedAtDesc();
    }

    public void addNewBook(Book book) {
        Optional<Book> bookByIsbn = bookRepository.findBookByIsbn(book.getIsbn());
        if (bookByIsbn.isPresent()) {
            throw new IllegalStateException("book already exist");
        }
        bookRepository.save(book);

    }

    @Transactional
    public void deleteBook(String bookIsbn) {
        Optional<Book> bookOptional = bookRepository.findBookByIsbn(bookIsbn);
        if (bookOptional.isEmpty()) {
            throw new IllegalStateException(
                    "book with isbn " + bookIsbn + " doesn't exist");
        }

        bookRepository.deleteByIsbn(bookIsbn);

    }

    @Transactional
    public void updateBook(String bookIsbn, Book updatedBook) {
        Optional<Book> bookOptional = bookRepository.findBookByIsbn(bookIsbn);

        if (bookOptional.isPresent()) {
            Book existingBook = bookOptional.get();
            if (updatedBook.getTitle() != null) {
                existingBook.setTitle(updatedBook.getTitle());
            }
            if (updatedBook.getAuthor() != null) {
                existingBook.setAuthor(updatedBook.getAuthor());
            }
            if (updatedBook.getPrice() != 0.0) {
                existingBook.setPrice(updatedBook.getPrice());
            }
            if (updatedBook.getStockQuantity() != 0) {
                existingBook.setStockQuantity(updatedBook.getStockQuantity());
            }
            bookRepository.save(existingBook);
        }
    }

    public Optional<Book> getBookByIsbn(String isbn) {

        return bookRepository.getBookByIsbn(isbn);
    }
}