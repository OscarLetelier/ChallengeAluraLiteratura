package com.alura.literatura.infrastructure.view;

import com.alura.literatura.application.repository.DataConverter;
import com.alura.literatura.application.service.APIConsumer;
import com.alura.literatura.domain.models.DataGutendex;

import java.util.Scanner;

public class ViewMenu {

    public static final String URL_BASE = "https://gutendex.com/books/";

    private APIConsumer consumer = new APIConsumer();
    private DataConverter converter = new DataConverter();
    private Scanner scanner = new Scanner(System.in);

    private String menuOptions = """
            ----------
            Seleccione una opcion:
            
            1. Buscar por Nombre de Libro
            2. Buscar por Nombre de Autor
            3. Mostrar Listado de Libros por Idioma
            
            0. Salir
            ----------
            """;

    public void start() {
        var opcion = -1;
        while (opcion != 0) {
            System.out.println(menuOptions);
            try {
                var input = scanner.nextLine();
                opcion = Integer.parseInt(input);

                switch (opcion) {
                    case 1:
                        findBook();
                        break;
                    case 2:
                        findAuthor();
                        break;
                    case 3:
                        findByLanguage();
                        break;
                    case 0:
                        System.out.println("Cerrando la aplicación...");
                        break;
                    default:
                        System.out.println("Opción inválida, intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
            }
        }
    }

    private void findBook() {
        System.out.println("Ingrese el nombre del libro que desea buscar:");
        var nameTitle = scanner.nextLine();
        var json = consumer.getData(URL_BASE + "?search=" + nameTitle.replace(" ", "%20"));
        var data = converter.getData(json, DataGutendex.class);

        // CORRECCIÓN: Usamos .results() en lugar de .resultados()
        if (data != null && data.results() != null && !data.results().isEmpty()) {
            System.out.println("\n--- Libros Encontrados ---");
            data.results().stream()
                    .limit(5)
                    .forEach(libro -> {
                        // CORRECCIÓN: Usamos .title() en lugar de .titulo()
                        System.out.println("Título: " + libro.title());

                        // CORRECCIÓN: Verificamos si la lista de autores está vacía
                        if (!libro.authors().isEmpty()) {
                            System.out.println("Autor: " + libro.authors().get(0).name());
                        } else {
                            System.out.println("Autor: Desconocido");
                        }

                        // CORRECCIÓN: Usamos .languages() (plural) si aplicaste el cambio de lista
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

    private void findAuthor() {
        System.out.println("Ingrese el nombre del autor que desea buscar:");
        var nombreAutor = scanner.nextLine();
        var json = consumer.getData(URL_BASE + "?search=" + nombreAutor.replace(" ", "%20"));
        var datos = converter.getData(json, DataGutendex.class);

        // CORRECCIÓN: Cambiado .resultados() a .results() y .titulo() a .title()
        if (datos != null && !datos.results().isEmpty()) {
            System.out.println("\n--- Libros asociados a '" + nombreAutor + "' ---");
            datos.results().forEach(l -> System.out.println(l.title()));
        } else {
            System.out.println("Autor no encontrado.");
        }
    }

    private void findByLanguage() {
        System.out.println("Ingrese el idioma (es, en, fr, pt):");
        var idioma = scanner.nextLine();
        var json = consumer.getData(URL_BASE + "?languages=" + idioma);
        var datos = converter.getData(json, DataGutendex.class);

        // CORRECCIÓN: Cambiado .resultados() a .results() y .titulo() a .title()
        if (datos != null && !datos.results().isEmpty()) {
            datos.results().stream().limit(5).forEach(l -> System.out.println(l.title()));
        } else {
            System.out.println("No hay libros en ese idioma.");
        }
    }
}