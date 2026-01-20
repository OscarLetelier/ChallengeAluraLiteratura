package com.alura.literatura.infrastructure.services;

import com.alura.literatura.application.repository.AuthorRepository;
import com.alura.literatura.application.repository.BookRepository;
import com.alura.literatura.application.repository.DataConverter;
import com.alura.literatura.application.service.APIConsumer;
import com.alura.literatura.domain.models.Author;
import com.alura.literatura.domain.models.Book;
import com.alura.literatura.domain.models.DataGutendex;

import java.util.Scanner;

public class FindBook {
    private final String URL_BASE = "https://gutendex.com/books/";
    private final APIConsumer consumer;
    private final DataConverter converter;
    private final Scanner scanner;

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public FindBook(APIConsumer consumer, DataConverter converter, Scanner scanner,
                    BookRepository bookRepository, AuthorRepository authorRepository) {
        this.consumer = consumer;
        this.converter = converter;
        this.scanner = scanner;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public void execute() {
        System.out.println("Ingrese el nombre del libro que desea buscar:");
        var bookTitle = scanner.nextLine();
        var json = consumer.getData(URL_BASE + "?search=" + bookTitle.replace(" ", "%20"));
        var data = converter.getData(json, DataGutendex.class);

        if (data != null && data.results() != null && !data.results().isEmpty()) {
            var bookData = data.results().get(0);
            var authorData = bookData.authors().get(0);

            // 1. Buscamos o creamos el Autor en DB
            Author author = authorRepository.findByName(authorData.name())
                    .orElseGet(() -> authorRepository.save(new Author(authorData)));

            // 2. Creamos y guardamos el Libro
            Book book = new Book(bookData);
            book.setAuthor(author);

            try {
                bookRepository.save(book);
                System.out.println("\n--- Libro GUARDADO en Base de Datos ---");
                System.out.println(book);
                System.out.println("---------------------------------------");
            } catch (Exception e) {
                System.out.println("\nEl libro ya estaba registrado.");
            }
        } else {
            System.out.println("\nNo se encontraron libros.");
        }
    }
}