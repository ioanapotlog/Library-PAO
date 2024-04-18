package model;

import java.util.Set;

public class PhysicalBook extends Book {
    private String location;
    private Integer availableCopyCount;
    private Integer copyCount;
    private String condition;
    private String language;
    private Integer pageCount;

    public PhysicalBook(String title, String author, Integer publicationYear, Set<String> genre, Boolean availability, String location, Integer availableCopyCount, String condition, String language, Integer pageCount) {
        super(title, author, publicationYear, genre, availability);
        this.location = location;
        this.availableCopyCount = availableCopyCount;
        this.condition = condition;
        this.language = language;
        this.pageCount = pageCount;
    }
}
