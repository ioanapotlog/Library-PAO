package model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "DIGITAL_BOOKS")
@DiscriminatorValue(value = "digital")
public class DigitalBook extends Book {
    @Column(name = "FORMAT")
    private String format;

    @Column(name = "DOWNLOAD_LINK")
    private String downloadLink;

    @Column(name = "LANGUAGE")
    private String language;

    @Column(name = "PAGE_COUNT")
    private Integer pageCount;

    protected DigitalBook() { }

    public DigitalBook(String title, String author, Integer publicationYear, String genre, String format, String downloadLink, String language, Integer pageCount) {
        super(title, author, publicationYear, genre);
        this.format = format;
        this.downloadLink = downloadLink;
        this.language = language;
        this.pageCount = pageCount;
    }
}
