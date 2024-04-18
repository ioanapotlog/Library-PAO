package model;

import java.util.Set;

public class DigitalBook extends Book {
    private String format;
    private Double fileSize;
    private String downloadLink;
    private String language;
    private Integer pageCount;

    public DigitalBook(String title, String author, Integer publicationYear, Set<String> genre, Boolean availability, String format, Double fileSize, String downloadLink, String language, Integer pageCount) {
        super(title, author, publicationYear, genre, availability);
        this.format = format;
        this.fileSize = fileSize;
        this.downloadLink = downloadLink;
        this.language = language;
        this.pageCount = pageCount;
    }
}
