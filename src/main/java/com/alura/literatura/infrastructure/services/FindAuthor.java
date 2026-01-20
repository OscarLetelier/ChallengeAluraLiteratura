package com.alura.literatura.infrastructure.services;

import com.alura.literatura.application.repository.DataConverter;
import com.alura.literatura.application.service.APIConsumer;
import com.alura.literatura.domain.models.DataGutendex;
import java.util.Scanner;

public class FindAuthor {
    private final String URL_BASE = "https://gutendex.com/books/";
    private final APIConsumer consumer;
    private final DataConverter converter;
    private final Scanner scanner;

    public FindAuthor(APIConsumer consumer, DataConverter converter, Scanner scanner) {
        this.consumer = consumer;
        this.converter = converter;
        this.scanner = scanner;
    }

    public void execute() {
        System.out.println("Ingrese el nombre del autor que desea buscar:");
        var authorName = scanner.nextLine();

        var json = consumer.getData(URL_BASE + "?search=" + authorName.replace(" ", "%20"));
        var data = converter.getData(json, DataGutendex.class);

        if (data != null && data.results() != null && !data.results().isEmpty()) {
            System.out.println("\n--- Libros asociados a '" + authorName + "' ---");
            data.results().stream()
                    .limit(10)
                    .forEach(book -> System.out.println("- " + book.title()));
        } else {
            System.out.println("Autor no encontrado o sin libros registrados.");
        }
    }
}