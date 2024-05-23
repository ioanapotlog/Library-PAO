package service;

import model.Book;
import model.PhysicalBook;
import model.User;

import java.util.Date;
import java.util.List;

public interface LibrarianService {
    void borrowBooks(User user, List<PhysicalBook> books);
    void returnBooks(User user, List<PhysicalBook> returnedBooks);
    List<PhysicalBook> getBorrowedBooksByUser(User user);
    void logBorrowTransaction(User user, List<PhysicalBook> books, Date borrowDate, Date returnDate);
    void logReturnTransaction(User user, List<PhysicalBook> returnedBooks);
}
