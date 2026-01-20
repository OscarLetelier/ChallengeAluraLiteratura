package com.alura.literatura.infrastructure.services;

import com.alura.literatura.application.repository.BookRepository;
import java.util.Scanner;

public class ListBooksByLanguage {
    private final BookRepository repository;
    private final Scanner scanner;

    public ListBooksByLanguage(BookRepository repository, Scanner scanner) {
        this.repository = repository;
        this.scanner = scanner;
    }

    public void execute() {
        System.out.println("Ingrese el idioma (es, en, fr, pt):");
        var language = scanner.nextLine();
        var books = repository.findByLanguage(language);

        if (books.isEmpty()) {
            System.out.println("No hay libros registrados en ese idioma.");
        } else {
            System.out.println("\n--- Libros en '" + language + "' ---");
            System.out.println("Cantidad total: " + books.size());
            books.forEach(System.out::println);
        }
    }
}