package model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "DOWNLOADS")
public class Download extends Operation {
    @Column(name = "DOWNLOAD_DATE")
    private Date downloadDate;

    @ManyToOne
    @JoinColumn(name = "DOWNLOADED_BOOK_ID")
    private Book downloadedBook;

    protected Download() { }

    public Download(User user, Date downloadDate, Book downloadedBook) {
        super(user);
        this.downloadDate = downloadDate;
        this.downloadedBook = downloadedBook;
    }
}
