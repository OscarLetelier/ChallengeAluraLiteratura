package com.alura.literatura.infrastructure.services;

import com.alura.literatura.application.repository.AuthorRepository;
import java.util.Scanner;

public class ListLivingAuthors {
    private final AuthorRepository repository;
    private final Scanner scanner;

    public ListLivingAuthors(AuthorRepository repository, Scanner scanner) {
        this.repository = repository;
        this.scanner = scanner;
    }

    public void execute() {
        System.out.println("Ingrese el año límite para buscar autores vivos:");
        try {
            int year = Integer.parseInt(scanner.nextLine());
            var authors = repository.findAuthorsAliveInYear(year);

            if (authors.isEmpty()) {
                System.out.println("No se encontraron autores vivos en ese año.");
            } else {
                System.out.println("\n--- Autores Vivos en " + year + " ---");
                authors.forEach(System.out::println);
            }
        } catch (NumberFormatException e) {
            System.out.println("Año inválido.");
        }
    }
}