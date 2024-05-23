package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "AUDIO_BOOKS")
public class AudioBook extends Book {
    @Column(name = "FORMAT")
    private String format;

    @Column(name = "FILE_SIZE")
    private Double fileSize;

    @Column(name = "DOWNLOAD_LINK")
    private String downloadLink;

    @Column(name = "NARRATOR")
    private String narrator;

    @Column(name = "LANGUAGE")
    private String language;

    @Column(name = "HOURS")
    private Integer hours;

    protected AudioBook() { }

    public AudioBook(String title, String author, Integer publicationYear, String genre, Boolean availability, String format, Double fileSize, String downloadLink, String narrator, String language, Integer hours) {
        super(title, author, publicationYear, genre, availability);
        this.format = format;
        this.fileSize = fileSize;
        this.downloadLink = downloadLink;
        this.narrator = narrator;
        this.language = language;
        this.hours = hours;
    }
}
