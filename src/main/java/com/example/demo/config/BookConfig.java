package com.example.demo.config;

import com.example.demo.entity.Book;
import com.example.demo.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class BookConfig {



    @Bean
    CommandLineRunner commandLineRunner(BookRepository repository){
        return args -> {

            Book Fildisi = new Book(
                    "1000",
                    "Fildisi",
                    "Ferhat Gocer",
                    10,
                    31
            );
            Book Mildisi = new Book(
                    "1001",
                    "Mildisi",
                    "Zombozo Bekir",
                    12,
                    23
            );

            repository.saveAll(
                    List.of(Fildisi, Mildisi)
            );
        };
    };
}
