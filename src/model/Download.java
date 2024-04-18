package model;

import java.util.Date;

public class Download extends Operation {
    private Date downloadDate;
    private Book downloadedBook;

    public Download(User user, Date downloadDate, Book downloadedBook) {
        super(user);
        this.downloadDate = downloadDate;
        this.downloadedBook = downloadedBook;
    }
}
