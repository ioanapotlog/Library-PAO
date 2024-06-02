package model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "AUDIO_BOOKS")
@DiscriminatorValue(value = "audio")
public class AudioBook extends Book {
    @Column(name = "FORMAT")
    private String format;

    @Column(name = "DOWNLOAD_LINK")
    private String downloadLink;

    @Column(name = "NARRATOR")
    private String narrator;

    @Column(name = "LANGUAGE")
    private String language;

    @Column(name = "HOURS")
    private Integer hours;

    public AudioBook() { }

    public AudioBook(String title, String author, Integer publicationYear, String genre, String format, String downloadLink, String narrator, String language, Integer hours) {
        super(title, author, publicationYear, genre);
        this.format = format;
        this.downloadLink = downloadLink;
        this.narrator = narrator;
        this.language = language;
        this.hours = hours;
    }
}
