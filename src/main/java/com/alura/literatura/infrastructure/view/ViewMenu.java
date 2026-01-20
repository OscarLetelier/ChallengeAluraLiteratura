package com.alura.literatura.infrastructure.view;

import com.alura.literatura.application.repository.AuthorRepository;
import com.alura.literatura.application.repository.BookRepository;
import com.alura.literatura.application.repository.DataConverter;
import com.alura.literatura.application.service.APIConsumer;
import com.alura.literatura.infrastructure.services.FindBook;
import com.alura.literatura.infrastructure.services.ListBooksByLanguage;
import com.alura.literatura.infrastructure.services.ListLivingAuthors;

import java.util.Scanner;

public class ViewMenu {

    private final Scanner scanner = new Scanner(System.in);
    private final APIConsumer consumer = new APIConsumer();
    private final DataConverter converter = new DataConverter();

    private final FindBook findBookService;
    private final ListBooksByLanguage listBooksByLanguageService;
    private final ListLivingAuthors listLivingAuthorsService;

    // Constructor que recibe los repositorios de Spring
    public ViewMenu(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.findBookService = new FindBook(consumer, converter, scanner, bookRepository, authorRepository);
        this.listBooksByLanguageService = new ListBooksByLanguage(bookRepository, scanner);
        this.listLivingAuthorsService = new ListLivingAuthors(authorRepository, scanner);
    }

    private final String menuOptions = """
            ----------
            1. Buscar libro (Guardar en DB)
            2. Listar autores vivos en determinado año
            3. Listar libros por idioma (Base de Datos)
            
            0. Salir
            ----------
            """;

    public void start() {
        var option = -1;
        while (option != 0) {
            System.out.println(menuOptions);
            try {
                var input = scanner.nextLine();
                option = Integer.parseInt(input);

                switch (option) {
                    case 1:
                        findBookService.execute();
                        break;
                    case 2:
                        listLivingAuthorsService.execute();
                        break;
                    case 3:
                        listBooksByLanguageService.execute();
                        break;
                    case 0:
                        System.out.println("Cerrando la aplicación...");
                        break;
                    default:
                        System.out.println("Opción inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida.");
            }
        }
    }
}