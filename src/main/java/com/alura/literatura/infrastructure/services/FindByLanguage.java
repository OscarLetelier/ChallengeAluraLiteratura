package com.alura.literatura.infrastructure.services;

import com.alura.literatura.application.repository.DataConverter;
import com.alura.literatura.application.service.APIConsumer;
import com.alura.literatura.domain.models.DataGutendex;
import java.util.Scanner;

public class FindByLanguage {
    private final String URL_BASE = "https://gutendex.com/books/";
    private final APIConsumer consumer;
    private final DataConverter converter;
    private final Scanner scanner;

    public FindByLanguage(APIConsumer consumer, DataConverter converter, Scanner scanner) {
        this.consumer = consumer;
        this.converter = converter;
        this.scanner = scanner;
    }

    public void execute() {
        System.out.println("Ingrese el idioma (ej: es, en, fr, pt):");
        var language = scanner.nextLine();

        var json = consumer.getData(URL_BASE + "?languages=" + language.trim());
        var data = converter.getData(json, DataGutendex.class);

        if (data != null && data.results() != null && !data.results().isEmpty()) {
            System.out.println("\n--- Libros encontrados en '" + language + "' ---");
            data.results().stream()
                    .limit(5)
                    .forEach(book -> System.out.println("- " + book.title()));
        } else {
            System.out.println("No hay libros disponibles en ese idioma.");
        }
    }
}