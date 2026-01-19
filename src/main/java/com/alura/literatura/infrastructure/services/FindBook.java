package com.alura.literatura.infrastructure.services;

import com.alura.literatura.application.repository.DataConverter;
import com.alura.literatura.application.service.APIConsumer;
import com.alura.literatura.domain.models.DataGutendex;
import java.util.Scanner;

public class FindBook {
    private final String URL_BASE = "https://gutendex.com/books/";
    private final APIConsumer consumer;
    private final DataConverter converter;
    private final Scanner scanner;

    // Inyección de dependencias por constructor
    public FindBook(APIConsumer consumer, DataConverter converter, Scanner scanner) {
        this.consumer = consumer;
        this.converter = converter;
        this.scanner = scanner;
    }

    public void execute() {
        System.out.println("Ingrese el nombre del libro que desea buscar:");
        var nameTitle = scanner.nextLine();

        var json = consumer.getData(URL_BASE + "?search=" + nameTitle.replace(" ", "%20"));
        var data = converter.getData(json, DataGutendex.class);

        if (data != null && data.results() != null && !data.results().isEmpty()) {
            System.out.println("\n--- Libros Encontrados ---");
            data.results().stream()
                    .limit(5)
                    .forEach(libro -> {
                        System.out.println("Título: " + libro.title());
                        if (!libro.authors().isEmpty()) {
                            System.out.println("Autor: " + libro.authors().get(0).name());
                        } else {
                            System.out.println("Autor: Desconocido");
                        }
                        if (!libro.languages().isEmpty()) {
                            System.out.println("Idioma: " + libro.languages().get(0));
                        }
                        System.out.println("Descargas: " + libro.downloadCount());
                        System.out.println("--------------------------");
                    });
        } else {
            System.out.println("\nNo se encontraron libros con ese nombre.");
        }
    }
}