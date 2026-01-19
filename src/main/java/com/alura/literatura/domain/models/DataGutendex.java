package com.alura.literatura.domain.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataGutendex(
        @JsonAlias("count") Integer count,
        @JsonAlias("results") List<DataBook> results
) {
}
