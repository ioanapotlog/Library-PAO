package model;

import java.util.Set;

public class AudioBook extends Book {
    private String format;
    private Double fileSize;
    private String downloadLink;
    private String narrator;
    private String language;
    private Integer hours;

    public AudioBook(String title, String author, Integer publicationYear, Set<String> genre, Boolean availability, String format, Double fileSize, String downloadLink, String narrator, String language, Integer hours) {
        super(title, author, publicationYear, genre, availability);
        this.format = format;
        this.fileSize = fileSize;
        this.downloadLink = downloadLink;
        this.narrator = narrator;
        this.language = language;
        this.hours = hours;
    }
}
