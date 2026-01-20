package com.alura.literatura.domain.models;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;
    private String language;
    private Double downloadCount;

    @ManyToOne
    private Author author;

    public Book() {}

    public Book(DataBook dataBook) {
        this.title = dataBook.title();
        this.language = !dataBook.languages().isEmpty() ? dataBook.languages().get(0) : null;
        this.downloadCount = dataBook.downloadCount();
    }

    // Getters y Setters
    public void setAuthor(Author author) { this.author = author; }
    public String getTitle() { return title; }
    public String getLanguage() { return language; }
    public Double getDownloadCount() { return downloadCount; }

    @Override
    public String toString() {
        return "Libro: " + title + " | Autor: " + (author != null ? author.getName() : "N/A");
    }
}