package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "DIGITAL_BOOKS")
public class DigitalBook extends Book {
    @Column(name = "FORMAT")
    private String format;

    @Column(name = "FILE_SIZE")
    private Double fileSize;

    @Column(name = "DOWNLOAD_LINK")
    private String downloadLink;

    @Column(name = "LANGUAGE")
    private String language;

    @Column(name = "PAGE_COUNT")
    private Integer pageCount;

    protected DigitalBook() { }

    public DigitalBook(String title, String author, Integer publicationYear, String genre, Boolean availability, String format, Double fileSize, String downloadLink, String language, Integer pageCount) {
        super(title, author, publicationYear, genre, availability);
        this.format = format;
        this.fileSize = fileSize;
        this.downloadLink = downloadLink;
        this.language = language;
        this.pageCount = pageCount;
    }
}
