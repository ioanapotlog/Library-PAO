package model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "RESERVATION")
public class Reservation extends Operation {
    @Column(name = "RESERVATION_DATE")
    private Date reservationDate;

    @ManyToOne
    @JoinColumn(name = "RESERVED_BOOK_ID")
    private Book reservedBook;

    @Column(name = "STATUS")
    private String status; // active, canceled, expired

    protected Reservation() { }

    public Reservation(User user, Date reservationDate, Book reservedBook, String status) {
        super(user);
        this.reservationDate = reservationDate;
        this.reservedBook = reservedBook;
        this.status = status;
    }
}
