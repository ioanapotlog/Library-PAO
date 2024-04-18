package model;

import java.util.Date;
import java.util.List;

public class Borrow extends Operation {
    private Date borrowDate;
    private Date returnDate;
    private List<Book> borrowedBooks;
    private Librarian librarian;
    private String status;

    public Borrow(User user, Date borrowDate, Date returnDate, List<Book> borrowedBooks, Librarian librarian, String status) {
        super(user);
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.borrowedBooks = borrowedBooks;
        this.librarian = librarian;
        this.status = status;
    }
}
