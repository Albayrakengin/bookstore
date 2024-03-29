package com.example.demo.repository;

import com.example.demo.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    @Query("Select s FROM Book s WHERE s.isbn = ?1")
    Optional<Book> findBookByIsbn(String isbn);

    void deleteByIsbn(String isbn);

    List<Book> findAllByOrderByCreatedAtDesc();

    Optional<Book> getBookByIsbn(String isbn);

}
