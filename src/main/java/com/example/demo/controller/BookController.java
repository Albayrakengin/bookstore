package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.exceptions.BookNotFoundException;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping
    public List<Book> getBooks() {
        return bookService.getBooks();
    }

    @GetMapping("/{isbn}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Optional<Book>> getBookByIsbn(@PathVariable String isbn) throws BookNotFoundException {
        Optional<Book> book = bookService.getBookByIsbn(isbn);

        if (book.isPresent()) {
            return ResponseEntity.ok(book);
        } else {
            throw new BookNotFoundException("Book not found with ISBN: " + isbn);
        }
    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public void registerNewBook(@RequestBody Book book){
        bookService.addNewBook(book);
    }

    @DeleteMapping(path = "{bookIsbn}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteBook(@PathVariable("bookIsbn") String bookIsbn){
        bookService.deleteBook(bookIsbn);
    }

    @PutMapping(path = "{bookIsbn}")
    @PreAuthorize("hasRole('ADMIN')")
    public void updateBook(
            @PathVariable String bookIsbn,
            @RequestBody Book updatedBook
    ){
        //add expection later on
        bookService.updateBook(bookIsbn, updatedBook);
    }

}
