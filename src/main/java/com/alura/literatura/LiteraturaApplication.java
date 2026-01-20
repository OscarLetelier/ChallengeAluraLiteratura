package com.alura.literatura;

import com.alura.literatura.application.repository.AuthorRepository;
import com.alura.literatura.application.repository.BookRepository;
import com.alura.literatura.infrastructure.view.ViewMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class LiteraturaApplication implements CommandLineRunner {

	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private AuthorRepository authorRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiteraturaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ViewMenu viewMenu = new ViewMenu(bookRepository, authorRepository);
		viewMenu.start();
	}
}