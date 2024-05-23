package model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "BORROWINGS")
public class Borrow extends Operation {
    @Column(name = "BORROW_DATE")
    private Date borrowDate;

    @Column(name = "RETURN_DATE")
    private Date returnDate;

    @OneToMany
    @JoinTable(name = "BORROWED_BOOKS",
            joinColumns = @JoinColumn(name = "BORROWING_ID", referencedColumnName = "ID", nullable = false))
    private List<Book> borrowedBooks;

    @ManyToOne
    @JoinColumn(name = "LIBRARIAN_ID")
    private Librarian librarian;

    @Column(name = "STATUS")
    private String status;

    protected Borrow() { }

    public Borrow(User user, Date borrowDate, Date returnDate, List<Book> borrowedBooks, Librarian librarian, String status) {
        super(user);
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.borrowedBooks = borrowedBooks;
        this.librarian = librarian;
        this.status = status;
    }
}
