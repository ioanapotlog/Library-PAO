package model;

import java.util.Set;

public class Book {
    private String title;
    private String author;
    private Integer publicationYear;
    private Set<String> genre;
    private Boolean availability;

    public Book(String title, String author, Integer publicationYear, Set<String> genre, Boolean availability) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.genre = genre;
        this.availability = availability;
    }
}
