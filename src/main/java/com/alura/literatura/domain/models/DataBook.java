package com.alura.literatura.domain.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataBook(
        @JsonAlias("title") String title,
        @JsonAlias("authors") List<DataAuthor> authors, // Debe coincidir con el JSON array
        @JsonAlias("languages") List<String> languages,
        @JsonAlias("download_count") Double downloadCount
) {
}