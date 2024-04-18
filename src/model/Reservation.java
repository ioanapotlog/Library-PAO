package model;

import java.util.Date;

public class Reservation extends Operation {
    private Date reservationDate;
    private Book reservedBook;
    private String status; // active, canceled, expired

    public Reservation(User user, Date reservationDate, Book reservedBook, String status) {
        super(user);
        this.reservationDate = reservationDate;
        this.reservedBook = reservedBook;
        this.status = status;
    }
}
